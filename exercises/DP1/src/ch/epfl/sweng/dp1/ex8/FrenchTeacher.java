package ch.epfl.sweng.dp1.ex8;

public class FrenchTeacher implements Teacher {
    @Override
    public void sayHi() {
        System.out.println("Bonjour");
    }

    @Override
    public void sayMyName(String myName) {
        System.out.println("French Name");
    }
}
