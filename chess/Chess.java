import javafx.application.*;
import javafx.beans.property.*;
import javafx.beans.property.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.layout.*;
import javafx.scene.layout.*;
import javafx.scene.layout.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import java.io.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import java.util.*;


public class Chess extends Application {
    private String [][] positions;
    private Bounds [][] tile_locations;
    private StackPane [][] all_cells;
    private static ArrayList<ImageView> allPieces = new ArrayList<ImageView>();
    private GridPane grid;

    private StackPane createCell(BooleanProperty cellSwitch, String place) {

        StackPane cell = new StackPane();
         if(place.length() > 0) {
            try{
            FileInputStream imageStream = new FileInputStream("images/" + place + ".png");
            Image image = new Image(imageStream);
            ImageView view = new ImageView(image);
            Draggable.Nature nature = new Draggable.Nature(view);
            view.setFitWidth(40);
            view.setFitHeight(40);
            cell.getChildren().add(view);
            allPieces.add(view);
        }
        catch(Exception e) {
            System.out.println("blah");
        }
        }

        if(cellSwitch.getValue() == true) {
            cell.getStyleClass().add("blackSquare");
        }
        else {
            cell.getStyleClass().add("cell");
        }
        
        
        return cell;
    }
    
    public static double[] getNearestCell(double x, double y) {
        
        int cell_x = (int)(x/600);
        int cell_y = (int)(x/600);
        
        double newx = ((cell_x)*75) + 32.5;
        double newy = ((cell_y)*75) + 32.5;
        
        return new double[]{newx, newy};
        
    }
    
    public static void center() {
        for(ImageView piece: allPieces) {
            double [] newloc = getNearestCell(piece.getLayoutX(), piece.getLayoutY());
            piece.relocate(newloc[0], newloc[1]);
        }
    }
        
    


    private GridPane createGrid(BooleanProperty[][] switches) {

        int numCols = switches.length ;
        int numRows = switches[0].length ;

        GridPane grid = new GridPane();

        for (int x = 0 ; x < numCols ; x++) {
            ColumnConstraints cc = new ColumnConstraints(75);
            cc.setFillWidth(true);
            //cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }

        for (int y = 0 ; y < numRows ; y++) {
            RowConstraints rc = new RowConstraints(75);
            rc.setFillHeight(true);
            //rc.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(rc);
        }

        for (int x = 0 ; x < numCols ; x++) {
            for (int y = 0 ; y < numRows ; y++) {
                if(positions[x][y] != null) {
                    grid.add(createCell(switches[x][y], positions[x][y]), x, y);
                }
                else {
                    grid.add(createCell(switches[x][y], ""), x, y);
                }
            }
        }
        grid.getStyleClass().add("grid");
        return grid;
    }

    @Override
    public void start(Stage primaryStage) {
        int numCols = 8;
        int numRows = 8;

        BooleanProperty[][] switches = new BooleanProperty[numCols][numRows];
        positions = new String[numCols][numRows];
        tile_locations = new Bounds[numCols][numRows];
        all_cells = new StackPane[numCols][numRows];
        positions[0][0] = "rook";
        positions[1][0] = "knight";
        positions[2][0] = "bishop";
        positions[3][0] = "queen";
        positions[4][0] = "king";
        positions[5][0] = "bishop";
        positions[6][0] = "knight";
        positions[7][0] = "rook";
        positions[0][7] = "rookb";
        positions[1][7] = "knightb";
        positions[2][7] = "bishopb";
        positions[3][7] = "queenb";
        positions[4][7] = "kingb";
        positions[5][7] = "bishopb";
        positions[6][7] = "knightb";
        positions[7][7] = "rookb";
        for(int i = 0; i < numRows; i++) {
            positions[i][1] = "pawn";
        }
        for(int i = 0; i < numRows; i++) {
            positions[i][6] = "pawnb";
        }
        boolean last = true;
        for (int y = 0 ; y < numRows ; y++) {
            for (int x = 0 ; x < numCols ; x++) {
                switches[x][y] = new SimpleBooleanProperty();
                if((y % 2) == 0) {
                    if((x % 2) == 1) {
                        switches[x][y].setValue(true);
                    }
                }
                else {
                    if((x % 2) == 0) {
                        switches[x][y].setValue(true);
                    }
                }
            }
        }
        

        grid = createGrid(switches);
        StackPane root = new StackPane(grid);
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
        


    public static void main(String[] args) {
        launch(args);
    }
}