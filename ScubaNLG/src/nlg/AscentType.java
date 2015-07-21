package nlg;
/**
 * 
 */

/**
 * This enum is created as an example that maps ranges of numerical values to language. Might be useful for lexicalization. 
 * @author csc242
 *
 */
public enum AscentType{
	FINE(0,6,"fine"),
	ACCEPTABLE(6,9,"acceptable"),
	RISKY(9,12,"rapid"),
	DANGEROUS(12,18,"very rapid"),
	VERY_DANGEROUS(18,Double.MAX_VALUE,"prohibitively rapid");
	
	double lowLimit;
	double highLimit;
	String adjective;
	
	AscentType(double lowLimit,double highLimit,String adjective){
		this.lowLimit = lowLimit;
		this.highLimit = highLimit;
		this.adjective = adjective;
	}
	
	public static String getAdjective(double speed){
		for(AscentType type: AscentType.values()){
			if((speed>type.lowLimit)&&(speed<=type.highLimit))
				return type.adjective;
		}
		return VERY_DANGEROUS.adjective;
	}
}
