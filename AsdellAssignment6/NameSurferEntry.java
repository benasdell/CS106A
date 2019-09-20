/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	
	private String newNameEntry;
	
	
	private int[] nameRankings = new int[NDECADES];

	/**
	 * Constructor: NameSurferEntry(line)
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file.  Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		readEntry(line);
	}

	private void readEntry(String line) {
		int firstSpace = line.indexOf(" ");
		newNameEntry = line.substring(0, firstSpace);
		String rankingData = line.substring(firstSpace + 1);
		Scanner inputEntry = new Scanner(rankingData);
		int count = 0;
		while (inputEntry.hasNextInt()) {
			nameRankings[count] = inputEntry.nextInt();
			count++;
		}
		inputEntry.close();
	}
	/**
	 * Public Method: getName()
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return newNameEntry;
	}

	/**
	 * Public Method: getRank(decade)
	 * Returns the rank associated with an entry for a particular
	 * decade.  The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE.  If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		return nameRankings[decade];
	}

	/**
	 * Public Method: toString()
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		String newLine = newNameEntry + " " + Arrays.toString(nameRankings);
		return newLine;
	}
}

