import java.util.Date;

/**
 * BEST (Borderline Evaluation of Severity over Time) form data
 * 
 * BEST is copyrighted 1997 by Bruce Pfohl, M.D. and Nancee Blum, M.S.W.
 * 	University of Iowa, Dept of Psychiatry, 200 Hawkins Dr, Iowa City, IA 52242
 * 
 * from STEPPS (Systems Training for Emotional Predictability and Problem Solving)
 *  (c) Copyright 2002, 2008, N.S. Blum, N.E. Bartels, D. St. John, B. Pfohl,
 *  	All rights reserved.
 *  STEPPS^TM is the Trademark of N.S. Blum, N.E. Bartels, D. St. John, B. Pfohl,
 *  	All rights reserved.
 */

/**
 * @author Jeffery Decker
 *
 */
public class BESTform {
	private Date date;
	private int timePeriod;		// number of days being evaluated, 7, 30 or other
	private int [] rating;		// for a 15 element array of responses from 1 to 5
	private int [] categorySum;	// for a three element array of section totals
	private int overallScore;
	
	// Constructor
	BESTform() {
		this.date = new Date();		// It's unusual to do the evaluation on a day
									// isn't the current day, but a setter is provided
		this.timePeriod = 7;		// 7 is the default / typical time period
									// The UI will let user select either 7, 30
									// or some other number of days
		this.rating = new int[15];
		this.categorySum = new int[3];
	}
	

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
	 * @return the timePeriod
	 */
	public int getTimePeriod() {
		return timePeriod;
	}


	/**
	 * @param timePeriod the timePeriod to set
	 */
	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
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
	 * @return the overallScore
	 */
	public int getOverallScore() {
		return overallScore;
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
	 *  Accumulate category sums and and calculate overallScore
	 */
	void processForm() {
		
		// calculate category sums and populate categorySum array
		int catIdx = 0;
		for(int i=0; i<15; i++) {
			categorySum[catIdx] += this.rating[i];
			if(i==7 || i == 11 )	// the end of the first two sections are at 
				catIdx++;			// statement 8 and 12; adjust for arrays
		}							// starting at 0
		this.overallScore = categorySum[0] + categorySum[1] - categorySum[2];
	}
	
	/**
	 * @return true if record is successfully written to (TODO file / database)
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
