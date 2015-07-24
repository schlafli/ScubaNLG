package nlg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import messages.Message;

public class DocPlan {
	
	ArrayList<ArrayList<Message>>	plan;
	
	ArrayList<Message>				currentSection;
	
	ArrayList<String>				sectionNames;
	
	public void newSection(String name) {
		if (currentSection.size() > 0) {
			plan.add(currentSection);
			sectionNames.add(name);
		}
		
		currentSection = new ArrayList<Message>();
	}
	
	public DocPlan(String initialSectionTitle) {
		currentSection = new ArrayList<Message>();
		plan = new ArrayList<ArrayList<Message>>();
		sectionNames = new ArrayList<String>();
		sectionNames.add(initialSectionTitle);
	}
	
	public ArrayList<ArrayList<Message>> getPlan() {
		newSection("");
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
	
	public List<String> getSectionNames() {
		return sectionNames;
	}
}
