package nlg;

import java.util.ArrayList;
import java.util.Collection;

import messages.Message;

public class DocPlan {
	
	ArrayList<ArrayList<Message>>	plan;
	
	ArrayList<Message>				currentSection;
	
	public void newSection() {
		if (currentSection.size() > 0) {
			plan.add(currentSection);
		}
		
		currentSection = new ArrayList<Message>();
	}
	
	public DocPlan() {
		currentSection = new ArrayList<Message>();
		plan = new ArrayList<ArrayList<Message>>();
	}
	
	public ArrayList<ArrayList<Message>> getPlan() {
		newSection();
		return plan;
	}
	
	public boolean add(Message e) {
		if (e == null)
			return false;
		else
			return currentSection.add(e);
	}
	
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
