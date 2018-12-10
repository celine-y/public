package ch.epfl.sweng.dp1.ex4;

public class PCFactory implements ComputerAbstractFactory{
    String ram;
    String HDD;
    String CPU;

    public PCFactory(String ram, String HDD, String CPU) {
        this.ram = ram;
        this.HDD = HDD;
        this.CPU = CPU;
    }

    @Override
    public Computer createComputer() {
        return new PC(ram, HDD, CPU);
    }
}
