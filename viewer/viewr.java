package viewer;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgresql.util.PGobject;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Composite;


public class viewr {

	protected Shell shell;
	private Text BDname;
	private List list;
	private Composite graphicView;
	private String hostName, bdName, tableName, portNb, username, pin;
	private Text connected;
	private Connection bd;
	private Table featureTable;
	private TableItem item;
	private int wndWidth = 1500;
	private int wndHeight = 1150;
	private Display display;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			viewr window = new viewr();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(display, SWT.MIN | SWT.CLOSE);
		shell.setSize(1389, 833);
		shell.setText("Visualisateur de base de donnees");

		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2, (screenSize.height - shell.getBounds().height) / 2);

		viewer.connectionCheck.connectionYes = true;

		BDname = new Text(shell, SWT.BORDER);
		BDname.setBounds(121, 7, 800, 35);
		BDname.setEnabled(false);

		graphicView = new Composite(shell, SWT.NONE);
		graphicView.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		graphicView.setBounds(10, 48, 1118, 545);
		

		int listWidth = 230;
		list = new List(shell, SWT.BORDER);
		list.setBounds(1135, 89, 230, 504);

		Button btnConnection = new Button(shell, SWT.NONE);
		btnConnection.setBounds(1135, 7, 230, 35);
		btnConnection.setText("Connexion");

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 150, 35);
		lblNewLabel.setText("Base de donnees : ");

		connected = new Text(shell, SWT.BORDER);
		connected.setBounds(927, 7, 202, 35);
		connected.setEnabled(false);

		featureTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		featureTable.setBounds(10, 599, 1355, 193);
		featureTable.setHeaderVisible(true);
		featureTable.setLinesVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		featureTable.setLayoutData(data);

		Label lblTablesDeLa = new Label(shell, SWT.NONE);
		lblTablesDeLa.setAlignment(SWT.CENTER);
		lblTablesDeLa.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblTablesDeLa.setBounds(1135, 48, 230, 35);
		lblTablesDeLa.setText("Tables: ");

		btnConnection.addListener(SWT.Selection, (Listener) new Listener()
		{
			public void handleEvent(Event event) {
				connectionCheck connectionView = new connectionCheck(shell, 0);

				if (viewer.connectionCheck.connectionYes == true && btnConnection.getText() == "Connexion") {
					connectionView.open();
					connectionBD();
					shell.redraw();
					btnConnection.setText("Deconnexion");
					viewer.connectionCheck.connectionYes = false;
				}else {
					btnConnection.setText("Connexion");
					logOutBD();
				}
			}
		});

	}

	protected void connectionBD() {
		hostName = viewer.connectionCheck.host;
		bdName = viewer.connectionCheck.BDname;
		tableName = viewer.connectionCheck.Table;
		portNb = viewer.connectionCheck.Port;
		username = viewer.connectionCheck.Username;
		pin = viewer.connectionCheck.PIN;

		String url = "jdbc:postgresql://" + hostName + ":" + portNb + "/" + bdName;
		Properties props = new Properties();
		props.setProperty("user",username);
		props.setProperty("password",pin);

		try {
			bd = DriverManager.getConnection(url, props);
			connected.setText("Connexion etablie");
			BDname.setText(bdName);
			getListBD();
			displayBD();
			displayTableBD();

		} catch (SQLException e) {
			connected.setText("Erreur connexion");
			e.printStackTrace();
		}

	}

	protected void getListBD() {
		try (ResultSet tables = bd.getMetaData().getTables(null, null, "%", new String[] {"TABLE"})){
			while(tables.next()) {
				list.add(tables.getString("TABLE_NAME"));
			}
			int select = list.indexOf(tableName);
			list.select(select);

		} catch (SQLException e) {
			list.add("Erreur de Table");
			e.printStackTrace();
		}

	}

	protected void displayTableBD() {
		String query = "SELECT * FROM " + tableName + ";";

		try (Statement Stat = bd.createStatement();
				ResultSet rs = Stat.executeQuery(query)){

			TableColumn column = null;
			for(int i=1; i<rs.getMetaData().getColumnCount();i++) {
				column = new TableColumn(featureTable,  SWT.NONE);
				String name = rs.getMetaData().getColumnName(i);
				column.setText(name);
				column.pack();
			}


			while(rs.next()) {
				item = new TableItem(featureTable, SWT.NONE);
				for(int i=1; i<=rs.getMetaData().getColumnCount();i++) {
					String name = rs.getMetaData().getColumnName(i);
					String text = rs.getString(name);
					item.setText(i-1, text);
				}
			}
			column.pack();

		} catch (SQLException e) {
			MessageBox messagebox = new MessageBox(shell, SWT.ICON_WARNING);
			messagebox.setMessage("Erreur dans le chargement de la base de donnees");
			messagebox.setText("Erreur");
			messagebox.open();
			e.printStackTrace();
		}
	}

	protected void displayBD() {
		
		String query = "SELECT geom, gid FROM " + tableName  + ";";

		try (Statement Stat = bd.createStatement();
				ResultSet rs = Stat.executeQuery(query)){
			
			while(rs.next()) {
				PGobject geom = (PGobject)rs.getObject(1); 
				Geometry geom2 = PGgeometry.geomFromString(geom.getValue());
			}
		} catch (SQLException e) {
			MessageBox messagebox = new MessageBox(shell, SWT.ICON_WARNING);
			messagebox.setMessage("Erreur dans le chargement de la base de donnees");
			messagebox.setText("Erreur");
			messagebox.open();
			e.printStackTrace();
		}
	}

	protected void logOutBD() {
		try {
			bd.close();
			list.removeAll();
			connected.setText("Conn. terminee");
			BDname.setText("");
			featureTable.removeAll();
			TableColumn[] columns = featureTable.getColumns();
			for (int i = 0; i < columns.length; i++) {
				columns[i].dispose();}
			viewer.connectionCheck.connectionYes = true;
		} catch (SQLException e) {
			MessageBox messagebox = new MessageBox(shell, SWT.ICON_WARNING);
			messagebox.setMessage("Impossible de deconnecter la base de donnees");
			messagebox.setText("Erreur");
			messagebox.open();
			System.out.println("Deconnexion impossible: " + e.toString());
		}

	}
}
