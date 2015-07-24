package nlg;

import java.util.ArrayList;
import java.util.List;

import messages.DiveEvaluation;
import messages.DiveletAscentRateEvaluationMessage;
import messages.DiveletDepthWarningMessage;
import messages.DiveletExcessDepthTimeMessage;
import messages.DiveletMessage;
import messages.Message;
import messages.MultipleDiveWarningMessage;
import messages.SafeAscentRateMessage;
import messages.SafeBottomTimeMessage;
import messages.SafeDiveDepthMessage;
import messages.SecondDiveletDeeperMessage;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;

public class DivePhraseFactory {
	
	public static NLGElement getAscentRateDescription(Message evaluation) {
		DiveletAscentRateEvaluationMessage eval;
		if ((eval = getTypedMessage(evaluation,
				DiveletAscentRateEvaluationMessage.class)) != null) {
			
			NPPhraseSpec ascentRate = NLGUtils.getFactory().createNounPhrase(
					"ascent rate");
			ascentRate.setDeterminer("your");
			
			SPhraseSpec sps = NLGUtils.getFactory().createClause();
			
			sps.setVerb("be");
			
			String description = "";
			
			String ascentRateString = "" + (int) (eval.getAscentRate() + 0.5);
			
			switch (eval.getEvaluation()) {
				case SLOW: // < 1.4 m/min
					// add ascentRate precise value to the subject:
					// "Your slow ascent rate of around 1m/min"
					ascentRate.addModifier("slow");
					ascentRate.addComplement("of around " + ascentRateString/*
																			 * rounded
																			 * !
																			 */
							+ "m/min");
					// IDEALLY:
					// Change verb and object
					sps.setVerb("help");
					sps.setObject("you");
					// Change description (complement)
					description = "to degas before you ascended to the surface";
					
					// IF the anterior is not possible, then :
					// String description =
					// "good, as it should have helped you to "
					// + "degas before you ascended to the surface";
					break;
				case FINE: // < 4 m/min
					description = "fine";
					break;
				case CLOSE: // < 6 m/min
					description = "close to the PADI safe ascent rate limit of 6m/min";
					break;
				case A_BIT_FASTER: // < 7 m/min
					description = "a little faster than the PADI recommended safe speed of 6m/min";
					break;
				case ACCEPTABLE: // < 10 m/min
					description = "acceptable";
					// add ascentRate precise value to the subject:
					// "Your ascent rate of around 9m/min"
					ascentRate.addComplement("of around " + ascentRateString /*
																			 * rounded
																			 * !
																			 */
							+ "m/min");
					// we should also add this complete sentence after the
					// clause :
					String addWarning = "But you could have entirely avoided the "
							+ "risk of micro-bubbles by ascending slower than PADI "
							+ "recommended safe ascent rate of 6m/min.";
					break;
				default:
					break;
			}
			
			AdjPhraseSpec adjSpec = NLGUtils.getFactory()
					.createAdjectivePhrase(description);
			
			sps.setSubject(ascentRate);
			sps.setComplement(adjSpec);
			
			return sps;
		}
		return null;
	}
	
	public static NLGElement generateMultiplDiveletWarning(Message message) {
		
		MultipleDiveWarningMessage msg;
		
		if ((msg = getTypedMessage(message, MultipleDiveWarningMessage.class)) != null) {
			
			NPPhraseSpec divelets = NLGUtils.getFactory().createNounPhrase(
					"dive");
			divelets.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
			
			divelets.setDeterminer("two");
			
			// for each divelet, create a variable with its referring expression
			NPPhraseSpec divelet1 = NLGUtils.getFactory().createNounPhrase(
					"divelet");
			divelet1.addModifier("first");
			
			// create sequenceDivelets sentence
			
			SPhraseSpec sequenceDivelets = NLGUtils.getFactory().createClause();
			
			sequenceDivelets.setSubject(NLGUtils.getFactory().createNounPhrase(
					"you"));
			
			sequenceDivelets.setVerb("perform");
			sequenceDivelets.addModifier("effectively");
			sequenceDivelets.setFeature(Feature.TENSE, Tense.PAST);
			
			sequenceDivelets.setObject(divelets);
			
			PPPhraseSpec way = NLGUtils.getFactory().createPrepositionPhrase(
					"in", "quick succession");
			sequenceDivelets.addComplement(way);
			
			List<DocumentElement> sentences = new ArrayList<DocumentElement>();
			sentences.add(NLGUtils.getFactory()
					.createSentence(sequenceDivelets));
			sentences
					.add(NLGUtils
							.getFactory()
							.createSentence(
									"what this means is that your body had too little time to degas from the first dive before you went on the second"));
			
			return NLGUtils.getFactory().createParagraph(sentences);
			// return sequenceDivelets;
		}
		return null;
	}
	
