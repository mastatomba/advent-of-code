package nl.schoutens.aoc2025.day11;

import java.util.ArrayList;
import java.util.List;

public class Device {
    private final String id;
    private final List<Device> connectedDevices;

    public Device(String id) {
        this.id = id;
        connectedDevices = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Device> getConnectedDevices() {
        return connectedDevices;
    }

    public void connectDevice(Device device) {
        connectedDevices.add(device);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Device device = (Device) obj;
        return id.equals(device.id);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", connectedDevices=" + connectedDevices +
                '}';
    }
}
