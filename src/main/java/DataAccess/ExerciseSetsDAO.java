package DataAccess;

import Connection.ConnectionFactory;
import Model.Exercise;
import Model.ExerciseSet;
import Model.MuscleGroup;
import Model.Workout;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExerciseSetsDAO {
    private static final Logger LOGGER = Logger.getLogger(ExerciseSetsDAO.class.getName());


    public boolean addSet(ExerciseSet set, int WorkoutExerciseId) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO exercise_sets (workout_exercise_id, reps,weight,set_number) VALUES (?, ?,?,?)";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, WorkoutExerciseId);
            statement.setInt(2, set.getNrReps());
            statement.setDouble(3, set.getWeight());
            statement.setInt(4, set.getSetNumber());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error inserting new set!", e);
        } finally {
            ConnectionFactory.close(conn);
            ConnectionFactory.close(statement);
        }
        return false;
    }

    public List<ExerciseSet> getSetsForWorkoutExercise(int workoutExerciseId) {
        Connection conn = null;
        PreparedStatement statement = null;
        List<ExerciseSet> exerciseSets = new ArrayList<ExerciseSet>();
        String query = "SELECT * FROM exercise_sets where workout_exercise_id=?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, workoutExerciseId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                exerciseSets.add(mapRowToExerciseSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error in getting all sets!", e);
        } finally {
            ConnectionFactory.close(conn);
            ConnectionFactory.close(statement);
        }
        return exerciseSets;
    }

    public boolean deleteSet(int setId) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM exercise_sets where set_id=?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, setId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error in deleting set!", e);
        } finally {
            ConnectionFactory.close(conn);
            ConnectionFactory.close(statement);
        }
        return false;
    }

    public boolean updateSet(ExerciseSet set, ExerciseSet updatedSet) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "UPDATE exercise_sets SET reps = ?, weight= ? WHERE set_id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, updatedSet.getNrReps());
            statement.setDouble(2, updatedSet.getWeight());
            statement.setInt(3, set.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error updateing the set!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return false;
    }

    private ExerciseSet mapRowToExerciseSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("set_id");
        int workoutExerciseId = rs.getInt("workout_exercise_id");
        int reps = rs.getInt("reps");
        double weight = rs.getDouble("weight");
        int setNumber = rs.getInt("set_number");
        return new ExerciseSet(id, workoutExerciseId, reps, weight, setNumber);
    }
}
