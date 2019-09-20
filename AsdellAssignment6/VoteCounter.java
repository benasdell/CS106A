/*
 * File: VoteCounter.java
 * ---------------------
 * Author: Benjamin Asdell
 * for Stanford CS106A Summer 2019
 * Section: Wednesday 5pm
 * TA: Trey Connelly
 * 
 * This program is an example of a sandcastle program that tallies votes
 * for given candidates in the provided starter code. An instance of the
 * candidate's name in the given ArrayList counts as one vote. If the candidate
 * receives no votes, their name and voting results are not printed.
 */

import acm.program.*;
import java.util.*;

public class VoteCounter extends ConsoleProgram {
	public void run() {
		ArrayList<String> votes = new ArrayList<String>();
		votes.add("Zaphod Beeblebrox");
		votes.add("Arthur Dent");
		votes.add("Trillian McMillian");
		votes.add("Zaphod Beeblebrox");
		votes.add("Marvin");
		votes.add("Mr. Zarniwoop");
		votes.add("Trillian McMillian");
		votes.add("Zaphod Beeblebrox");
		printVoteCounts(votes);
		
	}
	
	/*
	 * This helper method completes the main functionality of the VoteCounter. It builds
	 * a HashMap that links each candidate's name to the frequency of their name's appearance
	 * in the ArrayList passed into the method. This method then prints out the name of each candidate
	 * in addition to how many votes they received.
	 */
	private void printVoteCounts(ArrayList<String> votes) {
		HashMap<String, Integer> voteMap = new HashMap<>();
		for (int i = 0; i < votes.size(); i++) {
			Integer count = voteMap.get(votes.get(i));
			if (count == null) {
				voteMap.put(votes.get(i), 1); //adds first vote if candidate is not already in voteMap
			}
			else {
				count++;
				voteMap.put(votes.get(i), count); //increments amount of candidate's vote if alrerady in voteMap
			}
		}
		String[] nameArray = voteMap.keySet().toArray(new String[0]); //converts voteMap to array of keys in order to be iterable
		for (int j = 0; j < voteMap.keySet().size(); j++) {
			String candidateName = nameArray[j];
			Integer candidateVotes = voteMap.get(nameArray[j]);
			println("Votes for " + candidateName + ": " + candidateVotes);
		}
	}
}
