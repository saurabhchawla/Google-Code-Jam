package com.org.question005;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TriangleTrilemma {

	private final String inputFileName = "A-large-practice.in";
	private final String outputFileName = "A-large-practice.out";
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	
	public TriangleTrilemma() {
		// TODO Auto-generated constructor stub
		initializeFileReaders();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TriangleTrilemma solution = new TriangleTrilemma();
		try {
			solution.solve();
			solution.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public void close() throws IOException {
		bufferedReader.close();
		bufferedWriter.close();
	}
	
	public void solve() throws IOException {

		String line = bufferedReader.readLine();
		int n = Integer.parseInt(line);
		for(int i=0; i<n; i++) {
			line = bufferedReader.readLine();
			String[] coordinates = line.split(" ");
			Point2D vertex1 = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
			Point2D vertex2 = new Point(Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]));
			Point2D vertex3 = new Point(Integer.parseInt(coordinates[4]), Integer.parseInt(coordinates[5]));
			String output = analyzeVertices(vertex1, vertex2, vertex3);
			bufferedWriter.write("Case #" + (i + 1) + ": " + output);
			bufferedWriter.newLine();
			
		}
		
	}
	
	public String analyzeVertices(Point2D vertex1, Point2D vertex2, Point2D vertex3) {
		if(isATriangle(vertex1, vertex2, vertex3)) {
			double aSquare = Math.pow(vertex1.getX() - vertex2.getX(),2) + Math.pow(vertex1.getY() - vertex2.getY(), 2);
			double bSquare = Math.pow(vertex2.getX() - vertex3.getX(),2) + Math.pow(vertex2.getY() - vertex3.getY(), 2);
			double cSquare = Math.pow(vertex3.getX() - vertex1.getX(),2) + Math.pow(vertex3.getY() - vertex1.getY(), 2);
			String component2 = classifyBasedOnSides(aSquare, bSquare, cSquare);
			String component1 = "";
			if(aSquare>=bSquare && aSquare>=cSquare) {
				component1 = classifyBasedOnAngles(cSquare, bSquare, aSquare);
			} else if(bSquare>=aSquare && bSquare>=cSquare) {
				component1 = classifyBasedOnAngles(cSquare, aSquare, bSquare);
			} else if(cSquare>=bSquare && cSquare>=aSquare) {
				component1 = classifyBasedOnAngles(aSquare, bSquare, cSquare);
			}
			return component2 + " " + component1 + " triangle";
		} else {
			return "not a triangle";
		}
	}
	
	public boolean isATriangle(Point2D vertex1, Point2D vertex2, Point2D vertex3) {
		double difference = (vertex1.getY() - vertex2.getY()) * (vertex3.getX() - vertex2.getX()) - (vertex3.getY() - vertex2.getY())*(vertex1.getX()-vertex2.getX()); 
		if(difference == 0)
			return false;
		else
			return true;
	}
	
	public String classifyBasedOnSides(double aSquare, double bSquare, double cSquare) {
		if(aSquare==bSquare) {
			return "isosceles";
		} else if(bSquare==cSquare) {
			return "isosceles";
		} else if(aSquare == cSquare) {
			return "isosceles";
		} else
			return "scalene";
	}
	
	public String classifyBasedOnAngles(double aSquare, double bSquare, double cSquare) {
		//double largest = aSquare>bSquare? aSquare>cSquare?aSquare:cSquare:bSquare>cSquare?bSquare:cSquare;
		//if(cSquare>)
		double sumOfSquares = aSquare + bSquare;
		if(sumOfSquares > cSquare) {
			return "acute";
		} else if(sumOfSquares == cSquare) {
			return "right";
		} else {
			return "obtuse";
		}
	}
}
