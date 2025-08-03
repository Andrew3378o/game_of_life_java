package com.project;

import static com.project.Constants.*;
import static com.project.Cell.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;

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
                grid.add(cell, j, i);
            }
        }

        Button startButton = new Button("START");
        Button stopButton = new Button("STOP");
        Button resetButton = new Button("RESET");
        Button randomButton = new Button("RANDOM");
        Button exitButton = new Button("EXIT");

        Slider speedSlider = new Slider(1, 30, 3);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(1);
        speedSlider.setMinorTickCount(0);
        speedSlider.setSnapToTicks(true);

        Label speedLabel = new Label("Speed:");
        Label population = new Label();
        population.setText("Current population: " + countAliveCells(cells));

        startButton.setOnAction(_ -> {
            if(timeline != null) timeline.stop();

            timeline = new Timeline(new KeyFrame(Duration.millis(1000 / speedSlider.getValue()), _ -> {
                Cell.update(cells);
                population.setText("Current population: " + countAliveCells(cells));
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
            population.setText("Current population: " + countAliveCells(cells));
        });

        randomButton.setOnAction(_ ->{
            if(timeline != null) timeline.stop();
            cells = Cell.random(ROWS, COLS);
            updateCells();
            population.setText("Current population: " + countAliveCells(cells));
        });

        speedSlider.valueProperty().addListener((_, _, newVal) -> {
            if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
                timeline.stop();
                timeline.getKeyFrames().setAll(new KeyFrame(Duration.millis(1000 / newVal.doubleValue()), _ -> {
                    update(cells);
                    updateCells();
                    population.setText("Current population: " + countAliveCells(cells));
                }));
                timeline.play();
            }
        });

        exitButton.setOnAction(_ -> Platform.exit());

        VBox buttonsBox = new VBox(10, startButton, stopButton, resetButton, randomButton, exitButton, population);
        VBox sliderBox = new VBox(5, grid, speedLabel, speedSlider);
        HBox mainBox = new HBox(10, sliderBox, buttonsBox);

        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(new File("src/main/resources/style.css").toURI().toString());
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
    
    public static void main(String[] args){
        launch(args);
    }
}
