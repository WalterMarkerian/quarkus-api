package quarkus;

import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.NoSuchElementException;

@Path("/books")
@Transactional
public class BookResource {

    @Inject
    private BookRepository repo;

    @GET
    public List<Book> index(@QueryParam("q") String query){
        if(query == null){
            return repo.listAll(Sort.by("pubDate", Sort.Direction.Descending));
        }else{
            String filter = "%" + query + "%";
            return repo.list("title ILIKE ?1 OR description ILIKE ?2", filter, filter);
        }
    }

    @POST
    public Book insert(Book insertedBook){
        repo.persist(insertedBook);
        return insertedBook;
    }

    @GET
    @Path("/{id}")
    public Book retrieve(@PathParam("id") Long id){
        var book = repo.findById(id);
        if(book != null){
            return book;
        }
        throw new NoSuchElementException("No existe un libro con el ID " + id + ".");
    }

    @DELETE
    @Path("/{id}")
    public String delete(@PathParam("id") Long id){
        if(repo.deleteById(id)){
            return "Se ha borrado el libro con ID: " + id + ".";
        }else {
            throw new NoSuchElementException("No existe un libro con el ID " + id + ".");
        }
    }

    @PUT
    @Path("/{id}")
    public Book update(@PathParam("id") Long id, Book book){
        var updatedBook = repo.findById(id);
        if(updatedBook != null){
            updatedBook.setTitle(book.getTitle());
            updatedBook.setPubDate(book.getPubDate());
            updatedBook.setNumPages(book.getNumPages());
            updatedBook.setDescription(book.getDescription());
            repo.persist(updatedBook);
            return updatedBook;
        }
        throw new NoSuchElementException("No existe un libro con el ID " + id + ".");

    }
}
