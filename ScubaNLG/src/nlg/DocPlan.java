package nlg;

import java.util.ArrayList;
import java.util.Collection;

import messages.Message;

public class DocPlan extends ArrayList<Message> {
	
	@Override
	public boolean add(Message e) {
		if (e == null)
			return false;
		else
			return super.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends Message> c) {
		if (c == null) {
			return false;
		} else {
			for (Message message : c) {
				add(message);
			}
		}
		
		return true;
	}
}
