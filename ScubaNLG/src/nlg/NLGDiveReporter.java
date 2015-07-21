package nlg;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;
import analytics.DiveFeatures;

public class NLGDiveReporter extends DiveReporter {
	
	public NLGDiveReporter(DiveFeatures diveFeatures) {
		super(diveFeatures);
	}
	
	@Override
	public String generateText() {
		String initial = super.generateText();
		
		String generated = "<br><h4>Generated Text:</h4>";
		NLGFactory factory = NLGUtils.getFactory();
		Realiser realiser = NLGUtils.getRealiser();
		
		if (!diveFeatures.isRealDive()) {
			NPPhraseSpec theDive = NLGUtils.getFactory().createNounPhrase(
					"dive");
			theDive.setDeterminer("the");
			
			SPhraseSpec sps = NLGUtils.getFactory().createClause();
			
			sps.setSubject(theDive);
			sps.setVerb("be");
			
			sps.setFeature(Feature.TENSE, Tense.PAST);
			
			sps.setObject("shallow");
			
			generated += realiser.realiseSentence(sps);
		}
		
		SergeNLG additionalStep = new SergeNLG(diveFeatures);
		generated += additionalStep.generateText();
		
		return initial + generated;
		
	}
}
