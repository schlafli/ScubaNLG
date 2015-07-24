package app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nlg.NLGUtils;
import nlg.ObjectCoordinationRule;
import simplenlg.aggregation.Aggregator;
import simplenlg.aggregation.ClauseCoordinationRule;
import simplenlg.framework.NLGElement;
import simplenlg.realiser.english.Realiser;

public class TestNlLG {
	
	public static void main(String[] args) {
		
		List<NLGElement> planned = new ArrayList<NLGElement>();
		// ADD CODE HERE:
		
		planned.add(NLGUtils.getFactory().createClause("Michael", "find",
				"a bug"));
		planned.add(NLGUtils.getFactory()
				.createClause("Serge", "find", "a bug"));
		
		Aggregator aggregator = new Aggregator();
		
		aggregator.initialise();
		
		// Add aggregation rules to the Aggregator
		ObjectCoordinationRule ocoord = new ObjectCoordinationRule();
		ClauseCoordinationRule coord = new ClauseCoordinationRule();
		
		// ForwardConjunctionReductionRule fcr = new
		// ForwardConjunctionReductionRule();
		// BackwardConjunctionReductionRule bcr = new
		// BackwardConjunctionReductionRule();
		
		aggregator.addRule(ocoord);
		aggregator.addRule(coord);
		
		List<NLGElement> aggregated;
		if (planned.size() > 1) {
			aggregated = aggregator.realise(planned);
		} else {
			aggregated = planned;
		}
		
		Realiser realiser = NLGUtils.getRealiser();
		String finalOutput = aggregated.stream().map(realiser::realiseSentence)
				.collect(Collectors.joining());
		
		System.out.println(finalOutput);
	}
	
}
