package nlg;

import messages.DiveEvaluation;
import messages.DiveletDepthWarningMessage;
import messages.DiveletMessage;
import messages.Message;
import simplenlg.framework.NLGElement;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;

public class DivePhraseFactory {
	
	public static NLGElement getDiveletDepthWarning(Message message) {
		
		DiveletDepthWarningMessage warning;
		if ((warning = getTypedMessage(message,
				DiveletDepthWarningMessage.class)) != null) {
			
			SPhraseSpec spec = NLGUtils.getFactory().createClause("you", "go");
			
			PPPhraseSpec diveletNP = getDiveletNP(warning);
			if (diveletNP != null) {
				spec.addFrontModifier(diveletNP);
			}
			
			if (warning.isCloseToLimit()) {
				spec.addComplement("very close to the PADI depth limit of 42m");
			} else {
				String depth = ""
						+ ((int) (warning.getExcessDiveDepth() + 0.5)) + "m";
				spec.addComplement(depth
						+ " deeper than the PADI recommended depth limit of 42m");
			}
			
			return spec;
		}
		return null;
	}
	
	public static NLGElement getDiveDescription(Message evaluation) {
		
		DiveEvaluation eval;
		if ((eval = getTypedMessage(evaluation, DiveEvaluation.class)) != null) {
			
			NPPhraseSpec theDive = NLGUtils.getFactory().createNounPhrase(
					getDiveEntity());
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
				case SHALLOW:
					description.setAdjective("shallow");
					description.setPreModifier("very");
					break;
				case VERY_RISKY:
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
			
			spec.addPreModifier("your");
			
			if (divelet.getDiveletNumber() == 1) {
				spec.setDeterminer("first");
			} else {
				spec.setDeterminer("second");
			}
			
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
	
	private static NPPhraseSpec	entityDiver;
	private static NPPhraseSpec	entityDive;
	private static NPPhraseSpec	entityDive1;
	private static NPPhraseSpec	entityDive2;
	private static NPPhraseSpec	entityDive3;
	
	// public static String getRandomDeterminer
	
	public static NPPhraseSpec getDiveEntity() {
		
		if (entityDive == null) {
			entityDive = NLGUtils.getFactory().createNounPhrase("dive");
		}
		
		return entityDive;
	}
	
	public static NPPhraseSpec getDiverEntity() {
		
		if (entityDiver == null) {
			entityDiver = NLGUtils.getFactory().createNounPhrase("you");
		}
		
		return entityDiver;
	}
	
	public static NPPhraseSpec getDiveletNumberEntity(int diveletNumber) {
		
		if (entityDive1 == null) {
			entityDive1 = NLGUtils.getFactory().createNounPhrase("first",
					"dive");
			entityDive2 = NLGUtils.getFactory().createNounPhrase("second",
					"dive");
			entityDive3 = NLGUtils.getFactory().createNounPhrase("third",
					"dive");
		}
		switch (diveletNumber) {
			case 1:
				return entityDive1;
			case 2:
				return entityDive2;
			case 3:
				return entityDive3;
				
			default:
				break;
		}
		return entityDive;
	}
	
}
