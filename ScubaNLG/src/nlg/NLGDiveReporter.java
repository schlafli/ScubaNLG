package nlg;

import java.util.List;
import java.util.stream.Collectors;

import messages.DiveEvaluation;
import messages.DiveEvaluationValue;
import messages.DiveletDepthWarningMessage;
import messages.DiveletExcessDepthTimeMessage;
import messages.Message;
import messages.SafeBottomTimeMessage;
import messages.SafeDiveDepthMessage;
import messages.SecondDiveletDeeperMessage;
import simplenlg.realiser.english.Realiser;
import analytics.DiveFeatures;
import analytics.DiveletFeatures;

public class NLGDiveReporter extends DiveReporter {
	
	private MessageStore	mStore;
	
	public NLGDiveReporter(DiveFeatures diveFeatures) {
		super(diveFeatures);
		mStore = new MessageStore();
		
	}
	
	@Override
	public String generateText() {
		
		// Do all the message creation:
		createDiveEvaluation();
		
		evaluateDivelets();
		
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
	
	private void evaluateDivelets() {
		if (diveFeatures.getNumOfDivelets() > 0) {
			evaluateDivelet(diveFeatures.getFirstDiveletFeatures(), 1,
					diveFeatures.getNumOfDivelets());
			
			if (diveFeatures.getNumOfDivelets() > 1) {
				evaluateDivelet(diveFeatures.getSecondDiveletFeatures(), 2,
						diveFeatures.getNumOfDivelets());
				
				createSequentialDiveletDeeperWarning(
						diveFeatures.getFirstDiveletFeatures(),
						diveFeatures.getSecondDiveletFeatures());
			}
		}
	}
	
	private void createSequentialDiveletDeeperWarning(DiveletFeatures first,
			DiveletFeatures second) {
		if (first.getDiveDepth() < second.getDiveDepth()) {
			SecondDiveletDeeperMessage msg = new SecondDiveletDeeperMessage();
			
			msg.setExcessDepth((int) ((second.getDiveDepth() - first
					.getDiveDepth()) + 0.5));
			
			mStore.add(msg);
		}
	}
	
	public void evaluateDivelet(DiveletFeatures divelet, int diveletNumber,
			int totalDivelets) {
		evaluateDiveletDepth(divelet, diveletNumber, totalDivelets);
		evaluateDiveletDuration(divelet, diveletNumber, totalDivelets);
		evaluateDiveletBottomTime(divelet, diveletNumber, totalDivelets);
	}
	
	private void evaluateDiveletDuration(DiveletFeatures divelet,
			int diveletNumber, int totalDivelets) {
		if (divelet.getExcessBottomTime() > 0) {
			DiveletExcessDepthTimeMessage msg = new DiveletExcessDepthTimeMessage();
			msg.setDiveletNumber(diveletNumber);
			msg.setTotalDivelets(totalDivelets);
			msg.setDiveDepth((int) (divelet.getDiveDepth() + 0.5));
			msg.setExcessTime((int) divelet.getExcessBottomTime());
			
			mStore.add(msg);
		}
	}
	
	public void evaluateDiveletDepth(DiveletFeatures divelet,
			int diveletNumber, int totalDivelets) {
		
		if (divelet.getDiveDepth() > 40) {
			DiveletDepthWarningMessage msg = new DiveletDepthWarningMessage();
			msg.setDiveletNumber(diveletNumber);
			msg.setTotalDivelets(totalDivelets);
			
			if (divelet.getDiveDepth() < 42) {
				msg.setCloseToLimit(true);
				mStore.add(msg);
			} else {
				msg.setCloseToLimit(false);
				msg.setExcessDiveDepth(divelet.getExcessDiveDepth());
				mStore.add(msg);
			}
		} else {
			SafeDiveDepthMessage msg = new SafeDiveDepthMessage();
			msg.setDiveletNumber(diveletNumber);
			msg.setTotalDivelets(totalDivelets);
			msg.setDiveDepth((int) (divelet.getDiveDepth() + 0.5));
			
			mStore.add(msg);
		}
	}
	
	public void evaluateDiveletBottomTime(DiveletFeatures divelet,
			int diveletNumber, int totalDivelets) {
		if (divelet.getExcessBottomTime() < 0) {
			SafeBottomTimeMessage msg = new SafeBottomTimeMessage();
			msg.setDiveletNumber(diveletNumber);
			msg.setTotalDivelets(totalDivelets);
			mStore.add(msg);
		}
	}
	
	public void createDiveEvaluation() {
		DiveEvaluation eval = new DiveEvaluation();
		if (diveFeatures.getDiveDepth() < 12) {
			eval.setEvaluation(DiveEvaluationValue.SHALLOW);
		} else if (diveFeatures.getNumOfDivelets() == 1) {
			if (diveFeatures.getDiveDepth() < 15) {
				eval.setEvaluation(DiveEvaluationValue.REALLY_SHALLOW);
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
						.getFirstDiveletFeatures().getBottomTime() / 1.8) {
					eval.setEvaluation(DiveEvaluationValue.REALLY_RISKY);
				} else {
					eval.setEvaluation(DiveEvaluationValue.RISKY);
				}
			} else {
				eval.setEvaluation(DiveEvaluationValue.REALLY_RISKY);
			}
		} else {
			eval = null;
		}
		
		mStore.add(eval);
	}
}
