package com.org.question015;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimumScalarProduct {
	private final String inputFileName = "A-large-practice.in";
	private final String outputFileName = "A-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	MinimumScalarProduct() {
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
		int t = Integer.parseInt(line);
		for(int i=0; i<t; i++) {
			// Start coding from here
			line = bufferedReader.readLine();
			int n = Integer.parseInt(line);
			line = bufferedReader.readLine();
			String[] v1Values = line.split(" ");
			List<Long> v1List = new ArrayList<Long>();
			for(int j=0; j<n; j++) {
				v1List.add(Long.parseLong(v1Values[j]));
			}
			
			line = bufferedReader.readLine();
			String[] v2Values = line.split(" ");
			List<Long> v2List = new ArrayList<Long>();
			for(int j=0; j<n; j++) {
				v2List.add(Long.parseLong(v2Values[j]));
			}
			
			Collections.sort(v1List);
			Collections.sort(v2List);
			Collections.reverse(v2List);
		   	
			long output = 0; 
			for(int j=0; j<n; j++) {
				output+=(v1List.get(j) * v2List.get(j));
			}
			appendOutputTofile(i+1, String.valueOf(output), bufferedWriter);
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
		MinimumScalarProduct solution = new MinimumScalarProduct();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
