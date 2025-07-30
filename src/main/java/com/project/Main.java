package com.project;

import static com.project.Constants.*;
import static com.project.Cell.*;
import static java.lang.Thread.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        Cell[][] cells = random(ROWS, COLS);

        while (true) {
            update(cells);
            System.out.println("Step #" + counter++);
            print(cells);
            if (System.in.available() > 0) break;
            sleep(DELAY);
            clear();
        }

        scanner.close();
    }

    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Unable to clear console.");
        }
    }
}
