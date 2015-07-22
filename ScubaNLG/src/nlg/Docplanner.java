package nlg;

import java.util.List;
import java.util.stream.Collectors;

import messages.Message;

public class Docplanner {
	
	public List<Message> run(MessageStore messageStore) {
		return messageStore.stream().collect(Collectors.toList());
	}
}
