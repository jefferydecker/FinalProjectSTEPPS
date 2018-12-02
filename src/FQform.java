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
 *  Filter Questionnaire form data
 *  from STEPPS (Systems Training for Emotional Predictability and Problem Solving)
 *  (c) Copyright 2002, 2008, N.S. Blum, N.E. Bartels, D. St. John, B. Pfohl,
 *  	All rights reserved.
 *  STEPPS^TM is the Trademark of N.S. Blum, N.E. Bartels, D. St. John, B. Pfohl,
 *  	All rights reserved.
 * requires src/filterQ.txt and uses data file src/FQdata.txt
 * @author Jeffery Decker jefferydecker@gmail.com, jmdecker1@dmacc.edu
 *
 */
public class FQform {
	private Date date;
	private int[] rating;		// for a 60 element array of responses from 0 to 4
	private int[] categorySum;	// for a 10 element array of sums for each category
	private int[] categoryOrder;	// category refs ordered by sums
	// Priority Queue for storing statements to be rated.
	public PriorityQueue<String> stmntsPQ = new PriorityQueue<>();
	// stack for statements that have been rated for navigating backwards through statements
	public Stack<String> prevStack = new Stack<>();
	public String[] categoryName;	// an array to hold the 10 category names
	
	// Constructor
	FQform() {
		this.date = new Date();
		this.rating = new int[60];
		this.categorySum = new int[10];
		this.categoryOrder = new int[10];
		
		// load priority queue with statements to be rated
		try {
			loadStmnts();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// initialize categoryName array -- used to display results
		this.categoryName = new String[] {
				"Emotional Deprivation",  "Abandonment", "Mistrust",
				"Defectiveness/Social Undesirability", "Failure to Achieve",
				"Vulnerability to harm and illness", "Self-sacrifice",
				"Subjugation", "Unrelenting Standards", "Entitlement"
		};
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
	 * @param index - question number (minus 1)
	 * @param value rating value selected by user
	 */
	public void setRating(int index, int value) {
		this.rating[index] = value;
	}
	
	/**
	 *  Process the form once the user has selected all ratings.
	 *  Accumulate category sums and sort categories sums into array of
	 *  category indexes in descending order of sums (the result will be 
	 *  the categoryOrder array which is an array of category indexes to
	 *  the category names TODO clarify this) 
	 */
	public void processForm() {
		
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
									// selected as highest sum on next cycle
			
			categoryOrder[i] = maxSumIdx++;	// assign index of highest category
											// to current categoryOrder[] element
		}
	}

	/**
	 * Load Statements into priority queue
	 * @throws FileNotFoundException on file not found
	 */
	public void loadStmnts() throws FileNotFoundException {
		File file = new File("src/filterQ.txt"); 
		Scanner in = new Scanner(file); 
	    	  
		while (in.hasNextLine()) {
			stmntsPQ.add(in.nextLine());
		}
	    in.close();
	}
	

	/**
	 * Save form data to text file src/FQdata.txt
	 * @return true if form data is successfully written to (TODO file / database)
	 */
	public boolean saveForm() {
		// convert date to string
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String strDate = dateFormat.format(date);
		
		// convert rating[] to string
		String ratingStr="";
		for(int i=0; i<rating.length; i++) 
			ratingStr += (char) ('0' + rating[i]);
			
		// set data file name
		File dataFile = new File("src/FQdata.txt");
		try { 
			  
            // Open given file in append mode if file exists, otherwise create file. 
            BufferedWriter out = 
            		new BufferedWriter( new FileWriter(dataFile, dataFile.exists()) ); 
            
            // build data record
            String data = strDate +" ";
            for (int i=0; i<categorySum.length; i++)
            	data += categorySum[i] + " ";
			data += ratingStr +"\n"; 
			
            out.write(data);
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
	public boolean readForm() {
		// TODO

		return true;
	}
	
	/**
	 * Generate HTML code with a table listing category scores and names sorted by category scores descending
	 * @return String containing list of filter categories and scores, sorted by 
	 * category scores in descending order in html table form.
	 */
	public String getResultTable() {
		String resultTable = "<html><h2>Filter Questionnaire Results</h2>"
				+"<table><tr><th>Score</th><th>Filter Category</th></tr>";
		for(int i=0; i<categorySum.length; i++) {
			resultTable += "<tr><td>" +categorySum[categoryOrder[i]]  + "</td>";
			resultTable+= "<td>" + categoryName[categoryOrder[i]] + "</td></tr>";
		}
		resultTable += "</table></html>";
		
		return resultTable;
	}
}
