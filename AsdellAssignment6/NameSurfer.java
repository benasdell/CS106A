/*
 * File: NameSurfer.java
 * ---------------------
 * Author: Benjamin Asdell
 * for Stanford CS106A Summer 2019
 * Section: Wednesday 5pm
 * TA: Trey Connelly
 * NameSurfer
 * 
 * This program is a graphing utility for examining the popularity trends in baby names
 * over the course of the decades beginning in the 1900s and ending in the 2000s. The 
 * program reads specifically formatted data from a text file, constructs a searchable
 * database from the aggregate data, then allows a user to search various common names
 * to see their respective popularity rankings over time. The display window is rescalable
 * for better viewing and allows for multiple name trends to be graphed simultaneously (at
 * the expense of readability). The most popular baby name in a given decade is graphed as
 * the highest point on the graphing area, and likewise the least popular (1000th overall)
 * in a given decade is graphed at the bottom of the graphing area. If a baby name does not
 * appear in the top 1000 in any decade, the program disregards that search; however, if it
 * only appears in the top 1000 in some decades, the decades for which it is not available 
 * are marked with asterisks at the very bottom of the graphing area.
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import acm.util.*;

public class NameSurfer extends GraphicsProgram implements NameSurferConstants {
	
	private JTextField searchBar = new JTextField(TEXT_FIELD_WIDTH);
	
	private NameSurferDataBase nameData; 
	
	private NameSurferEntry outputEntry;
	
	private int dataCount = 0;
	
	private ArrayList<String> displayedNames = new ArrayList<>();
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window. It also
	 * adds action listeners that monitor for user interactions with 
	 * the interactors and the Enter key.
	 */
	public void init() {
		nameData = new NameSurferDataBase(NAMES_DATA_FILE);
		setCanvasSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		JLabel nameLabel = new JLabel("Name: ");
		add(nameLabel, NORTH);
		searchBar.addActionListener(this);
		searchBar.setActionCommand("Go");
		add(searchBar, NORTH);
		JButton graphButton = new JButton("Graph");
		add(graphButton, NORTH);
		JButton clearButton = new JButton("Clear");
		add(clearButton, NORTH);
		addActionListeners();
	}

	/**
	 * This method is responsible for pushing the various methods
	 * related to graphing and clearing the display whenever an interactor
	 * is acted upon. It reads the textField, searches the nameData database, and
	 * then calls the drawDataLine function to graph on the display. It also includes
	 * functionality for removing all graphs from the display without removing
	 * the background.
	 */
	public void actionPerformed(ActionEvent e) {
		if ((e.getActionCommand().equals("Go")) || (e.getActionCommand().equals("Graph"))) {
			String name = searchBar.getText();
			outputEntry = nameData.findEntry(name);
			if (outputEntry != null) {
				if (!(displayedNames.contains(outputEntry.getName()))) { //checks to make sure name is not already graphed
				drawDataLine();
				displayedNames.add(name);
				dataCount++;
				}
			}
		}
		else if (e.getActionCommand().equals("Clear")) {
			displayedNames.clear();
			redraw();
			dataCount = 0;
		}
	}
	
	/**
	 * This class is responsible for detecting when the the canvas
	 * is resized. This method is called on each resize!
	 */
	public void componentResized(ComponentEvent e) { 
		redraw();
	}
	
	/**
	 * This helper method is called whenever a change or resize is made
	 * to the program window. This method removes all graphics displays and
	 * rebuilds them according to the new size of the window or new graphs
	 * that have been added. When resizing, all previously drawn graphs that
	 * have not been cleared are also scaled to fit the new display.
	 */
	private void redraw() {
		removeAll();
		drawMargins();
		drawVerticalAxes();
		dataCount = 0;
		for (int i = 0; i < displayedNames.size(); i++) {
			outputEntry = nameData.findEntry(displayedNames.get(i));
			drawDataLine();
			dataCount++;
		}
	}
	
	/*
	 * This method draws the upper and lower bounding lines of the graphing area on
	 * the graphics display. The margins are set by the constant GRAPH_MARGIN_SIZE, and
	 * therefore do not scale in vertical size when the window is resized.
	 */
	private void drawMargins() {
		GLine topMargin = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		topMargin.setColor(Color.BLACK);
		add(topMargin);
		GLine bottomMargin = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		bottomMargin.setColor(Color.BLACK);
		add(bottomMargin);
	}
	
	/*
	 * This method draws the vertical axis lines corresponding to each decade on the display.
	 * It also draws the labels below the bottom margin immediately to the right of each axis
	 * line. 
	 */
	private void drawVerticalAxes() {
		GLine[] verticalLines = new GLine[NDECADES];
		GLabel[] yearLabels = new GLabel[NDECADES];
		for (int i = 0; i < NDECADES; i++) {
			double xOffset = i * getWidth() / NDECADES;
			verticalLines[i] = new GLine(xOffset, 0, xOffset, getHeight());
			add(verticalLines[i]);
			String currentDecade = "" + (START_DECADE + i * 10);
			yearLabels[i] = new GLabel(currentDecade, xOffset, getHeight() - DECADE_LABEL_MARGIN_SIZE);
			add(yearLabels[i]);
		}
	}
	
	/*
	 * This is the main method for implementing the graphing of each data set from the NameSurferEntry.
	 * Using the name and ranking data from the instance variable holding the NSEntry, this method
	 * constructs an array of GPoints corresponding to each data point. It then marks each point with
	 * a label that declares the name and ranking (1-1000). This is done iteratively for every decade
	 * that has recorded ranking data. Then, the method draws lines using the x and y values of each
	 * data point to draw the graph on the display. The color of each matching set of data, lines, and
	 * labels is chosen using the colorSelection helper method.
	 */
	private void drawDataLine() {
		double graphSpace = getHeight() - 2 * GRAPH_MARGIN_SIZE;
		double graphScale = graphSpace / MAX_RANK;
		String currentName = outputEntry.getName();
		//beginning of data point and data label construction block
		GPoint[] dataPoints = new GPoint[NDECADES];
		for (int i = 0; i < NDECADES; i++) {
			double xOffset = i * getWidth() / NDECADES;
			int currentRank = outputEntry.getRank(i);
			dataPoints[i] = new GPoint(xOffset, GRAPH_MARGIN_SIZE + outputEntry.getRank(i) * graphScale);
			GLabel dataLabel = new GLabel(currentName + " " + currentRank);
			if (currentRank == 0) {
				dataPoints[i].setLocation(xOffset, getHeight() - GRAPH_MARGIN_SIZE);
				dataLabel.setLabel(currentName + " *");
			}
			dataLabel.setLocation(dataPoints[i]);
			dataLabel.setColor(colorSelection());
			add(dataLabel);
		}
		//beginning of line drawing block
		GLine[] dataLines = new GLine[NDECADES - 1];
		for (int j = 0; j < NDECADES - 1; j++) {
			dataLines[j] = new GLine(dataPoints[j].getX(), dataPoints[j].getY(), dataPoints[j + 1].getX(), dataPoints[j + 1].getY());
			dataLines[j].setColor(colorSelection());
			add(dataLines[j]);
		}
	}
	
	/*
	 * This short helper method cycles through the four colors as specified in this assignment. It
	 * makes use of the instance variable dataCount and the modulo function in order to cycle
	 * through the Color array and return a single Color object. The dataCount variable is
	 * changed and reset at various points throughout the program runtime to ensure each set
	 * of graphs and related graphics is a uniform color, but each consecutive graph is distinct
	 * from the previous.
	 */
	private Color colorSelection() {
		Color[] colorArray = {Color.BLACK, Color.RED, Color.BLUE, Color.MAGENTA};
		return colorArray[dataCount % 4];
	}
}
