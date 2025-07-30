package com.project;

import static com.project.Constants.*;
import java.util.Random;;

public class Cell {
    public int state;

    public Cell(int state){
        this.state = state;
    }

    public static void update(Cell[][] grid){
        int n = grid.length;
        int m = grid[0].length;

        Cell[][] newGrid = new Cell[n][m];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                newGrid[i][j] = new Cell(DEAD);
                newGrid[i][j].state = grid[i][j].state;
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                Cell newCell = newGrid[i][j];
                Cell oldCell = grid[i][j];
                int count = countNeighbors(grid, i, j);

                switch (oldCell.state){
                    case ALIVE -> {
                        if(count < 2 || count > 3){
                            newCell.state = DEAD;
                        }
                    }
                    case DEAD -> {
                        if(count == 3){
                            newCell.state = ALIVE;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j].state = newGrid[i][j].state;
            }
        }
    }

    private static int countNeighbors(Cell[][] grid, int i, int j){
        int count = 0;
        int n = grid.length;
        int m = grid[0].length;

        for (int[] offset : NEIGHBOR_OFFSETS) {
            int ii = i + offset[0];
            int jj = j + offset[1];

            if (ii >= 0 && ii < n && jj >= 0 && jj < m) {
                count += grid[ii][jj].state;
            }
        }

        return count;
    }

    public static void print(Cell[][] grid){
        int n = grid.length;
        int m = grid[0].length;

        System.out.print("+");
        for(int i = 0; i < m; i++){
            System.out.print("-");
        }
        System.out.print("+\n");

        for(int i = 0; i < n; i++){
            System.out.print("|");
            for(int j = 0; j < m; j++){
                if(grid[i][j].state == DEAD){
                    System.out.print(" ");
                }
                else{
                    System.out.print("■");
                }
            }
            System.out.println("|");
        }

        System.out.print("+");
        for(int i = 0; i < m; i++){
            System.out.print("-");
        }
        System.out.print("+\n");
    }

    public static Cell[][] random(int n, int m){
        Cell[][] grid = new Cell[n][m];
        Random rand = new Random();

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                grid[i][j] = new Cell(rand.nextDouble() < PROBABILITY ? ALIVE: DEAD);
            }
        }

        return grid;
    }

    public static int countAliveCells(Cell[][] cells){
        int n = cells.length;
        int m = cells[0].length;
        int count = 0;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                count += cells[i][j].state;
            }
        }

        return count;
    }

    public static Cell[][] init(int n, int m){
        Cell[][] cells = new Cell[n][m];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                cells[i][j] = new Cell(DEAD);
            }
        }

        return cells;
    }
}
