package BusinessLogic;

import Model.Exercise;
import Model.ExerciseSet;
import Model.Workout;
import Model.WorkoutExercise;

import java.util.List;
import java.util.Map;

public class WorkoutDetail {
    private Workout workout;
    private List<WorkoutExercise> exercises;
    private Map<Integer,List<ExerciseSet>> exerciseSets;

    public WorkoutDetail(Workout workout,List<WorkoutExercise> exercises, Map<Integer,List<ExerciseSet>> exerciseSets) {
        this.workout = workout;
        this.exercises = exercises;
        this.exerciseSets = exerciseSets;
    }

    public int getExerciseCount() {
        return exercises.size();
    }

    public int getTotalSetCount() {
        return exerciseSets.values().stream().mapToInt(List::size).sum();
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<WorkoutExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    public Map<Integer, List<ExerciseSet>> getExerciseSets() {
        return exerciseSets;
    }

    public void setExerciseSets(Map<Integer, List<ExerciseSet>> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }
}
