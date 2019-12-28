package com.fmattaperdomo.restful.dao;

import com.fmattaperdomo.restful.exceptions.GeneralException;
import com.fmattaperdomo.restful.model.Country;
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
public class CountryDao {
    private final String GETBYID = "SELECT id, name FROM api_countries WHERE id = ?";
    private final String GETALL = "SELECT id, name FROM api_countries";
    private final String INSERTCOUNTRY = "INSERT INTO api_countries (id, name) VALUES (?,?)";
    private final String UPDATECOUNTRY = "UPDATE api_countries set name = ? WHERE ID = ?";
    private final String DELETECOUNTRY = "DELETE FROM api_countries WHERE ID = ?";
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    
    private Country convert(ResultSet rs) throws SQLException {
        Country country = new Country();
        country.setId(rs.getInt("id"));
        country.setName(rs.getString("name"));
        return country;
    }
    
    public Country getByID(int id) throws GeneralException {
        Country country = null;
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(GETBYID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                country = convert(resultSet);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error el país: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return country;
    }
    
    public Country insertCountry(Country country) throws GeneralException {
        Country countryadd = null;
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(INSERTCOUNTRY, statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, country.getName());
            int result = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                int id = resultSet.getInt(1);
                countryadd = getByID(id);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al insertar el país: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return countryadd;
    }    

    public Country updateCountry(Country country) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(UPDATECOUNTRY);
            preparedStatement.setString(1, country.getName());
            preparedStatement.setInt(2, country.getId());
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new GeneralException("No se actualizo el registro: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al actualizar el país: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return country;
    }    
    
    public void deleteCountry(int id) throws GeneralException {
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(DELETECOUNTRY);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new GeneralException("No se elimio el registro: ");
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al eliminar el país: "+sqle.getMessage());
        } finally {
            closeResources();
        }
    }
    
    public List<Country> getALL() throws GeneralException {
        List<Country> listCountries = new ArrayList<>();
        try {
            connection = new DBConnection().connect();
            preparedStatement = connection.prepareStatement(GETALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Country country = convert(resultSet);
                listCountries.add(country);
            }
        } catch (SQLException sqle) {
            throw new GeneralException("Error al obtener todos los Paises: "+sqle.getMessage());
        } finally {
            closeResources();
        }
        return listCountries;
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
