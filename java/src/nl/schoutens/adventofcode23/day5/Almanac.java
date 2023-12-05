package nl.schoutens.adventofcode23.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Almanac {
    private Map<Long, Long> seeds;
    private List<AlmanacMapping> seedToSoilMappings;
    private List<AlmanacMapping> soilToFertilizerMappings;
    private List<AlmanacMapping> fertilizerToWaterMappings;
    private List<AlmanacMapping> waterToLightMappings;
    private List<AlmanacMapping> lightToTemperatureMappings;
    private List<AlmanacMapping> temperatureToHumidityMappings;
    private List<AlmanacMapping> humidityToLocationMappings;

    public Almanac(Map<Long, Long> seeds) {
        this.seeds = seeds;
        this.seedToSoilMappings = new ArrayList<>();
        this.soilToFertilizerMappings = new ArrayList<>();
        this.fertilizerToWaterMappings = new ArrayList<>();
        this.waterToLightMappings = new ArrayList<>();
        this.lightToTemperatureMappings = new ArrayList<>();
        this.temperatureToHumidityMappings = new ArrayList<>();
        this.humidityToLocationMappings = new ArrayList<>();
    }

    public void addSeedToSoilMapping(AlmanacMapping almanacMapping) {
        this.seedToSoilMappings.add(almanacMapping);
    }

    public void addSoilToFertilizerMapping(AlmanacMapping almanacMapping) {
        this.soilToFertilizerMappings.add(almanacMapping);
    }

    public void addFertilizerToWaterMapping(AlmanacMapping almanacMapping) {
        this.fertilizerToWaterMappings.add(almanacMapping);
    }

    public void addWaterToLightMapping(AlmanacMapping almanacMapping) {
        this.waterToLightMappings.add(almanacMapping);
    }

    public void addLightToTemperatureMapping(AlmanacMapping almanacMapping) {
        this.lightToTemperatureMappings.add(almanacMapping);
    }

    public void addTemperatureToHumidityMapping(AlmanacMapping almanacMapping) {
        this.temperatureToHumidityMappings.add(almanacMapping);
    }

    public void addHumidityToLocationMapping(AlmanacMapping almanacMapping) {
        this.humidityToLocationMappings.add(almanacMapping);
    }

    public long calculateLowestLocationNumber() {
        long lowestLocationNumber = -1;
        for (Map.Entry<Long,Long> seedEntry : this.seeds.entrySet()) {
            System.out.println("Key = " + seedEntry.getKey() + ", Value = " + seedEntry.getValue());
            
            for (long j=0; j<seedEntry.getValue(); j++) {
                long seed = seedEntry.getKey()+j;

                long soil = this.getDestination(this.seedToSoilMappings, seed);
                long fertilizer = this.getDestination(this.soilToFertilizerMappings, soil);
                long water = this.getDestination(this.fertilizerToWaterMappings, fertilizer);
                long light = this.getDestination(this.waterToLightMappings, water);
                long temperature = this.getDestination(this.lightToTemperatureMappings, light);
                long humidity = this.getDestination(this.temperatureToHumidityMappings, temperature);
                long location = this.getDestination(this.humidityToLocationMappings, humidity);

                if (lowestLocationNumber == -1 || location < lowestLocationNumber) {
                    lowestLocationNumber = location;
                }
            }
        }
        return lowestLocationNumber;
    }

    private long getDestination(List<AlmanacMapping> almanacMappings, long source) {
        for (AlmanacMapping mapping: almanacMappings) {
            if (mapping.isSourceInRange(source)) {
                return mapping.getDestination(source);
            }
        }
        return source;
    }


}
