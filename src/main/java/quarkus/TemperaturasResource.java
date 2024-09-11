package quarkus;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("/temperaturas")
public class TemperaturasResource {

    private TemperaturasService temperaturasService;

    @Inject
    public TemperaturasResource(TemperaturasService temperaturasService){
        this.temperaturasService = temperaturasService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Temperatura nueva(Temperatura temp){
        temperaturasService.addTemperatura(temp);
        return temp;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Temperatura> list(){
        return temperaturasService.obtenerTemperaturas();
    }

    @GET
    @Path("/maxima")
    @Produces(MediaType.TEXT_PLAIN)
    public Response maxima(){
        if(temperaturasService.isEmpty()){
            return Response.status(404).entity("No hay temperaturas").build();
        } else{
            int temperaturaMaxima = temperaturasService.maxima();
            return Response.ok(Integer.toString(temperaturaMaxima))
                    .header("Hola","Buenos dias")
                    .build();
        }
    }

    @GET
    @Path("/{ciudad}")
    @Produces(MediaType.APPLICATION_JSON)
    public Temperatura sacar(@PathParam("ciudad") String ciudad){
        return temperaturasService.sacarTemperatura(ciudad)
                .orElseThrow(() ->
                        new NoSuchElementException("No hay registro para la ciudad " + ciudad));
    }
}
