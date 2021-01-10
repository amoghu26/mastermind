/**
 *  This class creates and manages one array of pegs from the game MasterMind.
 *
 *  @author Amogh Upadhyaya
 *  @since  October 29 2020
*/

public class PegArray {

	// array of pegs
	private Peg [] pegs;

	// the number of exact and partial matches for this array
	// as matched against the master.
	// Precondition: these values are valid after getExactMatches() and getPartialMatches()
	//				are called
	private int exactMatches, partialMatches;
	String masterString , userString ; // Strings that hold the guess and master peg values
		
	/**
	 *	Constructor
	 *	@param numPegs	number of pegs in the array
	 */
	public PegArray(int numPegs) 
	{
		masterString = "";
		userString = "";
		exactMatches = 0;
		partialMatches = 0;
		pegs = new Peg[numPegs];
		for(int i = 0; i<pegs.length; i++) 
		{
	         pegs[i] = new Peg();
	    }
	}
	
	/**
	 *	Return the peg object
	 *	@param n	The peg index into the array
	 *	@return		the peg object
	 */
	public Peg getPeg(int n) { return pegs[n]; }
	
	/**
	 *  Finds exact matches between master (key) peg array and this peg array
	 *	Postcondition: field exactMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of exact matches
	 */
	public int getExactMatches(PegArray master) 
	{ 
		 exactMatches = 0;
		 for(int i = 0; i<4; i++)
		 {
			 masterString+=master.getPeg(i).getLetter();
		 }
		 for(int i = 0; i<4; i++)
		 {
			 userString+=pegs[i].getLetter();
		 }
		 
	     for(int i = 0; i<4; i++) 
	     {
	         if (userString.charAt(i) == masterString.charAt(i) && userString.charAt(i) != ' ') 
	         {
	        	 exactMatches++;
	        	 masterString = masterString.substring(0,i) + " " + masterString.substring(i+1);
	        	 userString = userString.substring(0,i) + " " + userString.substring(i+1);
	         }
	     }
	     return exactMatches;
	}
	
	/**
	 *  Find partial matches between master (key) peg array and this peg array
	 *	Postcondition: field partialMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of partial matches
	 */
	public int getPartialMatches(PegArray master) 
	{ 
		partialMatches = 0;
		for(int i = 0; i<4; i++)
		{
			for(int j = 0; j<4; j++)
			{
				if(userString.charAt(i) == masterString.charAt(j) && userString.charAt(i) != ' ' && i!=j)
				{
					partialMatches++;
					userString = userString.substring(0, i) + " " + userString.substring(i+1);
					masterString = masterString.substring(0, j) + " " + masterString.substring(j+1);
				}
			}
		}
		return partialMatches; 
	}
	
	// Accessor methods
	// Precondition: getExactMatches() and getPartialMatches() must be called first
	public int getExact() { return exactMatches; }
	public int getPartial() { return partialMatches; }

}