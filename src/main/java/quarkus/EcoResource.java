package quarkus;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.Optional;

@Path("/saludar")
public class EcoResource {

    @GET
    public String saludar(@QueryParam("mensaje") String mensaje) {
        return Optional.ofNullable(mensaje)
                .map(n -> "> " + n)
                .orElse("No se que decir.");
    }

    @GET
    @Path("/{nombre}")
    public String saludo(@PathParam("nombre") String nombre){
        return "Hola, " + nombre;
    }
    @GET
    @Path("/{nombre}/gritar")
    public String gritar(@PathParam("nombre") String nombre){
        return "HOLA, " + nombre.toUpperCase();
    }
}
