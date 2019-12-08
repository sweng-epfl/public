package ex2;

class ComputerBuilder {
    private String name = "";
    private int dram = 0;
    private int storage = 0;
    private double frequency = 0;
    private boolean integratedGraphics = false;
    private boolean hasUsbC = false;
    private int color = Integer.MAX_VALUE;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private boolean hasHDMI = false;
    private boolean hasSDReader = false;
    private boolean hasJack = false;

    public Computer build() {
        if (name == "") throw new IllegalStateException("No name specified");
        if (dram == 0) throw new IllegalStateException("No valid DRAM amount specified");
        if (storage == 0) throw new IllegalStateException("No valid storage amount specified");
        if (frequency == 0) throw new IllegalStateException("No valid CPU frequency specified");
        if (color == Integer.MAX_VALUE) throw new IllegalStateException("No valid color code specified");
        if (screenWidth == 0) throw new IllegalStateException("No valid screen width specified");
        if (screenHeight == 0) throw new IllegalStateException("No valid screen height specified");
        return new Computer(name, dram, storage, frequency, integratedGraphics, hasUsbC, color, screenWidth,
                screenHeight, hasHDMI, hasSDReader, hasJack);
    }

    public ComputerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ComputerBuilder setDram(int dram) {
        this.dram = dram;
        return this;
    }

    public ComputerBuilder setStorage(int storage) {
        this.storage = storage;
        return this;
    }

    public ComputerBuilder setFrequency(double frequency) {
        this.frequency = frequency;
        return this;
    }

    public ComputerBuilder setIntegratedGraphics() {
        this.integratedGraphics = true;
        return this;
    }

    public ComputerBuilder unsetIntegratedGraphics() {
        this.integratedGraphics = false;
        return this;
    }

    public ComputerBuilder setUsbC() {
        this.hasUsbC = true;
        return this;
    }

    public ComputerBuilder unsetUsbC() {
        this.hasUsbC = false;
        return this;
    }

    public ComputerBuilder setColor(int color) {
        this.color = color;
        return this;
    }

    public ComputerBuilder setResolution(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        return this;
    }

    public ComputerBuilder setHDMI() {
        this.hasHDMI = true;
        return this;
    }

    public ComputerBuilder unsetHDMI() {
        this.hasHDMI = false;
        return this;
    }

    public ComputerBuilder setSDReader() {
        this.hasSDReader = true;
        return this;
    }

    public ComputerBuilder unsetSDReader() {
        this.hasSDReader = false;
        return this;
    }

    public ComputerBuilder setJack() {
        this.hasJack = true;
        return this;
    }

    public ComputerBuilder unsetJack() {
        this.hasJack = false;
        return this;
    }
}