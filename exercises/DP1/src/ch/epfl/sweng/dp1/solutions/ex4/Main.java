package ch.epfl.sweng.dp1.solutions.ex4;

public class Main {
    public static void main(String[] args) {
        ComputerAbstractFactory factory = null;
        String type = "Server";

        if (type.equalsIgnoreCase("Server")) {
            // A Server with 16GB RAM, 1 TB HDD, 2.9GHZ GPU
            factory = new ServerFactory("16 GB", "1 TB", "2.9 GHz");
        } else {
            // A PC with 2 GB RAM, 500 GB HDD, 2.4HZ CPU
            factory = new PCFactory("2 GB", "500 GB", "2.4 GHz");
        }
        Computer pc = factory.createComputer();
        System.out.println("PC Config::"+pc);
    }
}
