package com.org.question023;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IncreasingSpeedLimits {
	private final String inputFileName = "C-large-practice.in";
	private final String outputFileName = "C-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	IncreasingSpeedLimits() {
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
		int N = Integer.parseInt(line);
		for(int i=0; i<N; i++) {
			System.out.println("Case #:" + (i+1));
			line= bufferedReader.readLine();
			String[]  inputSplitted = line.split(" ");
			int n = Integer.parseInt(inputSplitted[0]);
			int m = Integer.parseInt(inputSplitted[1]);
			long X = Integer.parseInt(inputSplitted[2]);
			long Y = Integer.parseInt(inputSplitted[3]);
			long Z = Integer.parseInt(inputSplitted[4]);
			
			long A[] = new long[m];
			for(int j=0; j<m; j++) {
				line=bufferedReader.readLine();
				A[j] = Long.parseLong(line);
			}	
			long[] input = new long[n];
			for(int j=0; j<n; j++) {
//				System.out.print(A[(j % m)]);
//				System.out.print(" ");
				input[j] = A[(j%m)];
				A[j % m] = (long)((X * A[j % m] + Y * (j + 1)) % Z);
			}
//			System.out.println();
			long output = findSubsequences(n, input);
			appendOutputTofile(i+1, String.valueOf(output), bufferedWriter);
		}
		
	}
	
	private long findSubsequences(int n, long[] input) {
		long[] countArray = new long[n];
		for(int i=n-1; i>=0; i--) {
			countArray[i] = 1;
			for(int j=i+1; j<n; j++) {
				if(input[i]<input[j])
					countArray[i] = (countArray[i] + countArray[j]) %1000000007;
			}
		}
		long finalCount = 0;
		for(int i=0; i<n; i++) {
			finalCount = (finalCount+ countArray[i])%1000000007;
		}
		return (finalCount%1000000007);
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
		IncreasingSpeedLimits solution = new IncreasingSpeedLimits();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
