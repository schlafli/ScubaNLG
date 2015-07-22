package nlg;

import java.util.Random;

import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class NLGUtils {
	
	private static NLGFactory	_nlgFactoryInstance;
	private static Lexicon		_nlgLexiconInstance;
	private static Realiser		_nlgRealiserInstance;
	private static Random		_randomInstance;
	
	public static NLGFactory getFactory() {
		if (_nlgFactoryInstance == null) {
			_nlgFactoryInstance = createDefaultFactory();
		}
		
		return _nlgFactoryInstance;
	}
	
	public static Realiser getRealiser() {
		if (_nlgRealiserInstance == null) {
			_nlgRealiserInstance = createDefaultRealiser();
		}
		return _nlgRealiserInstance;
	}
	
	public static Lexicon getLexicon() {
		if (_nlgLexiconInstance == null) {
			_nlgLexiconInstance = Lexicon.getDefaultLexicon();
		}
		return _nlgLexiconInstance;
	}
	
	private static Realiser createDefaultRealiser() {
		Realiser realiser = new Realiser(getLexicon());
		return realiser;
	}
	
	private static NLGFactory createDefaultFactory() {
		NLGFactory factory = new NLGFactory(getLexicon());
		return factory;
	}
	
	public static NPPhraseSpec getNounPhrase(Object noun) {
		return getFactory().createNounPhrase(noun);
	}
	
	public static NPPhraseSpec getNounPhrase(Object determiner, Object noun) {
		return getFactory().createNounPhrase(determiner, noun);
	}
	
	public static Random getRandom() {
		if (_randomInstance == null) {
			_randomInstance = new Random();
		}
		return _randomInstance;
	}
}
