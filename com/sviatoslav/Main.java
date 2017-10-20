package sviatoslav;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import sviatoslav.enums.MazeAlgorithm;
import sviatoslav.mazes.Maze;
import sviatoslav.mazes.MazeAlgorithmFactory;
import sviatoslav.ui.NumberInput;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application {
    private BorderPane borderPane = new BorderPane();
    private Stage stage;
    private double xOffset;
    private double yOffset;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT) ;

        borderPane.getStyleClass().add("borderPaneStyle");
        BorderPane topBP = new BorderPane();
        ToolBar toolBar = new ToolBar();

        toolBar.getStyleClass().add("toolBarStyle");
        toolBar.setPrefHeight(25);
        toolBar.setMinHeight(25);
        toolBar.setMaxHeight(25);
        toolBar.getItems().add(new WindowButtons());
        toolBar.setPadding(new Insets(0, 0, 0, 0));

        toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
                System.out.print(event.getX());
            }
        });

        toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });

        toolBar.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });


        topBP.setTop(toolBar);
        topBP.setBottom(create_menu());

        borderPane.setTop(topBP);
        Scene scene = new Scene(borderPane, 800, 555);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("css\\style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");
            Button minimizeBtn = new Button("_");

            closeBtn.getStyleClass().add("btnCloseStyle");
            minimizeBtn.getStyleClass().add("btnMinimizeStyle");

            closeBtn.setOnAction(actionEvent -> Platform.exit());
            minimizeBtn.setOnAction(actionEvent -> stage.setIconified(true));

            this.getChildren().addAll(closeBtn, minimizeBtn);
        }
    }

    private HBox create_menu() {
        HBox menuViewHBox = new HBox();
        menuViewHBox.getStyleClass().add("menuHBoxStyle");
        menuViewHBox.setPadding(new Insets(15, 12, 15, 12));

        VBox inputFieldsVBox = new VBox();
        VBox chooseAlgorithmVBox = new VBox();
        HBox rowFieldHBox = new HBox();
        HBox colFieldHBox = new HBox();

        NumberInput rowNumberInput = new NumberInput();
        NumberInput colNumberInput = new NumberInput();
        rowNumberInput.getStyleClass().add("inputFieldStyle");
        colNumberInput.getStyleClass().add("inputFieldStyle");
        rowNumberInput.setMaxNumberLength(3);
        colNumberInput.setMaxNumberLength(3);
        rowNumberInput.setMaxWidth(40);
        colNumberInput.setMaxWidth(40);

        Label rowInputLabel = new Label("Rows:");
        Label colInputLabel = new Label("Columns:");
        rowInputLabel.getStyleClass().add("labelStyle");
        colInputLabel.getStyleClass().add("labelStyle");

        rowFieldHBox.getChildren().addAll(rowInputLabel, rowNumberInput);
        colFieldHBox.getChildren().addAll(colInputLabel, colNumberInput);
        rowFieldHBox.getStyleClass().add("inputHBoxStyle");
        colFieldHBox.getStyleClass().add("inputHBoxStyle");
        inputFieldsVBox.getChildren().addAll(rowFieldHBox, colFieldHBox);
        inputFieldsVBox.setSpacing(2);

        ToggleGroup algorithmsButtonsGroup = new ToggleGroup();
        RadioButton algorithmDepthFirstCheck = new RadioButton();
        RadioButton algorithmRecursiveDivisionCheck = new RadioButton();
        algorithmDepthFirstCheck.setText("Depth First Search");
        algorithmDepthFirstCheck.setTextFill(Color.web("whitesmoke"));
        algorithmDepthFirstCheck.setUserData("DepthFirst");
        algorithmRecursiveDivisionCheck.setText("Recursive Division");
        algorithmRecursiveDivisionCheck.setTextFill(Color.web("whitesmoke"));
        algorithmRecursiveDivisionCheck.setUserData("RecursiveDivision");
        algorithmDepthFirstCheck.setToggleGroup(algorithmsButtonsGroup);
        algorithmRecursiveDivisionCheck.setToggleGroup(algorithmsButtonsGroup);
        algorithmDepthFirstCheck.setSelected(true);
        VBox.setMargin(algorithmDepthFirstCheck, new Insets(2, 0, 5, 0));
        chooseAlgorithmVBox.getChildren().addAll(algorithmDepthFirstCheck, algorithmRecursiveDivisionCheck);

        Button saveMaze = new Button("Save Maze Image");
        saveMaze.getStyleClass().add("btnStyle");
        saveMaze.setDisable(true);
        saveMaze.setOnAction(event -> {
            Maze s = (Maze) borderPane.getCenter();
            s.saveAsPng();
        });

        Button createMazeBtn = new Button("Create Maze");
        createMazeBtn.getStyleClass().add("btnStyle");
        createMazeBtn.setOnAction(event -> {
            try {
                if(Integer.parseInt(rowNumberInput.getText()) > 44 || Integer.parseInt(colNumberInput.getText()) > 77
                        || Integer.parseInt(rowNumberInput.getText()) < 4 || Integer.parseInt(colNumberInput.getText()) < 4)
                    throw new IllegalArgumentException("Wrong size inputs");
                this.build_maze(
                        Integer.parseInt(rowNumberInput.getText()),
                        Integer.parseInt(colNumberInput.getText()),
                        algorithmsButtonsGroup.getSelectedToggle());
            }
            catch(final IllegalArgumentException exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("Size of the maze must be: \n - row in [4, 44]\n - col in [4, 77]");
                alert.showAndWait();
            }
        });

        menuViewHBox.getChildren().addAll(createMazeBtn, inputFieldsVBox, chooseAlgorithmVBox, saveMaze);
        return menuViewHBox;
    }

    private void build_maze(int rows, int cols, Toggle algorithm) {
        sviatoslav.mazes.MazeAlgorithm mazeAlgorithm;
        if(algorithm.getUserData().toString().equals("DepthFirst"))
            mazeAlgorithm = MazeAlgorithmFactory.getMazeAlgorithm(MazeAlgorithm.DEPTH_FIRST_SEARCH);
        else if(algorithm.getUserData().toString().equals("RecursiveDivision"))
            mazeAlgorithm = MazeAlgorithmFactory.getMazeAlgorithm(MazeAlgorithm.RECURSIVE_DIVIDE_SEARCH);
        else {
            return;
        }

        Maze maze = new Maze(mazeAlgorithm, rows, cols);
        System.out.print(maze);
        maze.createMaze();
        borderPane.setCenter(maze);
        BorderPane a = (BorderPane)borderPane.getTop();
        HBox menu = (HBox)a.getBottom();
        Button button = (Button)menu.getChildren().get(3);
        button.setDisable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
