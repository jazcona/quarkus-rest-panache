package org.azc;

import java.security.InvalidParameterException;
import java.util.Map;

import javax.naming.directory.InvalidAttributesException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/api/v1/myEntity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MyEntityController {
    private final MyEntityService myEntityService;

    @GET
    public Response getProducts(@QueryParam("field") @DefaultValue("") String field) {
        try {
            return Response.ok(myEntityService.getMyEntities(field)).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") long id) {
        try {
            final var myEntityByIdOpt = myEntityService.findMyEntityById(id);
            if (myEntityByIdOpt.isEmpty()) {
                return Response.status(Status.NOT_FOUND).build();
            }

            return Response.ok(myEntityByIdOpt.get()).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response create(MyEntity product) {
        try {
            myEntityService.create(product);
            return Response.noContent().build();
        } catch (Exception e) {
            if (e instanceof InvalidAttributesException) {
                return Response.status(Status.CONFLICT).entity(Map.of("message", e.getMessage())).build();
            }

            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response replace(@PathParam("id") long myEntityId, MyEntity myEntity) {
        try {
            return Response.ok(myEntityService.replace(myEntityId, myEntity)).build();
        } catch (Exception e) {
            if (e instanceof InvalidParameterException) {
                return Response.status(Status.NOT_FOUND).entity(Map.of("message", e.getMessage())).build();
            }

            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response update(@PathParam("id") long productId) {
        var isDeleted = myEntityService.delete(productId);
        if (!isDeleted) {
            return Response.notModified().build();
        }
        return Response.noContent().build();
    }

}
