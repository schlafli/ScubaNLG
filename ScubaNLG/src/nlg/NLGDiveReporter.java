package nlg;

import java.util.List;
import java.util.stream.Collectors;

import messages.DiveEvaluation;
import messages.DiveEvaluationValue;
import messages.Message;
import simplenlg.realiser.english.Realiser;
import analytics.DiveFeatures;

public class NLGDiveReporter extends DiveReporter {
	
	public NLGDiveReporter(DiveFeatures diveFeatures) {
		super(diveFeatures);
	}
	
	@Override
	public String generateText() {
		
		MessageStore mStore = new MessageStore();
		
		// Do all the message creation:
		mStore.add(createDiveEvaluation());
		
		// do the document planning
		Docplanner docplanner = new Docplanner();
		List<Message> docplan = docplanner.run(mStore);
		
		// run the microplanner to generate text
		String generatedText = runMicroplanner(docplan);
		
		// add additional text
		String initial = super.generateText();
		String generated = "<br><h4>Generated Text:</h4>";
		
		generated += generatedText;
		
		generated += new SergeNLG(diveFeatures).generateText();
		
		return initial + generated;
		
	}
	
	public String runMicroplanner(List<Message> docplan) {
		Realiser realiser = NLGUtils.getRealiser();
		Microplanner microplanner = new Microplanner();
		
		return microplanner.run(docplan).stream()
				.map(realiser::realiseSentence).collect(Collectors.joining());
	}
	
	public DiveEvaluation createDiveEvaluation() {
		DiveEvaluation eval = new DiveEvaluation();
		if (!diveFeatures.isRealDive()) {
			eval.setEvaluation(DiveEvaluationValue.SHALLOW);
		} else if (diveFeatures.getNumOfDivelets() == 1) {
			
			eval.setEvaluation(DiveEvaluationValue.FINE);
		} else {
			eval = null; // set to null and don't add
		}
		return eval;
	}
}
