package nlg;

import java.util.List;
import java.util.stream.Collectors;

import messages.DiveEvaluation;
import messages.DiveEvaluationValue;
import messages.Message;
import simplenlg.realiser.english.Realiser;
import analytics.DiveFeatures;
import analytics.DiveletFeatures;

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
	
	public List<Message> evaluateDivelet(DiveletFeatures divelet) {
		return null;
	}
	
	public DiveEvaluation createDiveEvaluation() {
		DiveEvaluation eval = new DiveEvaluation();
		if (diveFeatures.getDiveDepth() < 12) {
			eval.setEvaluation(DiveEvaluationValue.SHALLOW);
		} else if (diveFeatures.getNumOfDivelets() == 1) {
			if (diveFeatures.getDiveDepth() < 15) {
				eval.setEvaluation(DiveEvaluationValue.VERY_SHALLOW);
			} else if (diveFeatures.getDiveDepth() < 38
					&& diveFeatures.getFirstDiveletFeatures()
							.getExcessBottomTime() < 0) {
				if (diveFeatures.getFirstDiveletFeatures().getAscentSpeed() < 3) {
					eval.setEvaluation(DiveEvaluationValue.WELL_EXECUTED);
				} else if (diveFeatures.getFirstDiveletFeatures()
						.getAscentSpeed() <= 6) {
					eval.setEvaluation(DiveEvaluationValue.FINE);
				} else {
					eval.setEvaluation(DiveEvaluationValue.RISKY);
				}
				
			} else if (diveFeatures.getDiveDepth() < 50) {
				if (diveFeatures.getFirstDiveletFeatures()
						.getExcessBottomTime() > diveFeatures
						.getFirstDiveletFeatures().getBottomTime() / 2.5) {
					eval.setEvaluation(DiveEvaluationValue.VERY_RISKY);
				} else {
					eval.setEvaluation(DiveEvaluationValue.RISKY);
				}
			} else {
				eval.setEvaluation(DiveEvaluationValue.VERY_RISKY);
			}
		} else {
			eval = null;
		}
		
		return eval;
	}
}
