package BusinessLogic;

import DataAccess.ExerciseDAO;
import Model.Exercise;
import Model.MuscleGroup;
import Model.Workout;

import java.util.List;

public class ExerciseController {
    private ExerciseDAO exerciseDAO;

    public ExerciseController() {
        this.exerciseDAO = new ExerciseDAO();
    }

    public List<Exercise> getAllExercises() {
        return exerciseDAO.getAllExercises();
    }

    public List<Exercise> getExercisesByMuscleGroup(MuscleGroup muscleGroup) {
        return exerciseDAO.getExercisesByMuscleGroup(muscleGroup);
    }

    public Exercise getExerciseById(int id) {
        return exerciseDAO.getExerciseById(id);
    }

    public boolean createExercise(String name, MuscleGroup muscleGroup) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be null or empty!");
        }
        if (muscleGroup == null) {
            throw new IllegalArgumentException("Muscle group cannot be null!");
        }
        if (exerciseDAO.exerciseExists(name)) {
            throw new IllegalArgumentException("Exercise already exists!");
        }
        Exercise exercise = new Exercise(0, name, muscleGroup);
        return exerciseDAO.addExercise(exercise);
    }

    public boolean updateExercise(String newName, MuscleGroup newMuscleGroup, int id) {
        Exercise oldExercise = exerciseDAO.getExerciseById(id);
        if (oldExercise == null) {
            throw new IllegalArgumentException("Exercise does not exists!");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be null or empty!");
        }
        if (newMuscleGroup == null) {
            throw new IllegalArgumentException("Muscle group cannot be null!");
        }
        if (!oldExercise.getName().equalsIgnoreCase(newName) && exerciseDAO.exerciseExists(newName)) {
            throw new IllegalArgumentException("Exercise already exists!");
        }
        Exercise newExercise = new Exercise(0, newName, newMuscleGroup);
        return exerciseDAO.updateExercise(oldExercise, newExercise);
    }

    public boolean deleteExercise(int exerciseId) {
        Exercise exercise = exerciseDAO.getExerciseById(exerciseId);
        if (exercise == null) {
            throw new IllegalArgumentException("Workout with id " + exerciseId + " does not exist");
        }
        exerciseDAO.deleteExercise(exerciseId);
        return true;
    }

    public List<MuscleGroup> getAllMuscleGroups() {
        return List.of(MuscleGroup.values());
    }
}
