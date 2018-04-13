/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor
 */
public class Shape {

    private Tetrominoes pieceShape;
    private int[][] coordinates;
    private static int[][][] coordsTable = new int[][][]{
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
        {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
        {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
        {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
        {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
        {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
        {{1, -1}, {0, -1}, {0, 0}, {0, 1}}

    };

    public Shape(Tetrominoes pieceShape) {
        this.pieceShape = pieceShape;
        coordinates = new int[4][2];
        for (int point = 0; point < coordinates.length; point++) {
            coordinates[point][0] = coordsTable[pieceShape.ordinal()][point][0];
            coordinates[point][1] = coordsTable[pieceShape.ordinal()][point][1];
        }
    }

    public Shape() {
        int randomNumber = (int) (Math.random() * 7 + 1);
        pieceShape = Tetrominoes.values()[randomNumber];
        coordinates = new int[4][2];
        for (int point = 0; point < coordinates.length; point++) {
            coordinates[point][0] = coordsTable[pieceShape.ordinal()][point][0];
            coordinates[point][1] = coordsTable[pieceShape.ordinal()][point][1];
        }
    }

    public static Shape getRandomShape() {
        return new Shape();
    }

    public int[][] getCoordnates() {
        return coordinates;
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public Shape rotateRight() {
        Shape rotatedShape = new Shape(pieceShape);
        for (int point = 0; point < coordinates.length; point++) {
            rotatedShape.coordinates[point][0] = coordinates[point][0];
            rotatedShape.coordinates[point][1] = coordinates[point][1];
        }
        if (pieceShape != Tetrominoes.SquareShape) {

            for (int point = 0; point < coordinates.length; point++) {
                int temp = rotatedShape.coordinates[point][0];
                rotatedShape.coordinates[point][0] = coordinates[point][1];
                rotatedShape.coordinates[point][1] = -temp;
            }
        }
        return rotatedShape;
    }

    public int getXmin() {
        int candidates = coordinates[0][0];
        for (int i = 1; i < coordinates.length; i++) {
            if (coordinates[i][0] < candidates) {
                candidates = coordinates[i][0];
            }
        }
        return candidates;
    }

    public int getXmax() {
        int candidates = coordinates[0][0];
        for (int i = 1; i < coordinates.length; i++) {
            if (coordinates[i][0] > candidates) {
                candidates = coordinates[i][0];
            }
        }
        return candidates;
    }

    public int getYmin() {
        int candidates = coordinates[0][1];
        for (int i = 1; i < coordinates.length; i++) {
            if (coordinates[i][1] < candidates) {
                candidates = coordinates[i][1];
            }
        }
        return candidates;
    }

    public int getYmax() {
        int candidates = coordinates[0][1];
        for (int i = 1; i < coordinates.length; i++) {
            if (coordinates[i][1] > candidates) {
                candidates = coordinates[i][1];
            }
        }
        return candidates;
    }
}
