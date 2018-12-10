package ch.epfl.sweng.dp1.ex3;

public class Phone implements ScreenInterface {
    protected void printToPhone(String data){
        System.out.println("Phone - New data : " + data);
    }

    @Override
    public void update(String data) {
        printToPhone(data);
    }
}
