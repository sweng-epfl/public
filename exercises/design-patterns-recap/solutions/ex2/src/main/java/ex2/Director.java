package ex2;

class Director {
    public Computer buildMyAwesomePC(ComputerBuilder builder) {
        return builder.setName("eWhackintosh")
            .setDram(8192)
            .setStorage(512)
            .setFrequency(3.5)
            .setIntegratedGraphics()
            .setUsbC()
            .setColor(0xffffff)
            .setResolution(1920, 1080)
            .setHDMI()
            .setSDReader()
            .setJack()
            .build();
    }
}