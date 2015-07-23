package nlg;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import messages.Message;

public class MessageStore extends HashSet<Message> {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public <T extends Message> T query(Class<T> cls, Predicate<T> test,
			boolean remove) {
		
		Optional<T> item = this.stream().filter(cls::isInstance).map(cls::cast)
				.filter(test).findFirst();
		if (item.isPresent()) {
			if (remove) {
				this.remove(item.get());
			}
			
			return item.get();
		} else {
			return null;
		}
	}
	
	public <T extends Message> List<T> queryAll(Class<T> cls, Predicate<T> test) {
		return this.stream().filter(cls::isInstance).map(cls::cast)
				.filter(test).collect(Collectors.toList());
	}
	
	public <T extends Message> T query(Class<T> cls, Predicate<T> test) {
		return query(cls, test, false);
	}
	
	@Override
	public boolean add(Message message) {
		if (message != null) {
			return super.add(message);
		} else {
			return false;
		}
	}
	
	public boolean add(Collection<Message> messages) {
		if (messages != null) {
			for (Message message : messages) {
				add(message);
			}
			return true;
		} else {
			return false;
		}
	}
}
