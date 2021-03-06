package com.courses.management.solution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolutionDAOImpl implements SolutionDao {
    private static final Logger LOG = LogManager.getLogger(SolutionDAOImpl.class);
    private static final String INSERT = "INSERT INTO solution(text, status, mark) VALUES(?, ?, ?);";
    private static final String UPDATE = "UPDATE solution SET text = ?, mark = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM solution WHERE id = ?;";
    private static final String GET_BY_ID = "SELECT id, text, status, mark FROM solution WHERE id = ?;";
    private static final String GET_ALL = "SELECT id, text, status, mark FROM solution;";
    private static final String ALL_BY_USER_ID = "SELECT id, text, status, mark FROM solution " +
            "WHERE user_id = ?;";
    private static final String ALL_BY_HOMEWORK_ID = "SELECT id, text, status, mark FROM solution " +
            "WHERE home_work_id = ?;";
    private static final String GET_BY_USER_HOMEWORK_ID = "SELECT id, text, status, mark FROM solution " +
            "WHERE user_id = ? & home_work_id = ?;";
    private DataSource dataSource;

    public SolutionDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Solutions solutions) {
        LOG.debug(String.format("create: solutions.text=%s", solutions.getText()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)){
            statement.setString(1, solutions.getText());
            statement.setString(2, solutions.getStatus().getStatus());
            statement.setInt(3, solutions.getMark());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating solution: %s", solutions.getText()), e);
        }
    }

    @Override
    public void update(Solutions solutions) {
        LOG.debug(String.format("update: solutions.text=%s", solutions.getText()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, solutions.getText());
            statement.setInt(2, solutions.getMark());
            statement.setInt(3, solutions.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating solution: %s", solutions.getText()), e);
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: solution.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            int index = statement.executeUpdate();
            if (index == 0 ){
                LOG.warn(String.format("no solution with id=%s found", id));
            }
        } catch (SQLException e) {
            LOG.error(String.format("Error deleting solution with id: %s", id), e);
        }
    }

    @Override
    public Solutions get(int id) {
        Solutions solution = null;
        LOG.debug(String.format("get(id): solution.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            solution = new Solutions();
            solution.setId(resultSet.getInt(1));
            solution.setText(resultSet.getString(2));
            String status = resultSet.getString(3);
            solution.setStatus(SolutionStatus.valueOf(status));
            solution.setMark(resultSet.getInt(4));
        } catch (SQLException e) {
            LOG.error("Error retrieving Solution.", e);
        }
        return solution;
    }

    @Override
    public List<Solutions> getAll() {
        List<Solutions> solutions = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = statement.executeQuery();
            solutions = new ArrayList<>();
            while (resultSet.next()){
                Solutions sol = new Solutions();
                sol.setId(resultSet.getInt(1));
                sol.setText(resultSet.getString(2));
                String status = resultSet.getString(3);
                sol.setStatus(SolutionStatus.valueOf(status));
                sol.setMark(resultSet.getInt(4));
                solutions.add(sol);
            }
        } catch (SQLException e) {
            LOG.error("Error retrieving solution.", e);
        }
        return solutions;
    }

    @Override
    public List<Solutions> getAllByUser(int id) {
        List<Solutions> solutions = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ALL_BY_USER_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            solutions = new ArrayList<>();
            while (resultSet.next()){
                Solutions sol = new Solutions();
                sol.setId(resultSet.getInt(1));
                sol.setText(resultSet.getString(2));
                String status = resultSet.getString(3);
                sol.setStatus(SolutionStatus.valueOf(status));
                sol.setMark(resultSet.getInt(4));
                solutions.add(sol);
            }
        } catch (SQLException e) {
            LOG.error("Error retrieving solution by user id.", e);
        }
        return solutions;
    }

    @Override
    public List<Solutions> getAllByHomework(int id) {
        List<Solutions> solutions = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ALL_BY_HOMEWORK_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            solutions = new ArrayList<>();
            while (resultSet.next()){
                Solutions sol = new Solutions();
                sol.setId(resultSet.getInt(1));
                sol.setText(resultSet.getString(2));
                String status = resultSet.getString(3);
                sol.setStatus(SolutionStatus.valueOf(status));
                sol.setMark(resultSet.getInt(4));
                solutions.add(sol);
            }
        } catch (SQLException e) {
            LOG.error("Error retrieving solution by homework id.", e);
        }
        return solutions;
    }

    @Override
    public Solutions get(int userId, int homeworkId) {
        Solutions solution = null;
        LOG.debug(String.format("get(userId, homeworkId): solution.user=%d, homework=%d", userId, homeworkId));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_USER_HOMEWORK_ID)){
            statement.setInt(1, userId);
            statement.setInt(2, homeworkId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            solution = new Solutions();
            solution.setId(resultSet.getInt(1));
            solution.setText(resultSet.getString(2));
            String status = resultSet.getString(3);
            solution.setStatus(SolutionStatus.valueOf(status));
            solution.setMark(resultSet.getInt(4));
        } catch (SQLException e) {
            LOG.error("Error retrieving Solution.", e);
        }
        return solution;
    }
}
