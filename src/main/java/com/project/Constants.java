package com.project;

public class Constants {
    public static final int ALIVE = 1;
    public static final int DEAD = 0;

    public static final int[][] NEIGHBOR_OFFSETS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},          {0, 1},
            {1, -1},  {1, 0}, {1, 1}
    };
}
