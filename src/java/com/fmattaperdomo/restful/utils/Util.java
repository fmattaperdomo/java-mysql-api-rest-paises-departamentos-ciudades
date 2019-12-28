package com.fmattaperdomo.restful.utils;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francisco Matta Perdomo
 */
public final class Util {
    private final static Util instance = new Util();
    private Properties propertyBD;
    
    private Util(){}
    
    public static Util getInstance(){
        return instance;
    }
    
    public void setConfigureDataBase(Properties properties) {
        this.propertyBD = properties;
    }
    
    public Properties getConfigureDataBase() {
        return this.propertyBD;
    }
    
    public void gotoPage(HttpServletRequest request, HttpServletResponse response, ServletContext contexto, String url)
            throws ServletException, IOException {
        RequestDispatcher despachador = contexto.getRequestDispatcher(url);
        despachador.forward(request, response);
    }
    
}
