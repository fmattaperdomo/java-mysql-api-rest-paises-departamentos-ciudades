package com.fmattaperdomo.restful.services;

import com.fmattaperdomo.restful.dao.CountryDao;
import com.fmattaperdomo.restful.model.Country;
import java.util.List;

/**
 *
 * @author Francisco Matta Perdomo
 */
public class CountryService {
    
    CountryDao countryDao = new CountryDao();
    private List<Country> listCountries = countryDao.getALL();
    
    public List<Country> getCountries(){
        return listCountries;
    }
    
    public Country getCountry(int id){
        for (Country country: listCountries){
            if(country.getId() == id){
                return country;
            }
        }
        return null;
    }
    
    public Country addCountry(Country country){
        Country countryadd =  countryDao.insertCountry(country);
        if (countryadd != null){
            listCountries.add(countryadd);
            return countryadd;
        }
        return null;
    }

    public Country updateCountry(Country country){
        int position = getPosition(country.getId());
        if (position == -1) {
            return null;
        }        
        Country countryupd =  countryDao.updateCountry(country);
        if (countryupd != null){
            listCountries.set(position, countryupd);
            return countryupd;
        }
        return null;
    }
    
    public boolean delCountry(int id){
        int position = getPosition(id);
        if (position == -1) {
            return false;
        }
        countryDao.deleteCountry(id);
        listCountries.remove(position);
        return true;
    }
    
    private int getPosition(int id){
        for(int i = 0; i < listCountries.size(); i++){
            if(listCountries.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }
    
    
}
