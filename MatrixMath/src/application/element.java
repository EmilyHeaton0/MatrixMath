package application;

public class element {
	
	private int row;
	private int column;
	
	public element(int r, int c) {
		row = r;
		column = c;
	}
	
	public void setRow(int r) {
		row = r;
	}
	public int getRow() {
		return row;
	}
	
	public void setColumn(int c) {
		column = c;
	}
	public int getColumn() {
		return column;
	}

}
