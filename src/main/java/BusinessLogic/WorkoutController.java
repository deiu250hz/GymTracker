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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkoutController {
    private ExerciseSetsDAO exerciseDAO;
    private ExerciseSetsDAO exerciseSetDAO;
    private WorkoutDAO workoutDAO;
    private WorkoutExerciseDAO workoutExerciseDAO;

    public WorkoutController() {
        exerciseDAO = new ExerciseSetsDAO();
        exerciseSetDAO = new ExerciseSetsDAO();
        workoutDAO = new WorkoutDAO();
        workoutExerciseDAO = new WorkoutExerciseDAO();
    }

    public int createNewWorkout(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Workout name is null or empty");
        }
        Workout workout = new Workout(0, name, LocalDate.now());
        if (workoutDAO.addWorkout(workout)) {
            return 1;
        }
        return -1;
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
}
