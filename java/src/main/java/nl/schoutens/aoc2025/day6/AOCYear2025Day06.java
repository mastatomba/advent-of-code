package nl.schoutens.aoc2025.day6;

import nl.schoutens.datatype.StringGrid;
import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

import java.util.List;
import java.util.ArrayList;

public class AOCYear2025Day06 {

    public static void main(String[] args) {
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day6/input.txt");

            System.out.println("Part 1: " + part1(lines));
            System.out.println("Part 2: " + part2(lines));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long part1(List<String> lines) {
        var operators = lines.get(lines.size()-1).trim().split("\\s+");
        var grid = create2DIntGrid(lines, operators.length);

        long sum = 0;
        
        for (int col = 0; col < operators.length; col++) {
            long number1 = grid[0][col];
            var operator = operators[col].charAt(0);
            for (int row = 1; row < grid.length; row++) {
                long number2 = grid[row][col];
                switch (operator) {
                    case '+':
                        number1 += number2;
                        break;
                    case '*':
                        number1 *= number2;
                        break;
                    default:
                        throw new RuntimeException("Unknown operator: " + operator);
                }
            }
            sum += number1;
        }

        return sum;
    }

    private static long part2(List<String> lines) {
        var grid = createStringGrid(lines);

        long sum = 0;
        
        var operator = grid.charAt(grid.getRowSize()-1, 0);
        var numbers = new ArrayList<Integer>();
        for (int col = 0; col < grid.getColumnSize(); col++) {
            var newOperator = grid.charAt(grid.getRowSize()-1, col);
            if (newOperator != ' ') {
                if (numbers.size() > 0) {
                    sum += calculateResult(numbers, operator);
                    numbers.clear();
                }

                operator = newOperator;
            }

            var number = "";
            for (int row = 0; row < grid.getRowSize()-1; row++) {
                number += grid.charAt(row, col);
            }
            if (!number.trim().isEmpty()) {
                numbers.add(Integer.parseInt(number.trim()));
            }
        }

        if (numbers.size() > 0) {
            sum += calculateResult(numbers, operator);
        }

        return sum;
    }

    private static long calculateResult(List<Integer> numbers, char operator) {
        long result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            long number = numbers.get(i);
            switch (operator) {
                case '+':
                    result += number;
                    break;
                case '*':
                    result *= number;
                    break;
                default:
                    throw new RuntimeException("Unknown operator: " + operator);
            }
        }
        return result;
    }

    private static int[][] create2DIntGrid(List<String> lines, int columnCount) {
        var grid = new int[lines.size()-1][columnCount];
        for (int row = 0; row < lines.size()-1; row++) {
            var cells = lines.get(row).trim().split("\\s+");
            for (int col = 0; col < cells.length; col++) {
                grid[row][col] = Integer.parseInt(cells[col]);
            }
        }
        return grid;
    }

    private static StringGrid createStringGrid(List<String> lines) {
        var grid = new StringGrid();
        for (String line: lines) {
            grid.addRow(line);
        }
        return grid;
    }
}
