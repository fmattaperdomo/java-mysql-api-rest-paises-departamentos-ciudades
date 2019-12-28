package com.fmattaperdomo.restful.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Francisco Matta Perdomo
 */
public class User {
    private String username;
    private String password;
    private Calendar date_updated;

    public Calendar getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(Calendar date_updated) {
        this.date_updated = date_updated;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.date_updated = new GregorianCalendar();
    }

    public User() 
    {   
        this.date_updated = new GregorianCalendar();
    }
   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
