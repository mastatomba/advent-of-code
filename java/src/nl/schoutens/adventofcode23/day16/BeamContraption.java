package nl.schoutens.adventofcode23.day16;

import java.util.HashMap;
import java.util.Map;

import nl.schoutens.datatype.StringGrid;

public class BeamContraption {
    private static char EMPTY_SPACE = '.';
    private static char VERTICAL_SPLITTER = '|';
    private static char HORIZONTAL_SPLITTER = '-';
    private static char MIRROR_SLASH = '/';
    private static char MIRROR_BACKSLASH = '\\';

    private StringGrid stringGrid;
    private Map<String, Boolean> energizedTileMap;
    private int beamCount = 0;

    public BeamContraption(StringGrid stringGrid) {
        this.stringGrid = stringGrid;
        this.energizedTileMap = new HashMap<>();
    }

    public void energize() throws Exception {
        this.moveBeam(new Beam(++this.beamCount, 0, 0, BeamDirection.EAST));
    }

    private void moveBeam(Beam beam) throws Exception {
        if (!beam.isInBounds(this.stringGrid.getRowSize(), this.stringGrid.getColumnSize())) {
            System.out.println("Beam is moving out of contraption: " + beam);
        }

        int newRowIndex = beam.getRowIndex() + beam.getDirection().rowOffset;
        int newColumnIndex = beam.getColumnIndex() + beam.getDirection().colOffset;
        char tile = this.stringGrid.charAt(newRowIndex, newColumnIndex);
        

        BeamDirection newDirection;
        if (tile == EMPTY_SPACE || tile == VERTICAL_SPLITTER) { // continue moving in same direction
            this.moveBeamNorth(beamNumber, newRowIndex, newColumnIndex);
        } else if (tile == HORIZONTAL_SPLITTER) {
            if (!isTileEnergized) {
                this.moveBeamEast(beamNumber, newRowIndex, newColumnIndex);
                this.moveBeamWest(++this.beamCount, newRowIndex, newColumnIndex);
            }
        } else if (tile == MIRROR_BACKSLASH) {
            this.moveBeamWest(beamNumber, newRowIndex, newColumnIndex);
        } else if (tile == MIRROR_SLASH) {
            this.moveBeamEast(beamNumber, newRowIndex, newColumnIndex);
        }
        switch (tile) {
            case EMPTY_SPACE:
                newDirection = beam.getDirection();
                break;
        
            default:
                break;
        }

        switch (beam.getDirection()) {
            case NORTH:
                
                break;
        
            default:
                break;
        }
    }

    public int getNumberOfEnergizedTiles() {
        return this.energizedTileMap.size();
    }

    private void moveBeamNorth(int beamNumber, int rowIndex, int columnIndex) throws Exception {
        int newRowIndex = rowIndex - 1;
        int newColumnIndex = columnIndex;
        if (this.checkBounds(newRowIndex, newColumnIndex)) {
            boolean isTileEnergized = this.energizedTileMap.containsKey(newRowIndex+"_"+newColumnIndex);
            this.energizeTile(newRowIndex, newColumnIndex);
            char tile = this.stringGrid.charAt(newRowIndex, newColumnIndex);
            if (tile == EMPTY_SPACE || tile == VERTICAL_SPLITTER) { // continue moving in same direction
                this.moveBeamNorth(beamNumber, newRowIndex, newColumnIndex);
            } else if (tile == HORIZONTAL_SPLITTER) {
                if (!isTileEnergized) {
                    this.moveBeamEast(beamNumber, newRowIndex, newColumnIndex);
                    this.moveBeamWest(++this.beamCount, newRowIndex, newColumnIndex);
                }
            } else if (tile == MIRROR_BACKSLASH) {
                this.moveBeamWest(beamNumber, newRowIndex, newColumnIndex);
            } else if (tile == MIRROR_SLASH) {
                this.moveBeamEast(beamNumber, newRowIndex, newColumnIndex);
            }
        } else {
            System.out.println("Beam "+beamNumber+" out of grid moving north at "+rowIndex+","+columnIndex);
        }
    }

