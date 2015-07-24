package nlg;

import java.util.List;
import java.util.stream.Collectors;

import messages.AscentRateEvaluationValue;
import messages.DiveEvaluation;
import messages.DiveEvaluationValue;
import messages.DiveletAscentRateEvaluationMessage;
import messages.DiveletDepthWarningMessage;
import messages.DiveletExcessDepthTimeMessage;
import messages.MultipleDiveWarningMessage;
import messages.SafeAscentRateMessage;
import messages.SafeBottomTimeMessage;
import messages.SafeDiveDepthMessage;
import messages.SecondDiveletDeeperMessage;
import simplenlg.framework.NLGElement;
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
		DocPlan docplan = docplanner.run(mStore);
		
		// run the microplanner to generate text
		String generatedText = runMicroplanner(docplan);
		
		// add additional text
		String initial = super.generateText();
		String generated = "<h1>Generated Text:</h1>";
		
		generated += generatedText;
		
		// generated += new SergeNLG(diveFeatures).generateText();
		
		return initial + generated;
		
	}
	
	public String runMicroplanner(DocPlan docplan) {
		Realiser realiser = NLGUtils.getRealiser();
		Microplanner microplanner = new Microplanner();
		List<List<NLGElement>> res = microplanner.run(docplan);
		String output = "";
		
		int sectionIndex = 0;
		
		for (List<NLGElement> list : res) {
			
			if (sectionIndex >= 0
					&& sectionIndex < docplan.getSectionNames().size()) {
				String section = docplan.getSectionNames().get(sectionIndex);
				if (section.trim().length() > 0) {
					output += "<h2><u>" + section + "</u></h2>";
				}
			}
			
			output += list.stream().map(realiser::realiseSentence)
					.collect(Collectors.joining())
					+ "<br>";
			
			sectionIndex++;
		}
		
		return output;
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
		evaluateDiveletAscentRate(divelet, diveletNumber, totalDivelets);
		
	}
	
	private void evaluateDiveletAscentRate(DiveletFeatures divelet,
			int diveletNumber, int totalDivelets) {
		
		DiveletAscentRateEvaluationMessage msg = new DiveletAscentRateEvaluationMessage();
		msg.setDiveletNumber(diveletNumber);
		msg.setTotalDivelets(totalDivelets);
		msg.setAscentRate(divelet.getAscentSpeed());
		
		if (divelet.getAscentSpeed() < 5) {
			SafeAscentRateMessage msg2 = new SafeAscentRateMessage();
			msg2.setDiveletNumber(diveletNumber);
			msg2.setTotalDivelets(totalDivelets);
			mStore.add(msg2);
		}
		
		if (divelet.getAscentSpeed() < 1.4) {
			msg.setEvaluation(AscentRateEvaluationValue.SLOW);
		} else if (divelet.getAscentSpeed() < 4) {
			msg.setEvaluation(AscentRateEvaluationValue.FINE);
		} else if (divelet.getAscentSpeed() < 6) {
			msg.setEvaluation(AscentRateEvaluationValue.CLOSE);
		} else if (divelet.getAscentSpeed() < 7) {
			msg.setEvaluation(AscentRateEvaluationValue.A_BIT_FASTER);
		} else if (divelet.getAscentSpeed() < 10) {
			msg.setEvaluation(AscentRateEvaluationValue.ACCEPTABLE);
		} else {
			msg.setEvaluation(AscentRateEvaluationValue.FINE);
		}
		
		mStore.add(msg);
		
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
			MultipleDiveWarningMessage msg = new MultipleDiveWarningMessage();
			mStore.add(msg);
			
		}
		
		mStore.add(eval);
	}
}
