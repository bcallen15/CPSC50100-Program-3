//**************************************************************
//	NearestNeighbor.java				Author: Ben Callen
//	CPSC50100 Summer II 2020
//
//	Uses machine learning to identify three types of Iris plants
//************************************************************** 

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class NearestNeighbor {
	
	//Initial 2D data arrays
	static double[][] testingValues = new double[75][4];
	static double[][] trainingValues = new double[75][4];
	
	//Initial 1D data arrays
	static String[] testFlower = new String[75];
	static String[] trainFlower = new String[75];
	
	static String[] nearestFlower = new String[75];//initializes string of the closest flower compared to training data
	
	//Sets initial value of accuracy measurement to zero
	static double accuracy = 0;
	
	//Sets initial values for distance calculations
	static double[] distance = new double[75];
	static double shortestDistance = 0;
	
	//Scanner for the training data file
	static double[][] trainingData() throws FileNotFoundException {
		File trainingData = new File("iris-training-data.csv");
		
		Scanner trainData = new Scanner(trainingData);
		
		int row = 0;
		while(trainData.hasNext()) {
			String exNumLine = trainData.nextLine();
			String[] exNumLinePart = exNumLine.split(",");
			
			for (int col = 0; col <4; col++) {
				trainingValues[row][col] = Double.parseDouble(exNumLinePart[col]);
			}
			
			trainFlower[row] = exNumLinePart[4];
			row++;
		}
		
		trainData.close();
		return trainingValues;
	}
	
	//Scanner for testing data file
	static double[][] testingData() throws FileNotFoundException {
		File testingData = new File("iris-testing-data.csv");
		
		Scanner testData = new Scanner(testingData);
		
		int row = 0;
		while(testData.hasNext()) {
			String exNumLine = testData.nextLine();
			String[] exNumLinePart = exNumLine.split(",");
			
			for (int col = 0; col <4; col++) {
				testingValues[row][col] = Double.parseDouble(exNumLinePart[col]);
			}
			
			testFlower[row] = exNumLinePart[4];
			row++;
		}
		
		testData.close();
		return testingValues;
	}
	
	static String[] closestFlower() {
		
		int shortest = 0;
		
		for (int trainingRow = 0; trainingRow < 75; trainingRow++) {
			for (int testingRow = 0; testingRow <75; testingRow++) {
				//calculates distance between sepal and petal lengths and widths
				double sLDiff = testingValues[testingRow][0] - trainingValues[trainingRow][0];
				double sWDiff = testingValues[testingRow][1] - trainingValues[trainingRow][1];
				double pLDiff = testingValues[testingRow][2] - trainingValues[trainingRow][2];
				double pWDiff = testingValues[testingRow][3] - trainingValues[trainingRow][3];
				
				double distancesquared = Math.pow(sLDiff, 2) + Math.pow(sWDiff, 2) + Math.pow(pLDiff, 2) + Math.pow(pWDiff, 2);
				
				distance[trainingRow] = Math.sqrt(distancesquared);
				
				if(testingRow == 0|| distance[trainingRow] < shortestDistance) {
					shortest = testingRow;
					shortestDistance = distance[trainingRow];
				}
			}
			
			nearestFlower[trainingRow] = testFlower[shortest];
		}
		
		return nearestFlower;
		
	}
	
	//Find accuracy of the testing data
	static double testAccuracy() {
		
		int accurateGuess = 0;
		
		for(int row = 0; row <75; row++) {
			switch(nearestFlower[row]) {
			case "Iris-setosa":
				if(nearestFlower.equals(trainFlower[row])) {
					accurateGuess++;
				}
				break;
			case "Iris-versicolor":
				if(nearestFlower.equals(trainFlower[row])) {
					accurateGuess++;
				}
				break;
			case "Iris-virginica":
				if(nearestFlower.equals(trainFlower[row])) {
					accurateGuess++;
				}
				break;
			}
		}
		
		accuracy = (double)accurateGuess/75.0;
		return accuracy;
	}
	
	static void results() {
		System.out.println("Example #: Correct Guess, Predicted Guess");
		
		for(int row = 0; row<75; row++) {
			System.out.print(row + 1 + ": ");
			System.out.print(trainFlower[row] + " ");
			System.out.print(nearestFlower[row] + "\n");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Programming Fundamentals");
		System.out.println("NAME: Ben Callen");
		System.out.println("PROGRAMMING ASSIGNMENT 3");
		
		System.out.println("");
		
		System.out.println("Enter the name of the training file: ");
		Scanner inputTrain = new Scanner(System.in);
		
		System.out.println("Enter the name of the testing file: ");
		Scanner inputTest = new Scanner(System.in);
		
		System.out.println("");
		
		trainingData();
		testingData();
		closestFlower();
		
		results();
		System.out.println("Accuracy: " + accuracy);

	}

}
