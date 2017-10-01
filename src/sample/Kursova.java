package sample;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Kursova extends Application {
    private BorderPane borderPane = new BorderPane();
    @Override
    public void start(Stage stage) throws Exception {
        borderPane.setTop(create_menu());
        borderPane.setStyle("-fx-background-color: #ff0011;");
        Scene scene = new Scene(borderPane, 800, 480);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public HBox create_menu() {
        HBox menu_view = new HBox();
        menu_view.setPadding(new Insets(15, 12, 15, 12));
        menu_view.setSpacing(10);
        menu_view.setStyle("-fx-background-color: #336699;");

        TextField rows_number_input = new TextField();
        TextField columns_number_input = new TextField();

        Button but = new Button("Create Maze");
        but.setOnAction(event -> this.build_maze(
                Integer.parseInt(rows_number_input.getText()),
                Integer.parseInt(columns_number_input.getText())));
        menu_view.getChildren().addAll(but, rows_number_input, columns_number_input);
        return menu_view;
    }

    private void build_maze(int rows, int cols) {
        borderPane.setCenter(new Maze(cols, rows));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
