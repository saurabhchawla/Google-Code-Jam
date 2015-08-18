package com.org.question009;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OldMagician {
	private final String inputFileName = "A-large-practice.in";
	private final String outputFileName = "A-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	OldMagician() {
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
			String outputString;
			String[] values = line.split(" ");
			int w = Integer.parseInt(values[0]);
			int b = Integer.parseInt(values[1]);
			if(b%2 == 0) {
				outputString = "WHITE";
			} else {
				outputString = "BLACK";
			}
			appendOutputTofile(i+1, outputString, bufferedWriter);		
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
		OldMagician solution = new OldMagician();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
