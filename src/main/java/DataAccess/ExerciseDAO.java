package DataAccess;

import Model.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;
import Model.MuscleGroup;

public class ExerciseDAO {

    private static final Logger LOGGER = Logger.getLogger(ExerciseDAO.class.getName());

    public boolean addExercise(Exercise exercise) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO exercises (name,muscle_group) VALUES (?,?)";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setString(1, exercise.getName());
            statement.setString(2, exercise.getMuscleGroup().toString());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                LOGGER.info("Exercise has been added successfully");
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return false;
    }

    public Exercise getExerciseById(int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM exercises WHERE exercise_id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapRowToExercise(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting exercise by id!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return null;
    }

    public Exercise getExerciseByName(String name) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM exercises WHERE name = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapRowToExercise(rs);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting exercise by name!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return null;
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "SELECT exercise_id,name,muscle_group FROM exercises ORDER BY name";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                exercises.add(mapRowToExercise(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting all exercises!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return exercises;
    }

    public List<Exercise> getExercisesByMuscleGroup(MuscleGroup mg) {
        List<Exercise> exercises = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM exercises WHERE muscle_group = ? ORDER BY name";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setString(1, mg.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                exercises.add(mapRowToExercise(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting all exercises!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return exercises;
    }

    public boolean updateExercise(Exercise exercise, Exercise updatedExercise) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "UPDATE exercises SET name = ?, muscle_group = ? WHERE exercise_id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setString(1, updatedExercise.getName());
            statement.setString(2, updatedExercise.getMuscleGroup().toString());
            statement.setInt(3, exercise.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error updateing the exercise!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return false;
    }

    public boolean deleteExercise(int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM exercises WHERE exercise_id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error deleting the exercise!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return false;
    }

    public boolean exerciseExists(String name) {
        return getExerciseByName(name) != null;
    }

    private Exercise mapRowToExercise(ResultSet rs) throws SQLException {
        int id = rs.getInt("exercise_id");
        String name = rs.getString("name");
        MuscleGroup muscleGroup = MuscleGroup.valueOf(rs.getString("muscle_group"));
        return new Exercise(id, name, muscleGroup);
    }
}
