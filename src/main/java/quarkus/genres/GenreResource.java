package quarkus.genres;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import quarkus.PaginatedResponse;

import java.net.URI;
import java.util.NoSuchElementException;

@Transactional
@Path("/genres")
public class GenreResource {

    @Inject
    private GenreRepository repo;

    @Inject
    private GenreMapper mapper;

    @Inject
    private GenreValidator validator;


    @Inject
    public GenreResource(GenreRepository repo, GenreMapper mapper, GenreValidator validator){
        this.repo = repo;
        this.mapper = mapper;
        this.validator = validator;
    }

    @GET
    public PaginatedResponse<GenreResponseDto> list(
            @QueryParam("page") @DefaultValue("1") int page, @QueryParam("q") String q
    ){
        PanacheQuery<Genre> query = repo.findPage(page);
        PanacheQuery<GenreResponseDto> present = query.project(GenreResponseDto.class);
        if(q != null) {
            var nameLike = "%" + q + "%";
            present.filter("name.like", Parameters.with("name", nameLike));
        }
        return new PaginatedResponse<>(present);
    }

    @POST
    public Response create(CreateGenreDto genre){
        var error = this.validator.validateGenre(genre);
        if(error.isPresent()){
            var msg = error.get();
            return Response.status(400).entity(msg).build();
        }
        var entity = mapper.fromCreate(genre);
        repo.persist(entity);
        var representeation = mapper.present(entity);
        return Response.created(URI.create("/genres/" + entity.getId())).entity(representeation).build();
    }

    @GET
    @Path("{id}")
    public GenreResponseDto get(@PathParam("id") Long id){
    return repo
            .findByIdOptional(id)
            .map(mapper::present)
            .orElseThrow(() -> new NoSuchElementException("Genre " + id + " not found."));}

    @PUT
    @Path("{id}")
    public GenreResponseDto update(@PathParam("id") Long id,@Valid  UpdateGenreDto inbox){
        Genre found = repo
                .findByIdOptional(id)
                .orElseThrow(() -> new NoSuchElementException("Genre " + id + " not found."));
        mapper.update(inbox, found);
        repo.persist(found);
        return mapper.present(found);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id){
        repo.deleteById(id);
    }
}
