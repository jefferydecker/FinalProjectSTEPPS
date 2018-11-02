import java.util.Date;

/**
 *  Filter Questionnaire form data
 *  
 *  from STEPPS (Systems Training for Emotional Predictability and Problem Solving)
 *  (c) Copyright 2002, 2008, N.S. Blum, N.E. Bartels, D. St. John, B. Pfohl,
 *  	All rights reserved.
 *  STEPPS^TM is the Trademark of N.S. Blum, N.E. Bartels, D. St. John, B. Pfohl,
 *  	All rights reserved.
 *
 */

/**
 * @author Jeffery Decker
 *
 */
public class FQform {
	private Date date;
	private int[] rating;		// for a 60 element array of responses from 0 to 4
	private int[] categorySum;	// for a 10 element array of sums for each category
	private int[] categoryOrder;	// category refs ordered by sums
	
	// Constructor
	FQform() {
		this.date = new Date();
		this.rating = new int[60];
		this.categorySum = new int[10];
		this.categoryOrder = new int[10];
	}
	
	// Getters and setter(s)
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	
	/**
	 * @return the rating
	 */
	public int[] getRating() {
		return rating;
	}

	/**
	 * @return the categorySum
	 */
	public int[] getCategorySum() {
		return categorySum;
	}

	/**
	 * @return the categoryOrder
	 */
	public int[] getCategoryOrder() {
		return categoryOrder;
	}

	/**
	 * @param index
	 * @param value rating value selected by user
	 */
	void setRating(int index, int value) {
		this.rating[index] = value;
	}
	
	/**
	 *  Process the form once the user has selected all ratings.
	 *  Accumulate category sums and sort categories sums into array of
	 *  category indexes in descending order of sums (the result will be 
	 *  the categoryOrder array which is an array of category indexes to
	 *  the category names TODO clarify this) 
	 */
	void processForm() {
		
		// calculate category sums and populate categorySum array
		int catIdx = 0;
		for(int i=0; i<60; i++) {
			categorySum[catIdx] += this.rating[i];
			if( (i+1) % 6 == 0)		// Six statement/rating pairs per category,
				catIdx++;			// so increment category index every 6 items.
		}
		
		// place categorySums in descending order into categoryOrder array
		int [] temp = new int[10];
		for(int i=0; i<10; i++)
			temp[i] = categorySum[i];	// copy to temp to preserve categorySums

		for (int i=0; i<10; i++) {		// for each of ten categories
			int maxSumIdx = 0;
			for (int j = 1; j<10; j++) {		//
				if (temp[j] > temp[maxSumIdx])	// find highest scoring category
					maxSumIdx = j;
			}
			temp[maxSumIdx] = -1;	// set the current max sum to -1 so it's lower
									// than the lowest possible sum and can't be
									// be selected as highest sum on next cycle
			
			categoryOrder[i] = maxSumIdx++;	// assign index of highest category
											// to current categoryOrder[] element
		}
	}
	
	/**
	 * @return true if form data is successfully written to (TODO file / database)
	 */
	boolean saveForm() {
		// TODO
		
		return true;
	}
	
	
	/**
	 * @return true if form data is successfully read from (TODO file / database)
	 */
	boolean readForm() {
		// TODO

		return true;
	}
}
