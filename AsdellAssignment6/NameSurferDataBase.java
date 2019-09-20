/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class NameSurferDataBase implements NameSurferConstants {
	
	HashMap<String, NameSurferEntry> nameMap = new HashMap<>();
	
	/**
	 * Constructor: NameSurferDataBase(filename)
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		try {
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				NameSurferEntry nameEntry = new NameSurferEntry(input.nextLine());
				nameMap.put(nameEntry.getName(), nameEntry);
			}
			input.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Public Method: findEntry(name)
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists.  If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		name = validateName(name);
		if (nameMap.containsKey(name)) {
			return nameMap.get(name);
		}
		else {
			return null;
		}
	}
	
	/*
	 * This helper method validates the input name from the text field and ensures it is in
	 * the same format as the keys in the nameMap (first letter uppercase, all others lowercase).
	 * This allows the findEntry method to correctly return the state of the name data for names
	 * given in any type case.
	 */
	private String validateName(String name) {
		if (name.equals(null)) {
			return null;
		}
		else {
			char prefix = name.charAt(0);
			String suffix = name.substring(1);
			if (Character.isLowerCase(prefix)) {
				prefix = Character.toUpperCase(prefix);
			}
			name = prefix + suffix.toLowerCase();
			return name;
		}
	}
}

