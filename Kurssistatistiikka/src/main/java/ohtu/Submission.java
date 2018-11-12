package ohtu;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Submission {
    private int week;
    private int hours;
    private int[] exercises;
    private String course;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    public void setExercises(int... exercises) {
        this.exercises = exercises;
    }

    public int[] getExercises() {
        return exercises;
    }
    
    public void setHours(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }
    

    @Override
    public String toString() {
        return course+", viikolla "+week+" tehdyt tehtävät: "+String.join(",", Arrays.stream(exercises).sorted().mapToObj(Integer::toString).collect(Collectors.toList()))+" (aikaa käytetty "+hours+" tuntia)";
    }
    
}