    private void moveBeamEast(int beamNumber, int rowIndex, int columnIndex) throws Exception {
        int newRowIndex = rowIndex;
        int newColumnIndex = columnIndex + 1;
        if (this.checkBounds(newRowIndex, newColumnIndex)) {
            boolean isTileEnergized = this.energizedTileMap.containsKey(newRowIndex+"_"+newColumnIndex);
            this.energizeTile(newRowIndex, newColumnIndex);
            char tile = this.stringGrid.charAt(newRowIndex, newColumnIndex);
            if (tile == EMPTY_SPACE || tile == HORIZONTAL_SPLITTER) { // continue moving in same direction
                this.moveBeamEast(beamNumber, newRowIndex, newColumnIndex);
            } else if (tile == VERTICAL_SPLITTER) {
                if (!isTileEnergized) {
                    this.moveBeamNorth(beamNumber, newRowIndex, newColumnIndex);
                    this.moveBeamSouth(++this.beamCount, newRowIndex, newColumnIndex);
                }
            } else if (tile == MIRROR_BACKSLASH) {
                this.moveBeamSouth(beamNumber, newRowIndex, newColumnIndex);
            } else if (tile == MIRROR_SLASH) {
                this.moveBeamNorth(beamNumber, newRowIndex, newColumnIndex);
            }
        } else {
            System.out.println("Beam "+beamNumber+" out of grid moving east at "+rowIndex+","+columnIndex);
        }
    }

    private void moveBeamSouth(int beamNumber, int rowIndex, int columnIndex) throws Exception {
        int newRowIndex = rowIndex + 1;
        int newColumnIndex = columnIndex;
        if (this.checkBounds(newRowIndex, newColumnIndex)) {
            boolean isTileEnergized = this.energizedTileMap.containsKey(newRowIndex+"_"+newColumnIndex);
            this.energizeTile(newRowIndex, newColumnIndex);
            char tile = this.stringGrid.charAt(newRowIndex, newColumnIndex);
            if (tile == EMPTY_SPACE || tile == VERTICAL_SPLITTER) { // continue moving in same direction
                this.moveBeamSouth(beamNumber, newRowIndex, newColumnIndex);
            } else if (tile == HORIZONTAL_SPLITTER) {
                if (!isTileEnergized) {
                    this.moveBeamEast(beamNumber, newRowIndex, newColumnIndex);
                    this.moveBeamWest(++this.beamCount, newRowIndex, newColumnIndex);
                }
            } else if (tile == MIRROR_BACKSLASH) {
                this.moveBeamEast(beamNumber, newRowIndex, newColumnIndex);
            } else if (tile == MIRROR_SLASH) {
                this.moveBeamWest(beamNumber, newRowIndex, newColumnIndex);
            }
        } else {
            System.out.println("Beam "+beamNumber+" out of grid moving south at "+rowIndex+","+columnIndex);
        }
    }

    private void moveBeamWest(int beamNumber, int rowIndex, int columnIndex) throws Exception {
        int newRowIndex = rowIndex;
        int newColumnIndex = columnIndex - 1;
        if (this.checkBounds(newRowIndex, newColumnIndex)) {
            boolean isTileEnergized = this.energizedTileMap.containsKey(newRowIndex+"_"+newColumnIndex);
            this.energizeTile(newRowIndex, newColumnIndex);
            char tile = this.stringGrid.charAt(newRowIndex, newColumnIndex);
            if (tile == EMPTY_SPACE || tile == HORIZONTAL_SPLITTER) { // continue moving in same direction
                this.moveBeamWest(beamNumber, newRowIndex, newColumnIndex);
            } else if (tile == VERTICAL_SPLITTER) {
                if (!isTileEnergized) {
                    this.moveBeamNorth(beamNumber, newRowIndex, newColumnIndex);
                    this.moveBeamSouth(++this.beamCount, newRowIndex, newColumnIndex);
                }
            } else if (tile == MIRROR_BACKSLASH) {
                this.moveBeamNorth(beamNumber, newRowIndex, newColumnIndex);
            } else if (tile == MIRROR_SLASH) {
                this.moveBeamSouth(beamNumber, newRowIndex, newColumnIndex);
            }
        } else {
            System.out.println("Beam "+beamNumber+" out of grid moving west at "+rowIndex+","+columnIndex);
        }
    }

    private boolean checkBounds(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || columnIndex < 0) {
            return false;
        }
        if (rowIndex >= this.stringGrid.getRowSize()) {
            return false;
        }
        if (columnIndex >= this.stringGrid.getColumnSize()) {
            return false;
        }
        return true;
    }

    private void energizeTile(int rowIndex, int columnIndex) throws Exception {
        String key = rowIndex+"_"+columnIndex;
        this.energizedTileMap.put(key, true);
        // if (this.energizedTileMap.size()>100) {
        //     throw new Exception("map is full");
        // }
    }
}
