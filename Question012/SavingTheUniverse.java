package com.org.question012;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SavingTheUniverse {
	private final String inputFileName = "A-large-practice.in";
	private final String outputFileName = "A-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	SavingTheUniverse() {
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
			// Start coding from here
			line = bufferedReader.readLine();
			int nS = Integer.parseInt(line);
			List<String> searchEngines = new ArrayList<String>();
			for(int j=0; j<nS; j++) {
				line = bufferedReader.readLine();
				searchEngines.add(line);
			}
			line = bufferedReader.readLine();
			int nQ = Integer.parseInt(line);
			List<String> searchQueries = new ArrayList<String>();
			for(int k=0 ; k<nQ; k++) {
				line = bufferedReader.readLine();
				searchQueries.add(line);
			}
			int flag = 1;
			int output = 0;
			for(String searchEngine: searchEngines) {
				if(!searchQueries.contains(searchEngine)) {
					flag=0;
					output=0;
				}
			}
			if(flag == 1) {
				List<String> searchEngineDuplicateList = new ArrayList<String>(searchEngines) ;
				//Collections.copy(searchEngineDuplicateList, searchEngines);
				String previousQuery = "";
				for(String searchQuery: searchQueries) {
					if(!searchQuery.equals(previousQuery) || !previousQuery.equals("")) {
						searchEngineDuplicateList.remove(previousQuery);
						if(searchEngineDuplicateList.isEmpty()) {
							searchEngineDuplicateList = new ArrayList<String>(searchEngines);
							searchEngineDuplicateList.remove(previousQuery);
							output++;
						}
					}
					previousQuery = searchQuery;
				}
				searchEngineDuplicateList.remove(previousQuery);
				if(searchEngineDuplicateList.isEmpty()) {
					searchEngineDuplicateList = new ArrayList<String>(searchEngines);
					searchEngineDuplicateList.remove(previousQuery);
					output++;
				}
				
			}
			appendOutputTofile(i+1, Integer.toString(output), bufferedWriter);
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
		SavingTheUniverse solution = new SavingTheUniverse();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
