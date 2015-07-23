package nlg;

import simplenlg.aggregation.AggregationRule;
import simplenlg.aggregation.PhraseChecker;
import simplenlg.features.Feature;
import simplenlg.features.InternalFeature;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.PhraseCategory;
import simplenlg.phrasespec.SPhraseSpec;

/*
 this aggregation rule should coordinate sentences where
 *) subjects are the same
 *) verbs (and their modifiers are the same)
 *) direct objects are diffrent
 If the direct objects have the same modifiers and determiners, the rule keeps only one.
 It is probably not bug-free.
 */
public class ObjectCoordinationRule extends AggregationRule {
	
	public ObjectCoordinationRule() {
		super();
	}
	
	@Override
	/**
	 * Applies aggregation to two NLGElements e1 and e2, succeeding only if they
	 * are clauses (that is, e1.getCategory() == e2.getCategory ==
	 * {@link simplenlg.framework.PhraseCategory#CLAUSE}).
	 */
	public NLGElement apply(NLGElement previous, NLGElement next) {
		NLGElement aggregated = null;
		
		if (previous.getCategory() == PhraseCategory.CLAUSE
				&& next.getCategory() == PhraseCategory.CLAUSE
				&& PhraseChecker.nonePassive(previous, next)
				&& !PhraseChecker.expletiveSubjects(previous, next)) {
			
			// case 1: identical sentences: remove the current
			if (PhraseChecker.sameSentences(previous, next)) {
				aggregated = previous;
				
				// case 2: subjects and verbs identical: coordinate Direct
				// OBjects
			} else if (PhraseChecker.sameFrontMods(previous, next)
					&& (PhraseChecker.sameSubjects(previous, next) || (((SPhraseSpec) next)
							.getSubject().getFeatureAsBoolean(Feature.ELIDED))
							&& ((SPhraseSpec) next)
									.getSubject()
									.getFeatureAsString(InternalFeature.HEAD)
									.equals(((SPhraseSpec) previous)
											.getSubject().getFeatureAsString(
													InternalFeature.HEAD))) // subject
																			// might
																			// have
																			// been
																			// elided
																			// by
																			// another
																			// rule
					&& PhraseChecker.samePostMods(previous, next)
					&& PhraseChecker.sameVPHead(previous, next)) {
				aggregated = this.factory.createClause();
				aggregated.setFeature(InternalFeature.SUBJECTS, previous
						.getFeatureAsElementList(InternalFeature.SUBJECTS));
				aggregated.setFeature(InternalFeature.FRONT_MODIFIERS, previous
						.getFeatureAsElement(InternalFeature.FRONT_MODIFIERS));
				aggregated.setFeature(Feature.CUE_PHRASE,
						previous.getFeatureAsElement(Feature.CUE_PHRASE));
				aggregated
						.setFeature(
								InternalFeature.POSTMODIFIERS,
								previous.getFeatureAsElementList(InternalFeature.POSTMODIFIERS));
				NLGElement vp;
				
				// case 2.1: VPs have different arguments but same
				// head & mods
				if (!PhraseChecker.sameVPArgs(previous, next)
						&& PhraseChecker.sameVPHead(previous, next)
						&& PhraseChecker.sameVPModifiers(previous, next)) {
					
					NLGElement vp1 = previous
							.getFeatureAsElement(InternalFeature.VERB_PHRASE);
					NLGElement vp2 = previous
							.getFeatureAsElement(InternalFeature.VERB_PHRASE);
					if (!vp1.getFeature(Feature.TENSE).equals(
							vp2.getFeature(Feature.TENSE)))
						return null;
					
					vp = this.factory.createVerbPhrase();
					vp.setFeature(Feature.TENSE, vp1.getFeature(Feature.TENSE));
					vp.setFeature(InternalFeature.HEAD,
							vp1.getFeatureAsElement(InternalFeature.HEAD));
					vp.setFeature(
							InternalFeature.PREMODIFIERS,
							vp1.getFeatureAsElementList(InternalFeature.PREMODIFIERS));
					vp.setFeature(
							InternalFeature.POSTMODIFIERS,
							vp1.getFeatureAsElementList(InternalFeature.POSTMODIFIERS));
					
					SPhraseSpec sentence1 = (SPhraseSpec) previous;
					SPhraseSpec sentence2 = (SPhraseSpec) next;
					NLGElement object1 = sentence1.getObject();
					NLGElement object2 = sentence2.getObject();
					if (object1 == null || object2 == null)
						return null;
					// check if the modifiers for the objects are the same so we
					// can remove them
					if (object1
							.getFeatureAsElementList(
									InternalFeature.PREMODIFIERS)
							.equals(object2
									.getFeatureAsElementList(InternalFeature.PREMODIFIERS))
							&& object1
									.getFeatureAsElementList(
											InternalFeature.POSTMODIFIERS)
									.equals(object2
											.getFeatureAsElementList(InternalFeature.POSTMODIFIERS))
							&& object1
									.getFeatureAsElementList(
											InternalFeature.SPECIFIER)
									.equals(object2
											.getFeatureAsElementList(InternalFeature.SPECIFIER))) {
						object2.setFeature(InternalFeature.PREMODIFIERS, null);
						object2.setFeature(InternalFeature.POSTMODIFIERS, null);
						object2.setFeature(InternalFeature.SPECIFIER, null);
					}
					
					CoordinatedPhraseElement objs = new CoordinatedPhraseElement(
							object1, object2);
					
					aggregated.setFeature(InternalFeature.VERB_PHRASE, vp);
					aggregated.setFeature(InternalFeature.POSTMODIFIERS, objs);
					
				}
				
			}
		}
		
		return aggregated;
	}
}
