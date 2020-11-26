package src.Java3_Exercice2;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import java.awt.Color;

public class outilsJTS {

	private JFrame frame;
	private JTextField textField;
	protected JFileChooser ouvrirFichier;
	private String textWKT;
	private Geometry geom;
	private Geometry geomBuffer;
	private Dimension dimGraphic;
	private float bufferDist;
	private JPanel graphicView;
	
	public Double[] yPts;
	public Double[] xPts;
	public Double[] yPtsPolygon;
	public Double[] xPtsPolygon;
	public Double[] yPtsBuffer;
	public Double[] xPtsBuffer;
	
	public static double xPts_min;
	public double xPts_max = 0;
	public static double yPts_min;
	public double yPts_max = 0;
	public static double areaValue;
	
	private Double scale;
	public static GeneralPath polygon;
	private BufferedReader br;
	private String activePath;
	private File file;
	private FileNameExtensionFilter filtre;
	public int bufferValueOutil;
	private WKTReader reader;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					outilsJTS window = new outilsJTS();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public outilsJTS() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1028, 706);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblChargement = new JLabel("Chargement du fichier: ");
		lblChargement.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChargement.setBounds(53, 27, 186, 25);
		frame.getContentPane().add(lblChargement);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		textField = new JTextField();
		textField.setBounds(174, 24, 572, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnChargerFichier = new JButton("Charge Fichier");
		btnChargerFichier.setBounds(792, 27, 193, 25);
		frame.getContentPane().add(btnChargerFichier);

		JButton btnEffacerTout = new JButton("Effacer Tout");
		btnEffacerTout.setBounds(403, 633, 193, 25);
		frame.getContentPane().add(btnEffacerTout);

		JLabel lblOutilsJTS = new JLabel("Outils JTS: ");
		lblOutilsJTS.setBounds(53, 74, 104, 22);
		frame.getContentPane().add(lblOutilsJTS);

		JButton btnBuffer = new JButton("Buffer");
		btnBuffer.setBounds(174, 74, 89, 23);
		frame.getContentPane().add(btnBuffer);

		JButton btnAire = new JButton("Aire");
		btnAire.setBounds(303, 74, 89, 23);
		frame.getContentPane().add(btnAire);

		JButton btnIntersection = new JButton("Intersection ?");
		btnIntersection.setBounds(426, 74, 117, 23);
		frame.getContentPane().add(btnIntersection);

		JButton btnUnion = new JButton("Union");
		btnUnion.setBounds(565, 74, 89, 23);
		frame.getContentPane().add(btnUnion);
		
		graphicView = new JPanel();
		graphicView.setBorder(null);
		graphicView.setBounds(10, 107, 992, 515);
		frame.getContentPane().add(graphicView);

		// Charger fichier et geometrie
		btnChargerFichier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Fichier
				try {
					ouvrirFichier = new JFileChooser("../lab-gmt4101_gmt7023-a2020-03-partie_1-files/exercice_2/wkt/");
					filtre = new FileNameExtensionFilter(".wkt", "wkt");
					ouvrirFichier.setFileFilter(filtre);
					int returnVal = ouvrirFichier.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						activePath = ouvrirFichier.getSelectedFile().getPath();
						file = new File(activePath); 
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Aucune selection de fichier", "Erreur", JOptionPane.WARNING_MESSAGE);
					
				}
				
				// Lecture geometrie
				try {
					br = new BufferedReader(new FileReader(file));
					textWKT = br.readLine();
					reader = new WKTReader();
					geom = reader.read(textWKT);
					System.out.println(textWKT);

					// Creation polygone
					/*
					polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPts.length);
					polygon.moveTo(xPts[0], yPts[0]);
					for (i=0; i<xPts.length; i++) {
						polygon.lineTo(xPts[i], yPts[i]);
					}
					polygon.closePath();
					*/
					
					graphicView.removeAll();
					drawPolygon(geom, Color.BLACK);
					System.out.println(geom.intersects(geom));
					Geometry geomInstersetion = geom.intersection(geom);
					Boolean hasIntersection = geom.intersects(geom);
					
					System.out.println(hasIntersection + " " + geomInstersetion.getGeometryType());
					
					
				} catch (IOException | ParseException e1) {
					JOptionPane.showMessageDialog(null, "Erreur de geometrie", "Erreur", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		// Dialog buffer
		btnBuffer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogBuffer.showWnd();
				bufferDist = dialogBuffer.getBufferValue();
				geomBuffer = geom.buffer(bufferDist);
				
				graphicView.removeAll();
				drawPolygonBuffer(geom, geomBuffer, Color.BLACK, Color.RED);
			}
		});
		
		// Dialog aire
		btnAire.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogAire.showWnd();
				System.out.println(geom.getEnvelope().getArea());
				System.out.println(geom.buffer(bufferDist).getEnvelope().getArea());
			}
		});
		
	}
	
	/**
	 * Fonction d'affichage de polygone
	 * @param _geom : Geometrie du polygone a creer et afficher
	 */
	private void drawPolygon(Geometry _geom, Color _color) {
		// Creation de tableaux de coordonnees vides
		xPts = new Double[_geom.getNumPoints()];
		yPts = new Double[_geom.getNumPoints()];
		System.out.println(_geom.getNumPoints() + " points");
		
		// Reccuperation des coordonnees
		int i = 0;
		for (Coordinate coords : _geom.getCoordinates()) {
			// Initialisation des points minimals
			if (i == 0) {
				xPts_min = coords.x;
				yPts_min = coords.y;
			}
			// Detection des points extremes
			else {
				if (coords.x > xPts_max) {
					xPts_max = coords.x;
				}
				if (coords.x < xPts_min) {
					xPts_min = coords.x;
				}
				if (coords.y > yPts_max) {
					yPts_max = coords.y;
				}
				if (coords.y < yPts_min) {
					yPts_min = coords.y;
				}
			}
			
			xPts[i] = (Double) coords.x;
			yPts[i] = (Double) coords.y;
			
			System.out.println(String.valueOf(xPts[i]) + "," + String.valueOf(yPts[i]));
			
			i++;
			}
		
		// Calcul de l'aire
		areaValue = _geom.getEnvelope().getArea();
		
		System.out.println("----\nmin : " + xPts_min + "," + yPts_min + "\nmax : " + xPts_max + "," + yPts_max);
		
		// Variables graphiques
		drawWKT dwkt = new drawWKT();
		dimGraphic = new Dimension(graphicView.getWidth(), graphicView.getHeight());

		// Parametres graphiques du polygone
		dwkt.setPreferredSize(dimGraphic);
		scale = setPolygonScale(xPts_min, xPts_max, yPts_min, yPts_max);
		dwkt.setScale(scale);
		System.out.println("----\n" + scale);
		dwkt.setWndW(0.10*graphicView.getWidth());
		dwkt.setWndH(0.90*graphicView.getHeight());
		dwkt.setCountPoint(_geom.getNumPoints());
		dwkt.setColor(_color);
		dwkt.coordx = xPts;
		dwkt.coordy = yPts;
		
		graphicView.add(dwkt);
		graphicView.validate();
	}

	
	/**
	 * Fonction d'affichage de polygone avec son buffer
	 * @param _geom : Geometrie du polygone a creer et afficher
	 */
	private void drawPolygonBuffer(Geometry _geomPolygon, Geometry _geomBuffer, Color _colorPolygon, Color _colorBuffer) {
		// Creation de tableaux de coordonnees vides
		xPtsPolygon = new Double[_geomPolygon.getNumPoints()];
		yPtsPolygon = new Double[_geomPolygon.getNumPoints()];
		xPtsBuffer = new Double[_geomBuffer.getNumPoints()];
		yPtsBuffer = new Double[_geomBuffer.getNumPoints()];
		
		// Reccuperation des coordonnees du polygone
		int i = 0;
		for (Coordinate coords : _geomPolygon.getCoordinates()) {
			xPtsPolygon[i] = (Double) coords.x;
			yPtsPolygon[i] = (Double) coords.y;
			
			i++;
		}
		
		// Reccuperation des coordonnees du buffer
		i = 0;
		for (Coordinate coords : _geomBuffer.getCoordinates()) {
			// Initialisation des points minimals
			if (i == 0) {
				xPts_min = coords.x;
				yPts_min = coords.y;
			}
			// Detection des points extremes
			else {
				if (coords.x > xPts_max) {
					xPts_max = coords.x;
				}
				if (coords.x < xPts_min) {
					xPts_min = coords.x;
				}
				if (coords.y > yPts_max) {
					yPts_max = coords.y;
				}
				if (coords.y < yPts_min) {
					yPts_min = coords.y;
				}
			}
			// Coordonnees
			xPtsBuffer[i] = (Double) coords.x;
			yPtsBuffer[i] = (Double) coords.y;
			
			i++;
		}	
		
		// Calcul de l'aire
		areaValue = _geomBuffer.getEnvelope().getArea();
		
		// Variables graphiques
		drawWKTBuffer dwktBuffer = new drawWKTBuffer();
		dimGraphic = new Dimension(graphicView.getWidth(), graphicView.getHeight());

		// Parametres graphiques du polygone et du buffer
		scale = setPolygonScale(xPts_min, xPts_max, yPts_min, yPts_max);
		dwktBuffer.setPreferredSize(dimGraphic);
		dwktBuffer.setScale(scale);
		dwktBuffer.setWndW(0.10*graphicView.getWidth());
		dwktBuffer.setWndH(0.90*graphicView.getHeight());
		dwktBuffer.setCountPointPolygon(_geomPolygon.getNumPoints());
		dwktBuffer.setCountPointBuffer(_geomBuffer.getNumPoints());
		dwktBuffer.setColorPolygon(_colorPolygon);
		dwktBuffer.setColorBuffer(_colorBuffer);

		dwktBuffer.coordxPolygon = xPtsPolygon;
		dwktBuffer.coordyPolygon = yPtsPolygon;
		dwktBuffer.coordxBuffer = xPtsBuffer;
		dwktBuffer.coordyBuffer = yPtsBuffer;
		
		graphicView.add(dwktBuffer);
		graphicView.validate();
	}
	
	/**
	 * Foncion de calcul d'echelle pour l'affichage d'un polygone
	 * @param _xPts_min : Coordonnee minimale en x
	 * @param _xPts_max : Coordonnee maximale en x
	 * @param _yPts_min : Coordonnee minimale en y
	 * @param _yPts_max : Coordonnee maximale en y
	 * @return
	 */
	private Double setPolygonScale(Double _xPts_min, Double _xPts_max, Double _yPts_min, Double _yPts_max ) {
		// Initialisation
		Double _scale;
		Double _polygonWidth = _xPts_max - _xPts_min;
		Double _polygonHeight = _yPts_max - _yPts_min;
		
		// Polygone horizontal
		if (_polygonWidth >= _polygonHeight) {
			_scale = 0.90*graphicView.getWidth()/_polygonWidth;
			if (_polygonHeight*_scale >= 0.90*graphicView.getHeight()) {
				_scale = 0.90*graphicView.getHeight()/_polygonHeight;
			}
		}
		
		// Polygon vertical
		else {
			_scale = 0.90*graphicView.getHeight()/_polygonHeight;
			if (_polygonWidth*_scale >= 0.90*graphicView.getWidth()) {
				_scale = 0.90*graphicView.getWidth()/_polygonWidth;
			}
		}
		return _scale;
	}
	
}

