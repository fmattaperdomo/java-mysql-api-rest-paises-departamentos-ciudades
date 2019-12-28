package com.fmattaperdomo.restful.services;

import com.fmattaperdomo.restful.dao.CityDao;
import com.fmattaperdomo.restful.model.City;
import java.util.List;

/**
 *
 * @author Francisco Matta Perdomo
 */
public class CityService {
    CityDao cityDao = new CityDao();
    private List<City> listCities = cityDao.getALL();
    
    public List<City> getCities(){
        return listCities;
    }
    
    public City getCity(int id){
        for (City city: listCities){
            if(city.getId() == id){
                return city;
            }
        }
        return null;
    }
    
    public City addCity(City city){
        City cityAdd =  cityDao.insertCity(city);
        if (cityAdd != null){
            listCities.add(cityAdd);
            return cityAdd;
        }
        return null;
    }

    public City updateCity(City city){
        int position = getPosition(city.getId());
        if (position == -1) {
            return null;
        }        
        City cityUpd =  cityDao.updateCity(city);
        if (cityUpd != null){
            listCities.set(position, cityUpd);
            return cityUpd;
        }
        return null;
    }
    
    public boolean delCity(int id){
        int position = getPosition(id);
        if (position == -1) {
            return false;
        }
        cityDao.deleteCity(id);
        listCities.remove(position);
        return true;
    }
    
    private int getPosition(int id){
        for(int i = 0; i < listCities.size(); i++){
            if(listCities.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }
    
}
