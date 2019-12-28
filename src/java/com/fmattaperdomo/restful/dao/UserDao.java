package com.fmattaperdomo.restful.dao;

import com.fmattaperdomo.restful.exceptions.GeneralException;
import com.fmattaperdomo.restful.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Francisco Matta
 */
public class UserDao {
    private final String GETBYID = "SELECT id, name, date_updated FROM api_users WHERE id = ?";
    private final String GETALL = "SELECT id, name, date_updated FROM api_users";
    private final String INSERTCOUNTRY = "INSERT INTO api_users (username, password) VALUES (?,?)";
    private final String UPDATECOUNTRY = "UPDATE api_users set password = ? WHERE ID = ?";
    private final String DELETECOUNTRY = "DELETE FROM api_users WHERE ID = ?";
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    
    private User convert(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    }
    
    public User getByUsername(String username) throws GeneralException {
        User user = null;
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(GETBYID);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = convert(resultSet);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al consultar el usuario: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return user;
    }
    
    public User insertUser(User user) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(INSERTCOUNTRY);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            int result = preparedStatement.executeUpdate();
            if (result != 1) {
                throw new GeneralException("Error al insertar el usuario: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al insertar el usuario: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return user;
    }    

    public User updateUser(User user) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(UPDATECOUNTRY);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUsername());
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new GeneralException("No se actualizo el registro: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al actualizar el usuario: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return user;
    }    
    
    public void deleteUser(String username) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(DELETECOUNTRY);
            preparedStatement.setString(1, username);
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new GeneralException("No se elimio el registro: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al eliminar el usuario: "+sqle.getMessage());
        } finally {
            closeResources();
        }
    }
    
    public List<User> getALL() throws GeneralException {
        List<User> listUsers = new ArrayList<>();
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(GETALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = convert(resultSet);
                listUsers.add(user);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al obtener todos los Paises: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return listUsers;
    }
    
    private void closeResources() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqle) {}
    }
    
}
