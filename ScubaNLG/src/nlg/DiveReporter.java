package nlg;

import analytics.DiveFeatures;

/**
 * 
 */

/**
 * This class implements the Reporter interface to serve as an example class. There is no NLG functionality here.
 * @author csc242
 *
 */
public class DiveReporter implements Reporter {
	DiveFeatures diveFeatures;

	public DiveReporter(DiveFeatures diveFeatures) {
		this.diveFeatures = diveFeatures;
	}

	/* (non-Javadoc)
	 * @see scuba.Reporter#generateText()
	 */
	@Override
	public String generateText() {
		String corpusText = CorpusText.getText(diveFeatures.getDiveNo().intValue());
		return corpusText+"<br><b>Computer Text:</b><br>"+diveFeatures.toString();
	}
}
