package com.fmattaperdomo.restful.dao;

import com.fmattaperdomo.restful.exceptions.GeneralException;
import com.fmattaperdomo.restful.model.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Francisco Matta Perdomo
 */
public class StateDao {
    private final String GETBYID = "SELECT id, name, idCountry FROM api_states WHERE id = ?";
    private final String GETALL = "SELECT id, name, idCountry FROM api_states";
    private final String INSERTSTATE = "INSERT INTO api_states (id, name, idCountry) VALUES (?,?,?)";
    private final String UPDATESTATE = "UPDATE api_states set name = ?, idCountry = ? WHERE ID = ?";
    private final String DELETESTATE = "DELETE FROM api_states WHERE ID = ?";
    
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    
    private State convert(ResultSet rs) throws SQLException {
        State state = new State();
        state.setId(rs.getInt("id"));
        state.setName(rs.getString("name"));
        CountryDao countryDao = new CountryDao();
        state.setCountry(countryDao.getByID(rs.getShort("idCountry")));
        return state;
    }
    
    public State getByID(int id) throws GeneralException {
        State state = null;
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(GETBYID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                state = convert(resultSet);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al obtener el departamento: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return state;
    }
    
    public List<State> getALL() throws GeneralException {
        List<State> listStates = new ArrayList<>();
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(GETALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                State state = convert(resultSet);
                listStates.add(state);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al obtener todos los Departamentos: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return listStates;
    }
    
    public State insertState(State state) throws GeneralException {
        State stateAdd = null;
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(INSERTSTATE, statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, state.getName());
            preparedStatement.setInt(3, state.getCountry().getId());
            int result = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                int id = resultSet.getInt(1);
                stateAdd = getByID(id);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al insertar el departamento: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return stateAdd;
    }    

    public State updateState(State state) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(UPDATESTATE);
            preparedStatement.setString(1, state.getName());
            preparedStatement.setInt(2, state.getCountry().getId());
            preparedStatement.setInt(3, state.getId());
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new GeneralException("No se actualizo el registro: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al actualizar el departamento: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return state;
    }    
    
    public void deleteState(int id) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(DELETESTATE);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new GeneralException("No se elimio el registro: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al eliminar el departamento: "+sqle.getMessage());
        } finally {
            closeResources();
        }
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
