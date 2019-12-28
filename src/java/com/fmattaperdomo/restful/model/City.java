package com.fmattaperdomo.restful.model;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Matta Perdomo
 */
public class City {
    private int id;
    @Size(min=5, max=100)
    @NotNull
    private String name;
    @NotNull
    private State state;

    public City() {
    }

    public City(int id, String name, State state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<ConstraintViolation<City>> validate()
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
        
    }
}
