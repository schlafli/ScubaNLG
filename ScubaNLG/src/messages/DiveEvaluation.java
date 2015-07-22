package messages;

public class DiveEvaluation extends Message {
	
	private DiveEvaluationValue	evaluation;
	
	public DiveEvaluationValue getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(DiveEvaluationValue evaluation) {
		this.evaluation = evaluation;
	}
	
}
