package sviatoslav;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import sviatoslav.enums.MazeAlgorithm;
import sviatoslav.exceptions.MazeGenerationNotFinishedException;
import sviatoslav.exceptions.MazeSizeOutOfRangeException;
import sviatoslav.mazes.Maze;
import sviatoslav.mazes.MazeAlgorithmFactory;
import sviatoslav.ui.NumberInput;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    private BorderPane mainBorderPane = new BorderPane();
    private Stage stage;
    private Maze maze;
    private double xOffset;
    private double yOffset;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT) ;

        BorderPane topBorderPane = new BorderPane();
        topBorderPane.setTop(createToolBar());
        topBorderPane.setBottom(createMenu());

        mainBorderPane.getStyleClass().add("borderPaneStyle");
        mainBorderPane.setTop(topBorderPane);
        Scene scene = new Scene(mainBorderPane, 800, 555);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("css\\style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();
        toolBar.getStyleClass().add("toolBarStyle");
        toolBar.setPrefHeight(25);
        toolBar.setMinHeight(25);
        toolBar.setMaxHeight(25);
        toolBar.getItems().add(createWindowButtons());
        toolBar.setPadding(new Insets(0, 0, 0, 0));

        toolBar.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        toolBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        toolBar.setOnMouseReleased(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
        return  toolBar;
    }

    private HBox createWindowButtons(){
        HBox bar = new HBox();
        Button closeBtn = new Button("X");
        Button minimizeBtn = new Button("_");
        closeBtn.getStyleClass().add("btnCloseStyle");
        minimizeBtn.getStyleClass().add("btnMinimizeStyle");
        closeBtn.setOnAction(actionEvent -> Platform.exit());
        minimizeBtn.setOnAction(actionEvent -> stage.setIconified(true));
        bar.getChildren().addAll(closeBtn, minimizeBtn);
        return bar;
    }

    private HBox createMenu() {
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

        Button saveMazeBtn = new Button("Save Maze Image");
        saveMazeBtn.getStyleClass().add("btnStyle");
        saveMazeBtn.setDisable(true);
        saveMazeBtn.setOnAction(event -> {
            Maze s = (Maze) mainBorderPane.getCenter();
            s.saveAsPng();
        });

        Button shortestWayBtn = new Button("The Shortest Way");
        shortestWayBtn.getStyleClass().add("btnStyle");
        shortestWayBtn.setOnAction(event -> findShortestWay());

        Button createMazeBtn = new Button("Create Maze");
        createMazeBtn.getStyleClass().add("btnStyle");
        createMazeBtn.setOnAction(event -> this.build_maze(
                        Integer.parseInt(rowNumberInput.getText()),
                        Integer.parseInt(colNumberInput.getText()),
                        algorithmsButtonsGroup.getSelectedToggle()));

        menuViewHBox.getChildren().addAll(createMazeBtn, inputFieldsVBox, chooseAlgorithmVBox, saveMazeBtn, shortestWayBtn);
        return menuViewHBox;
    }

    private void build_maze(int rows, int cols, Toggle algorithm) {
        BorderPane a = (BorderPane) mainBorderPane.getTop();
        HBox menu = (HBox)a.getBottom();
        Button saveBtn = (Button)menu.getChildren().get(3);
        saveBtn.setDisable(false);

        sviatoslav.mazes.MazeAlgorithm mazeAlgorithm;
        if(algorithm.getUserData().toString().equals("DepthFirst"))
            mazeAlgorithm = MazeAlgorithmFactory.getMazeAlgorithm(MazeAlgorithm.DEPTH_FIRST_SEARCH);
        else if(algorithm.getUserData().toString().equals("RecursiveDivision"))
            mazeAlgorithm = MazeAlgorithmFactory.getMazeAlgorithm(MazeAlgorithm.RECURSIVE_DIVIDE_SEARCH);
        else {
            return;
        }
        try {
            maze = new Maze(mazeAlgorithm, rows, cols);
            maze.createMaze();
            mainBorderPane.setCenter(maze);
        } catch (MazeSizeOutOfRangeException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Size of the maze must be: \n - row in [4, 44]\n - col in [4, 77]");
            alert.showAndWait();
        }

    }

    private void findShortestWay() {
        try {
            maze.findShortestWay();
        }
        catch (MazeGenerationNotFinishedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MazeGenerator Message");
            alert.setHeaderText("Can't complete action");
            alert.setContentText("Wait for maze generation to finish");
            alert.showAndWait();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