	public static SPhraseSpec getDiveletWithinLimitPhrase(NPPhraseSpec noun,
			DiveletMessage msg) {
		
		VPPhraseSpec vpSpec = NLGUtils.getFactory().createVerbPhrase("be");
		
		PPPhraseSpec prepo = NLGUtils.getFactory().createPrepositionPhrase(
				"within", "the safety limits recommended by PADI");
		vpSpec.setObject(prepo);
		
		SPhraseSpec spec = NLGUtils.getFactory().createClause(noun, vpSpec);
		
		PPPhraseSpec diveletNP = getDiveletNP(msg);
		
		if (diveletNP != null) {
			spec.addFrontModifier(diveletNP);
		}
		
		return spec;
	}
	
	public static NLGElement getSafeDepthElement(Message message) {
		
		SafeDiveDepthMessage msg;
		if ((msg = getTypedMessage(message, SafeDiveDepthMessage.class)) != null) {
			
			NPPhraseSpec depth = NLGUtils.getFactory()
					.createNounPhrase("depth");
			
			depth.setDeterminer("your");
			
			return getDiveletWithinLimitPhrase(depth, msg);
		}
		
		return null;
	}
	
	public static NLGElement getSafeAscentRateElement(Message message) {
		
		SafeAscentRateMessage msg;
		if ((msg = getTypedMessage(message, SafeAscentRateMessage.class)) != null) {
			
			NPPhraseSpec depth = NLGUtils.getFactory().createNounPhrase(
					"ascent rate");
			
			depth.setDeterminer("your");
			
			return getDiveletWithinLimitPhrase(depth, msg);
		}
		
		return null;
	}
	
	public static NLGElement getSafeBottomTimeElement(Message message) {
		
		SafeBottomTimeMessage msg;
		if ((msg = getTypedMessage(message, SafeBottomTimeMessage.class)) != null) {
			
			NPPhraseSpec bottomTime = NLGUtils.getFactory().createNounPhrase(
					"time");
			bottomTime.addModifier("bottom");
			bottomTime.setDeterminer("your");
			
			return getDiveletWithinLimitPhrase(bottomTime, msg);
		}
		
		return null;
	}
	
	public static NLGElement getExcessDepthTimeWarning(Message message) {
		
		DiveletExcessDepthTimeMessage msg;
		if ((msg = getTypedMessage(message, DiveletExcessDepthTimeMessage.class)) != null) {
			
			VPPhraseSpec verb = NLGUtils.getFactory().createVerbPhrase("stay");
			
			PPPhraseSpec prepp = NLGUtils.getFactory()
					.createPrepositionPhrase("longer",
							"than the NDL by " + msg.getExcessTime() + "mins");
			verb.setObject(prepp);
			
			NPPhraseSpec you = NLGUtils.getFactory().createNounPhrase("you");
			SPhraseSpec spec = NLGUtils.getFactory().createClause(you, verb);
			
			PPPhraseSpec diveletNP = getDiveletNP(msg);
			if (diveletNP != null) {
				spec.addFrontModifier(diveletNP);
			}
			
			return spec;
		}
		
		return null;
	}
	
	public static NLGElement getExcessSecondDiveDepth(Message message) {
		SecondDiveletDeeperMessage msg;
		if ((msg = getTypedMessage(message, SecondDiveletDeeperMessage.class)) != null) {
			
			DocumentElement firstSentence = NLGUtils.getFactory()
					.createSentence(
							"on your second dive you went deeper than the first (by "
									+ msg.getExcessDepth()
									+ "m) which is generally not recommended");
			
			DocumentElement secondSentence = NLGUtils
					.getFactory()
					.createSentence(
							"your first dive of the day should always be the deepest followed by shallower repeat dives");
			
			List<DocumentElement> sentences = new ArrayList<DocumentElement>();
			sentences.add(firstSentence);
			sentences.add(secondSentence);
			
			return NLGUtils.getFactory().createParagraph(sentences);
			
		}
		return null;
	}
	
