package application;

import java.util.Scanner;

public class Matrix {
	
	private double[][] mtx; //Matrix is a 2D array
	private int rowLength; //row dimensions of the matrix
	private int columnLength; //column dimensions of the matrix
	private String output;
	
	//default matrix is a 2x2 with all 0's
	public Matrix() {
		mtx= new double[2][2];
		rowLength= 2;
		columnLength= 2;
		for(int i=0; i<rowLength; i++) {
			for(int j=0; j<columnLength; j++) {
				mtx[i][j]= 0;
			}
		}
	}
	
	//matrix with dimensions entered
	public Matrix(int rowNum, int colNum) {
		mtx= new double[rowNum][colNum];
		rowLength= rowNum;
		columnLength= colNum;
		for(int i=0; i<rowLength; i++) {
			for(int j=0; j<columnLength; j++) {
				mtx[i][j]= 0;
			}
		}
	}
	
	//placing elements into the matrix from comand line
	public void setMatrix_CMD() {
		System.out.println("Enter elements separated by a comma: (enter only numbers)");
		Scanner input= new Scanner(System.in);
		String[] numbers = input.nextLine().split(",");
		int index= 0;
		for(int i=0; i<rowLength; i++) {
			for(int j=0; j<columnLength; j++) {
				mtx[i][j] = Integer.parseInt(numbers[index]);
				index++;
			}
		}
	}
	
	//placing elements into the matrix from an array
	public void setMatrix(double[] array) {
		int index = 0;
		for(int i=0; i<rowLength; i++) {
			for(int j=0; j<columnLength; j++) {
				mtx[i][j]= array[index];
				index++;
			}
		}
	}
	
	//change a specific element
	public void setElement(int rowNum, int colNum, int entry) {
		mtx[rowNum][colNum]= entry;
	}
	
	//matrix toSting method
	public String toString() {
		String mtxPrint= "";
		for(int i=0; i<rowLength; i++) {
			for(int j=0; j<columnLength; j++) {
				mtxPrint+= mtx[i][j]+"\t";
			}
			mtxPrint+= "\n";
		}
		return mtxPrint;
	}
	
	public String getOutput() {
		int index = 0;
		for(int i=0; i<output.length(); i++) {
			if(output.charAt(i) == '\n') {
				index = i;
				break;
			}
		}
		String formatted = output.substring(index+1);
		return formatted;
	}
	
	//INTERCHANGE (row a with row b)  note: row and column numbers start at 0, not 1
	public void interchange(int a, int b) {
		
		double[] RowATemp = mtx[a];
		mtx[a] = mtx[b];
		mtx[b] = RowATemp;

	} 
		
	//SCALAR
	public void scalar(int row, double scalar) {
		for(int i=0; i<columnLength; i++) {
			mtx[row][i]*= scalar;
		}
	}
	
	//REPLACE note: to subtract use a negative scalar
	public void replace(int a, int b, double scalar) { 
		for(int i=0; i<columnLength; i++) {
			mtx[a][i]+=(mtx[b][i]*scalar);
		}
	}
	
	//check a column for all zeroes (note: a represents the column number)
	public boolean isColumnZeroes(int a) {
		for(int i=0; i<rowLength; i++) {
			if(mtx[i][a] != 0) {
				return false;
			}
		}
		return true;
	}
	
	//check a row for all zeroes (note: a represents the row number)
	public boolean isRowZeroes(int a) {
		for(int i=0; i<columnLength; i++) {
			if(mtx[a][i] != 0) {
				return false;
			}
		}
		return true;
	}
	 
	//Find the pivot of a matrix
	public element findPivot(element a) {
		int row = a.getRow();
		element pivot = new element(a.getRow(), a.getColumn());
		element current = new element(a.getRow(), a.getColumn());
		
		for(int i= a.getRow(); i<(rowLength - row); i++) {
			current.setRow(i);
			if(mtx[current.getRow()][current.getColumn()] == 1) {
				interchange(current.getRow(), a.getRow());
				output += "Interchange with "+(current.getRow()+1)+" with "+(a.getRow()+1)+'\n';
				output += '\n';
				output += toString()+'\n';
			}
		}
		current.setRow(a.getRow());
		for(int i=current.getRow(); i<(rowLength - row); i++) {
			current.getRow();
			if(mtx[current.getRow()][current.getColumn()] != 0) {
				pivot.setRow(i);
				break;
			}
		}
		return pivot;

	}

	public double getElement(element a) {
		return mtx[a.getRow()][a.getColumn()];
	}
	
	//RREF Matrix Method
	public void RREF() {
		element pivot = new element(0,0);
		
		int submatrix= 0;
		for(int i=0; i<columnLength; i++) {
			pivot = new element(pivot.getRow(), i);
			
			//leftmost nonzero column
			for(int j=i; j<columnLength; j++) {
				if(isColumnZeroes(pivot.getColumn()) == false) {
					break;
				}else {
					pivot.setColumn(i);
				}
			}
			
			//nonzero with highest value
			pivot = findPivot(pivot);
			
			if(mtx[pivot.getRow()][pivot.getColumn()] == 0) {
				pivot.setRow(pivot.getRow()+1);
				continue;
			}
			
			//interchange rows
			if(pivot.getRow() != submatrix) {
				interchange(submatrix,pivot.getRow());
				output += "Interchange row "+(submatrix+1)+" with "+(pivot.getRow()+1)+'\n';
				output += '\n';
				output += toString()+'\n';
			}
			
			//force pivot to be 1
			if(mtx[pivot.getRow()][pivot.getColumn()] != 1) {
				double scalar = (1.0/mtx[pivot.getRow()][pivot.getColumn()]);
				scalar(pivot.getRow(),scalar);
				output += "Scale row "+(pivot.getRow()+1)+" with "+scalar+'\n';
				output += '\n';
				output += toString()+'\n';
			}
			
			//create zeros below pivot
			for(int j = pivot.getRow(); j<rowLength; j++) {
				if(j==pivot.getRow()) {
					continue;
				}
				double complement = (-1*mtx[j][pivot.getColumn()])/(mtx[pivot.getRow()][pivot.getColumn()]);
				replace(j,pivot.getRow(),complement);
				output += "Replace row "+(j+1)+" with "+(pivot.getRow()+1)+" scaled by "+complement+'\n';
				output += '\n';
				output += toString()+'\n';
			}
			
			//working backwards (rightmost pivot moving left and up)
			for(int j = pivot.getRow(); j >= 0; j--) {
				if(j == pivot.getRow()) {
					if(mtx[pivot.getRow()][pivot.getColumn()] != 1) {
						double reciporocal = 1/mtx[pivot.getRow()][pivot.getColumn()];
						scalar(pivot.getRow(),reciporocal);
						output += "Scale row "+(pivot.getRow()+1)+" with "+reciporocal+'\n';
						output += '\n';
						output += toString()+'\n';
					}
					continue;
				}
				if (j == pivot.getRow()) {
					continue;
				}
				
				element abovePivot = new element(j,pivot.getColumn());
				double complement = (-1*mtx[j][pivot.getColumn()])/mtx[pivot.getRow()][pivot.getColumn()];
				replace(j,pivot.getRow(),complement);
				output += "Replace row "+(j+1)+" with "+(pivot.getRow()+1)+" scaled by "+complement+'\n';
				output += '\n';
				output += toString()+'\n';
			}
			
			if( (pivot.getRow()+1) >= rowLength || isRowZeroes(pivot.getRow()+1) ) {
				break;
			}
			
			submatrix++;
			pivot.setRow(pivot.getRow()+1);
			
		}
	}	
}
