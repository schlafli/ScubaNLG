package app;

/*
 * ScubaNLGMain.java
 *
 * Created on 01 February 2007, 15:57
 */

/**
 *This is the main class for the ScubaNLG application. Connects to the database, reads the dive log data from it and displays the dive locations on a map.
 * @author  ssripada
 */
import hci.AlladinLogMapView;
import hci.LinePlot;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import analytics.MyJDBC;
import analytics.Utils;

/**
 * This is the main class for the ScubaNLG application. This class has
 * functionality for reading and displaying dive log and profile information
 * from the database.
 * 
 * @author csc242
 */
public class ScubaNLGMain extends javax.swing.JFrame {
	
	private static final long	serialVersionUID	= 1L;
	private int					PREFERRED_WIDTH;
	private int					PREFERRED_HEIGHT;
	MyJDBC						dataBase;
	Vector<Double>				x					= new Vector<Double>(),
			y = new Vector<Double>();
	Double						diveNo;
	TreeMap<Long, Double>		profile, speedSeries;
	
	AlladinLogMapView			gPanel;
	
	/** Creates new ScubaNLGMain */
	public ScubaNLGMain() {
		ArrayList<String> diveLabels = new ArrayList<String>();
		// dataBase = new
		// MyJDBC("jdbc:odbc:scuba","sun.jdbc.odbc.JdbcOdbcDriver","","");
		dataBase = new MyJDBC("jdbc:sqlite:scuba.db", "org.sqlite.JDBC", "", "");
		
		dataBase.executeQuery("Select * from alladinlogs");
		
		for (int i = 0; i < dataBase.getRowCount(); i++) {
			x.add(new Double(Utils.convertLongitude((String) dataBase
					.getValueAt(i, 9))));
			y.add(new Double(Utils.convertLatitude((String) dataBase
					.getValueAt(i, 10))));
			diveLabels.add(String.valueOf(((Double) dataBase.getValueAt(i, 0))
					.intValue()));
			
		}
		
		gPanel = new AlladinLogMapView(0, 0, 360, 180, 20, 10, x, y,
				diveLabels, this);
		this.add(gPanel, java.awt.BorderLayout.CENTER);
		// set the preferred size of the scatterplot window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		PREFERRED_WIDTH = (new Double(0.75 * screenSize.width)).intValue();
		PREFERRED_HEIGHT = (new Double(0.75 * screenSize.height)).intValue();
		this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
		this.pack();
		this.setVisible(true);
		
	}
	
	/**
	 * Reads the profile information for the dive selected by the user in the
	 * map view
	 */
	public TreeMap<Long, Double> readDiveProfile(int row) {
		// read profile data for the dive
		profile = new TreeMap<Long, Double>();
		dataBase.executeQuery("Select * from alladinlogs");
		diveNo = (Double) dataBase.getValueAt(row, 0);
		
		dataBase.executeQuery("Select Time, Depth from alladin"
				+ diveNo.intValue());
		for (int i = 0; i < dataBase.getRowCount(); i++) {
			profile.put(
					Utils.getTimeInSeconds((String) dataBase.getValueAt(i, 0)),
					(Double) dataBase.getValueAt(i, 1));
		}
		
		return profile;
	}
	
	/**
	 * Displays the dive profile and the text for the dive selected by the user
	 */
	
	public void displayReport(TreeMap<Long, Double> diveProfile, String text) {
		long first = ((Long) profile.firstKey()).longValue();
		long last = ((Long) profile.lastKey()).longValue();
		Double maxDepth = Utils.myMax(profile.values());
		LinePlot gPanel = new LinePlot(0, 0, last + 120,
				maxDepth.doubleValue() + 10, diveProfile, first, maxDepth);
		
		javax.swing.JDialog dialog = new javax.swing.JDialog(this,
				"Dive Report " + diveNo.intValue());
		dialog.add(gPanel, java.awt.BorderLayout.CENTER);
		javax.swing.JEditorPane textPane = new javax.swing.JEditorPane(
				"text/html", "");
		
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(
				textPane);
		textPane.setEditable(false);
		textPane.setText("<b>Corpus Text:</b>\n" + text);
		dialog.add(scrollPane, java.awt.BorderLayout.SOUTH);
		
		// set the preferred size of the scatterplot window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		PREFERRED_WIDTH = (new Double(0.75 * screenSize.width)).intValue();
		PREFERRED_HEIGHT = (new Double(0.85 * screenSize.height)).intValue();
		/*
		 * dialog.addKeyListener(new KeyListener() {
		 * 
		 * @Override public void keyTyped(KeyEvent e) { if( e.getKeyCode() ==
		 * KeyEvent.VK_ESCAPE){ dialog. } }
		 * 
		 * @Override public void keyReleased(KeyEvent e) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void keyPressed(KeyEvent e) { // TODO Auto-generated
		 * method stub
		 * 
		 * } });
		 */
		dialog.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
		dialog.pack();
		dialog.setVisible(true);
	}
	
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		ScubaNLGMain scuba = new ScubaNLGMain();
		scuba.setTitle("Scuba NLG");
		scuba.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		scuba.setVisible(true);
	}
	
	public Double getDiveNo() {
		return diveNo;
	}
	
}
