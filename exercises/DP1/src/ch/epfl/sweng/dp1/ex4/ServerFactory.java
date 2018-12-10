package ch.epfl.sweng.dp1.ex4;

public class ServerFactory implements ComputerAbstractFactory {
    String ram;
    String HDD;
    String CPU;

    public ServerFactory(String ram, String HDD, String CPU){
        this.ram = ram;
        this.HDD = HDD;
        this.CPU = CPU;
    }

    @Override
    public Computer createComputer() {
        return new Server(ram, HDD, CPU);
    }
}