	public static NLGElement getDiveletDepthWarning(Message message) {
		
		DiveletDepthWarningMessage warning;
		if ((warning = getTypedMessage(message,
				DiveletDepthWarningMessage.class)) != null) {
			
			PPPhraseSpec prepp;
			
			if (warning.isCloseToLimit()) {
				prepp = NLGUtils.getFactory().createPrepositionPhrase(
						"very close to", "the PADI depth limit of 42m");
			} else {
				String depth = ""
						+ ((int) (warning.getExcessDiveDepth() + 0.5)) + "m";
				prepp = NLGUtils.getFactory().createPrepositionPhrase(
						depth + " deeper than",
						" the PADI recommended depth limit of 42m");
			}
			
			VPPhraseSpec verb = NLGUtils.getFactory().createVerbPhrase("go");
			
			verb.setObject(prepp);
			
			NPPhraseSpec you = NLGUtils.getFactory().createNounPhrase("you");
			
			SPhraseSpec spec = NLGUtils.getFactory().createClause(you, verb);
			
			PPPhraseSpec diveletNP = getDiveletNP(warning);
			if (diveletNP != null) {
				spec.addFrontModifier(diveletNP);
			}
			
			return spec;
			
		}
		return null;
	}
	
	public static NLGElement getDiveDescription(Message evaluation) {
		
		DiveEvaluation eval;
		if ((eval = getTypedMessage(evaluation, DiveEvaluation.class)) != null) {
			
			NPPhraseSpec theDive = NLGUtils.getFactory().createNounPhrase(
					NLGUtils.getFactory().createNounPhrase("dive"));
			theDive.setDeterminer("the");
			
			SPhraseSpec sps = NLGUtils.getFactory().createClause();
			
			AdjPhraseSpec description = NLGUtils.getFactory()
					.createAdjectivePhrase();
			
			switch (eval.getEvaluation()) {
				case FINE:
					description.setAdjective("fine");
					break;
				case RISKY:
					description.setAdjective("risky");
					break;
				case REALLY_SHALLOW:
					description.setPreModifier("really");
				case SHALLOW:
					description.setAdjective("shallow");
					break;
				case REALLY_RISKY:
					description.setAdjective("risky");
					description.setPreModifier("really");
					break;
				case WELL_EXECUTED:
					description.setAdjective("executed");
					description.setPreModifier("well");
					break;
				default:
					break;
			
			}
			
			// description.setPreModifier("very");
			// description.setAdjective("shallow");
			//
			sps.setSubject(theDive);
			sps.setVerb("be");
			sps.setComplement(description);
			
			// sps.setFeature(Feature.TENSE, Tense.PAST);
			
			return sps;
		}
		return null;
		
	}
	
	public static PPPhraseSpec getDiveletNP(DiveletMessage divelet) {
		
		if (divelet.getTotalDivelets() <= 1) {
			return null;
		} else {
			
			NPPhraseSpec spec = NLGUtils.getFactory().createNounPhrase("dive");
			
			if (divelet.getDiveletNumber() == 1) {
				spec.addPreModifier("first");
			} else {
				spec.addPreModifier("second");
			}
			
			spec.setDeterminer("your");
			
			PPPhraseSpec ppSpec = NLGUtils.getFactory()
					.createPrepositionPhrase("on", spec);
			
			return ppSpec;
		}
		
	}
	
	public static <T extends Message> T getTypedMessage(Message m, Class<T> type) {
		if (type.isInstance(m)) {
			return type.cast(m);
		} else {
			return null;
		}
	}
	
	// private static NPPhraseSpec entityDiver;
	// private static NPPhraseSpec entityDive;
	// private static NPPhraseSpec entityDive1;
	// private static NPPhraseSpec entityDive2;
	// private static NPPhraseSpec entityDive3;
	//
	// public static String getRandomDeterminer
	//
	// public static NPPhraseSpec getDiveEntity() {
	//
	// if (entityDive == null) {
	// entityDive = NLGUtils.getFactory().createNounPhrase("dive");
	// }
	//
	// return entityDive;
	// }
	//
	// public static NPPhraseSpec getDiverEntity() {
	//
	// if (entityDiver == null) {
	// entityDiver = NLGUtils.getFactory().createNounPhrase("you");
	// }
	//
	// return entityDiver;
	// }
	//
	// public static NPPhraseSpec getDiveletNumberEntity(int diveletNumber) {
	//
	// if (entityDive1 == null) {
	// entityDive1 = NLGUtils.getFactory().createNounPhrase("first",
	// "dive");
	// entityDive2 = NLGUtils.getFactory().createNounPhrase("second",
	// "dive");
	// entityDive3 = NLGUtils.getFactory().createNounPhrase("third",
	// "dive");
	// }
	// switch (diveletNumber) {
	// case 1:
	// return entityDive1;
	// case 2:
	// return entityDive2;
	// case 3:
	// return entityDive3;
	//
	// default:
	// break;
	// }
	// return entityDive;
	// }
	
}
