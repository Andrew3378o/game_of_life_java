package com.project;

public class Constants {
    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    public static final int ROWS = 25;
    public static final int COLS = 100;
    public static final int DELAY = 40;
    public static final double PROBABILITY = 0.2;

    public static final int[][] NEIGHBOR_OFFSETS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},          {0, 1},
            {1, -1},  {1, 0}, {1, 1}
    };
}
