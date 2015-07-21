package nlg;
/**
 *This is the interface to be implemented by any class with NLG functionality so that the text generated can be displayed in the dive report visualization. 
 * @author  ssripada
 */

public interface Reporter {

	public abstract String generateText();

}