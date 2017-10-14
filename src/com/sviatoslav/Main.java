package com.sviatoslav;

import com.sviatoslav.enums.MazeAlgorithm;
import com.sviatoslav.mazes.Maze;
import com.sviatoslav.mazes.MazeAlgorithmFactory;
import com.sviatoslav.ui.NumberInput;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
    private BorderPane borderPane = new BorderPane();
    @Override
    public void start(Stage stage) throws Exception {
        borderPane.setTop(create_menu());
        borderPane.setStyle("-fx-background-color: #ff0011;");
        Scene scene = new Scene(borderPane, 800, 490);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private HBox create_menu() {
        HBox menu_view = new HBox();
        menu_view.setPadding(new Insets(15, 12, 15, 12));
        menu_view.setSpacing(10);
        menu_view.setStyle("-fx-background-color: #336699;");

        NumberInput rows_number_input = new NumberInput();
        NumberInput columns_number_input = new NumberInput();
        Label rows_label = new Label("Rows");
        Label cols_label = new Label("Columns");
        rows_number_input.setMaxLength(2);
        columns_number_input.setMaxLength(2);

        rows_number_input.setMaxWidth(40);
        columns_number_input.setMaxWidth(40);

        Button but = new Button("Create Maze");
        but.setOnAction(event -> this.build_maze(
                Integer.parseInt(rows_number_input.getText()),
                Integer.parseInt(columns_number_input.getText())));
        menu_view.getChildren().addAll(but, rows_label, rows_number_input, cols_label, columns_number_input);
        return menu_view;
    }

    private void build_maze(int rows, int cols) {
        Maze maze = new Maze(MazeAlgorithmFactory.getMazeAlgorithm(MazeAlgorithm.RECURSIVE_DIVIDE_SEARCH), rows, cols);
        maze.createMaze();
        borderPane.setCenter(maze);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*
WritableImage image = snapshot(new SnapshotParameters(), null);
                        File file = new File("maze.png");
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                        } catch (IOException e) {
                            System.out.print("Error of making an image");
                        }

 */