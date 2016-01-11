package com.org.question018;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CropTriangles {
	private final String inputFileName = "A-large-practice.in";
	private final String outputFileName = "A-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	
	CropTriangles() {
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
			// Start coding from here
			System.out.println("Case Progress: " + (i+1)+ "#");
			line = bufferedReader.readLine();
			String[] splitInputs = line.split(" ");
			int nTrees = Integer.parseInt(splitInputs[0]);
			int A_ = Integer.parseInt(splitInputs[1]);
			int B_ = Integer.parseInt(splitInputs[2]);
			int C_ = Integer.parseInt(splitInputs[3]);
			int D_ = Integer.parseInt(splitInputs[4]);
			int x_ = Integer.parseInt(splitInputs[5]);
			int y_ = Integer.parseInt(splitInputs[6]);
			int M_ = Integer.parseInt(splitInputs[7]);
			long[][] pointModuloMatrix= new long[3][3];
			fillPointModuloMatrix(nTrees, A_, B_, C_, D_, x_, y_, M_, pointModuloMatrix);
			
			long output = 0L;
			// for Triangles created by group of 3 points within a cell
			for(int j=0; j<3; j++) {
				for(int k=0; k<3; k++) {
					output += ((pointModuloMatrix[j][k])*(pointModuloMatrix[j][k]-1)*(pointModuloMatrix[j][k]-2))/6;
				}
			}
			
			// for Triangles created by points one from every column in each row and by points one from every row in each column
			for(int j=0; j<3; j++) {
				output += pointModuloMatrix[j][0] * pointModuloMatrix[j][1] * pointModuloMatrix[j][2];
				output += pointModuloMatrix[0][j] * pointModuloMatrix[1][j] * pointModuloMatrix[2][j]; 
			}
			
			//for Triangles created by points by taking only one point  from a row and a column
			for(int j=0; j<3; j++) {
				for(int k=0; k<3; k++) {
					for(int l=0; l<3; l++){
						if(l!=j && j!=k && l!=k) {
							output += pointModuloMatrix[0][j] * pointModuloMatrix[1][k] * pointModuloMatrix[2][l];
						}
					}
				}
			}
		
			appendOutputTofile(i+1, String.valueOf(output), bufferedWriter);
		}
		
		
		/*
		 * X = x0, Y = y0
		 * print X, Y
		 * for i = 1 to n-1
		 *   X = (A * X + B) mod M
		 *   Y = (C * Y + D) mod M
		 *   print X, Y
		 */
	}

	
	private void fillPointModuloMatrix(int n, int a_, int b_, int c_, int d_, int x_,
			int y_, int m_, long[][] pointModuloMatrix) {
		
		
		
		// TODO Auto-generated method stub
		/*
		 * X = x0, Y = y0
		 * print X, Y
		 * for i = 1 to n-1
		 *   X = (A * X + B) mod M
		 *   Y = (C * Y + D) mod M
		 *   print X, Y
		 */
		
		long X = x_, Y = y_;
		pointModuloMatrix[(int)X%3][(int)Y%3]++;
		
		for(int i=1; i<=n-1;i++) {
			//System.out.println("Point entry: " + (i+1) + "#");
			X = (a_*X + b_) % m_;
			Y = (c_*Y + d_) % m_;
			//System.out.println("Points: X,Y= " + X +"," +Y);
			(pointModuloMatrix[((int)X%3)][((int)Y%3)])++;
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
		CropTriangles solution = new CropTriangles();
		try {
			solution.solve();
			solution.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
