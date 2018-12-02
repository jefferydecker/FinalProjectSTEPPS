import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

/**
 * BEST (Borderline Evaluation of Severity over Time) form data
 * BEST is copyrighted 1997 by Bruce Pfohl, M.D. and Nancee Blum, M.S.W.
 * 	University of Iowa, Dept of Psychiatry, 200 Hawkins Dr, Iowa City, IA 52242
 * from STEPPS (Systems Training for Emotional Predictability and Problem Solving)
 *  (c) Copyright 2002, 2008, N.S. Blum, N.E. Bartels, D. St. John, B. Pfohl,
 *  	All rights reserved.
 *  STEPPS^TM is the Trademark of N.S. Blum, N.E. Bartels, D. St. John, B. Pfohl,
 *  	All rights reserved.
 *  requires src/BESTquestions.txt and uses data file src/BESTdata.txt, which appends if
 *  it exists and creates if it does not.
 * @author jefferydecker@gmail.com jmdecker1@dmacc.edu
 *
 */
public class BESTform {
	/**
	 *  Constant for number of questions on the form.
	 */
	private final int NUMQ = 15;	// number of questions
	private Date date;
	private int timePeriod;		// number of days being evaluated, 7, 30 or other
	private int [] rating;		// for a 15 element array of responses from 1 to 5
	private int [] categorySum;	// for a three element array of section totals
	private int overallScore;
	/*
	 * field for fill in the blank on question 11
	 */
	private String q11blankFill;
	/*
	 * Priority Queue holding the statements yet to be evaluated. Initially loaded
	 * from a text file: src/BESTdata.txt.
	 */
	public PriorityQueue<String> stmntsPQ = new PriorityQueue<>(); 
	/**
	 * stack of statements that have already been evaluated to enable navigating
	 * to previous statements. Not yet implemented.
	 */
	public Stack<String> prevStack = new Stack();
	
	// Constructor
	BESTform() {
		this.date = new Date();		// It's unusual to do the evaluation on a day
									// isn't the current day, but a setter is provided
		this.timePeriod = 7;		// 7 is the default / typical time period
									// The UI will let user select either 7, 30
									// or some other number of days
		this.rating = new int[NUMQ];
		this.categorySum = new int[3];
		
		try {
			loadStmnts();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Date of evaluation.
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}


	/**
	 * Date of the evaluation. In this version it is hard-coded to the current
	 * date, but in future version the user will be able to enter a date so that
	 * past evaluations (performed on paper form) can be input.
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	
	/**
	 * The time period in number of days that is being evaluated. Hard-coded to 
	 * seven in this version.
	 * @return the timePeriod
	 */
	public int getTimePeriod() {
		return timePeriod; 
	}


	/**
	 * The time period that is being evaluated in number of days, typically 7
	 * or 30 days can be something else. For now it's set to 7 and the user is
	 * not given the option to change it. In a future version, I intend to read
	 * in the date of the previous evaluation and set it by default to the number
	 * of days since then.
	 * @param timePeriod the timePeriod to set
	 */
	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
	}


	/**
	 * get the specified user response
	 * @param index - the question/statement number (minus 1)
	 * @return a rating
	 */
	public int getRating(int index) {
		return rating[index];
	}

	/**
	 * get entire array of user responses
	 * @return rating array
	 */
	public int[] getRatings() {
		return rating;
	}


	/**
	 * @return the categorySum array
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
	 * Given the question number (minus 1) set its result to value provided.
	 * @param index - the question / statement number (minus 1)
	 * @param value rating value selected by user
	 */
	void setRating(int index, int value) {
		this.rating[index] = value;
	}
	
	/**
	 *  Process the form once the user has selected all ratings.
	 *  Accumulate category sums and and calculate overallScore.
	 */
	void processForm() {
		
		// calculate category sums and populate categorySum array
		int catIdx = 0;
		for(int i=0; i<NUMQ; i++) {
			categorySum[catIdx] += this.rating[i];
			if(i==7 || i == 11 )	// the end of the first two sections are at 
				catIdx++;			// statement 8 and 12; adjust minus 1 for arrays
		}
		this.overallScore = NUMQ + categorySum[0] + categorySum[1] - categorySum[2];
	}
	
	/**
	 * Load Statements into priority queue
	 * @throws FileNotFoundException on missing src/BESTquestions.txt
	 */
	void loadStmnts() throws FileNotFoundException {
		File file = new File("src/BESTquestions.txt"); 
		Scanner in = new Scanner(file); 
	    	  
		while (in.hasNextLine()) {
			stmntsPQ.add(in.nextLine());
		}
	    in.close();
	}
	
	/**
	 * Save form data to text file src/BESTdata.txt. Note: currently saving score and 
	 * section sums: considering not saving these.
	 * @return true if record is successfully written to (TODO file / database)
	 */
	boolean saveForm() {
		//TODO: add question 11  blankFill field
		
		// convert date to string
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String strDate = dateFormat.format(date);
		
		// convert rating[] to string
		String ratingStr="";
		for(int i=0; i<rating.length; i++) 
			ratingStr += (char) ('0' + rating[i]);
		System.out.println(ratingStr +" "+ ratingStr.length());
		System.out.println("Score="+this.overallScore + " " + this.categorySum[0]
				+ " " + this.categorySum[1] + " " + categorySum[2]);
			
		// set data file name
		File dataFile = new File("src/BESTdata.txt");
		try { 
			  
            // Open given file in append mode if file exists, otherwise create file. 
            BufferedWriter out = new BufferedWriter( new FileWriter(dataFile, dataFile.exists())); 
            
            out.write(strDate +" "+ timePeriod +" "+ overallScore +" "+ categorySum[0]
            		+" "+ categorySum[1] +" "+ categorySum[2] +" "+ ratingStr +"\n"); 
            out.close(); 
        } 
        catch (IOException e) { 
            System.out.println("exception occoured" + e); 
        } 
		
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
