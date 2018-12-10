package ch.epfl.sweng.dp1.ex3;

public class Screen implements ScreenInterface {
    protected void display(String data){
        System.out.println("Screen - New data : " + data);
    }

    @Override
    public void update(String data) {
        display(data);
    }
}