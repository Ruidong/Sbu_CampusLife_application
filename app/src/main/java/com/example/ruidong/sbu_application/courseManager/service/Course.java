package com.example.ruidong.sbu_application.courseManager.service;

/**
 * Created by Ruidong on 6/15/2015.
 */
public class Course {
    private String courseTitle;
    private String courseName;
    private String courseTerm;
    private String courseGrade;
    private String courseUnits;

    public Course(String title, String name, String term, String grade, String units){
        this.courseTitle=title;
        this.courseName=name;
        this.courseTerm=term;
        this.courseGrade=grade;
        this.courseUnits=units;
    }


    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public String getCourseUnits() {
        return courseUnits;
    }

    public void setCourseTitle(String title){
        this.courseTitle=title;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public void setCourseUnits(String courseUnits) {
        this.courseUnits = courseUnits;
    }
}
