package ex2;

class Computer {
    private final String name;
    private final int dram;
    private final int storage;
    private final double frequency;
    private final boolean integratedGraphics;
    private final boolean hasUsbC;
    private final int color;
    private final int screenWidth;
    private final int screenHeight;
    private final boolean hasHDMI;
    private final boolean hasSDReader;
    private final boolean hasJack;

    public Computer(String name, int dram, int storage, double frequency, boolean integratedGraphics, boolean hasUsbC, int color, int screenWidth, int screenHeight, boolean hasHDMI, boolean hasSDReader, boolean hasJack) {
        this.name = name;
        this.dram = dram;
        this.storage = storage;
        this.frequency = frequency;
        this.integratedGraphics = integratedGraphics;
        this.hasUsbC = hasUsbC;
        this.color = color;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.hasHDMI = hasHDMI;
        this.hasSDReader = hasSDReader;
        this.hasJack = hasJack;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
            .append(this.name).append(": ")
            .append(this.dram).append(" MB RAM, ")
            .append(this.storage).append(" GB storage, ")
            .append(this.frequency).append(" GHz CPU, ")
            .append("Color code: ")
            .append(String.format("0x%08X",this.color)).append(", ")
            .append(this.screenWidth).append("x").append(this.screenHeight)
            .append(" resolution, ");
        if (this.integratedGraphics) sb.append("integrated graphics, ");
        if (this.hasUsbC) sb.append("USB-C, ");
        if (this.hasHDMI) sb.append("HDMI, ");
        if (this.hasSDReader) sb.append("SD Reader, ");
        if (this.hasJack) sb.append("Jack port");
        return sb.toString();
    }
}