package nlg;

import messages.DiveEvaluation;
import messages.DiveletMessage;
import messages.MultipleDiveWarningMessage;
import messages.SecondDiveletDeeperMessage;

public class Docplanner {
	
	public DocPlan getDefaultDocPlan(MessageStore mStore) {
		DocPlan docplan = new DocPlan("Overall");
		
		if (mStore.query(MultipleDiveWarningMessage.class, msg -> true) != null) {
			
			docplan.add(mStore.query(MultipleDiveWarningMessage.class,
					eval -> true));
		} else {
			
			docplan.add(mStore.query(DiveEvaluation.class, eval -> true));
		}
		
		docplan.add(mStore
				.query(SecondDiveletDeeperMessage.class, item -> true));
		
		String firstSectionName = "Dive Evaluation";
		if (mStore.query(DiveletMessage.class,
				divelet -> divelet.getDiveletNumber() == 2) != null) {
			firstSectionName = "First " + firstSectionName;
		}
		
		docplan.newSection(firstSectionName);
		docplan.addAll(mStore.queryAll(DiveletMessage.class,
				divelet -> divelet.getDiveletNumber() == 1));
		
		docplan.newSection("Second Dive Evaluation");
		docplan.addAll(mStore.queryAll(DiveletMessage.class,
				divelet -> divelet.getDiveletNumber() == 2));
		
		docplan.newSection("");
		
		return docplan;
	}
	
	public DocPlan run(MessageStore mStore) {
		return getDefaultDocPlan(mStore);
	}
}
