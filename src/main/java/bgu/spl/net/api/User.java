package bgu.spl.net.srv;

import src.Course;

import java.util.Vector;

public class User {
     private String username;
     private String password;
     private Vector<Course> registeredCourses;
     final boolean isAdmin;
     private boolean loggedIn;

     public User(String username, String password, boolean isAdmin){
         this.username = username;
         this.password = password;
         this.registeredCourses = new Vector<Course>();
         this.isAdmin = isAdmin;
         loggedIn = false;
     }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Vector<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logIn(){
         loggedIn = true;
    }

    public void registerToCourse(Course course){
         registeredCourses.add(course);
    }
}
