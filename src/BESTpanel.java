import javax.swing.JPanel;
import javax.swing.JToggleButton;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.Component;
import java.awt.event.KeyEvent;

/**
 * @author Jeffery Decker jefferydecker@gmail.com, jmdecker1@dmacc.edu
 * GUI for BEST form from STEPPs [add copyright and trademark info]
 *
 */
public class BESTpanel extends JPanel implements ActionListener {
	private int stmntNum = 0; // to track current question
	private final int EOA = 8, EOB = 12, EOC = 15; // question #s at end of sections
	private BESTform form = new BESTform();

	/* TODO - form.date and form.timePeriod fields
	 * - add copyright info
	 * - BEST history graph, etc)
	 * - add keyboard bindings
	 * - add q11blankFill input on stmntNum==11
	 * - implement prev question navigation AND navigate to next question when option selected...
	 *   ... AND only have Next button enabled when nothing has been selected yet ...
	 *   ... AND when navigating through questions already answered, set selection from getRating()...
	 *   ... AND add action listeners to toggle buttons
	 */

	// initialize these components here so that the ActionListener can access them.
	/**
	 * 
	 */
	private JTextArea txtrStmnt = new JTextArea();
	private JToggleButton tglbtn1 = new JToggleButton("None/slight");
	private JToggleButton tglbtn2 = new JToggleButton("Mild");
	private JToggleButton tglbtn3 = new JToggleButton("Moderate");
	private JToggleButton tglbtn4 = new JToggleButton("Severe");
	private JToggleButton tglbtn5 = new JToggleButton("Extreme");
	private ButtonGroup rateGrp = new ButtonGroup();
	private JTextArea txtrInstructions = new JTextArea();
	private final JTextArea txtrFormHead = new JTextArea();
	private JTextArea txtrSectHead = new JTextArea();
	private JProgressBar progressBar = new JProgressBar();
	private JButton btnNext = new JButton("Next ->");
	private final JButton button = new JButton("<- Prev");

	public BESTpanel() {
		setLayout(null);
		tglbtn1.setMnemonic(KeyEvent.VK_1);

		tglbtn1.setBounds(305, 136, 131, 29);
		add(tglbtn1);
		tglbtn2.setMnemonic(KeyEvent.VK_2);

		tglbtn2.setBounds(305, 160, 131, 29);
		add(tglbtn2);
		tglbtn3.setMnemonic(KeyEvent.VK_3);

		tglbtn3.setBounds(305, 184, 131, 29);
		add(tglbtn3);
		tglbtn4.setMnemonic(KeyEvent.VK_4);

		tglbtn4.setBounds(305, 208, 131, 29);
		add(tglbtn4);
		tglbtn5.setMnemonic(KeyEvent.VK_5);

		tglbtn5.setBounds(305, 232, 131, 29);
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
		txtrStmnt.setBounds(39, 160, 254, 99);
		txtrStmnt.setText(form.stmntsPQ.remove().substring(4));
		txtrStmnt.setAlignmentY(CENTER_ALIGNMENT);
		txtrStmnt.setCaretPosition(0);
		add(txtrStmnt);

		txtrInstructions.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtrInstructions.setWrapStyleWord(true);
		txtrInstructions.setText("Sections A&B: The highest rating (Extreme) means that "
				+ "the item caused extreme distress, severe difficulties with relationships, "
				+ "and/or kept you from getting things done. The lowest rating means it "
				+ "caused little or no problems.");
		txtrInstructions.setLineWrap(true);
		txtrInstructions.setEditable(false);
		txtrInstructions.setBackground(SystemColor.window);
		txtrInstructions.setBounds(22, 32, 406, 71);
		add(txtrInstructions);

		txtrFormHead.setWrapStyleWord(true);
		txtrFormHead.setText("Borderline Evaluation of Severity over Time    (version 1.7)");
		txtrFormHead.setLineWrap(true);
		txtrFormHead.setEditable(false);
		txtrFormHead.setBackground(SystemColor.window);
		txtrFormHead.setBounds(22, 6, 406, 21);
		add(txtrFormHead);
		txtrSectHead.setForeground(new Color(128, 0, 128));

		txtrSectHead.setEditable(false);
		txtrSectHead.setWrapStyleWord(true);
		txtrSectHead.setText("Section A. Thoughts and Feelings:");
		txtrSectHead.setLineWrap(true);
		txtrSectHead.setBackground(SystemColor.window);
		txtrSectHead.setBounds(26, 115, 410, 17);
		add(txtrSectHead);

		progressBar.setMaximum(EOC - 1);
		progressBar.setBounds(22, 260, 406, 20);
		progressBar.setStringPainted(true);
		add(progressBar);
		button.setToolTipText("Move to previous statement -- not yet implemented");
		button.setMnemonic('p');
		button.setEnabled(false);
		button.setBounds(244, 279, 97, 29);
		
		add(button);
	}

