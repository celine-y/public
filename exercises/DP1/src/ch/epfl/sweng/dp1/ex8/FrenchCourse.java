package ch.epfl.sweng.dp1.ex8;

public class FrenchCourse extends Course {

    @Override
    protected Teacher getTeacher() {
        return new FrenchTeacher();
    }
}
