package com.fmattaperdomo.restful.services;

import com.fmattaperdomo.restful.dao.CountryDao;
import com.fmattaperdomo.restful.dao.StateDao;
import com.fmattaperdomo.restful.model.Country;
import com.fmattaperdomo.restful.model.State;
import java.util.List;

/**
 *
 * @author Francisco Matta Perdomo
 */
public class StateService {
    StateDao stateDao = new StateDao();
    private List<State> listStates = stateDao.getALL();
    
    public List<State> getStates(){
        return listStates;
    }
    
    public State getState(int id){
        for (State state: listStates){
            if(state.getId() == id){
                return state;
            }
        }
        return null;
    }
    
    public State addState(State state){
        State stateAdd =  stateDao.insertState(state);
        if (stateAdd != null){
            listStates.add(stateAdd);
            return stateAdd;
        }
        return null;
    }

    public State updateState(State state){
        int position = getPosition(state.getId());
        if (position == -1) {
            return null;
        }        
        State stateUpd =  stateDao.updateState(state);
        if (stateUpd != null){
            listStates.set(position, stateUpd);
            return stateUpd;
        }
        return null;
    }
    
    public boolean delState(int id){
        int position = getPosition(id);
        if (position == -1) {
            return false;
        }
        stateDao.deleteState(id);
        listStates.remove(position);
        return true;
    }
    
    private int getPosition(int id){
        for(int i = 0; i < listStates.size(); i++){
            if(listStates.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }
    
}
