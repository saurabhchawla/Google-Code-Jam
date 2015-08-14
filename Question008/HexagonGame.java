package com.org.question008;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HexagonGame {
	private final String inputFileName = "D-large-practice.in";
	private final String outputFileName = "D-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	
	HexagonGame() {
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
	

	public static void appendOutputTofile(int caseNumber, String output, BufferedWriter bufferedWriter) throws FileNotFoundException, IOException{
		bufferedWriter.write("Case #" + caseNumber + ": " + output);
		bufferedWriter.newLine();
		
	}
	
	public void solve() throws IOException {
		// for each testcase
		// take value in the datastructure 2D matrix
		// find cost for each checker on each location
		// look for the minimum cost recursively
		// print the output for the testcase..
		
		String line = bufferedReader.readLine();
		int n = Integer.parseInt(line);
		for(int i = 0; i <n; i++) {
			System.out.println("testcase number: " + (i+1));
			line = bufferedReader.readLine();
			String[] checkerPos = line.split(" ");
			line = bufferedReader.readLine();
			String[] checkerMovementCost = line.split(" ");
			int s = checkerPos.length;
			List<Checker> checkerList;
			checkerList = getCheckerList(checkerPos, checkerMovementCost, s);
			int[][] matrixNumbered = new int[s][s];
			int sequenceNo = 1;
			for(int k=(s/2); k>=0; k--) { 
				for(int j=0; j<s-k; j++) {
					matrixNumbered[(s/2) - k][j] = sequenceNo++;
				}
			}
			for(int k=1; k<=(s/2); k++) {
				for(int j=k; j<s; j++) {
					matrixNumbered[(s/2)+k][j] = sequenceNo++;
				}
			}
			
//			for(int k=0;k<s;k++) {
//				for(int j=0;j<s;j++) {
//					System.out.print(matrixNumbered[k][j]+ ",");
//				}
//				System.out.println();
//			}
//           System.out.println();
			for(int l=0; l<s; l++) {
				for(int k=0;k<s;k++) {
					for(int j=0;j<s;j++) {
						if(matrixNumbered[j][k] == checkerList.get(l).getPos()) {
							evaluateCheckerCost(checkerList.get(l), j, k, matrixNumbered, s);
//							System.out.println();
						}
					}
				}
			}
			int minimum = minimumCostForCheckerPlacement(checkerList, s);
			appendOutputTofile(i+1, Integer.toString(minimum), bufferedWriter);
			
		}
		System.out.println("terminated");
	}
	
	private int minimumCostForCheckerPlacement(List<Checker> checkerList, int s) {
		boolean[] availablePositions = new boolean[s];
		for(int i=0;i<s;i++)
			availablePositions[i] = true;
		int cost = 0;
		int minimumCostHorizontal = minimizedBipartiteMatching(checkerList, s, "horizontal");
		int minimumCostLeftD = minimizedBipartiteMatching(checkerList, s, "leftDiagonal");
		int minimumCostRightD = minimizedBipartiteMatching(checkerList, s, "rightDiagonal");

//		int minimumCostHorizontal = minimumCost(checkerList, availablePositions, cost, s, "horizontal");
//		int minimumCostLeftD = minimumCost(checkerList, availablePositions, cost, s, "leftDiagonal");
//		int minimumCostRightD = minimumCost(checkerList, availablePositions, cost, s, "rightDiagonal");

		//		System.out.println("\n" + minimumCostHorizontal+ "," + minimumCostLeftD + "," + minimumCostRightD);
		
		int minimum=minimumCostHorizontal;
		if(minimum>minimumCostLeftD)
			minimum = minimumCostLeftD;
		if(minimum>minimumCostRightD)
			minimum = minimumCostRightD;
		return minimum;
		
	}
	
	public int minimizedBipartiteMatching(List<Checker> checkerList, int s, String way) {
//		System.out.println("Checkpoint1 : " + way);
		int minimum = 0;
		int[][] checkerMatrix = new int[s][s];
		boolean[][] selectionMatrix = new boolean[s][s];
		for(int i=0; i<s; i++) {
			for(int j=0; j<s; j++) {
				selectionMatrix[i][j] = false;
			}
		}
//		System.out.println("Checkpoint2 : " + way);
			
		for(int i =0; i<s; i++) {
			for(int j=0; j<s; j++) {
				if(way.equals("horizontal"))
					checkerMatrix[i][j] = checkerList.get(i).horizontalRowMovementCost[j];
				else if(way.equals("leftDiagonal"))
					checkerMatrix[i][j] = checkerList.get(i).leftDiagonalMovementCost[j];
				else if(way.equals("rightDiagonal"))
					checkerMatrix[i][j] = checkerList.get(i).rightDiagonalMovementCost[j];
			}
		}
//		System.out.println("Checkpoint3 : " + way);

		for(int i=0; i<s; i++) {
			int min = checkerMatrix[i][0];
			for(int j=0; j<s; j++) {
				if(checkerMatrix[i][j]<min)
					min = checkerMatrix[i][j];
			}
			for(int j=0; j<s; j++) 
				checkerMatrix[i][j]-=min;
			
		}

//		System.out.println("Checkpoint4 : " + way);

		for(int j=0; j<s; j++) {
			int min = checkerMatrix[0][j];
			for(int i=0; i<s; i++) {
				if(checkerMatrix[i][j]<min)
					min = checkerMatrix[i][j];
			}
			for(int i=0; i<s; i++) 
				checkerMatrix[i][j]-=min;
			
		}
		
//		System.out.println("Checkpoint5 : " + way);

		List<Point> resultIndices = new ArrayList<Point>();
		boolean[] horizontalMarks = new boolean[s];
		boolean[] verticalMarks = new boolean[s];
		boolean[] horizontalAssignment = new boolean[s];
		boolean[] verticalAssignment = new boolean[s];
		
//		System.out.println("Checkpoint6 : " + way);

		//initialize
		for(int i=0; i<s; i++) {
			horizontalMarks[i] = false;
			verticalMarks[i] = false;
			horizontalAssignment[i] = false;
			verticalAssignment[i] = false;
		}
		
//		System.out.println("Checkpoint7 : " + way);
		 do {
//				System.out.println("Checkpoint8 : " + way);
			
			for(int i=0; i<s; i++) {
				int zeroCount = 0;
				int posI = i;
				int posJ = -1;
				for(int j=0;j<s;j++) {
					if(!verticalAssignment[j] && checkerMatrix[i][j]==0) {
						zeroCount++;
						posJ = j;
					}
				}
				if(zeroCount == 1) {
					horizontalAssignment[posI] = true;
					verticalAssignment[posJ] = true;
					selectionMatrix[posI][posJ] = true; 
				}
					
			}
//			System.out.println("Checkpoint9 : " + way);
			if(!allAssignmentsDone(horizontalAssignment, s)) {
				for(int j=0; j<s; j++) {
					int zeroCount = 0;
					int posI = -1;
					int posJ = j;
					for(int i=0; i<s; i++) {
						if(!horizontalAssignment[i] && !verticalAssignment[j] && checkerMatrix[i][j]==0) {
							zeroCount++;
							posI = i;
						}
					}
					if(zeroCount ==1) {
						horizontalAssignment[posI] = true;
						verticalAssignment[posJ] = true;
						selectionMatrix[posI][posJ] = true;
					}
				}
			}
//			System.out.println("Checkpoint10 : " + way);
// if there is still scope of assignment but couldn't do it because of confusion, do random assignments
			if(!allAssignmentsDone(verticalAssignment, s)) {
				for(int j=0; j<s; j++) {
					for(int i=0; i<s; i++) {
						if(!horizontalAssignment[i] && !verticalAssignment[j] && checkerMatrix[i][j]==0) {
							selectionMatrix[i][j]= true;
							horizontalAssignment[i] = true; 
							verticalAssignment[j] =true;
						}
					}
				}
			}
			
			printMatrix(selectionMatrix, s);
			if(!allAssignmentsDone(horizontalAssignment, s)) {
				boolean status = updateCheckerMatrix(checkerMatrix, selectionMatrix, horizontalMarks, verticalMarks, horizontalAssignment, verticalAssignment, s);
//				System.out.println("Checkpoint11 : " + way);
				printMatrix(checkerMatrix, s);
				clearAllAssignments(selectionMatrix, horizontalMarks, verticalMarks, horizontalAssignment, verticalAssignment, s);
//				System.out.println("Checkpoint12 : " + way);
				if(!status) {
					findMinimumNodesRecursively(selectionMatrix, checkerMatrix, horizontalAssignment, verticalAssignment, s, 0);
					printMatrix(selectionMatrix, s);
					break;
				}
			}
		} while(!allAssignmentsDone(horizontalAssignment, s));
		 
		for(int i=0; i<s; i++) {
			for(int j=0; j<s; j++) {
				if(selectionMatrix[i][j]) {
					if(way.equals("horizontal"))
						minimum += checkerList.get(i).horizontalRowMovementCost[j];
					else if(way.equals("leftDiagonal"))
						minimum += checkerList.get(i).leftDiagonalMovementCost[j];
					else if(way.equals("rightDiagonal"))
						minimum += checkerList.get(i).rightDiagonalMovementCost[j];
				}
			}
		}
		return minimum;
	}

	private boolean findMinimumNodesRecursively(boolean[][] selectionMatrix,
		int[][] checkerMatrix, boolean[] horizontalAssignment, boolean[] verticalAssignment, int s, int rowToBeProcessed) {
		if(rowToBeProcessed == s)
			return true;
		boolean processFlag=false;
		for(int j=0; j<s; j++) {
			if(!verticalAssignment[j] && checkerMatrix[rowToBeProcessed][j]==0) {
				processFlag = true;
				selectionMatrix[rowToBeProcessed][j] = true;
				horizontalAssignment[rowToBeProcessed] = true;
				verticalAssignment[j] = true;
				boolean assigned = findMinimumNodesRecursively(selectionMatrix, checkerMatrix, horizontalAssignment, verticalAssignment, s, rowToBeProcessed+1);
				if(assigned) {
					return true;
				} else {					
					selectionMatrix[rowToBeProcessed][j] = false;
					horizontalAssignment[rowToBeProcessed] = false;
					verticalAssignment[j] = false;
					processFlag = false;
				}
			}
		}
		if(rowToBeProcessed<s && !processFlag) {
			return false;
		} else 
			return true;
		
	}

	private void printArray(boolean[] array, int s) {
//		System.out.println();
//		for(int i=0; i<s; i++) {
//			System.out.print(array[i]+ ",");
//		}
//		System.out.println();
	}
	private void printMatrix(int[][] matrix,int s) {
//		System.out.println();
//		for(int i=0;i<s;i++) {
//			for(int j=0;j<s;j++) {
//				System.out.print(matrix[i][j] +",");
//			}
//			System.out.println();
//		}
	}
	
	private void printMatrix(boolean[][] matrix,int s) {
//		System.out.println();
//		for(int i=0;i<s;i++) {
//			for(int j=0;j<s;j++) {
//				System.out.print(matrix[i][j] +",");
//			}
//			System.out.println();
//		}
	}
	
	private void clearAllAssignments(boolean[][] selectionMatrix,
			boolean[] horizontalMarks, boolean[] verticalMarks,
			boolean[] horizontalAssignment, boolean[] verticalAssignment, int s) {
		for(int i=0; i<s; i++) {
			horizontalMarks[i] = false;
			verticalMarks[i] = false;
			horizontalAssignment[i] = false;
			verticalAssignment[i] = false;
			for(int j=0; j<s; j++) {
				selectionMatrix[i][j] = false;
			}
		}
		
	}

	private boolean updateCheckerMatrix(int[][] checkerMatrix,
			boolean[][] selectionMatrix, boolean[] horizontalMarks,
			boolean[] verticalMarks, boolean[] horizontalAssignment,
			boolean[] verticalAssignment, int s) {
		//updateCheckerMatrix
//		printMatrix(selectionMatrix, s);
//		printMatrix(checkerMatrix, s);
//		printArray(horizontalAssignment, s);
//		printArray(horizontalMarks, s);
//		printArray(verticalAssignment, s);
//		printArray(verticalMarks, s);
		
		//Correct Assignment Logic 
		
		List<Integer> processListVertical = new ArrayList<Integer>();
		List<Integer> processListHorizontal = new ArrayList<Integer>();
		
		for(int j=0; j<s; j++) {
			if(!horizontalAssignment[j]) {
				horizontalMarks[j] = true;
				for(int i=0; i<s; i++) {
					if(checkerMatrix[j][i]==0) {
						verticalMarks[i] = true;
						processListVertical.add(i);
					}
				}
			}
		}
		
		while(!processListHorizontal.isEmpty() || !processListVertical.isEmpty()) {
			
			for(Integer i: processListVertical) {
				if(verticalMarks[i]) {
					for(int j=0; j<s; j++) {
						if(!horizontalMarks[j] && selectionMatrix[j][i]) {
							horizontalMarks[j] =true;
							processListHorizontal.add(j);
						}
					}
				}
			}
			processListVertical.clear();
			for(Integer j: processListHorizontal) {
				if(horizontalMarks[j]) {
					for(int i=0; i<s; i++) {
						if(!verticalMarks[i] && checkerMatrix[j][i] ==0) {
							verticalMarks[i] =  true;
							processListVertical.add(i);
						}
					}
				}
			}
			processListHorizontal.clear();
		}
		
		if(allAssignmentsDone(horizontalMarks, s) || allAssignmentsDone(verticalMarks, s)) {
			System.out.println("Found all false");
			return false;
		}
		
		int theta = -1;
		for(int i=0; i<s; i++) {
			for(int j=0; j<s; j++) {
				if(horizontalMarks[i] && !verticalMarks[j] && (theta==-1 || checkerMatrix[i][j]<theta))
					theta = checkerMatrix[i][j];
			}
		}
		System.out.println("Theta: " + theta);
		for(int i=0; i<s; i++) {
			for(int j=0; j<s; j++) {
				if(horizontalMarks[i] && !verticalMarks[j])
					checkerMatrix[i][j]-=theta;
				if(!horizontalMarks[i] && verticalMarks[j])
					checkerMatrix[i][j]+=theta;
			}
		}
		return true;
		
		
	}

	private boolean allAssignmentsDone(boolean[] horizontalAssignment, int s) {
		for(int i=0; i<s; i++) {
			if(!horizontalAssignment[i])
				return false;
		}
		return true;
	}

	private int minimumCost(List<Checker> remainingCheckerList,
			boolean[] availablePositions, int cost, int s, String way) {
		if(remainingCheckerList.isEmpty())
			return cost;
		
		int minCost=-1;
		List<Checker> remainingCheckerListCopy = new ArrayList<Checker>();
		remainingCheckerListCopy.addAll(remainingCheckerList);
		Checker currentChecker = remainingCheckerListCopy.remove(0);
		
		for(int i=0; i<s; i++) {
			int costUpdated = cost;
			if(availablePositions[i]) {
				boolean[] availablePositionsUpdated = availablePositions.clone();
				availablePositionsUpdated[i] = false;
				if(way.equals("horizontal"))
					costUpdated+=currentChecker.horizontalRowMovementCost[i];
				else if(way.equals("rightDiagonal"))
					costUpdated+=currentChecker.rightDiagonalMovementCost[i];
				else 
					costUpdated+= currentChecker.leftDiagonalMovementCost[i];
				int minCostLocal = minimumCost(remainingCheckerListCopy, availablePositionsUpdated, costUpdated, s, way);
				if(minCostLocal<minCost || minCost == -1)
					minCost = minCostLocal;
			}
		}
		return minCost;
	}

	private class MatrixLocation {
		int row;
		int col;
		public MatrixLocation(int i, int j) {
			this.row =i;
			this.col =j;
		}
	}
	
	private void evaluateCheckerCost(Checker checker, int j, int k, int[][] matrixNumbered, int s) {
		int[][] checkerMovementMatrix = new int[s][s];
		for(int x=0;x<s;x++) {
			for(int y=0;y<s;y++) {
				checkerMovementMatrix[x][y] = -1; 
			}
		}
		
		checkerMovementMatrix[j][k] = 0;
		Queue<MatrixLocation> locationQueues = new LinkedList<HexagonGame.MatrixLocation>();
		addNeighbours(locationQueues, j, k, matrixNumbered, checkerMovementMatrix, s);
		while(!locationQueues.isEmpty()) {
			MatrixLocation loc= locationQueues.remove();
			addNeighbours(locationQueues, loc.row, loc.col, matrixNumbered, checkerMovementMatrix, s);
		}
//		for(int x=0;x<s;x++) {
//			for(int y=0;y<s;y++) {
//				System.out.print(checkerMovementMatrix[x][y]+ ",");
//			}
//			System.out.println();
//		}
//		System.out.println();
		checker.horizontalRowMovementCost = new int[s];
		checker.rightDiagonalMovementCost = new int[s];
		checker.leftDiagonalMovementCost = new int[s];
		for(int x=0;x<s;x++) {
			checker.horizontalRowMovementCost[x] = checkerMovementMatrix[s/2][x] * checker.getMovementCost();
			checker.rightDiagonalMovementCost[x] = checkerMovementMatrix[x][s/2] * checker.getMovementCost();
			checker.leftDiagonalMovementCost[x] = checkerMovementMatrix[x][x] * checker.getMovementCost();
//			System.out.print(checker.leftDiagonalMovementCost[x] + ",");
		}
//		System.out.println();
		
	}
	
	

	private void addNeighbours(Queue<MatrixLocation> locationQueues, int j,
			int k, int[][] matrixNumbered, int[][] checkerMovementMatrix, int s) {
		
		int movementUnits =checkerMovementMatrix[j][k]+1;
		validateAndAddInQueues(j-1, k-1, locationQueues, matrixNumbered, checkerMovementMatrix, movementUnits, s);
		validateAndAddInQueues(j+1, k+1, locationQueues, matrixNumbered, checkerMovementMatrix, movementUnits, s);
		validateAndAddInQueues(j, k-1, locationQueues, matrixNumbered, checkerMovementMatrix, movementUnits, s);
		validateAndAddInQueues(j, k+1, locationQueues, matrixNumbered, checkerMovementMatrix, movementUnits, s);
		validateAndAddInQueues(j-1, k, locationQueues, matrixNumbered, checkerMovementMatrix, movementUnits, s);
		validateAndAddInQueues(j+1, k, locationQueues, matrixNumbered, checkerMovementMatrix, movementUnits, s);
			
	}

	private void validateAndAddInQueues(int row, int col,
			Queue<MatrixLocation> locationQueues, int[][] matrixNumbered,
			int[][] checkerMovementMatrix, int movementUnits, int s) {

		if((row)>=0 && (col)>=0 && (row)<s && (col)<s && matrixNumbered[row][col] != 0 && checkerMovementMatrix[row][col]==-1) {
			locationQueues.add(new MatrixLocation(row, col));
			checkerMovementMatrix[row][col] = movementUnits;
		}
		
	}

	private List<Checker> getCheckerList(String[] checkerPos,
			String[] checkerMovementCost, int s) {
		List<Checker> checkerList = new ArrayList<Checker>();
		for(int i=0; i<s; i++) {
			checkerList.add(new Checker(Integer.parseInt(checkerPos[i]), Integer.parseInt(checkerMovementCost[i])));
		}
		return checkerList;
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
		HexagonGame solution = new HexagonGame();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}


