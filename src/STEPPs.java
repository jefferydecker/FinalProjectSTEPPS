import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.FlowLayout;

public class STEPPs extends JPanel implements ActionListener{

    //Create and set up the window.
    static JFrame frame = new JFrame("STEPPsDemo");

	/**
	 * Main menu for STEPPs final project. Need to add copyright and trademark info.
	 */
	public STEPPs() {
		//TODO: add copyright and trademark info
		
		JButton btnBorderlineEvaluationOf = new JButton("Borderline Evaluation of Severity over TIme (BEST)");
		btnBorderlineEvaluationOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        //Create and set up the content pane.
		        BESTpanel newContentPane = new BESTpanel();
		        newContentPane.setOpaque(true); //content panes must be opaque
		        frame.setContentPane(newContentPane);

			}
		});
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblToolsForStepps = new JLabel("Tools for STEPPs™");
		lblToolsForStepps.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		add(lblToolsForStepps);
		
		JLabel lblsystemsTrainingFor = new JLabel("(Systems Training for Emotional Predictability)");
		lblsystemsTrainingFor.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		add(lblsystemsTrainingFor);
		add(btnBorderlineEvaluationOf);
		
		JButton btnBorderlineEvaluationStatus = new JButton("Skills Monitoring Card");
		btnBorderlineEvaluationStatus.setToolTipText("Feature available in future version.");
		btnBorderlineEvaluationStatus.setEnabled(false);
		btnBorderlineEvaluationStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton button_2 = new JButton("Filter Questionnaire");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        //Create and set up the content pane.
		        filterPanel newContentPane = new filterPanel();
		        newContentPane.setOpaque(true); //content panes must be opaque
		        frame.setContentPane(newContentPane);
			}
		});
		add(button_2);
		add(btnBorderlineEvaluationStatus);
		
		JButton btnEmotionalIntensityContinuum = new JButton("Emotional Intensity Continuum");
		btnEmotionalIntensityContinuum.setToolTipText("Feature available in future version.");
		btnEmotionalIntensityContinuum.setEnabled(false);
		btnEmotionalIntensityContinuum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnEmotionalIntensityContinuum);
		
		JButton button_3 = new JButton("Reference Cards");
		button_3.setToolTipText("Feature available in future version.");
		button_3.setEnabled(false);
		add(button_3);
		
		JButton btnFutureToolsTo = new JButton("Future tools to come...");
		btnFutureToolsTo.setToolTipText("Feature available in future version.");
		btnFutureToolsTo.setEnabled(false);
		btnFutureToolsTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnFilterAwarenessLog = new JButton("Filter Awareness Log");
		btnFilterAwarenessLog.setToolTipText("Record the filters (helpful and unhelpful) you notice yourself using. Feature available in future version.");
		btnFilterAwarenessLog.setEnabled(false);
		add(btnFilterAwarenessLog);
		add(btnFutureToolsTo);
		
	}
	
	/**
     * Create the GUI and show it. 
     */
    private static void createAndShowGUI() {
 
        //Create and set up the content pane.
        STEPPs newContentPane = new STEPPs();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 
        //Display the window.
        frame.pack();
        frame.setSize(450, 335);
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
