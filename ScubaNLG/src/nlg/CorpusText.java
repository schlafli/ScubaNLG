/**
 * 
 */
package nlg;

/**
 * This is an enum with corpus texts for each of the dives from the database. These can be viewed as the target texts for the NLG system or some variations of these could be the target texts.
 * @author csc242
 *
 */
public enum CorpusText {
	T1460(1460,"You effectively performed two dives in quick succession. What this means is that your body had too little time to degas from the first dive before you went on the second. On your first dive you ascended a little faster than the PADI recommended safe speed of 6m/min. On your second dive you went deeper than the first which is generally not recommended. Your first dive of the day should always be the deepest followed by shallower repeat dives. Although your second ascent rate is fine, you did stay 3min longer than the ANDL. For the above reasons, your second dive could be labeled risky."),
	T1461(1461,"This dive was fine overall, although you could have ascended a little slower by making a safety stop on your way up to the surface."),
	T1462(1462,"This was a risky dive. You went 5m deeper than the PADI recommended depth limit of 42m. Also your bottom time of 12mins was 100% longer than the NDL for this depth. Your slow ascent rate of 1m/min should have helped you to degas before you ascended to the surface."),
	T1463(1463,"This was really a shallow dive, a little deeper than the start depth on the PADI table. This dive was fine."),
	T1464(1464,"This was overall a well executed dive. The dive depth, bottom time and ascent rate have all been within the safety limits recommended by PADI. Particularly your less than 1m/min ascent rate should have given enough time for your body to degas before you ascended to the surface."),
	T1465(1465,"You effectively performed two dives in quick succession. What this means is that your body had too little time to degas from the first dive before you went on the second. Your first ascent rate is acceptable. You should have made a safety stop on the way up. Your second dive was deeper than the first one which is not recommnded. You should always schedule your deepest dive first followed by shallower repeat dives. On your second dive you stayed longer than ANDL by 3mins which is not recommended. For the above reasons your second dive could be called risky."),
	T1466(1466,"This was a risky dive. On this dive you went very close to the PADI depth limit of 42m. Moreover, at this depth, you stayed longer than the NDL by 6mins which is 75% longer. Your ascent rate was fine."),
	T1467(1467,"You effectively performed two dives in quick succession. What this means is that your body had too little time to degas from the first dive before you went on the second. Your first ascent rate was close to the PADI safe ascent rate limit of 6m/min. You should have made a safety stop on your way up. Your second dive was deeper than the first one which is really not recommended. You should always schedule your deepest dive first followed by shallower repeat dives. You cut it very close in terms of your bottom time on the second dive. Your second ascent rate was fine."),
	T1468(1468,"This was a shallow dive."),
	T1469(1469,"This was a really risky dive. On this dive you went very close to the PADI depth limit of 42m. Moreover, at this depth, you stayed longer than the NDL by 12mins which was 150% longer. Your ascent rate was fine."),
	T1470(1470,"Your ascent speed of nearly 9m/min on this not so deep dive was acceptable. But you could have entirely avoided the risk of micro-bubbles by ascending slower than PADI recommended safe ascent rate of 6m/min.");
	
	String text;
	private int diveNo;

	CorpusText(int diveNo,String text){
		this.diveNo = diveNo;
		this.text = text;
	}
	
	public static String getText(int diveNo){
		String rtn = "";
		for(CorpusText text: CorpusText.values()){
			if(text.diveNo==diveNo)
				rtn = text.text;
		}
		return rtn;
	}
}
