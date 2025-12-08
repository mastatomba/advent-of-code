package nl.schoutens.aoc2025.day8;

public class JunctionBox {
    private int x;
    private int y;
    private int z;

    public JunctionBox(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double calculateDistance(JunctionBox other) {
        return distance3D(this.x, this.y, this.z, other.x, other.y, other.z);
    }

    public static JunctionBox fromString(String input) {
        String[] parts = input.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);
        return new JunctionBox(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JunctionBox that = (JunctionBox) obj;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public String toString() {
        return "JunctionBox{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public static double distance3D(
        double x1, 
        double y1, 
        double z1,
        double x2,
        double y2,
        double z2
    ) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
}
