package DataAccess;

import Model.Exercise;
import Model.MuscleGroup;
import Model.Workout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

public class WorkoutDAO {
    private static final Logger LOGGER = Logger.getLogger(WorkoutDAO.class.getName());

    public int addWorkout(Workout workout) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO workouts (name,workout_date) VALUES (?,?)";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setString(1, workout.getName());
            statement.setObject(2, workout.getDate());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error creating new workout!", e);
        } finally {
            ConnectionFactory.close(conn);
            ConnectionFactory.close(statement);
        }
        return -1;
    }

    public Workout getWorkoutById(int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM workouts WHERE workout_id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapRowToWorkout(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting workout by id!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return null;
    }

    private Workout mapRowToWorkout(ResultSet rs) throws SQLException {
        int id = rs.getInt("workout_id");
        String name = rs.getString("name");
        LocalDate date = rs.getDate("workout_date").toLocalDate();
        return new Workout(id, name, date);
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "SELECT workout_id,name,workout_date FROM workouts ORDER BY name";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                workouts.add(mapRowToWorkout(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error getting all workouts!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return workouts;
    }

    public boolean updateWorkout(Workout workout, Workout updatedWorkout) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "UPDATE workouts SET name = ?, workout_date= ? WHERE workout_id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setString(1, updatedWorkout.getName());
            statement.setObject(2, updatedWorkout.getDate());
            statement.setInt(3, workout.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error updateing the workout!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return false;
    }

    public boolean deleteWorkout(int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM workouts WHERE workout_id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error deleting the workout!", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return false;
    }
}
