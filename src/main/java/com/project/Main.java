package com.project;

import static com.project.Constants.*;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static Timeline timeline;
    private static Rectangle[][] rectangles;
    private static Cell[][] cells = Cell.init(ROWS, COLS);

    @Override
    public void start(Stage stage) {
        
        stage.setTitle("GAME OF LIFE");
        GridPane grid = new GridPane();
        rectangles = new Rectangle[ROWS][COLS];

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                Rectangle cell = new Rectangle(SIZE, SIZE);
                cell.setFill(Color.WHITE);
                cell.setStroke(Color.LIGHTGRAY);

                int ii = i, jj = j;
                cell.setOnMouseClicked(_ -> {
                    cells[ii][jj].state = 1 - cells[ii][jj].state;
                    cell.setFill(cells[ii][jj].state == ALIVE ? Color.BLACK: Color.WHITE);
                });

                rectangles[i][j] = cell;
                grid.add(cell, i, j);
            }
        }

        Button startButton = new Button("START");
        startButton.setPrefWidth(120);
        startButton.setPrefHeight(40);

        Button stopButton = new Button("STOP");
        stopButton.setPrefWidth(120);
        stopButton.setPrefHeight(40);

        Button resetButton = new Button("RESET");
        resetButton.setPrefWidth(120);
        resetButton.setPrefHeight(40);

        Button randomButton = new Button("RANDOM");
        randomButton.setPrefWidth(120);
        randomButton.setPrefHeight(40);

        startButton.setOnAction(_ -> {
            if(timeline != null) timeline.stop();

            timeline = new Timeline(new KeyFrame(Duration.millis(DELAY), _ -> {
                Cell.update(cells);
                for(int i = 0; i < ROWS; i++){
                    for(int j = 0; j < COLS; j++){
                        rectangles[i][j].setFill(cells[i][j].state == ALIVE ? Color.BLACK: Color.WHITE);
                    }
                }
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });

        stopButton.setOnAction(_ -> {
            if(timeline != null) timeline.stop();
        });

        resetButton.setOnAction(_ ->{
            for(int i = 0; i < ROWS; i++){
                for(int j = 0; j < COLS; j++){
                    cells[i][j].state = DEAD;
                    rectangles[i][j].setFill(Color.WHITE);
                }
            }
        });

        randomButton.setOnAction(_ ->{
            if(timeline != null) timeline.stop();
            cells = Cell.random(ROWS, COLS);
            updateCells();
        });

        VBox buttonsBox = new VBox(10, startButton, stopButton, resetButton, randomButton);
        HBox mainBox = new HBox(10, grid, buttonsBox);

        Scene scene = new Scene(mainBox);
        stage.setScene(scene);

        stage.show();
    }

    public static void updateCells(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                rectangles[i][j].setFill(cells[i][j].state == ALIVE ? Color.BLACK: Color.WHITE);
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException, IOException {
        launch(args);
    }
}
