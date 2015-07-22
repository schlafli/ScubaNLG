package messages;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {
	
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
	public boolean hasProperty(String property) {
		try {
			
			List<Method> methods = new ArrayList<>(Arrays.asList(this
					.getClass().getMethods()));
			
			return methods.stream().map(method -> method.getName())
					.filter(s -> s.startsWith("get"))
					.map(s -> s.substring(3, s.length()))
					.anyMatch(s -> s.equalsIgnoreCase(property));
		} catch (Exception e) {
			return false;
		}
	}
}
