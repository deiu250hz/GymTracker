package org.example;

import java.util.ArrayList;
import java.util.List;

public class WorkoutExercise {
    private int id;
    private Exercise exercise;
    private List<ExerciseSet> sets;

    public WorkoutExercise(int id, Exercise exercise) {
        this.id = id;
        this.exercise = exercise;
        this.sets = new ArrayList<ExerciseSet>();

    }

    public void addSet(ExerciseSet set) {
        this.sets.add(set);
    }

    public void removeSet(ExerciseSet set) {
        this.sets.remove(set);
    }

    public Exercise getExercise() {
        return exercise;
    }

    public double getExerciseVolume() {
        return sets.stream().mapToDouble(set -> set.getVolume()).sum();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
