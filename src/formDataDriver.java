/**
 * 
 */

/**
 * @author user
 *
 */
public class formDataDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FQform FQ = new FQform();
		BESTform BEST = new BESTform();
		
		// populate ratings arrays with a different number for each section
			// Filter Questionnaire ratings
		int value = 0;
		for(int i=0; i<60; i++) {
			FQ.setRating(i, value);
			if ((i+1) % 6 == 0)
				value++;
//			if(value == 5) value = 0;	// keep within valid ratings though not
										// not necessary for this test
		}
		
			// BEST ratings
		value = 3;
		for(int i=0; i <15; i++) {
			BEST.setRating(i, value);
			if(i==7 || i == 11)
				value++;
		}
		
		// get ratings arrays and display them to verify the sections are
		// being divided properly. For BEST, there should be eight ones, four twos
		// and three threes. For FQ there should be six of each digit from zero to
		// nine. The same tests to divide the sections are used for summing the 
		// sections in the form classes, so this should build confidence for that.
		int[] Bratings = BEST.getRating();
		int[] FQratings = FQ.getRating();
		
		for (int i=0; i<60; i++)
			System.out.print(FQratings[i]);
		System.out.println();
		
		System.out.println();
		
		// Process FQ form then print the category totals and then final score for
		// the BEST form. We should see ___.
		FQ.processForm();
		int[] FQsum = FQ.getCategorySum();
		
		for(int i=0; i<10; i++)
			System.out.print(i+1+":"+FQsum[i] + " ");
		System.out.println();
		
		// display array of category indexes sorted by category totals descending
		int[] FQcatOrder = FQ.getCategoryOrder();
		for(int i=0; i<10; i++)
			System.out.print(FQcatOrder[i]+1 + " ");
		System.out.println();
		
		for (int i=0; i<15; i++)
			System.out.print(Bratings[i]);
		System.out.println();
		
		// Process BEST form then print the category totals in the overall score
		// formula and then the overall score for the BEST form. We should see ___.
		BEST.processForm();
		int[] BESTsum = BEST.getCategorySum();
		System.out.print(BESTsum[0] + " + " + BESTsum[1]+" - "+BESTsum[2]);
		System.out.println(" = overall BEST score of " + BEST.getOverallScore() );
	}

}
