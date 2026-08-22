package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Workout {
    private int id;
    private String name;
    private LocalDate date;
    private List<WorkoutExercise> exercises;

    public Workout(int id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.exercises = new ArrayList<WorkoutExercise>();
    }

    public void addExercise(WorkoutExercise exercise) {
        this.exercises.add(exercise);
    }

    public void removeExercise(WorkoutExercise exercise) {
        this.exercises.remove(exercise);
    }

    public double getWorkoutVolume() {
        return exercises.stream().mapToDouble(e -> e.getExerciseVolume()).sum();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<WorkoutExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
