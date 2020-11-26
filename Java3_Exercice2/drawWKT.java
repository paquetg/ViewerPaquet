package src.Java3_Exercice2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class drawWKT extends JPanel{

	public int pts = 0;
	private double scale = 1;
	private double width = 1;
	private double height = 1;
	
	public Double[] coordx = {};
	public Double[] coordy = {};

	private Color color;
	private Graphics2D graph2;

	/*
	 * Setters
	 */
	public void setCountPoint(int _pts) {
		pts = _pts;
	}

	public void setScale(double _scale) {
		scale = _scale;
	}

	public void setColor(Color _color) {
		color = _color;
	}
	
	public void setWndW(double widthidth) {
		width = widthidth;
	}
	
	public void setWndH(double heighteight) {
		height = heighteight;
	}
	
	/*
	 * Fonction d'affichage
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		graph2 = (Graphics2D) g;
		graph2.setColor(color);

		for(int i=0; i<pts-1; i++) {
			Shape rootline = new Line2D.Double(
					(coordx[i] - src.Java3_Exercice2.outilsJTS.xPts_min)*scale+width, - (coordy[i]-src.Java3_Exercice2.outilsJTS.yPts_min)*scale+height,
					(coordx[i+1] - src.Java3_Exercice2.outilsJTS.xPts_min)*scale+width, - (coordy[i+1]-src.Java3_Exercice2.outilsJTS.yPts_min)*scale+height);
		
			graph2.draw(rootline);
		}		
	}
}