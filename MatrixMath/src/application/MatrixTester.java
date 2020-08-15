package application;

import java.util.Scanner;

public class MatrixTester {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the number of rows: ");
		int row= input.nextInt();
		System.out.println("Please enter the number of columns: ");
		int column= input.nextInt();
		Matrix myMatrix= new Matrix(row,column);
		myMatrix.setMatrix_CMD();
		
		//Test interchange
//		myMatrix.interchange(0, 1);
//		System.out.println(myMatrix.toString());
		
		//Test scalar
//		myMatrix.scalar(0, 3);
//		System.out.println(myMatrix.toString());
		
		//Test replacement
//		myMatrix.replace(2, 1, -1);
//		System.out.println(myMatrix.toString());
		
		//Test RREF
		myMatrix.RREF();
		System.out.println(myMatrix.getOutput());
		//System.out.println(myMatrix.toString());
		
	}

}
