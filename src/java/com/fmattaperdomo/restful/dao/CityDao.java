package com.fmattaperdomo.restful.dao;

import com.fmattaperdomo.restful.exceptions.GeneralException;
import com.fmattaperdomo.restful.model.City;
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
public class CityDao {
    private final String GETBYID = "SELECT id, name, idState FROM api_cities WHERE id = ?";
    private final String GETALL = "SELECT id, name, idState FROM api_cities";
    private final String INSERTCITY = "INSERT INTO api_cities (id, name, idState) VALUES (?,?,?)";
    private final String UPDATECITY = "UPDATE api_cities set name = ?, idState = ? WHERE ID = ?";
    private final String DELETECITY = "DELETE FROM api_cities WHERE ID = ?";
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    
    private City convert(ResultSet rs) throws SQLException {
        City city = new City();
        city.setId(rs.getInt("id"));
        city.setName(rs.getString("name"));
        StateDao stateDao = new StateDao();
        city.setState(stateDao.getByID(rs.getInt("idState") ));
        
        return city;
    }
    
    public City getByID(int id) throws GeneralException {
        City city = null;
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(GETBYID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                city = convert(resultSet);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al obtener el departamento: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return city;
    }
    
    public List<City> getALL() throws GeneralException {
        List<City> listCities = new ArrayList<>();
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(GETALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                City city = convert(resultSet);
                listCities.add(city);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al obtener todos los Departamentos: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return listCities;
    }
    
    
    public City insertCity(City city) throws GeneralException {
        City cityAdd = null;
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(INSERTCITY, statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, city.getName());
            preparedStatement.setInt(3, city.getState().getId());
            int result = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                int id = resultSet.getInt(1);
                cityAdd = getByID(id);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al insertar el departamento: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return cityAdd;
    }    

    public City updateCity(City city) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(UPDATECITY);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setInt(2, city.getState().getId());
            preparedStatement.setInt(3, city.getId());
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new GeneralException("No se actualizo el registro: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al actualizar la ciudad: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return city;
    }    
    
    public void deleteCity(int id) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(DELETECITY);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new GeneralException("No se elimio el registro: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al eliminar la ciudad: "+sqle.getMessage());
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
