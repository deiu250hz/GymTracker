package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;
import Model.Exercise;
import Model.MuscleGroup;

public class WorkoutExerciseDAO {
    private static final Logger LOGGER = Logger.getLogger(ExerciseDAO.class.getName());

    public boolean addWorkoutExercise(int workoutId, int exerciseId) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO workout_exercises (workout_id,exercise_id) VALUES (?,?)";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, workoutId);
            statement.setInt(2, exerciseId);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error adding exercise to workout!", e);
        } finally {
            ConnectionFactory.close(conn);
            ConnectionFactory.close(statement);
        }
        return false;
    }

    public List<Exercise> getAllWorkoutExercises(int workoutId) {
        Connection conn = null;
        PreparedStatement statement = null;
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT e.exercise_id, e.name, e.muscle_group " +
                "FROM exercises e " +
                "JOIN workout_exercises we ON e.exercise_id = we.exercise_id " +
                "WHERE we.workout_id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, workoutId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                exercises.add(mapRowToExercise(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting the exercises in the workout!", e);
        } finally {
            ConnectionFactory.close(conn);
            ConnectionFactory.close(statement);
        }
        return exercises;
    }

    private Exercise mapRowToExercise(ResultSet rs) throws SQLException {
        int id = rs.getInt("exercise_id");
        String name = rs.getString("name");
        MuscleGroup muscleGroup = MuscleGroup.valueOf(rs.getString("muscle_group"));
        return new Exercise(id, name, muscleGroup);
    }

    public boolean deleteExerciseFromWorkout(int workoutExerciseId) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM workout_exercises WHERE workout_exercise_id=?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error deleting exercise from workout!", e);
        } finally {
            ConnectionFactory.close(conn);
            ConnectionFactory.close(statement);
        }
        return false;
    }
}
