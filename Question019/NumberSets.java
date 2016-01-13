package com.org.question019;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NumberSets {
	private final String inputFileName = "input.txt";
	private final String outputFileName = "output.txt";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	NumberSets() {
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
			int lowerBound = Integer.parseInt(lineSplitted[0]);
			int upperBound = Integer.parseInt(lineSplitted[1]);
			int p = Integer.parseInt(lineSplitted[2]);
			
			DisjointSets disjointSetManager = new DisjointSets();
			for(int j=lowerBound; j<=upperBound; j++)
				disjointSetManager.create_set(j);
			int setCount = upperBound-lowerBound+1;
			
			List<Integer> primes = getPrimesBetween(p,(upperBound - lowerBound));
			for(int prime: primes) {
				List<Integer> sameSetIntegers = new ArrayList<Integer>(); 
				for(int j=lowerBound; j<=upperBound; j++) {
					if(j%prime == 0) {
						sameSetIntegers.add(j);
					}					
				}
				if(!sameSetIntegers.isEmpty()) {
					int baseSet = disjointSetManager.find_set(sameSetIntegers.get(0));
					for(int k=1; k<sameSetIntegers.size(); k++) {
						int foundSet =disjointSetManager.find_set(sameSetIntegers.get(k)); 
						if( foundSet!= baseSet) {
							disjointSetManager.union(baseSet, foundSet);
							setCount--;
						}
					}
				}
			}
			
			appendOutputTofile(i+1, String.valueOf(setCount), bufferedWriter);
		}
		
	}
	int[] PRIMES_1000 = {2,   3,   5,   7,  11,  13,  17,  19,  23,  29,  31,  37,  41,
			  43,  47,  53,  59,  61,  67,  71,  73,  79,  83,  89,  97, 101,
			  103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167,
			  173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239,
			  241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313,
			  317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397,
			  401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467,
			  479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569,
			  571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643,
			  647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733,
			  739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823,
			  827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911,
			  919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997  };
	
	private List<Integer> getPrimesBetween(int lowerBound, int upperBound) {
		List<Integer> returnList = new ArrayList<Integer>();
		if(lowerBound>upperBound)
			return returnList;
		Integer lowerIndex = null;
		Integer higherIndex = null;
		for(int i=0; i<PRIMES_1000.length; i++) {
			if(lowerIndex==null && PRIMES_1000[i]>=lowerBound) {
				lowerIndex = i;
			}
			if(higherIndex== null && PRIMES_1000[i]>upperBound) {
				higherIndex = i-1;
				break;
			}
		}
		if(higherIndex == null && upperBound>=997) {
			higherIndex=PRIMES_1000.length-1;
		}
		for(int i = lowerIndex; i<=higherIndex; i++) {
			returnList.add(PRIMES_1000[i]);
		}
	
		return returnList;
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
		NumberSets solution = new NumberSets();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
