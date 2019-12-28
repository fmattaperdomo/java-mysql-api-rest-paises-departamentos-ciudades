package com.fmattaperdomo.restful.dao;

import com.fmattaperdomo.restful.utils.Util;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

/**
 *
 * @author Francisco Matta Perdomo
 */
public class DBConnection {
    Connection connect() {
        Connection connection = null;
        Properties properties = Util.getInstance().getConfigureDataBase();
        /*
        String url = "jdbc:mysql://" +
                        properties.getProperty("servidor") +
                        ":" + properties.getProperty("puerto") +
                        "/" + properties.getProperty("basededatos");
        String user = properties.getProperty("usuariobd");
        String pass = properties.getProperty("passwordbd");
        */
        
        String url = "jdbc:mysql://localhost:3306/fmattaperdomo";
        String user = "root";
        String pass = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        }
        
        return connection;
    }
}
