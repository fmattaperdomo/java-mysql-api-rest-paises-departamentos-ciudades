package com.fmattaperdomo.restful.resources;


import com.fmattaperdomo.restful.model.Country;
import com.fmattaperdomo.restful.services.CountryService;
import java.util.HashMap;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Francisco Matta Perdomo
 */
@Path("/paises")
public class CountryResource {
    CountryService countryService = new CountryService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountries(){
        return Response.ok(countryService.getCountries()).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountry(@PathParam("id") int id){
        Country country = countryService.getCountry(id);
        if (country != null){
            return Response.ok(country).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }    
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCountry(@PathParam("id") int id){
        if (countryService.delCountry(id)){
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }   
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCountry(Country country){
        HashMap<String, String> mistakes = new HashMap();
        for (ConstraintViolation error: country.validate())
        {
            mistakes.put(error.getPropertyPath().toString(), error.getMessage());
        }
        if (mistakes.size() > 0){
            return Response.status(Response.Status.BAD_REQUEST).entity(mistakes).build();
        }

        Country countryAdd = countryService.addCountry(country);
        if (countryAdd != null){
            return Response.status(Response.Status.CREATED).entity(countryAdd).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }    

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updCountry(Country country){
        HashMap<String, String> mistakes = new HashMap();
        for (ConstraintViolation error: country.validate())
        {
            mistakes.put(error.getPropertyPath().toString(), error.getMessage());
        }
        if (mistakes.size() > 0){
            return Response.status(Response.Status.BAD_REQUEST).entity(mistakes).build();
        }
        
        Country countryUpd = countryService.updateCountry(country);
        if (countryUpd != null){
            return Response.status(Response.Status.CREATED).entity(countryUpd).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }         
}
