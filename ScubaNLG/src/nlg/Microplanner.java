package nlg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import messages.Message;
import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;

public class Microplanner {
	
	public List<Function<Message, NLGElement>>	lexRules;
	
	public Microplanner() {
		lexRules = new ArrayList<>();
		lexRules.add(DivePhraseFactory::getDiveDescription);
		
	}
	
	public List<NLGElement> run(List<Message> docplan) {
		return docplan.stream().map(this::singleMessageRun).map(element -> {
			element.setFeature(Feature.TENSE, Tense.PAST);
			return element;
		}).collect(Collectors.toList());
	}
	
	public NLGElement singleMessageRun(Message message) {
		try {
			return lexRules.stream()
					.filter(func -> func.apply(message) != null).findFirst()
					.get().apply(message);
		} catch (Exception e) {
			return NLGUtils.getFactory().createNLGElement("[ERROR]");
		}
	}
	
}
