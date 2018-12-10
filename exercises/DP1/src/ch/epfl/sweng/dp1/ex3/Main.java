package ch.epfl.sweng.dp1.ex3;

public class Main {
    public static void main(String[] args) {
        // Create two screens
        Screen screen = new Screen();
        Phone phone = new Phone();
        // Create a datastore
        DataStore dataStore = new DataStore();
        // Connect the dataStore and the screens
        dataStore.addScreen(screen);
        dataStore.addScreen(phone);

        // Update data in the dataStore
        String data = "Some data";
        dataStore.setData(data);

        data = "New data";
        dataStore.setData(data);
    }
}