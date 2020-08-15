package application;
	
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	
	//private ArrayList<Matrix> MatrixList;
	private Matrix myMatrix;
	private ArrayList<TextField> TextFieldList;
	private int rowNumber;

	
	//Main Window----------------------------------------------------------------------------------
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Font myFont= new Font("Arial", 30);

			GridPane root = new GridPane();
			
			ScrollPane sp= new ScrollPane();
			sp.setContent(root);
			
			Scene scene = new Scene(sp,800,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			
			//Set Application window to the size of the screen
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.setX(bounds.getMinX());
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth());
			primaryStage.setHeight(bounds.getHeight());
			
			//Set application to be maximized
			primaryStage.setMaximized(true);
			
			root.setHgap(50.0);
			root.setVgap(50.0);
			
			Label MainLabel = new Label("Please enter the matrix dimensions");
			MainLabel.setFont(myFont);
			root.add(MainLabel, 0, 0);
			
			//Rows and Columns Prompt---------------------------------
			Label rowNum = new Label("Rows: ");
			rowNum.setFont(myFont);
			
			TextField rowEnter = new TextField();
			rowEnter.setFont(myFont);
			
			Label columnNum = new Label("Columns: ");
			columnNum.setFont(myFont);
			
			TextField columnEnter = new TextField();
			columnEnter.setFont(myFont);
			
			Button submit= new Button("Submit");
			submit.setFont(myFont);
			
			root.add(rowNum,0,1);
			root.add(rowEnter, 1, 1);
			root.add(columnNum,2,1);
			root.add(columnEnter,3, 1);
			root.add(submit, 0, 2);
			
			Button rrefButton= new Button("Run RREF");
			rrefButton.setFont(myFont);
			
			Label rrefLabel= new Label("");
			rrefLabel.setFont(myFont);
			rowNumber= 0;
			
			
			//Only enter numbers: "columnEnter" and "rowEnter"
			columnEnter.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			            columnEnter.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			    }
			});
			rowEnter.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			            rowEnter.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			    }
			});
			
			
			
			//submit button listener--------------------------------------
			submit.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	
			    	//clear previous boxes
			    	if(TextFieldList != null) {
			    	     Iterator<TextField> iter = TextFieldList.iterator();
			    	      while (iter.hasNext()) {
			    	         root.getChildren().remove(iter.next());
			    	      }
			    	      TextFieldList.clear();
			    	}
			    	
			    	
			    	//clear rrefLabel if text is already set
			    	if(rrefLabel.getText().length() != 0) {
			    		rrefLabel.setText("");
			    		root.getChildren().remove(rrefLabel);
			    	}
			    	
			        //System.out.println("Submit button action");
			    	int row = Integer.parseInt(rowEnter.getText());
			    	int column = Integer.parseInt(columnEnter.getText());
			    	
			    	myMatrix= new Matrix(row,column);
			    	
			    	TextFieldList = new ArrayList<TextField>();
			    	
			    	//generate matrix text fields
			    	for(int i=0; i<row; i++) {
			    		for(int j=0; j<column; j++) {
			    			TextField box= new TextField();
			    			//Only enter numbers: "box"
			    			box.textProperty().addListener(new ChangeListener<String>() {
			    			    @Override
			    			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			    			        String newValue) {
			    			        if (!newValue.matches("\\d*")) {
			    			            box.setText(newValue.replaceAll("[^\\d]", ""));
			    			        }
			    			    }
			    			});
			    			box.setFont(myFont);
			    			root.add(box, j, i+3);
			    			TextFieldList.add(box);
			    		}
			    	}
					root.add(rrefButton, 0, row+4);
			    	rowNumber = row+4;
			 
			    }
			});
			
			//rref button listener
			rrefButton.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    		
			    	//pull values from textboxes into an array
			    	double[] values = new double[TextFieldList.size()];
			    	int index = 0;
		    	    Iterator<TextField> iter = TextFieldList.iterator();
		    	    while (iter.hasNext()) {
		    	    	values[index] = Double.parseDouble(iter.next().getText());
		    	    	index++;
		    	    }
				    	
		    	    myMatrix.setMatrix(values);
		    	    myMatrix.RREF();
		    	    
		    	    rrefLabel.setText(myMatrix.getOutput());
		    	    root.add(rrefLabel, 0, rowNumber);
		    	    
		    	    root.getChildren().remove(rrefButton);			    	
			    }
			});
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		launch(args);
	}
}
