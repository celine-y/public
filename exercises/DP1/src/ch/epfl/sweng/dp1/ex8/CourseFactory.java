package ch.epfl.sweng.dp1.ex8;

public class CourseFactory {
    public Course getCourse(String type){
        if (type.equals("French"))
            return new FrenchCourse();

        return new Course();
    }
}
