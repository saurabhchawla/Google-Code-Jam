package com.org.question020;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Mousetrap {
	private final String inputFileName = "C-large-practice.in";
	private final String outputFileName = "C-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	Mousetrap() {
		initializeFileReaders();
	}

	public void initializeFileReaders() {
		FileReader fileReader;
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			FileWriter fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void solve() throws IOException {
		String line = bufferedReader.readLine();
		int n = Integer.parseInt(line);
		for(int i=0; i<n; i++) {
			System.out.println("Case No: " + n+1);
			line = bufferedReader.readLine();
			int k = Integer.parseInt(line);
			line = bufferedReader.readLine();
			String[] lineSplitted = line.split(" ");
			int numberOfOutputIndices =Integer.parseInt(lineSplitted[0]); 
			int[] indices = new int[numberOfOutputIndices];
			for(int j=0; j<numberOfOutputIndices; j++) {
				indices[j] = Integer.parseInt(lineSplitted[j+1]);
			}
			
			int[] deckArrangement = new int[k];
			
			arrangeCards(deckArrangement, k);
			
			String output = "";
			for(int j=0; j<numberOfOutputIndices; j++) {
				output += String.valueOf(deckArrangement[indices[j]-1]);
				output += " ";
			}
			output.trim();
			appendOutputTofile(i+1, output, bufferedWriter);
		}
		
	}
	
	private void arrangeCards(int[] deckArrangement, int k) {
		boolean[] isPlaced = new boolean[k];
		for(int i=0; i<k; i++) {
			isPlaced[i] = false;
		}
		int lastPlacementIndex = 0;
		for(int divisingFactor = k, i=1; i<=k; i++, divisingFactor--) {
			int relativePlacementPosition = i%divisingFactor;
			if(relativePlacementPosition == 0)
				relativePlacementPosition = divisingFactor;
			int placementIndex = getNextAvailableIndex(isPlaced, relativePlacementPosition, lastPlacementIndex);
			deckArrangement[placementIndex] = i;
			isPlaced[placementIndex] = true;
			lastPlacementIndex = placementIndex;
		}
	}

	private int getNextAvailableIndex(boolean[] isPlaced,
			int relativePlacementPosition, int lastPlacementIndex) {
		int indexTrack = lastPlacementIndex;
		for(int i=0; i<(isPlaced.length); i++) {
			if(isPlaced[indexTrack] == false) {
				relativePlacementPosition--;
			} 
			if(relativePlacementPosition==0)
				return indexTrack;
			
			indexTrack = (indexTrack+1)%isPlaced.length;
		}
		return -1;
	}

	public static void appendOutputTofile(int caseNumber, String output, BufferedWriter bufferedWriter) throws FileNotFoundException, IOException{
		bufferedWriter.write("Case #" + caseNumber + ": " + output);
		bufferedWriter.newLine();
		
	}
	
	public void close() throws IOException {
		bufferedReader.close();
		bufferedWriter.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mousetrap solution = new Mousetrap();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
