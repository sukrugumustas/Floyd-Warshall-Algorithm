//Atakan ÜLGEN, 150115066
//Þükrü GÜMÜÞTAÞ, 150114032

//importing the necessary classes
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class hw3problem2 {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//if command line argument is empty the program will stop
		if(args.length == 0) {
			System.out.println("There is no file!");
			return;
		}
		//declaring variables
		Scanner sc = null;
		//file location can be given as string and command line argument
		//is a string stored in args[0]
		File file = new File(args[0]);
		try {
			//if possible scan the file
			sc = new Scanner(file);
		} catch (Exception e) {
			//if not possible give the error message
			e.printStackTrace();
			System.out.println("There is no such file!");
			return;
		}
		//this array contains locations and range
		double [][] properties;
		int [][] destinations;
		int size;
		//scanner can read the numbers or other thnings via its methods like nextInt, nextDouble
		//so if the right algorithm is created, the blank spaces will not be a problem
		String deneme = sc.nextLine();
		while(deneme.charAt(0)=='#') {
			deneme=sc.nextLine();
		}
		//declaring the number of agents
		size = Integer.parseInt(deneme);
		properties = new double[size][3];
		destinations = new int[size][size];
		for (int i = 0; i<size; i++) {
			for (int j = 0; j<3; j++) {
				//here we scan the location and range values and store them in the double array
				//according to index of wifi agent
				properties[i][j] = Double.parseDouble(sc.next());
			}
		}
		destinations = distanceCalculator(properties, size);
		floydWarshall(destinations, size);
		writeToFile(destinations, args[0]);
	}
	
	private static int [][] distanceCalculator (double[][] array, int size) {
		int [][] returner = new int[size][size];
		for (int i = 0; i<size; i++) {
			for (int j = 0; j<size; j++) {
				if (i==j) {
					//an agent doesn't need to communicate itself
					returner[i][j] = 0;
					continue;
				}
				//using distance between two points from basic geomethry
				//if a wifi is in range of other wifi agent, array with those
				//indexes will become 1 otherwise 999999 to declare infinity
				if (Math.sqrt(Math.pow(array[i][0]-array[j][0], 2) + Math.pow(array[i][1]-array[j][1], 2)) > array[i][2]) {
					returner[i][j] = 999999;
				} else {
					returner[i][j] = 1;
				}
			}
		}
		return returner;
	}
	
	//this is floyd warshall algorithm
	private static void floydWarshall (int [][] array, int size) {
		for (int k = 0; k<size; k++) {
			for (int i = 0; i<size; i++) {
				for (int j = 0; j<size; j++) {
					if (array[i][k] + array[k][j] < array[i][j]) {
						array[i][j] = array[i][k] + array[k][j];
					}
				}
			}
		}
	}
	
	//this method writes to file
	private static void writeToFile(int [][] array, String filename) {
		int k = 0;
		//we find the input file name so we can add _output.txt to end of it and create output file
		while(filename.charAt(k)!='.') {
			k++;
		}
		try {
			 BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename.substring(0, k) + "_output.txt")));
			 for (int i = 0; i<array.length; i++) {
				 //if a value between two agents is 999999 then they can not reach each other
				 if (array[0][i] == 999999) {
					 writer.write("0");
				 } else {
					 writer.write(Integer.toString(array[0][i]));
				 }
				 writer.newLine();
			 }
			 writer.close();
		 } catch (Exception e) {
			 e.printStackTrace();
			 return;
		 }
	}
}
