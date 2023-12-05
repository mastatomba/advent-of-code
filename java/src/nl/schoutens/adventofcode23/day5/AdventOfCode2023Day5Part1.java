package nl.schoutens.adventofcode23.day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.schoutens.util.StringUtils;

/*
 * Question: What is the lowest location number that corresponds to any of the initial seed numbers?
 */
public class AdventOfCode2023Day5Part1 {

    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day5/input.txt";
        long lowestLocationNumber = calculateLowestLocationNumber(fileName, StandardCharsets.UTF_8);
        System.out.println("Lowest location number: " + lowestLocationNumber);
    }

    private static long calculateLowestLocationNumber(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        List<String> almanacLines = new ArrayList<>();

        while((line = br.readLine()) != null){
            almanacLines.add(line);
        }
        br.close();

        Almanac almanac = new Almanac(getSeeds(almanacLines.get(0)));

        addSeedToSoilMappings(almanac, almanacLines);
        addSoilToFertilizerMappings(almanac, almanacLines);
        addFertilizerToWaterMappings(almanac, almanacLines);
        addWaterToLightMappings(almanac, almanacLines);
        addLightToTemperatureMappings(almanac, almanacLines);
        addTemperatureToHumidityMappings(almanac, almanacLines);
        addHumidityToLocationMappings(almanac, almanacLines);

        return almanac.calculateLowestLocationNumber();
    }

    private static Map<Long, Long> getSeeds(String seedLine) {
        Map<Long, Long> seeds = new HashMap<>();
        long[] inputSeeds = StringUtils.extractLongNumbersFromString(seedLine);
        for (long inputSeed : inputSeeds) {
            seeds.put(inputSeed, (long)1);
        }
        return seeds;
    }

    private static void addSeedToSoilMappings(Almanac almanac, List<String> almanacLines) {
        boolean append = false;
        for (String line: almanacLines) {
            if (line.contains("seed-to-soil")) {
                append = true;
            }
            else if (append) {
                if (!line.trim().isEmpty()) {
                    long[] mappingNumbers = StringUtils.extractLongNumbersFromString(line);
                    almanac.addSeedToSoilMapping(
                        new AlmanacMapping(mappingNumbers[0], mappingNumbers[1], mappingNumbers[2])
                    );
                } else {
                    break;
                }
            }
        }
    }

    private static void addSoilToFertilizerMappings(Almanac almanac, List<String> almanacLines) {
        boolean append = false;
        for (String line: almanacLines) {
            if (line.contains("soil-to-fertilizer")) {
                append = true;
            }
            else if (append) {
                if (!line.trim().isEmpty()) {
                    long[] mappingNumbers = StringUtils.extractLongNumbersFromString(line);
                    almanac.addSoilToFertilizerMapping(
                        new AlmanacMapping(mappingNumbers[0], mappingNumbers[1], mappingNumbers[2])
                    );
                } else {
                    break;
                }
            }
        }
    }

    private static void addFertilizerToWaterMappings(Almanac almanac, List<String> almanacLines) {
        boolean append = false;
        for (String line: almanacLines) {
            if (line.contains("fertilizer-to-water")) {
                append = true;
            }
            else if (append) {
                if (!line.trim().isEmpty()) {
                    long[] mappingNumbers = StringUtils.extractLongNumbersFromString(line);
                    almanac.addFertilizerToWaterMapping(
                        new AlmanacMapping(mappingNumbers[0], mappingNumbers[1], mappingNumbers[2])
                    );
                } else {
                    break;
                }
            }
        }
    }

    private static void addWaterToLightMappings(Almanac almanac, List<String> almanacLines) {
        boolean append = false;
        for (String line: almanacLines) {
            if (line.contains("water-to-light")) {
                append = true;
            }
            else if (append) {
                if (!line.trim().isEmpty()) {
                    long[] mappingNumbers = StringUtils.extractLongNumbersFromString(line);
                    almanac.addWaterToLightMapping(
                        new AlmanacMapping(mappingNumbers[0], mappingNumbers[1], mappingNumbers[2])
                    );
                } else {
                    break;
                }
            }
        }
    }

    private static void addLightToTemperatureMappings(Almanac almanac, List<String> almanacLines) {
        boolean append = false;
        for (String line: almanacLines) {
            if (line.contains("light-to-temperature")) {
                append = true;
            }
            else if (append) {
                if (!line.trim().isEmpty()) {
                    long[] mappingNumbers = StringUtils.extractLongNumbersFromString(line);
                    almanac.addLightToTemperatureMapping(
                        new AlmanacMapping(mappingNumbers[0], mappingNumbers[1], mappingNumbers[2])
                    );
                } else {
                    break;
                }
            }
        }
    }

    private static void addTemperatureToHumidityMappings(Almanac almanac, List<String> almanacLines) {
        boolean append = false;
        for (String line: almanacLines) {
            if (line.contains("temperature-to-humidity")) {
                append = true;
            }
            else if (append) {
                if (!line.trim().isEmpty()) {
                    long[] mappingNumbers = StringUtils.extractLongNumbersFromString(line);
                    almanac.addTemperatureToHumidityMapping(
                        new AlmanacMapping(mappingNumbers[0], mappingNumbers[1], mappingNumbers[2])
                    );
                } else {
                    break;
                }
            }
        }
    }

    private static void addHumidityToLocationMappings(Almanac almanac, List<String> almanacLines) {
        boolean append = false;
        for (String line: almanacLines) {
            if (line.contains("humidity-to-location")) {
                append = true;
            }
            else if (append) {
                if (!line.trim().isEmpty()) {
                    long[] mappingNumbers = StringUtils.extractLongNumbersFromString(line);
                    almanac.addHumidityToLocationMapping(
                        new AlmanacMapping(mappingNumbers[0], mappingNumbers[1], mappingNumbers[2])
                    );
                } else {
                    break;
                }
            }
        }
    }
}
