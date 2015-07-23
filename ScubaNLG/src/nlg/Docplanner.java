package nlg;

import messages.DiveEvaluation;
import messages.DiveletMessage;
import messages.MultipleDiveWarningMessage;

public class Docplanner {
	
	public DocPlan getDefaultDocPlan(MessageStore mStore) {
		DocPlan docplan = new DocPlan();
		
		if (mStore.query(MultipleDiveWarningMessage.class, msg -> true) != null) {
			
			docplan.add(mStore.query(MultipleDiveWarningMessage.class,
					eval -> true, true));
		} else {
			
			docplan.add(mStore.query(DiveEvaluation.class, eval -> true, true));
		}
		
		docplan.addAll(mStore.queryAll(DiveletMessage.class,
				divelet -> divelet.getDiveletNumber() == 1));
		
		docplan.addAll(mStore.queryAll(DiveletMessage.class,
				divelet -> divelet.getDiveletNumber() == 2));
		
		// return mStore.stream().collect(Collectors.toList());
		return docplan;
	}
	
	public DocPlan run(MessageStore mStore) {
		return getDefaultDocPlan(mStore);
	}
}
