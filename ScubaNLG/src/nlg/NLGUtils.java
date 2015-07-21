package nlg;

import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.realiser.english.Realiser;

public class NLGUtils {
	
	private static NLGFactory	_nlgFactoryInstance;
	private static Lexicon		_nlgLexiconInstance;
	private static Realiser		_nlgRealiserInstance;
	
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
	
}
