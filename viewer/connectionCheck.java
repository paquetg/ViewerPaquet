package viewer;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;

public class connectionCheck extends Dialog {

	public static boolean connectionYes = false;
	public static String host, BDname, Table, Port, Username, PIN;
	protected Object result;
	protected Shell shell;
	private Text TextHost;
	private Label lblMachineHte;
	private Label lblNomDeLa;
	private Label lblNomDeLa_1;
	private Label lblPort;
	private Label lblUtilisateur;
	private Label lblMotDePasse;
	private Text TextNameBD;
	private Text TextNameTable;
	private Text TextPort;
	private Text TextUsername;
	private Text TextPIN;
	private Button btnAnnuler;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public connectionCheck(Shell parent, int style) {
		super(parent, style);
		setText("Informations de connexion");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		int locationX = ((getParent().getBounds().width - shell.getBounds().width)/2 + getParent().getBounds().x);
		int locationY = ((getParent().getBounds().height - shell.getBounds().height)/2 + getParent().getBounds().y);
		shell.setLocation(new Point(locationX, locationY));
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(508, 430);
		shell.setText(getText());
		
		lblMachineHte = new Label(shell, SWT.NONE);
		lblMachineHte.setBounds(46, 33, 137, 38);
		lblMachineHte.setText("Machine h\u00F4te:");
		
		lblNomDeLa = new Label(shell, SWT.NONE);
		lblNomDeLa.setText("Nom de la BD:");
		lblNomDeLa.setBounds(46, 77, 137, 38);
		
		lblNomDeLa_1 = new Label(shell, SWT.NONE);
		lblNomDeLa_1.setText("Nom de la table:");
		lblNomDeLa_1.setBounds(46, 121, 137, 38);
		
		lblPort = new Label(shell, SWT.NONE);
		lblPort.setText("Port:");
		lblPort.setBounds(46, 165, 137, 38);
		
		lblUtilisateur = new Label(shell, SWT.NONE);
		lblUtilisateur.setText("Utilisateur:");
		lblUtilisateur.setBounds(46, 209, 137, 38);
		
		lblMotDePasse = new Label(shell, SWT.NONE);
		lblMotDePasse.setText("Mot de passe:");
		lblMotDePasse.setBounds(46, 253, 137, 38);

		TextHost = new Text(shell, SWT.BORDER);
		TextHost.setBounds(189, 30, 277, 40);
		TextHost.setText("132.203.82.236");
		TextHost.setEnabled(false);
		
		TextNameBD = new Text(shell, SWT.BORDER);
		TextNameBD.setBounds(189, 75, 277, 40);
		TextNameBD.setText("geofla");
		TextNameBD.setEnabled(false);
		
		TextNameTable = new Text(shell, SWT.BORDER);
		TextNameTable.setBounds(189, 119, 277, 40);
		TextNameTable.setText("departement");
		TextNameTable.setEnabled(false);
		
		TextPort = new Text(shell, SWT.BORDER);
		TextPort.setBounds(189, 165, 277, 40);
		TextPort.setText("5432");
		TextPort.setEnabled(false);
		
		TextUsername = new Text(shell, SWT.BORDER);
		TextUsername.setBounds(189, 209, 277, 40);
		TextUsername.setText("etudiant");
		TextUsername.setEnabled(false);
		
		TextPIN = new Text(shell, SWT.BORDER);
		TextPIN.setBounds(189, 253, 277, 40);
		TextPIN.setText("scg");
		TextPIN.setEnabled(false);
		
		Button btnConnection = new Button(shell, SWT.NONE);
		btnConnection.setBounds(72, 335, 146, 40);
		btnConnection.setText("Connection");
		btnConnection.addListener(SWT.Selection, (Listener) new Listener()
		{
			public void handleEvent(Event event) {
				connectionYes = true;
				host = TextHost.getText();
				BDname = TextNameBD.getText();
				Table = TextNameTable.getText();
				Port = TextPort.getText();
				Username = TextUsername.getText();
				PIN = TextPIN.getText();
				shell.close();
			}
		});
		
		btnAnnuler = new Button(shell, SWT.NONE);
		btnAnnuler.setText("Annuler");
		btnAnnuler.setBounds(254, 335, 146, 40);
		btnAnnuler.addListener(SWT.Selection, (Listener) new Listener()
		{
			public void handleEvent(Event event) {
				shell.close();
			}
		});

	}
}
