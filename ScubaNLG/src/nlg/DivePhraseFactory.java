package nlg;

import messages.DiveEvaluation;
import messages.Message;
import simplenlg.framework.NLGElement;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;

public class DivePhraseFactory {
	
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
					description.setPreModifier("very");
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
