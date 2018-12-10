package ch.epfl.sweng.dp1.ex3;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<ScreenInterface> screens;

    public Subject(){
        screens = new ArrayList<>();
    }


    public void updateScreens(String data){
        for (ScreenInterface screen: screens) {
            screen.update(data);
        }
    }

    public void addScreen(ScreenInterface screen){
        screens.add(screen);
    }

    public void removeScreen(ScreenInterface screen){
        if (screens.contains(screen)){
            screens.remove(screen);
        }
    }

}
