/*
 * File: Weather.java
 * ----------------------------
 * Program by Benjamin Asdell
 * Written for Stanford University CS106A, Copyright 2019
 * Section Leader: Trey Connelly
 * Section: Wednesday 5:30 p.m.
 * Problem 2
 * 
 * This program serves as a interactive weather statistic analysis. The user can input
 * a variable amount of temperatures, reflecting the average temperature of consecutive days,
 * in order to receive information about the entire set of data. The output includes the 
 * maximum and minimum recorded temperatures, the amount of "cold" days (specified to be
 * 50 degrees or below in the assignment), and the average temperature over the range of days.
 * The main data gathering process is ended by inputting a specfic sentinel value, which can be
 * modified by changing the value of the class constant QUIT_VALUE.
 */
import acm.program.*;

public class Weather extends ConsoleProgram {
	private static final int QUIT_VALUE = -1; // class constant representing sentinel value
	
	public void run() {
		println("CS 106A \"Weather Master 4000\"!");
		double totalTemp = 0;
		double tempCount = 0;
		int coldCount = 0;
		int maxTemp = 0;
		int minTemp = 0;
		int currentTemp = readInt("Next temperature (or " + QUIT_VALUE + " to quit)? ");
		if (currentTemp != QUIT_VALUE) {
			maxTemp = currentTemp;
			minTemp = currentTemp;
			if (currentTemp <= 50) {
				coldCount ++;
			}
			totalTemp += currentTemp;
			tempCount ++;
			while (true) {
				currentTemp = readInt("Next temperature (or " + QUIT_VALUE + " to quit)? ");
				if (currentTemp == QUIT_VALUE) {
					break;
				}
				totalTemp += currentTemp;
				tempCount ++;
				if (currentTemp <= 50) {
					coldCount ++;
				}
				if (currentTemp > maxTemp) {
					maxTemp = currentTemp;
				}
				else if (currentTemp < minTemp) {
					minTemp = currentTemp;
				}
			}
		}
		if (tempCount == 0) { //special case of first entry being sentinel value
			println("No temperatures were entered.");
		}
		else {
			double avgTemp = totalTemp / tempCount;
			println("Highest temperature = " + maxTemp);
			println("Lowest temperature = " + minTemp);
			println("Average = " + avgTemp);
			println(coldCount + " cold day(s).");
		}
	}
}
