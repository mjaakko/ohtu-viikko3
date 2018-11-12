/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

/**
 *
 * @author jaakko
 */
public class WeekStats {
    private double hour_total;
    private int exercise_total;
    private Integer[] exercises;

    public WeekStats(double hour_total, int exercise_total, Integer[] exercises) {
        this.hour_total = hour_total;
        this.exercise_total = exercise_total;
        this.exercises = exercises;
    }

    public double getHour_total() {
        return hour_total;
    }

    public void setHour_total(double hour_total) {
        this.hour_total = hour_total;
    }

    public int getExercise_total() {
        return exercise_total;
    }

    public void setExercise_total(int exercise_total) {
        this.exercise_total = exercise_total;
    }

    public Integer[] getExercises() {
        return exercises;
    }

    public void setExercises(Integer[] exercises) {
        this.exercises = exercises;
    }
   
    
}
