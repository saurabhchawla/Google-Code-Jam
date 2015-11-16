package com.org.question016;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Milkshakes {
	private final String inputFileName = "B-large-practice.in";
	private final String outputFileName = "B-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	int[] finalMilkshakesStatusArray = null;
	int minMaltedCount = 99999;

	
	Milkshakes() {
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

	public enum MilkshakeStatus{
		MALTED, NONMALTED, TBD
	}
	public enum CustomerPreferences {
		LIKE_NONMALTED, LIKE_MALTED, DISLIKE
	}
/*
 * So, we willl keep a customer to Milkshake matrix and manipulate that matrix
 * The rows will represent customers and the columns will represent Milkshake.. 0 & 1 & -1 will be used to indicate preferences. -1 for dislike and 0 for nonmalted preference 
 * and 1 for malted preference
 * 
 * Workout things from the perspective of the milkshakes
 * 
 * We have to take care of the following cases 
 * Case1: In case, a customer has just one preference, that should be satified
 * Case2: In case a milkshake has either '-' or '0' in terms of customer preferences, call it to be '0'
 * Case3: In case a milkshake has all '1's in terms of customer preferences, call it to be '1'
 * Case4: In case a milkshake has a combination of '0', '1', '-' in terms of preferences, try out with both the pathways recursively
 * 
 * Look for the minimum malted case and print the output
 * 
 * So, the first task is to take the input in a matrix and print the same-done
 * Second task is to .. create two arrays: one would be called Milkshake type status Array which will contain the 0 for malted, 1 for non malted, -1 for TBD and the Customer Satisfaction
 * Array that indicates the satisfaction status of the customer. Initialize these arrays, make small functions to check these arrays completion.
 * 
 *  Write the main function, call it satisfyCustomersMinimizingMalted(), the parameter to this function would be (1) Customer Preference Matrix (2) Customer Satisfaction Array
 *  (3) Milkshake type status array, for (2) & (3), if we are going via the recursive root, then they will have their own copy;
 * 
 */
	public void solve() throws IOException {
		String line = bufferedReader.readLine();
		int t = Integer.parseInt(line);
		for(int i=0; i<t; i++) {
			// Start coding from here
			finalMilkshakesStatusArray = null;
			minMaltedCount = 99999;
			line = bufferedReader.readLine();
			int nm = Integer.parseInt(line);
			line = bufferedReader.readLine();
			int nc = Integer.parseInt(line);
			int[][] customerMilkshakeRelationshipMatrix = new int[nc][nm];
			for(int j=0; j<nc; j++) {
				for(int k=0; k<nm; k++) {
					customerMilkshakeRelationshipMatrix[j][k] =  -1;
				}
			}
			for (int j=0 ; j<nc ; j++) {
				line = bufferedReader.readLine();
				String[] splitLine = line.split(" ");
				int numOfPreferences = Integer.parseInt(splitLine[0]);
				for(int k = 0; k<numOfPreferences ; k++) {
					int preferenceNumber = (Integer.parseInt(splitLine[1+(2*k)]))-1;
					int preferenceType = Integer.parseInt(splitLine[2+(2*k)]);
					customerMilkshakeRelationshipMatrix[j][preferenceNumber]=preferenceType;
				}
			}

			System.out.println("TestCase No." + (i+1));
//			printMatrix(customerMilkshakeRelationshipMatrix, nc, nm);
			
			int[] milkshakeTypePreparationPlan = findAllSatisfatoryMilkShakeTypePreparationPlan(customerMilkshakeRelationshipMatrix, nc, nm);
			
			if(milkshakeTypePreparationPlan == null) {
				appendOutputTofile(i+1, "IMPOSSIBLE", bufferedWriter);
			} else {
				String output = "";
				for(int milkshakeType: milkshakeTypePreparationPlan) {
					output += (milkshakeType + " " );
				}
				output.trim();
				appendOutputTofile(i+1, output, bufferedWriter);
			}
			
		}
	}
	
	private int[] findAllSatisfatoryMilkShakeTypePreparationPlan(
			int[][] customerMilkshakeRelationshipMatrix, int nc, int nm) {
		int[] customerSatisfactionArray = new int[nc];
		for(int i=0; i<nc; i++) {
			customerSatisfactionArray[i] = 0;
		}
		
		int[] milkshakeTypePreparationPlan = new int[nm];
		for(int j=0; j<nm; j++) {
			milkshakeTypePreparationPlan[j] = -1;
		}
		
		computeAllSatisfactoryMinimumMaltedMilkshakesPreparationPlan(customerMilkshakeRelationshipMatrix, nc, nm, customerSatisfactionArray, milkshakeTypePreparationPlan);
		return finalMilkshakesStatusArray;
	}

	private void computeAllSatisfactoryMinimumMaltedMilkshakesPreparationPlan(
			int[][] customerMilkshakeRelationshipMatrix, int nc, int nm,
			int[] customerSatisfactionArray, int[] milkshakeTypePreparationPlan) {
		
		int[] customerSatisfactionArrayLocal = customerSatisfactionArray.clone();
		int[] milkshakeTypePreparationPlanLocal = milkshakeTypePreparationPlan.clone();
		
		boolean updateFlag = true;
		while (updateFlag) {
			updateFlag = false;
	///////if an unsatisfied customer has just 1 preference on the TBD milkshakes, it should be satisfied and the same should be updated for other customers
			
			for(int j=0; j<nc; j++) {
				if(customerSatisfactionArrayLocal[j] == 0) {
					int preferenceCount = 0;
					int lastPreferenceMilkshakeIndex = -1;
					int lastPreferenceMilkshakeType = -1;
					for(int i = 0; i<nm; i++) {
						if(milkshakeTypePreparationPlanLocal[i] == -1 && customerMilkshakeRelationshipMatrix[j][i] != -1) {
							preferenceCount++;
							lastPreferenceMilkshakeIndex = i;
							lastPreferenceMilkshakeType = customerMilkshakeRelationshipMatrix[j][i];
						}
					}
					if(preferenceCount == 0)
						return;
					if(preferenceCount == 1) {
						//
						// TO BE CONTINUED at 05:30 pm . Then, you will have just 2 hours to do this.. or you can take your laptop there to finish it but this is going away today.
						// Take the laptop along.
						//
						updateFlag = true;
						milkshakeTypePreparationPlanLocal[lastPreferenceMilkshakeIndex] = lastPreferenceMilkshakeType;
						customerSatisfactionArrayLocal[j] = 1;
						
						//satisy others who have the same milkshake type preference for this just set milkshake index
						for(int i=0; i<nc; i++) {
							if(customerMilkshakeRelationshipMatrix[i][lastPreferenceMilkshakeIndex] == lastPreferenceMilkshakeType) {
								customerSatisfactionArrayLocal[i] = 1;
							}
						}
						
						
						
					}
				}
				
			}
			
	///// this is all for the Milkshakes whose status is TBD, If a Milkshake preference for unsatisfied customers has all 1's Or all 0's & '-', update the milkshake status matrix and then update the customer satisfaction matrix
			for(int i =0; i<nm; i++) {
				if(milkshakeTypePreparationPlanLocal[i] == -1) {
					boolean flagfor1 = true;
					boolean flagfor0 = true;
					for(int j=0; j<nc; j++) {
						if(customerSatisfactionArray[j] == 0) {
							if(customerMilkshakeRelationshipMatrix[j][i] != 1) {
								flagfor1 = false;
							} else {
								flagfor0 = false;
							}
						}
					}
					if(flagfor1) {
//						milkshakeTypePreparationPlanLocal[i] = 1;
//						updateFlag = true;
					} else if(flagfor0) {
						milkshakeTypePreparationPlanLocal[i] = 0;
						updateFlag = true;
					}
					for(int j=0; j<nc; j++) {
						if(customerSatisfactionArrayLocal[j] ==0 && customerMilkshakeRelationshipMatrix[j][i] == milkshakeTypePreparationPlanLocal[i] && milkshakeTypePreparationPlanLocal[i] ==0) {
							customerSatisfactionArrayLocal[j] =1;
						}
					}
				}
			}
		}
///// Do the above process recursively and realize that you have hit the fourth case.

//// MARKER
		boolean allSatisfied = true;
		for(int i=0; i<nc; i++) {
			if (customerSatisfactionArrayLocal[i] ==0) {
				allSatisfied = false;
			}
		}
		if(allSatisfied) {
			int maltedCount = 0;
			for(int i=0; i<nm; i++) {
				if(milkshakeTypePreparationPlanLocal[i] == -1) {
					milkshakeTypePreparationPlanLocal[i] = 0;
				} else if (milkshakeTypePreparationPlanLocal[i] == 1) {
					maltedCount++;
				}
			}
			if(minMaltedCount>maltedCount) {
				if(finalMilkshakesStatusArray == null)
					finalMilkshakesStatusArray = new int[nm];
				System.arraycopy(milkshakeTypePreparationPlanLocal, 0, finalMilkshakesStatusArray, 0, nm);
				minMaltedCount = maltedCount;			
			}
			return;
		}
		
/////// Look at the Milkshake type prepartion plan array and see if there are some TBD statuses, pick the lowest index one and send two values to the recursive loop, give preference to 0;
		for(int i=0; i<nm; i++) {
			if(milkshakeTypePreparationPlanLocal[i] == -1) {
				for(int k=0; k<2; k++) {
					int[] milkshakeTypePreparationPlanLocalClone = milkshakeTypePreparationPlanLocal.clone();
					int[] customerSatisfactionArrayLocalClone = customerSatisfactionArrayLocal.clone();
					milkshakeTypePreparationPlanLocalClone[i] = k;
					for(int j=0; j<nc; j++){
						if(customerSatisfactionArrayLocalClone[j] == 0 && customerMilkshakeRelationshipMatrix[j][i] == k) {
							customerSatisfactionArrayLocalClone[j] = 1;
						}
					}
					computeAllSatisfactoryMinimumMaltedMilkshakesPreparationPlan(customerMilkshakeRelationshipMatrix, nc, nm, customerSatisfactionArrayLocalClone, milkshakeTypePreparationPlanLocalClone);
				}
				break;
			}
		}

/// When will the global array updated in the whole process??-- Right here-- nops , at the marker		
	}

	private void printMatrix(int[][] customerMilkshakeRelationshipMatrix,
			int nc, int nm) {
		System.out.println();
		for(int j=0; j<nc; j++) {
			for(int k=0; k<nm; k++) {
				if(k!=0)
					System.out.print(", ");
				System.out.print(customerMilkshakeRelationshipMatrix[j][k]);
			}
			System.out.println();
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
		Milkshakes solution = new Milkshakes();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
