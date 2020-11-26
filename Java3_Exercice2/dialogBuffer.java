package src.Java3_Exercice2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class dialogBuffer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static int bufferValue;
	private static dialogBuffer dialog;
	private SpinnerNumberModel spinnerModel;
	private JSpinner bufferSpinner;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		showWnd();
	}
	
	public static void showWnd() {
		try {
			dialog = new dialogBuffer();
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	public dialogBuffer() {
		setBounds(100, 100, 198, 136);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Buffer");
			lblNewLabel.setBounds(44, 24, 42, 20);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(lblNewLabel);
		}
		try {
			spinnerModel = new SpinnerNumberModel(10,1,25,1);  
			bufferSpinner = new JSpinner(spinnerModel);
			bufferSpinner.setBounds(96, 24, 42, 20);
			contentPanel.add(bufferSpinner);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Erreur de selection du buffer", "Erreur", JOptionPane.WARNING_MESSAGE);
			
		}

		
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
				    	 bufferValue = (Integer) bufferSpinner.getValue();
				    	 dialog.dispose();
				     }
				 });
				
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
				    	 dialog.dispose();
					}
				});
			}
		}
	}
	
	public static Integer getBufferValue() {
		return bufferValue;
	}
}
