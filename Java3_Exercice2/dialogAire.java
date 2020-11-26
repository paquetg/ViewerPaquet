package src.Java3_Exercice2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("serial")
public class dialogAire extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static dialogAire dialog;
	private Double areaTruncated;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		showWnd();
	}
	
	public static void showWnd() {
		try {
			dialog = new dialogAire();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public dialogAire() {
		setBounds(100, 100, 198, 136);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		  
		JLabel lblAire = new JLabel("Aire:");
		lblAire.setBounds(10, 11, 46, 14);
		contentPanel.add(lblAire);
		
		areaTruncated = BigDecimal.valueOf(src.Java3_Exercice2.outilsJTS.areaValue)
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue();
		
		JLabel lblAireValue = new JLabel(String.valueOf(areaTruncated) + " m2");
		lblAireValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAireValue.setBounds(10, 27, 162, 23);
		contentPanel.add(lblAireValue);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
				    	 dialog.dispose();
					}
				});
				
			}
		}
	}
}
