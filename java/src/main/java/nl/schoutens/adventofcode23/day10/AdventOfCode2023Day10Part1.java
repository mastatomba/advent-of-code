package nl.schoutens.adventofcode23.day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

/**
 * Question: Find the single giant loop starting at S. How many steps along the loop does it take to get from the starting position to the point farthest from the starting position?
 * Tip: add -Xss4m to prevent StackOverFlowError
 */
public class AdventOfCode2023Day10Part1 {
    private static char VERTICAL_PIPE = '|';
    private static char HORIZONTAL_PIPE = '-';
    private static char BEND_F = 'F';
    private static char BEND_7 = '7';
    private static char BEND_L = 'L';
    private static char BEND_J = 'J';

    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day10/input.txt";
        int numberOfSteps = calculateNumberOfSteps(fileName, StandardCharsets.UTF_8);
        System.out.println("Number of steps: " + numberOfSteps);
    }

    private static int calculateNumberOfSteps(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        List<String> tileRows = new ArrayList<>();
        while((line = br.readLine()) != null){
            tileRows.add(line);
        }
        br.close();

        Point startPosition = findStartPosition(tileRows);
        System.out.println(startPosition);

        List<Point> nodes = new ArrayList<>();
        nodes.add(startPosition);
        boolean foundMainLoop = false;
        try {
            nodes = traverseNorth(tileRows, nodes, startPosition);
            foundMainLoop = true;
        }
        catch (Exception e) {
            System.out.println("north was not a success");
        }
        if (!foundMainLoop) {
            try {
                nodes = traverseSouth(tileRows, nodes, startPosition);
                foundMainLoop = true;
            }
            catch (Exception e) {
                System.out.println("south was not a success");
            }
        }
        if (!foundMainLoop) {
            try {
                nodes = traverseEast(tileRows, nodes, startPosition);
                foundMainLoop = true;
            }
            catch (Exception e) {
                System.out.println("east was not a success");
            }
        }
        if (!foundMainLoop) {
            try {
                nodes = traverseWest(tileRows, nodes, startPosition);
                foundMainLoop = true;
            }
            catch (Exception e) {
                System.out.println("west was not a success");
            }
        }

        if (foundMainLoop) {
            System.out.println(nodes.size());
            return 1;
        } else {
            System.out.println("Main loop was not found..");
            return 0;
        }
    }

    private static List<Point> traverseNorth(
        List<String> tileRows,
        List<Point> nodes,
        Point fromPosition
    ) throws Exception {
        if (fromPosition.x == 0) {
            throw new Exception("Cannot traverse north from first row");
        }

        Point toPosition = new Point(fromPosition.x-1, fromPosition.y);
        nodes.add(toPosition);

        char toTile = getTileAt(tileRows, toPosition);
        if (toTile == 'S') {
            System.out.println("\t\tS found!");

            return nodes;
        }
        if (toTile == VERTICAL_PIPE) {
            try {
                return traverseNorth(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }
        if (toTile == BEND_7) {
            try {
                return traverseEast(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }
        if (toTile == BEND_F) {
            try {
                return traverseWest(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }

        throw new Exception("Cannot traverse north");
    }

    private static List<Point> traverseSouth(
        List<String> tileRows,
        List<Point> nodes,
        Point fromPosition
    ) throws Exception {
        if (fromPosition.x == tileRows.size()-1) {
            throw new Exception("Cannot traverse south from last row");
        }

        Point toPosition = new Point(fromPosition.x+1, fromPosition.y);
        nodes.add(toPosition);

        char toTile = getTileAt(tileRows, toPosition);
        if (toTile == 'S') {
            System.out.println("\t\tS found!");

            return nodes;
        }
        if (toTile == VERTICAL_PIPE) {
            try {
                return traverseSouth(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }
        if (toTile == BEND_J) {
            try {
                return traverseEast(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }
        if (toTile == BEND_L) {
            try {
                return traverseWest(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }

        throw new Exception("Cannot traverse south");
    }

    private static List<Point> traverseEast(
        List<String> tileRows,
        List<Point> nodes,
        Point fromPosition
    ) throws Exception {
        if (fromPosition.y == 0) {
            throw new Exception("Cannot traverse east from first column");
        }

        Point toPosition = new Point(fromPosition.x, fromPosition.y-1);
        nodes.add(toPosition);

        char toTile = getTileAt(tileRows, toPosition);
        if (toTile == 'S') {
            System.out.println("\t\tS found!");

            return nodes;
        }
        if (toTile == HORIZONTAL_PIPE) {
            try {
                return traverseEast(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }
        if (toTile == BEND_F) {
            try {
                return traverseSouth(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }
        if (toTile == BEND_L) {
            try {
                return traverseNorth(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }

        throw new Exception("Cannot traverse east");
    }

    private static List<Point> traverseWest(
        List<String> tileRows,
        List<Point> nodes,
        Point fromPosition
    ) throws Exception {
        if (fromPosition.y == tileRows.get(0).length()-1) {
            throw new Exception("Cannot traverse west from last column");
        }

        Point toPosition = new Point(fromPosition.x, fromPosition.y+1);
        nodes.add(toPosition);

        char toTile = getTileAt(tileRows, toPosition);
        if (toTile == 'S') {
            System.out.println("\t\tS found!");

            return nodes;
        }
        if (toTile == HORIZONTAL_PIPE) {
            try {
                return traverseWest(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }
        if (toTile == BEND_7) {
            try {
                return traverseSouth(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }
        if (toTile == BEND_J) {
            try {
                return traverseNorth(tileRows, nodes, toPosition);
            }
            catch (Exception e) {}
        }

        throw new Exception("Cannot traverse west");
    }

    private static Point findStartPosition(List<String> tileRows) {
        for (int i=0; i<tileRows.size(); i++) {
            String tileRow = tileRows.get(i);
            if (tileRow.contains("S")) {
                return new Point(i, tileRow.indexOf("S"));
            }
        }
        return new Point(-1, -1);
    }

    private static int getNumberOfSteps(List<String> tileRows, Point currentPosition, Point previousPosition, int currentNumberOfSteps) {
        char currentTile = getTileAt(tileRows, currentPosition);

        System.out.println(currentPosition.x + "," + currentPosition.y + " (" + currentTile + "): " + currentNumberOfSteps);

        if (currentNumberOfSteps >= 3000) {
            return currentNumberOfSteps;
        }

        boolean checkNorth = false;
        boolean checkSouth = false;
        boolean checkEast = false;
        boolean checkWest = false;

        if (currentTile == 'S') {
            checkNorth = true;
            checkSouth = true;
            checkEast = true;
            checkWest = true;
        } else if (currentTile == '|') {
            checkNorth = true;
            checkSouth = true;
        } else if (currentTile == '-') {
            checkEast = true;
            checkWest = true;
        } else if (currentTile == 'L') {
            checkNorth = true;
            checkWest = true;
        } else if (currentTile == 'J') {
            checkNorth = true;
            checkEast = true;
        } else if (currentTile == 'F') {
            checkSouth = true;
            checkWest = true;
        } else if (currentTile == '7') {
            checkSouth = true;
            checkEast = true;
        }

        if (checkNorth && currentPosition.x > 0) {
            Point nextPosition = new Point(currentPosition.x-1, currentPosition.y);
            if (!isSamePosition(nextPosition, previousPosition)) {
                char tile = getTileAt(tileRows, nextPosition);

                // System.out.println("\t"+currentTile+"^ NORTH: "+nextPosition.x + "," + nextPosition.y + " (" + tile + ")");

                if (tile == 'S') {
                    System.out.println("\t\tS found!");

                    return currentNumberOfSteps;
                }
                if (checkNorthPath(currentTile, tile)) {
                    int stepAmount = getNumberOfSteps(tileRows, nextPosition, currentPosition, currentNumberOfSteps+1);
                    if (stepAmount > 0) {
                        return stepAmount;
                    }
                }
            }
        }
        if (checkSouth && currentPosition.x < tileRows.size()-1) {
            Point nextPosition = new Point(currentPosition.x+1, currentPosition.y);
            if (!isSamePosition(nextPosition, previousPosition)) {
                char tile = getTileAt(tileRows, nextPosition);

                // System.out.println("\t"+currentTile+"v SOUTH: "+nextPosition.x + "," + nextPosition.y + " (" + tile + ")");

                if (tile == 'S') {
                    System.out.println("\t\tS found!");

                    return currentNumberOfSteps;
                }
                if (checkSouthPath(currentTile, tile)) {
                    int stepAmount = getNumberOfSteps(tileRows, nextPosition, currentPosition, currentNumberOfSteps+1);
                    if (stepAmount > 0) {
                        return stepAmount;
                    }
                }
            }
        }
        if (checkEast && currentPosition.y > 0) {
            Point nextPosition = new Point(currentPosition.x, currentPosition.y-1);
            if (!isSamePosition(nextPosition, previousPosition)) {
                char tile = getTileAt(tileRows, nextPosition);

                // System.out.println("\t"+currentTile+"< EAST: "+nextPosition.x + "," + nextPosition.y + " (" + tile + ")");

                if (tile == 'S') {
                    System.out.println("\t\tS found!");

                    return currentNumberOfSteps;
                }
                if (checkEastPath(currentTile, tile)) {
                    int stepAmount = getNumberOfSteps(tileRows, nextPosition, currentPosition, currentNumberOfSteps+1);
                    if (stepAmount > 0) {
                        return stepAmount;
                    }
                }
            }
        }
        if (checkWest && currentPosition.y < tileRows.get(0).length()-1) {
            Point nextPosition = new Point(currentPosition.x, currentPosition.y+1);
            if (!isSamePosition(nextPosition, previousPosition)) {
                char tile = getTileAt(tileRows, nextPosition);

                // System.out.println("\t"+currentTile+"> WEST: "+nextPosition.x + "," + nextPosition.y + " (" + tile + ")");

                if (tile == 'S') {
                    System.out.println("\t\tS found!");

                    return currentNumberOfSteps;
                }
                if (checkWestPath(currentTile, tile)) {
                    int stepAmount = getNumberOfSteps(tileRows, nextPosition, currentPosition, currentNumberOfSteps+1);
                    if (stepAmount > 0) {
                        return stepAmount;
                    }
                }
            }
        }

        return -1;
    }

    private static boolean checkNorthPath(char fromTile, char toTile) {
        return (toTile == '|' || toTile == 'F' || toTile == '7');
    }

    private static boolean checkSouthPath(char fromTile, char toTile) {
        return (toTile == '|' || toTile == 'L' || toTile == 'J');
    }

    private static boolean checkEastPath(char fromTile, char toTile) {
        return (toTile == '-' || toTile == 'L' || toTile == 'F');
    }

    private static boolean checkWestPath(char fromTile, char toTile) {
        return (toTile == '-' || toTile == 'J' || toTile == '7');
    }

    private static char getTileAt(List<String> tileRows, Point position) {
        return tileRows.get(position.x).charAt(position.y);
    }

    private static boolean isSamePosition(Point position1, Point position2) {
        return position1.x==position2.x && position1.y==position2.y;
    }

    private static void writeFileWithImpossiblePipesRemoved(List<String> tileRows) {
        for (int cleanupRuns = 0; cleanupRuns<10; cleanupRuns++) {
            for (int i=0; i<tileRows.size(); i++) {
                String tileRow = tileRows.get(i);
                String newTitleRow = "";
                boolean isLastRow = i == (tileRows.size()-1);
                char[] tiles = tileRow.toCharArray();

                for (int j=0; j<tiles.length; j++) {
                    char tile = tiles[j];
                    boolean isLastTile = j == (tiles.length-1);
                    boolean isImpossible = false;

                    if (tile == '|') {
                        if (i==0 || isLastRow || !checkVerticalPipe(j, tileRows.get(i-1), tileRows.get(i+1))) {
                            isImpossible = true;
                        }
                    } else if (tile == '-') {
                        if (j==0 || isLastTile || !checkHorizontalPipe(tiles[j-1], tiles[j+1])) {
                            isImpossible = true;
                        }
                    } else if (tile == 'L') {
                        if (i==0 || isLastTile || !check90DegreeBendL(j, tileRows.get(i-1), tiles[j+1])) {
                            isImpossible = true;
                        }
                    } else if (tile == 'J') {
                        if (i==0 || j==0 || !check90DegreeBendJ(j, tileRows.get(i-1), tiles[j-1])) {
                            isImpossible = true;
                        }
                    } else if (tile == 'F') {
                        if (isLastRow || isLastTile || !check90DegreeBendF(j, tileRows.get(i+1), tiles[j+1])) {
                            isImpossible = true;
                        }
                    } else if (tile == '7') {
                        if (isLastRow || j==0 || !check90DegreeBend7(j, tileRows.get(i+1), tiles[j-1])) {
                            isImpossible = true;
                        }
                    }

                    if (isImpossible) {
                        newTitleRow += ".";
                    } else {
                        newTitleRow += tile;
                    }
                }

                if (!newTitleRow.equals(tileRow)) {
                    System.out.println("Impossible tiles found!");
                    System.out.println("\t"+tileRow);
                    System.out.println("\t\t"+newTitleRow);
                    tileRows.set(i, newTitleRow);
                }
            }
            System.out.println("Iteration "+cleanupRuns);
        }

        try {
            FileWriter fileWriter = new FileWriter("resources/input/2023/day10/input_1.txt");
            for (String tileRow : tileRows) {
                fileWriter.write(tileRow + System.lineSeparator());
            }
            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static boolean checkVerticalPipe(int tileIndex, String northTileRow, String southTileRow) {
        char northTile = northTileRow.charAt(tileIndex);
        if (northTile == '.' || northTile == '-' || northTile == 'L' || northTile == 'J') {
            return false;
        }
        char southTile = southTileRow.charAt(tileIndex);
        if (southTile == '.' || southTile == '-' || southTile == 'F' || southTile == '7') {
            return false;
        }
        return true;
    }

    private static boolean checkHorizontalPipe(char leftTile, char rightTile) {
        if (leftTile == '|' || leftTile == '.' || leftTile == '7' || leftTile == 'J') {
            return false;
        }
        if (rightTile == '|' || rightTile == '.' || rightTile == 'F' || rightTile == 'L') {
            return false;
        }
        return true;
    }

    private static boolean check90DegreeBendL(int tileIndex, String northTileRow, char rightTile) {
        if (rightTile == '|' || rightTile == '.' || rightTile == 'L' || rightTile == 'F') {
            return false;
        }
        char northTile = northTileRow.charAt(tileIndex);
        if (northTile == '.' || northTile == '-' || northTile == 'L' || northTile == 'J') {
            return false;
        }
        return true;
    }

    private static boolean check90DegreeBendJ(int tileIndex, String northTileRow, char leftTile) {
        if (leftTile == '|' || leftTile == '.' || leftTile == 'J' || leftTile == '7') {
            return false;
        }
        char northTile = northTileRow.charAt(tileIndex);
        if (northTile == '.' || northTile == '-' || northTile == 'J' || northTile == 'L') {
            return false;
        }
        return true;
    }

    private static boolean check90DegreeBendF(int tileIndex, String southTileRow, char rightTile) {
        if (rightTile == '|' || rightTile == '.' || rightTile == 'F' || rightTile == 'L') {
            return false;
        }
        char southTile = southTileRow.charAt(tileIndex);
        if (southTile == '.' || southTile == '-' || southTile == 'F' || southTile == '7') {
            return false;
        }
        return true;
    }

    private static boolean check90DegreeBend7(int tileIndex, String southTileRow, char leftTile) {
        if (leftTile == '|' || leftTile == '.' || leftTile == '7' || leftTile == 'J') {
            return false;
        }
        char southTile = southTileRow.charAt(tileIndex);
        if (southTile == '.' || southTile == '-' || southTile == '7' || southTile == 'F') {
            return false;
        }
        return true;
    }
}
