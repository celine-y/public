package ch.epfl.sweng.dp1.ex7;

public class Logger {
    private static Logger instance;

    private Logger(){
        if (instance != null)
            throw new IllegalStateException("Already instantiated");
        instance = new Logger();
    }

    public static Logger getInstance(){
        return instance;
    }

    public void print(){
        System.out.println("Logged");
    }
}