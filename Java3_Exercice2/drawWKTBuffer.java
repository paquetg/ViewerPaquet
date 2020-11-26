package src.Java3_Exercice2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import javax.swing.JPanel;


@SuppressWarnings("serial")


public class drawWKTBuffer extends JPanel{

	public int ptsPolygon = 0;
	public int ptsBuffer = 0;
	private double scale = 1;
	private double width = 1;
	private double height = 1;
	
	public Double[] coordxPolygon = {};
	public Double[] coordyPolygon = {};
	public Double[] coordxBuffer = {};
	public Double[] coordyBuffer = {};

	private Color colorPolygon;
	private Color colorBuffer;
	
	private Graphics2D graph2;

	/*
	 * Setters
	 */
	public void setCountPointPolygon(int _pts) {
		ptsPolygon = _pts;
	}

	public void setCountPointBuffer(int _pts) {
		ptsBuffer = _pts;
	}
	
	public void setScale(double _scale) {
		scale = _scale;
	}

	public void setColorPolygon(Color _color) {
		colorPolygon = _color;
	}
	
	public void setColorBuffer(Color _color) {
		colorBuffer = _color;
	}
	
	public void setWndW(double _width) {
		width = _width;
	}
	
	public void setWndH(double _height) {
		height = _height;
	}

	/*
	 * Fonction d'affichage
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		graph2 = (Graphics2D) g;
		
		// Polygone		
		graph2.setColor(colorPolygon);
		for(int i=0; i<ptsPolygon-1; i++) {
			Shape shpPolygon = new Line2D.Double(
					(coordxPolygon[i] - src.Java3_Exercice2.outilsJTS.xPts_min)*scale+width, - (coordyPolygon[i]-src.Java3_Exercice2.outilsJTS.yPts_min)*scale+height,
					(coordxPolygon[i+1] - src.Java3_Exercice2.outilsJTS.xPts_min)*scale+width, - (coordyPolygon[i+1]-src.Java3_Exercice2.outilsJTS.yPts_min)*scale+height);
			graph2.draw(shpPolygon);
		}	
		
		// Buffer
		graph2.setColor(colorBuffer);
		for(int i=0; i<ptsBuffer-1; i++) {
			Shape shpBuffer = new Line2D.Double(
					(coordxBuffer[i] - src.Java3_Exercice2.outilsJTS.xPts_min)*scale+width, - (coordyBuffer[i]-src.Java3_Exercice2.outilsJTS.yPts_min)*scale+height,
					(coordxBuffer[i+1] - src.Java3_Exercice2.outilsJTS.xPts_min)*scale+width, - (coordyBuffer[i+1]-src.Java3_Exercice2.outilsJTS.yPts_min)*scale+height);
			graph2.draw(shpBuffer);
		}	
	}
}