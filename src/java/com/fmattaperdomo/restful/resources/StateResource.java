package com.fmattaperdomo.restful.resources;

import com.fmattaperdomo.restful.dao.StateDao;
import com.fmattaperdomo.restful.model.State;
import com.fmattaperdomo.restful.services.StateService;
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
@Path("/departamentos")
public class StateResource {
    StateService stateService = new StateService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStates(){
        return Response.ok(stateService.getStates()).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getState(@PathParam("id") int id){
        State state = stateService.getState(id);
        if (state != null){
            return Response.ok(state).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }    
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteState(@PathParam("id") int id){
        if (stateService.delState(id)){
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }   
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addState(State state){
        HashMap<String, String> mistakes = new HashMap();
        for (ConstraintViolation error: state.validate())
        {
            mistakes.put(error.getPropertyPath().toString(), error.getMessage());
        }
        if (mistakes.size() > 0){
            return Response.status(Response.Status.BAD_REQUEST).entity(mistakes).build();
        }
        
        State stateAdd = stateService.addState(state);
        if (stateAdd != null){
            return Response.status(Response.Status.CREATED).entity(stateAdd).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }    

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updState(State state){
        HashMap<String, String> mistakes = new HashMap();
        for (ConstraintViolation error: state.validate())
        {
            mistakes.put(error.getPropertyPath().toString(), error.getMessage());
        }
        if (mistakes.size() > 0){
            return Response.status(Response.Status.BAD_REQUEST).entity(mistakes).build();
        }
        
        State stateUpd = stateService.updateState(state);
        if (stateUpd != null){
            return Response.status(Response.Status.CREATED).entity(stateUpd).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }         
}