	// Listens to Next button
	public void actionPerformed(ActionEvent e) {
		// find which rating button is selected
		boolean ratingSelectedFlag = true; // assume one is selected until all are checked
		if (tglbtn1.isSelected())
			form.setRating(stmntNum, 1);
		else if (tglbtn2.isSelected())
			form.setRating(stmntNum, 2);
		else if (tglbtn3.isSelected())
			form.setRating(stmntNum, 3);
		else if (tglbtn4.isSelected())
			form.setRating(stmntNum, 4);
		else if (tglbtn5.isSelected())
			form.setRating(stmntNum, 5);
		else
			ratingSelectedFlag = false;

		// if something was selected, move on to next statement/question
		if (ratingSelectedFlag && stmntNum < EOC) {
			// reset toggle buttons
			rateGrp.clearSelection();

			// update progressBar and increment statement counter
			progressBar.setValue(stmntNum++);
			progressBar.setString(stmntNum + " of " + EOC);

			// check for end of section B ...
			if (stmntNum == EOB) {
				// Notify user of new situation
				JOptionPane.showMessageDialog(this,
						"Entering Section C: Behaviors (Positive)\nNote: New Instructions and Selections",
						"Note: New Section", JOptionPane.INFORMATION_MESSAGE);
				// ... use alternate option text
				tglbtn1.setText("Almost never");
				tglbtn2.setText("Sometimes");
				tglbtn3.setText("Half of the time");
				tglbtn4.setText("Most of the time");
				tglbtn5.setText("Almost always");

				// ... switch instructions
				txtrInstructions.setText(
						"\nFor Section C:\nIndicate how often you used the following positive behaviors:");
				// ... switch section heading
				txtrSectHead.setText("\t\tSection C: BEHAVIORS (Positive):");
			}

			// check for end of section A, switch section heading
			if (stmntNum == EOA) {
				String msg = "Entering Section B: Behaviors (Negative)";
				txtrSectHead.setText("\t"+msg);
				JOptionPane.showMessageDialog(this, msg, "Note: New Section", JOptionPane.INFORMATION_MESSAGE);
			}
			
			// TODO: add fill in blank for question 11 - stmntNum == 11, get input 

			// on final question
			if (stmntNum == EOC) {
				//disable button and process form
				btnNext.setEnabled(false);
				form.processForm();
				
				// show results
				String msg = "Overall Score: " + form.getOverallScore();
				JOptionPane.showMessageDialog(this, msg, "Final Score", JOptionPane.PLAIN_MESSAGE);

				// ask whether to save
				int choice = JOptionPane.showConfirmDialog(this, "Save form data?", "Choose",
						JOptionPane.YES_NO_OPTION);
				if (choice == 0)
					form.saveForm();
			}
			else	// else if not on final question
			// Load text of next statement to rate, skipping line# with .substring(4)
			txtrStmnt.setText(form.stmntsPQ.remove()/* .substring(4) */);
		}
	}
}
