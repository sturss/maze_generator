package sviatoslav;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
        BorderPane s = new BorderPane();

        borderPane.setStyle("-fx-background-color: #e0f2f1;  -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
        ToolBar toolBar = new ToolBar();

        int height = 25;
        toolBar.setStyle("-fx-background-color: #005b4f; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);
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


        s.setTop(toolBar);
        s.setBottom(create_menu());

        borderPane.setTop(s);
        Scene scene = new Scene(borderPane, 800, 555);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");
            Button minimizeBtn = new Button("_");

            closeBtn.setStyle( " -fx-border-width: 0 1 0 0; -fx-border-radius: 10 0 0 0;" +
                    " -fx-background-radius: 10 0 0 0;  -fx-border-color: #00766c; -fx-background-color: #4ebaaa");
            closeBtn.setOnMouseEntered(e -> closeBtn.setStyle("-fx-border-width: 0 1 0 0; -fx-border-radius: 10 0 0 0;" +
                    " -fx-background-radius: 10 0 0 0;  -fx-border-color: #00766c; -fx-background-color: #64d8cb"));
            closeBtn.setOnMouseExited(e -> closeBtn.setStyle("-fx-border-width: 0 1 0 0; -fx-border-radius: 10 0 0 0;" +
                    " -fx-background-radius: 10 0 0 0;  -fx-border-color: #00766c; -fx-background-color: #4ebaaa"));

            minimizeBtn.setStyle( "-fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0; -fx-background-color: #4ebaaa");
            minimizeBtn.setOnMouseEntered(e -> minimizeBtn.setStyle("-fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0; -fx-background-color: #64d8cb"));
            minimizeBtn.setOnMouseExited(e -> minimizeBtn.setStyle("-fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0; -fx-background-color: #4ebaaa"));


            closeBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });

            minimizeBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.setIconified(true);
                }
            });

            this.getChildren().addAll(closeBtn, minimizeBtn);
        }
    }

    private HBox create_menu() {
        HBox menuViewHBox = new HBox();
        menuViewHBox.setPadding(new Insets(15, 12, 15, 12));
        menuViewHBox.setSpacing(10);
        menuViewHBox.setStyle("-fx-background-color: #00897b;");

        VBox inputFieldsVBox = new VBox();
        VBox chooseAlgorithmVBox = new VBox();
        HBox rowFieldHBox = new HBox();
        HBox colFieldHBox = new HBox();

        NumberInput rowNumberInput = new NumberInput();
        NumberInput colNumberInput = new NumberInput();
        rowNumberInput.setStyle("-fx-background-color: #4ebaaa; -fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2;");
        colNumberInput.setStyle("-fx-background-color: #4ebaaa; -fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2;");
        rowNumberInput.setMaxNumberLength(3);
        colNumberInput.setMaxNumberLength(3);
        rowNumberInput.setMaxWidth(40);
        colNumberInput.setMaxWidth(40);

        Label rowInputLabel = new Label("Rows:");
        Label colInputLabel = new Label("Columns:");
        rowInputLabel.setAlignment(Pos.CENTER_RIGHT);
        colInputLabel.setAlignment(Pos.CENTER_RIGHT);
        rowInputLabel.setMinWidth(55);
        colInputLabel.setMinWidth(55);
        rowInputLabel.setTextFill(Color.web("white"));
        colInputLabel.setTextFill(Color.web("white"));

        rowFieldHBox.getChildren().addAll(rowInputLabel, rowNumberInput);
        rowFieldHBox.setSpacing(2);
        rowFieldHBox.setAlignment(Pos.CENTER);
        colFieldHBox.getChildren().addAll(colInputLabel, colNumberInput);
        colFieldHBox.setSpacing(2);
        colFieldHBox.setAlignment(Pos.CENTER);
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

        Button createMazeBtn = new Button("Create Maze");

        createMazeBtn.setStyle( "-fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2; -fx-background-color: #4ebaaa");
        createMazeBtn.setOnMouseEntered(e -> createMazeBtn.setStyle("-fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2; -fx-background-color: #64d8cb"));
        createMazeBtn.setOnMouseExited(e -> createMazeBtn.setStyle("-fx-border-radius: 2 2 2 2; -fx-background-radius: 2 2 2 2; -fx-background-color: #4ebaaa"));

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


        menuViewHBox.getChildren().addAll(createMazeBtn, inputFieldsVBox, chooseAlgorithmVBox);
        menuViewHBox.setAlignment(Pos.CENTER_LEFT);
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