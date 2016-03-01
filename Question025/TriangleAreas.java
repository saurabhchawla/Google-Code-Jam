package com.org.question025;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TriangleAreas {
	private final String inputFileName = "B-large-practice.in";
	private final String outputFileName = "B-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	TriangleAreas() {
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
			String[] inputSplitted = line.split(" ");
			int N = Integer.parseInt(inputSplitted[0]);
			int M = Integer.parseInt(inputSplitted[1]);
			int A = Integer.parseInt(inputSplitted[2]);
			
			if(A>(N*M)) {
				appendOutputTofile(i+1, "IMPOSSIBLE", bufferedWriter);
				continue;
			}
			int Ax = 0;
			int Ay = 0;
			int Bx = 1;
			int By = M;
			int Cx, Cy;
			if(A%M !=0) {
				Cx = 1 + A/M;
				Cy = M-(A%M);
			} else {
				Cx = A/M;
				Cy = 0;
			}
			
			String outputString = "" +Ax + " " + Ay + " " + Bx+ " " + By+ " " + Cx+ " " + Cy;
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
		TriangleAreas solution = new TriangleAreas();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
