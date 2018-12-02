import javax.swing.JPanel;
import javax.swing.JToggleButton;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class filterPanel extends JPanel implements ActionListener {
	private int stmntNum = 0;		// to track current question
	private final int EOQ=60;	//question #s at end of sections
	private FQform form = new FQform();
	
	// TODO - form.date and form.timePeriod fields
	//		- add copyright info
	//		- add keyboard bindings
	
	// initialize these components here so that the ActionListener can access them.
	private JTextArea txtrStmnt = new JTextArea();
	private JToggleButton tglbtn1 = new JToggleButton("0-Not true at all");
	private JToggleButton tglbtn2 = new JToggleButton("1-Rarely true");
	private JToggleButton tglbtn3 = new JToggleButton("2-Somewhat true");
	private JToggleButton tglbtn4 = new JToggleButton("3-Mostly true");
	private JToggleButton tglbtn5 = new JToggleButton("4-Definitely true");
	private ButtonGroup rateGrp = new ButtonGroup();
	private JTextArea txtrInstructions = new JTextArea();
	private final JTextArea txtrFormHead = new JTextArea();
	private JProgressBar progressBar = new JProgressBar();
	private JButton btnNext = new JButton("Next ->");
	private final JButton btnPrev = new JButton("<- Prev");

	public filterPanel() {
		setLayout(null);

		tglbtn1.setMnemonic(KeyEvent.VK_0);
		tglbtn1.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtn1.setBounds(292, 136, 144, 29);
		add(tglbtn1);

		tglbtn2.setMnemonic(KeyEvent.VK_1);
		tglbtn2.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtn2.setBounds(292, 160, 144, 29);
		add(tglbtn2);

		tglbtn3.setMnemonic(KeyEvent.VK_2);
		tglbtn3.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtn3.setBounds(292, 184, 144, 29);
		add(tglbtn3);

		tglbtn4.setMnemonic(KeyEvent.VK_3);
		tglbtn4.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtn4.setBounds(292, 208, 144, 29);
		add(tglbtn4);

		tglbtn5.setMnemonic(KeyEvent.VK_4);
		tglbtn5.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtn5.setBounds(292, 232, 144, 29);
		add(tglbtn5);
		
		rateGrp.add(tglbtn1);
		rateGrp.add(tglbtn2);
		rateGrp.add(tglbtn3);
		rateGrp.add(tglbtn4);
		rateGrp.add(tglbtn5);

		btnNext.setMnemonic(KeyEvent.VK_N);
		btnNext.setBounds(337, 279, 97, 29);
		btnNext.setToolTipText("Move to next Statement");
		add(btnNext);
		// add listener to next button
		btnNext.addActionListener(this);

		txtrStmnt.setToolTipText("Statement / Question to rate");
		txtrStmnt.setEditable(false);
		txtrStmnt.setLineWrap(true);
		txtrStmnt.setWrapStyleWord(true);
		txtrStmnt.setBackground(UIManager.getColor("Label.background"));
		txtrStmnt.setBounds(39, 136, 254, 123);
		
		// dequeue from statement priority queue and push onto prevStack
		form.prevStack.push(form.stmntsPQ.remove());
		// peek from prevStack and put into text area, sans question number (via .substring(4) )
		txtrStmnt.setText(form.prevStack.peek().substring(4));
		txtrStmnt.setAlignmentY(CENTER_ALIGNMENT);
		txtrStmnt.setCaretPosition(0);
		add(txtrStmnt);

		txtrInstructions.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtrInstructions.setWrapStyleWord(true);
		txtrInstructions.setText("Below are statements that a person might use to describe "
				+"him/herself. Please read each statement and decide how well it describes "
				+"you. Do not think too long about each question. Select the first choice that "
				+"comes to mind from the buttons on the right.");
		txtrInstructions.setLineWrap(true);
		txtrInstructions.setEditable(false);
		txtrInstructions.setBackground(SystemColor.window);
		txtrInstructions.setBounds(22, 32, 406, 92);
		add(txtrInstructions);
		txtrFormHead.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txtrFormHead.setWrapStyleWord(true);
		txtrFormHead.setText("Filter Questionnaire*");
		txtrFormHead.setLineWrap(true);
		txtrFormHead.setEditable(false);
		txtrFormHead.setBackground(SystemColor.window);
		txtrFormHead.setBounds(22, 6, 406, 21);
		add(txtrFormHead);
		
		progressBar.setMaximum(EOQ-1);
		progressBar.setBounds(22, 260, 406, 20);
		progressBar.setStringPainted(true);
		add(progressBar);
		btnPrev.setMnemonic('p');
		btnPrev.setEnabled(false);
		btnPrev.setToolTipText("Move to previous statement -- not yet implemented");
		btnPrev.setBounds(247, 279, 97, 29);
		
		add(btnPrev);
	}
	
	// Listens to Next button
	public void actionPerformed(ActionEvent e) {
		// find which rating button is selected 	/?TODO: put into a method, say, hasSelected()? setSelected() ?/
		boolean ratingSelectedFlag = true;	// assume one is selected until all are checked
		if(tglbtn1.isSelected())
			form.setRating(stmntNum, 1);
		else if(tglbtn2.isSelected())
			form.setRating(stmntNum, 2);
		else if(tglbtn3.isSelected())
			form.setRating(stmntNum, 3);
		else if(tglbtn4.isSelected())
			form.setRating(stmntNum, 4);
		else if(tglbtn5.isSelected())
			form.setRating(stmntNum, 5);
		else ratingSelectedFlag = false;	// none have been selected
		
		// if something was selected, move on to next statement/question
		if(ratingSelectedFlag && stmntNum < EOQ) {
			// reset toggle buttons
			rateGrp.clearSelection();
			
			// update progressBar and increment statement counter
			progressBar.setValue(stmntNum++);
			progressBar.setString(stmntNum +" of "+EOQ);

			// check for last question
			if(stmntNum == EOQ) {
				// disable button
				btnNext.setEnabled(false);

				// calculate totals and sort categories by category totals
				form.processForm();
				
				// show results
				String msg = form.getResultTable();
				JOptionPane.showMessageDialog(this, msg, "Results", JOptionPane.PLAIN_MESSAGE);

				// ask whether to save
				int choice = JOptionPane.showConfirmDialog(this, "Save form data?", "Choose",
						JOptionPane.YES_NO_OPTION);
				if (choice == 0)
					form.saveForm();
			}
			else
				// Load next statement
				// dequeue from statement priority queue and push onto prevStack
				form.prevStack.push(form.stmntsPQ.remove());
				// peek from prevStack and put into text area
				txtrStmnt.setText(form.prevStack.peek() /*.substring(4)*/ );
														// commented out code to skip
														// statement number... for  now.

		}
	}
}
