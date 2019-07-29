	/*
 * File: MouseReporter.java
 * -----------------------------
 * Program written by Benjamin Asdell
 * Stanford University CS106A, Copyright 2019
 * Section: Wednesday 5pm
 * TA: Trey Connolly
 * Problem 2
 * 
 * This program is a simple mouse coordinate tracker that uses a mouse event listener to record and display the current
 * x and y coordinates of the mouse during the program runtime. The label used to display the coordinates also turns from
 * blue to red if the mouse is hovering over the label itself.
 */

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.*;

public class MouseReporter extends GraphicsProgram {

	// A constant for the x value of the label
	private static final int INDENT = 20;
	
	// This variable instances a blank label to be printed later, visible to the entire program
	private GLabel label = new GLabel("");
	
	public void run() {	
		// this code already adds the label to the screen
		label.setFont("Courier-24");
		label.setColor(Color.BLUE);
		
		// this setLabel method takes in a "String" 
		label.setLabel(0 + "," + 0);
		
		// add the label to the screen!
		add(label, INDENT, getHeight()/2);
	}
	
	public void mouseMoved(MouseEvent e) {
		double mouseX = e.getX();
		double mouseY = e.getY();
		label.setLabel(mouseX + "," + mouseY);
		if (label.contains(mouseX,mouseY)) { //checks if label rectangle contains the mouse position
			label.setColor(Color.RED);
		}
		else {
			label.setColor(Color.BLUE);
		}
	} 

}
