package com.org.question008;

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
	private final String inputFileName = "input.txt";
	private final String outputFileName = "output.txt";
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
	
	private class Checker {
		private int pos;
		private int movementCost;
		private int[] horizontalRowMovementCost;
		private int[] leftDiagonalMovementCost;
		private int[] rightDiagonalMovementCost;
		
		public Checker(int pos, int movementCost) {
			this.setPos(pos);
			this.setMovementCost(movementCost);
		}

		public int getPos() {
			return pos;
		}

		public void setPos(int pos) {
			this.pos = pos;
		}

		public int getMovementCost() {
			return movementCost;
		}

		public void setMovementCost(int movementCost) {
			this.movementCost = movementCost;
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
			
			for(int k=0;k<s;k++) {
				for(int j=0;j<s;j++) {
					System.out.print(matrixNumbered[k][j]+ ",");
				}
				System.out.println();
			}
           System.out.println();
			for(int l=0; l<s; l++) {
				for(int k=0;k<s;k++) {
					for(int j=0;j<s;j++) {
						if(matrixNumbered[j][k] == checkerList.get(l).getPos()) {
							evaluateCheckerCost(checkerList.get(l), j, k, matrixNumbered, s);
							System.out.println();
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
		int minimumCostHorizontal = minimumCost(checkerList, availablePositions, cost, s, "horizontal");
		int minimumCostLeftD = minimumCost(checkerList, availablePositions, cost, s, "leftDiagonal");
		int minimumCostRightD = minimumCost(checkerList, availablePositions, cost, s, "rightDiagonal");
//		System.out.println("\n" + minimumCostHorizontal+ "," + minimumCostLeftD + "," + minimumCostRightD);
		int minimum=minimumCostHorizontal;
		if(minimum>minimumCostLeftD)
			minimum = minimumCostLeftD;
		if(minimum>minimumCostRightD)
			minimum = minimumCostRightD;
		return minimum;
		
	}
	
	private int minimizedBipartiteMatching(List<Checker> checkerList, int s) {
		int minimum = -1;
		int[][] checkerMatrix = new int[s][s];
		for(int i =0; i<s; i++) {
			for(int j=0; j<s; j++) {
				checkerMatrix[i][j] = checkerList.get(i).horizontalRowMovementCost[j];
			}
		}
		for(int i=0; i<s; i++) {
			int min = checkerMatrix[i][0];
			for(int j=0; j<s; j++) {
				if(checkerMatrix[i][j]<min)
					min = checkerMatrix[i][j];
			}
			for(int j=0; j<s; j++) 
				checkerMatrix[i][j]-=min;
			
		}

		for(int j=0; j<s; j++) {
			int min = checkerMatrix[0][j];
			for(int i=0; i<s; i++) {
				if(checkerMatrix[i][j]<min)
					min = checkerMatrix[i][j];
			}
			for(int i=0; i<s; i++) 
				checkerMatrix[i][j]-=min;
			
		}
		
		int[] resultIndices = new int[s];
		int[] horizontalMarks = new int[s];
		int[] verticalMarks = new int[s];
		
		return minimum;
	}

	private int minimumCost(List<Checker> remainingCheckerList,
			boolean[] availablePositions, int cost, int s, String way) {
		if(remainingCheckerList.isEmpty())
			return cost;
		
		int minCost=-1;
		List<Checker> remainingCheckerListCopy = new ArrayList<HexagonGame.Checker>();
		remainingCheckerListCopy.addAll(remainingCheckerList);
		Checker currentChecker = remainingCheckerListCopy.remove(0);
		
		for(int i=0; i<s; i++ ) {
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
		for(int x=0;x<s;x++) {
			for(int y=0;y<s;y++) {
				System.out.print(checkerMovementMatrix[x][y]+ ",");
			}
			System.out.println();
		}
		System.out.println();
		checker.horizontalRowMovementCost = new int[s];
		checker.rightDiagonalMovementCost = new int[s];
		checker.leftDiagonalMovementCost = new int[s];
		for(int x=0;x<5;x++) {
			checker.horizontalRowMovementCost[x] = checkerMovementMatrix[s/2][x] * checker.getMovementCost();
			checker.rightDiagonalMovementCost[x] = checkerMovementMatrix[x][s/2] * checker.getMovementCost();
			checker.leftDiagonalMovementCost[x] = checkerMovementMatrix[x][x] * checker.getMovementCost();
			System.out.print(checker.horizontalRowMovementCost[x] + ",");
		}
		System.out.println();
		
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
		List<Checker> checkerList = new ArrayList<HexagonGame.Checker>();
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


