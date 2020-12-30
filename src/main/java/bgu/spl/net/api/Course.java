package src;
import bgu.spl.net.srv.User;

import java.util.Vector;

public class Course {

    private int courseNumber;
    private String courseName;
    private Vector<Integer> KdamCourses;
    private Vector<User> registeredStudentsNames;
    private int maxStudents;
    private int registeredStudents;

    public Course(int courseNum, String name, Vector<Integer> kdam, int maxStudents){
        courseNumber = courseNum;
        courseName = name;
        KdamCourses = kdam;
        registeredStudentsNames = new Vector<User>();
        this.maxStudents = maxStudents;
        registeredStudents = 0;
    }

    public int getCourseNumber(){
        return courseNumber;
    }

    public String getCourseName(){
        return courseName;
    }

    public Vector<Integer> getKdamCourses(){
        return KdamCourses;
    }

    public int getMaxStudents(){
        return maxStudents;
    }

    public int getRegisteredStudents() {
        return registeredStudents;
    }

    public Vector<User> getRegisteredStudentsNames() {
        return registeredStudentsNames;
    }
}
