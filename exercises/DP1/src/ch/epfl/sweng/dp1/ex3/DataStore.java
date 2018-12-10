package ch.epfl.sweng.dp1.ex3;

import java.util.ArrayList;
import java.util.List;

public class DataStore extends Subject{
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        // Data has changed. Update the UI
        updateScreens(data);
    }
}