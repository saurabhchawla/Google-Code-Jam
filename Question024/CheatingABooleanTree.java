package com.org.question024;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CheatingABooleanTree {
	private final String inputFileName = "A-large-practice.in";
	private final String outputFileName = "A-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	// int minimum = -1;

	CheatingABooleanTree() {
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
		for (int i = 0; i < n; i++) {
			System.out.println("Case: " + (i + 1));
			int output = -1;
			// minimum = 99999;
			line = bufferedReader.readLine();
			String[] inputs = line.split(" ");
			int n_nodes = Integer.parseInt(inputs[0]);
			boolean expectedOutput = Integer.parseInt(inputs[1]) > 0 ? true
					: false;
			Boolean[] mutability = new Boolean[n_nodes];
			Boolean[] gateType = new Boolean[n_nodes];
			Boolean[] value = new Boolean[n_nodes];

			for (int j = 0; j < (n_nodes - 1) / 2; j++) {
				line = bufferedReader.readLine();
				inputs = line.split(" ");
				mutability[j] = Integer.parseInt(inputs[1]) > 0 ? true : false;
				gateType[j] = Integer.parseInt(inputs[0]) > 0 ? true : false;
				value[j] = (Boolean) null;
			}

			for (int j = (n_nodes - 1) / 2; j < n_nodes; j++) {
				line = bufferedReader.readLine();
				mutability[j] = null;
				gateType[j] = null;
				value[j] = Integer.parseInt(line) > 0 ? true : false;
			}

			int minimum = computeMutations(mutability, gateType, value, n_nodes,
					expectedOutput);
			if (minimum >= 99999)
				appendOutputTofile(i + 1, "IMPOSSIBLE", bufferedWriter);
			else
				appendOutputTofile(i + 1, String.valueOf(minimum),
						bufferedWriter);
		}

	}

	private int computeMutations(Boolean[] mutability, Boolean[] gateType,
			Boolean[] value, int n_nodes, boolean expectedOutput) {
		for(int i=0; i<(n_nodes-1)/2; i++) {
			value[i] = computeExistingValues(mutability, gateType, value, n_nodes,
					i+1);
		}
		
		int minimum = getMinimumMutations(mutability, gateType, value, n_nodes,
				expectedOutput, 0, 1);
		return minimum;
	}

	private int getMinimumMutations(Boolean[] mutability, Boolean[] gateType,
			Boolean[] value, int n_nodes, boolean expectedOutput,
			int currentMutationCount, int currentNodeNumber) {
		//
		//
		//
		//
		//
		//
		// if(expectedOutput == value[currentNodeNumber-1])
		// return currentMutationCount;

		if(currentMutationCount>n_nodes)
			return 99999;
		if ((currentNodeNumber * 2) >= n_nodes)
			return 99999;
		if (value[currentNodeNumber - 1] == expectedOutput) {
			return currentMutationCount;
		}

		int currentIndex = currentNodeNumber - 1;

		if (expectedOutput) {
			if (gateType[currentIndex]) {
				if (value[(2 * currentNodeNumber) - 1]
						&& value[2 * currentNodeNumber]) {

				} else if (!value[(2 * currentNodeNumber) - 1]
						&& value[2 * currentNodeNumber]) {
					if (mutability[currentIndex]) {
						currentMutationCount++;
						return currentMutationCount;
					} else {
						return getMinimumMutations(mutability, gateType, value,
								n_nodes, true, currentMutationCount,
								(2 * currentNodeNumber));
					}
				} else if (value[(2 * currentNodeNumber) - 1]
						&& !value[2 * currentNodeNumber]) {
					if (mutability[currentIndex]) {
						currentMutationCount++;
						return currentMutationCount;
					} else {
						return getMinimumMutations(mutability, gateType, value,
								n_nodes, true, currentMutationCount,
								(2 * currentNodeNumber + 1));
					}
				} else {
					if (mutability[currentIndex]) {
						currentMutationCount++;
						int minLeft = getMinimumMutations(mutability, gateType,
								value, n_nodes, true, currentMutationCount,
								(2 * currentNodeNumber));
						int minRight = getMinimumMutations(mutability,
								gateType, value, n_nodes, true,
								currentMutationCount,
								(2 * currentNodeNumber) + 1);
						if (minLeft > minRight)
							return minRight;
						else
							return minLeft;
					} else {
						int diffLeft = getMinimumMutations(mutability,
								gateType, value, n_nodes, true, 0,
								(2 * currentNodeNumber));
						int diffRight = getMinimumMutations(mutability,
								gateType, value, n_nodes, true, 0,
								(2 * currentNodeNumber) + 1);
						return (currentMutationCount + diffLeft + diffRight);
					}
				}
			} else {
				if (!value[(2 * currentNodeNumber) - 1]
						&& !value[2 * currentNodeNumber]) {
					int minLeft = getMinimumMutations(mutability, gateType,
							value, n_nodes, true, currentMutationCount,
							(2 * currentNodeNumber));
					int minRight = getMinimumMutations(mutability, gateType,
							value, n_nodes, true, currentMutationCount,
							(2 * currentNodeNumber) + 1);
					if (minLeft > minRight)
						return minRight;
					else
						return minLeft;

				}

			}
		} else {
			if (gateType[currentIndex]) {
				if (value[(2 * currentNodeNumber) - 1]
						&& value[2 * currentNodeNumber]) {
					int minLeft = getMinimumMutations(mutability, gateType,
							value, n_nodes, false, currentMutationCount,
							(2 * currentNodeNumber));
					int minRight = getMinimumMutations(mutability, gateType,
							value, n_nodes, false, currentMutationCount,
							(2 * currentNodeNumber) + 1);
					if (minLeft > minRight)
						return minRight;
					else
						return minLeft;

				}
			} else {
				if (value[(2 * currentNodeNumber) - 1]
						&& value[2 * currentNodeNumber]) {
					if (mutability[currentIndex]) {
						currentMutationCount++;
						int minLeft = getMinimumMutations(mutability, gateType,
								value, n_nodes, false, currentMutationCount,
								(2 * currentNodeNumber));
						int minRight = getMinimumMutations(mutability,
								gateType, value, n_nodes, false,
								currentMutationCount,
								(2 * currentNodeNumber) + 1);
						if (minLeft > minRight)
							return minRight;
						else
							return minLeft;
					} else {
						int diffLeft = getMinimumMutations(mutability,
								gateType, value, n_nodes, false, 0,
								(2 * currentNodeNumber));
						int diffRight = getMinimumMutations(mutability,
								gateType, value, n_nodes, false, 0,
								(2 * currentNodeNumber) + 1);
						return (currentMutationCount + diffLeft + diffRight);
					}

				} else if (value[(2 * currentNodeNumber) - 1]
						&& !value[2 * currentNodeNumber]) {
					if (mutability[currentIndex]) {
						currentMutationCount++;
						return currentMutationCount;
					} else {
						return getMinimumMutations(mutability, gateType, value,
								n_nodes, false, currentMutationCount,
								(2 * currentNodeNumber));
					}
				} else if (!value[(2 * currentNodeNumber) - 1]
						&& value[2 * currentNodeNumber]) {
					if (mutability[currentIndex]) {
						currentMutationCount++;
						return currentMutationCount;
					} else {
						return getMinimumMutations(mutability, gateType, value,
								n_nodes, false, currentMutationCount,
								(2 * currentNodeNumber) + 1);
					}

				}

			}

		}

		return 0;
	}

	private Boolean computeExistingValues(Boolean[] mutability,
			Boolean[] gateType, Boolean[] value, int n_nodes, int parent) {
		
		if ((2 * parent) >= n_nodes || value[parent-1]!=null)
			return value[parent - 1];
		else {
			if (gateType[parent - 1]) {
				value[parent - 1] = computeExistingValues(mutability, gateType,
						value, n_nodes, 2 * parent)
						&& computeExistingValues(mutability, gateType, value,
								n_nodes, (2 * parent + 1));
				return value[parent - 1];
			} else {
				value[parent - 1] = computeExistingValues(mutability, gateType,
						value, n_nodes, 2 * parent)
						|| computeExistingValues(mutability, gateType, value,
								n_nodes, (2 * parent + 1));
				return value[parent - 1];
			}
		}
	}

	public static void appendOutputTofile(int caseNumber, String output,
			BufferedWriter bufferedWriter) throws FileNotFoundException,
			IOException {
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
		CheatingABooleanTree solution = new CheatingABooleanTree();
		try {
			solution.solve();
			solution.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
