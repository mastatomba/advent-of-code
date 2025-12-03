package nl.schoutens.aoc2025.day3;

import nl.schoutens.util.StringUtils;

public class BatteryBank {
    private String bank;

    public BatteryBank(String bank) {
        this.bank = bank;
    }

    public int calculateMaxVoltage() {
        var batteries = StringUtils.convertToIntArray(bank);
        var max = 0;
        for (int i = 0; i < batteries.length - 1; i++) {
            for (int j = i + 1; j < batteries.length; j++) {
                var voltage = Integer.parseInt("" + batteries[i] + batteries[j]);

                if (voltage > max) {
                    max = voltage;
                }
            }
        }
        return max;
    }

    public long calculateMaxVoltage(int requiredBatteries) {
        var batteries = StringUtils.convertToIntArray(bank);
        var voltage = "";
        var startIdx = 0;
        while (voltage.length() < requiredBatteries) {
            var nextBatteryIdx = findNextBatteryIndex(batteries, startIdx, (requiredBatteries - voltage.length()));
            voltage += batteries[nextBatteryIdx];
            startIdx = nextBatteryIdx + 1;
        }
        return Long.parseLong(voltage);
    }

    private int findNextBatteryIndex(int[] batteries, int startIdx, int requiredBatteries) {
        var batteryIdx = startIdx;
        var battery = batteries[startIdx];

        for (int i = startIdx + 1; i <= batteries.length - requiredBatteries; i++) {
            if (batteries[i] > battery) {
                battery = batteries[i];
                batteryIdx = i;
            }
        }
        return batteryIdx;
    }
}
