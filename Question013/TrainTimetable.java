package com.org.question013;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TrainTimetable {
	static final long ONE_MINUTE_IN_MILLIS=60000;
	private final String inputFileName = "B-small-practice.in";
	private final String outputFileName = "B-small-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	TrainTimetable() {
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

	public void solve() throws IOException, ParseException {
		String line = bufferedReader.readLine();
		int n = Integer.parseInt(line);
		for(int i=0; i<n; i++) {
			// Start coding from here
			line = bufferedReader.readLine();
			int T = Integer.parseInt(line);
			line = bufferedReader.readLine();
			String[] lineSplit = line.split(" ");
			int nA = Integer.parseInt(lineSplit[0]);
			int nB = Integer.parseInt(lineSplit[1]);
			List<Date> arrivalListA = new ArrayList<Date>();
			List<Date> departureListA = new ArrayList<Date>();
			List<Date> arrivalListB = new ArrayList<Date>();
			List<Date> departureListB = new ArrayList<Date>();
			DateFormat f = new SimpleDateFormat("HH:mm");
			
			for(int j=0; j<nA; j++) {
				line = bufferedReader.readLine();
				lineSplit = line.split(" ");
				departureListA.add((Date) f.parse(lineSplit[0]));
				Collections.sort(departureListA);
				long t = ((Date) f.parse(lineSplit[1])).getTime();
				t += (T* ONE_MINUTE_IN_MILLIS);
				arrivalListB.add(new Date(t));
//				arrivalListB.add((Date) f.parse(lineSplit[1]));
				Collections.sort(arrivalListB);
			}
			
			for(int k=0; k<nB; k++) {
				line = bufferedReader.readLine();
				lineSplit = line.split(" ");
				departureListB.add((Date) f.parse(lineSplit[0]));
				Collections.sort(departureListB);
				long t = ((Date) f.parse(lineSplit[1])).getTime();
				t += (T* ONE_MINUTE_IN_MILLIS);
				arrivalListA.add(new Date(t));
//				arrivalListA.add((Date) f.parse(lineSplit[1]));
				Collections.sort(arrivalListA);
			}
			
			int cTrainsA = 0;
			int cTrainsB = 0;
			int rTrainsA = 0;
			int rTrainsB = 0;
			Integer aA=0, aB=0, dA=0, dB=0;
			boolean c1 = true, c2 = true, c3 = true, c4 = true;
			if(nA==0) {
				c2 = false;
				c3 = false;
			}
			if(nB==0) {
				c1 = false;
				c4 = false;
			}
			
			Integer incrementWhom = 0;
		    while (aA<nB || aB<nA || dA<nA || dB<nB) {
				//findSmallest()
		    	//do operation && move ahead
				incrementWhom = findSmallest((nB == 0 || aA >= nB) ? null
						: arrivalListA.get(aA), nA == 0 || aB >= nA ? null
						: arrivalListB.get(aB), nA == 0 || dA >= nA ? null
						: departureListA.get(dA), nB == 0 || dB >= nB ? null
						: departureListB.get(dB), c1, c2, c3, c4);
		    	if(incrementWhom == 1) {
		    		aA++;
		    		cTrainsA++;
		    		if(aA>=nB)
		    			c1=false;
		    	} else if(incrementWhom == 2) {
		    		aB++;
		    		cTrainsB++;
		    		if(aB>=nA)
		    			c2=false;
		    	} else if(incrementWhom ==3) {
		    		dA++;
		    		if(dA>=nA)
		    			c3=false;
		    		if(cTrainsA>0) {
		    			cTrainsA--;
		    		} else {
		    			rTrainsA++;
		    		}
		    	} else if(incrementWhom == 4) {
		    		dB++;
		    		if(dB>=nB)
		    			c4=false;
		    		if(cTrainsB>0) {
		    			cTrainsB--;
		    		} else {
		    			rTrainsB++;
		    		}
		    	}
			}
			appendOutputTofile(i+1, rTrainsA + " " + rTrainsB, bufferedWriter);
		}
		
	}
	
	private Integer findSmallest(Date date1aA, Date date2aB,  Date date3dA, Date date4dB, boolean c1, boolean c2, boolean c3, boolean c4) {
		
		long min = Long.MAX_VALUE;
		int return_value = -1;
		if(c1 && date1aA.getTime()<min) {
				min = date1aA.getTime();
				return_value = 1;
		}
		if(c2 && date2aB.getTime() < min) {
			min = date2aB.getTime();
			return_value = 2;
		}
		if(c3 && date3dA.getTime() <min) {
			min = date3dA.getTime();
			return_value = 3;
		}
		if(c4 && date4dB.getTime() <min) {
			min = date4dB.getTime();
			return_value = 4;
		}
		
		if(c1 && return_value == 3 && date1aA.getTime()==date3dA.getTime()) {
			return_value = 1;
		}else if(c2 && return_value == 4 && date2aB.getTime()== date4dB.getTime()) {
			return_value = 2;
		}
		return return_value;
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
		TrainTimetable solution = new TrainTimetable();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
