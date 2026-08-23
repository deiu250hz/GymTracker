package BusinessLogic;

import DataAccess.ExerciseDAO;
import DataAccess.ExerciseSetsDAO;
import DataAccess.WorkoutDAO;
import DataAccess.WorkoutExerciseDAO;
import Model.Exercise;
import Model.ExerciseSet;
import Model.Workout;
import Model.WorkoutExercise;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkoutController {
    private ExerciseDAO exerciseDAO;
    private ExerciseSetsDAO exerciseSetDAO;
    private WorkoutDAO workoutDAO;
    private WorkoutExerciseDAO workoutExerciseDAO;

    public WorkoutController() {
        exerciseDAO = new ExerciseDAO();
        exerciseSetDAO = new ExerciseSetsDAO();
        workoutDAO = new WorkoutDAO();
        workoutExerciseDAO = new WorkoutExerciseDAO();
    }

    public int createNewWorkout(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Workout name is null or empty");
        }
        Workout workout = new Workout(0, name, LocalDate.now());
        return workoutDAO.addWorkout(workout);
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = workoutDAO.getAllWorkouts();
        return workouts.stream().sorted((w1, w2) -> w2.getDate().compareTo(w1.getDate())).collect(Collectors.toList());
    }

    public WorkoutDetail getWorkoutDetail(int workoutId) {
        Workout workout = workoutDAO.getWorkoutById(workoutId);
        if (workout == null) {
            throw new IllegalArgumentException("Workout with id " + workoutId + " does not exist");
        }
        List<WorkoutExercise> exercises = workoutExerciseDAO.getAllWorkoutExercises(workoutId);
        Map<Integer, List<ExerciseSet>> exerciseSets = new HashMap<>();
        for (WorkoutExercise we : exercises) {
            int workoutExerciseId = we.getId();
            List<ExerciseSet> sets = exerciseSetDAO.getSetsForWorkoutExercise(workoutExerciseId);
            exerciseSets.put(workoutExerciseId, sets);
        }
        return new WorkoutDetail(workout, exercises, exerciseSets);
    }

    public List<Workout> getWorkoutsInPeriod(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        return workoutDAO.getAllWorkouts().stream().filter(w -> !w.getDate().isBefore(start) && !w.getDate().isAfter(end))
                .sorted((w1, w2) -> w2.getDate().compareTo(w1.getDate())).collect(Collectors.toList());

    }

    public boolean updateWorkoutName(String newName, int workoutId) {
        if (newName == null || newName.isEmpty()) {
            throw new IllegalArgumentException("Workout name is null or empty");
        }
        Workout oldWorkout = workoutDAO.getWorkoutById(workoutId);
        if (oldWorkout == null) {
            throw new IllegalArgumentException("Workout with id " + workoutId + " does not exist");
        }
        Workout workout = new Workout(0, newName, oldWorkout.getDate());
        workoutDAO.updateWorkout(oldWorkout, workout);
        return true;
    }

    public boolean deleteWorkout(int workoutId) {
        Workout workout = workoutDAO.getWorkoutById(workoutId);
        if (workout == null) {
            throw new IllegalArgumentException("Workout with id " + workoutId + " does not exist");
        }
        workoutDAO.deleteWorkout(workoutId);
        return true;
    }
}
