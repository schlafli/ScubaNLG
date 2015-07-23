package nlg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import messages.Message;
import simplenlg.aggregation.Aggregator;
import simplenlg.aggregation.ClauseCoordinationRule;
import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;

public class Microplanner {
	
	public List<Function<Message, NLGElement>>	lexRules;
	
	public Microplanner() {
		lexRules = new ArrayList<>();
		lexRules.add(DivePhraseFactory::getDiveDescription);
		lexRules.add(DivePhraseFactory::getDiveletDepthWarning);
		lexRules.add(DivePhraseFactory::getExcessSecondDiveDepth);
		lexRules.add(DivePhraseFactory::getExcessDepthTimeWarning);
		lexRules.add(DivePhraseFactory::getSafeDepthElement);
		lexRules.add(DivePhraseFactory::getSafeBottomTimeElement);
		lexRules.add(DivePhraseFactory::generateMultiplDiveletWarning);
	}
	
	public List<List<NLGElement>> run(DocPlan docplan) {
		
		List<List<NLGElement>> result = new ArrayList<List<NLGElement>>();
		for (ArrayList<Message> subPlan : docplan.getPlan()) {
			result.add(run(subPlan));
		}
		
		return result;
	}
	
	public List<NLGElement> run(List<Message> docplan) {
		List<NLGElement> planned = docplan.stream().map(this::singleMessageRun)
				.map(element -> {
					element.setFeature(Feature.TENSE, Tense.PAST);
					return element;
				}).collect(Collectors.toList());
		
		Aggregator aggregator = new Aggregator();
		
		aggregator.initialise();
		
		// Add aggregation rules to the Aggregator
		ClauseCoordinationRule coord = new ClauseCoordinationRule();
		ObjectCoordinationRule ocoord = new ObjectCoordinationRule();
		
		// ForwardConjunctionReductionRule fcr = new
		// ForwardConjunctionReductionRule();
		// BackwardConjunctionReductionRule bcr = new
		// BackwardConjunctionReductionRule();
		
		// aggregator.addRule(fcr);
		// aggregator.addRule(bcr);
		
		// aggregator.addRule(ocoord);
		aggregator.addRule(coord);
		
		List<NLGElement> aggregated = planned;
		
		boolean skip = false;
		if (!skip) {
			if (planned.size() > 1) {
				aggregated = aggregator.realise(planned);
			}
		}
		
		return aggregated;
	}
	
	public NLGElement singleMessageRun(Message message) {
		try {
			return lexRules.stream()
					.filter(func -> func.apply(message) != null).findFirst()
					.get().apply(message);
		} catch (Exception e) {
			return NLGUtils.getFactory().createNLGElement(
					"[ERROR in lexicalising " + message.getType() + "]");
		}
	}
	
}
