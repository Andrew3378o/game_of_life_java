package com.project;

import static com.project.Constants.*;
import static com.project.Cell.*;

public class Main {
    public static void main(String[] args){
        Cell[][] cells = {
                {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
                {new Cell(DEAD), new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE)},
                {new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD)},
                {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)}
        };

        System.out.println("Step #0");

        print(cells);

        System.out.println("Step #1");

        update(cells);
        print(cells);

        System.out.println("Step #2");

        update(cells);
        print(cells);
    }
}
