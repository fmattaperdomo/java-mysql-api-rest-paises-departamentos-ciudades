package com.fmattaperdomo.restful.resources;

import com.fmattaperdomo.restful.model.City;
import com.fmattaperdomo.restful.services.CityService;
import java.util.HashMap;
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
@Path("/ciudades")
public class CityResource {
    CityService cityService = new CityService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCities(){
        return Response.ok(cityService.getCities()).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCity(@PathParam("id") int id){
        City city = cityService.getCity(id);
        if (city != null){
            return Response.ok(city).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }    
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCity(@PathParam("id") int id){
        if (cityService.delCity(id)){
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }   
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCity(City city){
        HashMap<String, String> mistakes = new HashMap();
        for (ConstraintViolation error: city.validate())
        {
            mistakes.put(error.getPropertyPath().toString(), error.getMessage());
        }
        if (mistakes.size() > 0){
            return Response.status(Response.Status.BAD_REQUEST).entity(mistakes).build();
        }
        City cityAdd = cityService.addCity(city);
        if (cityAdd != null){
            return Response.status(Response.Status.CREATED).entity(cityAdd).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }    

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updCity(City city){
        HashMap<String, String> mistakes = new HashMap();
        for (ConstraintViolation error: city.validate())
        {
            mistakes.put(error.getPropertyPath().toString(), error.getMessage());
        }
        if (mistakes.size() > 0){
            return Response.status(Response.Status.BAD_REQUEST).entity(mistakes).build();
        }
        
        City cityUpd = cityService.updateCity(city);
        if (cityUpd != null){
            return Response.status(Response.Status.CREATED).entity(cityUpd).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }         
}
