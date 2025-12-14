package nl.schoutens.aoc2025.day11;

import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

import java.util.ArrayList;
import java.util.List;

public class AOCYear2025Day11 {

    public static void main(String[] args) {
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day11/input.txt");

            var devices = parseDevices(lines);
            devices.add(new Device("out"));

            System.out.println("Part 1: " + part1(devices));

            lines = FileUtils.fileToList(INPUT_BASE_PATH + "day11/input.txt");

            devices = parseDevices(lines);
            devices.add(new Device("out"));

            System.out.println("Part 2: " + part2(devices));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(List<Device> devices) {
        for (Device device : devices) {
            if (device.getId().equals("you")) {
                return search(device, devices, new ArrayList<>());
            }
        }
        return -1;
    }

    private static int part2(List<Device> devices) {
        for (Device device : devices) {
            if (device.getId().equals("svr")) {
                return search2(device, devices, new ArrayList<>(), false, false);
            }
        }
        return -1;
    }

    private static int search(Device device, List<Device> devices, List<String> visited) {
        if (device.getId().equals("out")) {
            return 1;
        }
        visited.add(device.getId());
        int paths = 0;
        for (Device connected : device.getConnectedDevices()) {
            if (!visited.contains(connected.getId())) {
                Device nextDevice = devices.stream()
                        .filter(d -> d.getId().equals(connected.getId()))
                        .findFirst()
                        .orElse(null);
                if (nextDevice != null) {
                    paths += search(nextDevice, devices, visited);
                }
            }
        }
        visited.remove(device.getId());
        return paths;
    }

    private static int search2(Device device, List<Device> devices, List<String> visited, boolean seenFft, boolean seenDac) {
        if (device.getId().equals("out")) {
            return seenFft && seenDac ? 1 : 0;
        }
        visited.add(device.getId());

        if (device.getId().equals("fft")) {
            seenFft = true;
        }
        if (device.getId().equals("dac")) {
            seenDac = true;
        }

        int paths = 0;
        for (Device connected : device.getConnectedDevices()) {
            if (!visited.contains(connected.getId())) {
                Device nextDevice = devices.stream()
                        .filter(d -> d.getId().equals(connected.getId()))
                        .findFirst()
                        .orElse(null);
                if (nextDevice != null) {
                    paths += search2(nextDevice, devices, visited, seenFft, seenDac);
                }
            }
        }
        visited.remove(device.getId());
        return paths;
    }

    private static List<Device> parseDevices(List<String> lines) {
        List<Device> devices = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(": ");

            String deviceId = parts[0].trim();
            Device device = new Device(deviceId);
            devices.add(device);

            String[] connectedIds = parts[1].split(" ");
            for (String connectedId : connectedIds) {
                String trimmedId = connectedId.trim();
                Device connectedDevice = new Device(trimmedId);
                device.connectDevice(connectedDevice);
            }
        }
        return devices;
    }
}
