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
public class State {
    private int id;
    @Size(min=5, max=100)
    @NotNull
    private String name;
    @NotNull
    private Country country;

    public State() {
    }

    public State(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
           
    public Set<ConstraintViolation<State>> validate()
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }    
           
}
