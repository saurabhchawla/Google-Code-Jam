package com.org.question021;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class TextMessagingOutrage {
	private final String inputFileName = "A-large-practice.in";
	private final String outputFileName = "A-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	TextMessagingOutrage() {
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
			line = bufferedReader.readLine();
			String[] lineSplitted = line.split(" ");
			
			//int nP = Integer.parseInt(lineSplitted[0]);
			int nK = Integer.parseInt(lineSplitted[1]);;
			int nA = Integer.parseInt(lineSplitted[2]);;
			
			line = bufferedReader.readLine();
			lineSplitted = line.split(" ");
			int[] letterFrequencies = new int[nA]; 
			for(int j=0; j<nA; j++) {
				letterFrequencies[j] = Integer.parseInt(lineSplitted[j]);
			}
			Arrays.sort(letterFrequencies);
			long keyPressCount = 0;
			int factor = 1;

			for(int j=nA-1,counter=0; j>=0; j--,counter++) {
				keyPressCount += (factor*letterFrequencies[j]);
				if((counter+1)%nK==0)
					factor++;
			}
			appendOutputTofile(i+1, String.valueOf(keyPressCount), bufferedWriter);
		}
		
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
		TextMessagingOutrage solution = new TextMessagingOutrage();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
