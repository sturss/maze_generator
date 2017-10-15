package sviatoslav;

import javafx.scene.layout.VBox;
import sviatoslav.enums.MazeAlgorithm;
import sviatoslav.mazes.Maze;
import sviatoslav.mazes.MazeAlgorithmFactory;
import sviatoslav.ui.NumberInput;
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
        borderPane.setStyle("-fx-background-color: grey;");
        Scene scene = new Scene(borderPane, 800, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private HBox create_menu() {
        HBox menu_view = new HBox();
        menu_view.setPadding(new Insets(15, 12, 15, 12));
        menu_view.setSpacing(10);
        menu_view.setStyle("-fx-background-color: #336699;");

        VBox input_fields = new VBox();
        HBox row_input_field = new HBox();
        HBox col_input_field = new HBox();

        NumberInput rows_number_input = new NumberInput();
        NumberInput columns_number_input = new NumberInput();

        rows_number_input.setMaxNumberLength(3);
        columns_number_input.setMaxNumberLength(3);

        rows_number_input.setMaxWidth(40);
        columns_number_input.setMaxWidth(40);


        Label row_input_label = new Label("Rows:");
        Label col_input_label = new Label("Columns:");

        row_input_label.setMinWidth(55);
        col_input_label.setMinWidth(55);

        row_input_field.getChildren().addAll(row_input_label, rows_number_input);
        col_input_field.getChildren().addAll(col_input_label, columns_number_input);
        input_fields.getChildren().addAll(row_input_field, col_input_field);


        Button but = new Button("Create Maze");
        but.setOnAction(event -> this.build_maze(
                Integer.parseInt(rows_number_input.getText()),
                Integer.parseInt(columns_number_input.getText())));
        menu_view.getChildren().addAll(but, input_fields);
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