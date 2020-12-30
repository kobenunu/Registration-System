package bgu.spl.net.srv;

import bgu.spl.net.srv.User;
import src.Course;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

    private ConcurrentHashMap<String, User> users;
    private ConcurrentHashMap<Integer, Course> courses;
    private Object usersLock;

    //to prevent user from creating new Database
    private static class SingletonHolder {
        private static Database instance = new Database();

        private static void reset() {
            instance = new Database();
        }
    }


    public static Database getInstance() {
        return SingletonHolder.instance;
    }

    public static void reset() {
        SingletonHolder.reset();
    }

    private Database()  {
        courses = new ConcurrentHashMap<Integer, Course>();
        users = new ConcurrentHashMap<String, User>();
        initialize("CoursesAss3");
    }
    boolean initialize(String coursesFilePath) {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(coursesFilePath));
            String line = reader.readLine();
            while (line != null) {
                System.out.println("the whole line of the course is " + line);
                int i = 0;
                String courseStringNumber = "";
                while (line.charAt(i)!='|'){
                    char next = line.charAt(i);
                    courseStringNumber = courseStringNumber + next;
                    i++;
                }
                int courseNumber = Integer.parseInt(courseStringNumber);
                System.out.println("course number is " + courseNumber);
                i++;
                String courseName = "";
                while (line.charAt(i)!='|'){
                    char next = line.charAt(i);
                    courseName = courseName + next;
                    i++;
                }
                System.out.println("course name is " + courseName);
                i++;
                Vector<Integer> kdam = new Vector<>();
                String nextKdamCourseNumber = "";
                while (line.charAt(i)!='|'){
                    char next = line.charAt(i);
                    if ((next==',' || next==']')&&!nextKdamCourseNumber.equals("")) {
                        int kdamCourseNumber = Integer.parseInt(nextKdamCourseNumber);
                        kdam.add(kdamCourseNumber);
                        nextKdamCourseNumber = "";
                    }
                    else {
                        if (next != '[') nextKdamCourseNumber = nextKdamCourseNumber + next;
                    }
                    i++;
                }
                System.out.println("kdam courses are " + kdam.toString());
                i++;
                String maxStudentsString = "";
                while (i<line.length()){
                    char next = line.charAt(i);
                    maxStudentsString = maxStudentsString + next;
                    i++;
                }
                int maxStudents = Integer.parseInt(maxStudentsString);
                System.out.println("max students is " + maxStudents);

                Course newCourse = new Course(courseNumber, courseName, kdam, maxStudents);
                courses.putIfAbsent(courseNumber, newCourse);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User registerAdmin(String username, String password) {
        if (!users.containsKey(username)){
            synchronized (usersLock){
                if (!users.containsKey(username)) {
                    User newAdmin = new User(username, password, true);
                    users.put(username, newAdmin);
                    return newAdmin;
                }
            }
        }
        return null;
    }

    public User registerStudent(String username, String password) {
        if (!users.containsKey(username)){
            synchronized (usersLock){
                if (!users.containsKey(username)) {
                    User newStudent = new User(username, password, false);
                    users.put(username, newStudent);
                    return newStudent;
                }
            }
        }
        return null;
    }

    public void logIn(String username, String password) throws IllegalAccessException, IllegalArgumentException{
        User logAttempt = users.get(username);
        if (logAttempt!=null){
            if (!logAttempt.isLoggedIn()){
                if (logAttempt.getPassword().equals(password)) {
                    logAttempt.logIn();
                    return;
                }
                throw new IllegalAccessException("password incorrect");
            }
            throw new IllegalAccessException("user is already logged in");
        }
        throw new IllegalArgumentException("no such username in the system");
    }

//	public static void main(String[] args){
//
//		BaseServer b = Server.threadPerClient(98, );
//		Database a = Database.getInstance();
//	}

}
