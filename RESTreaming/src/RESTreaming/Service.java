package RESTreaming;

import javax.ws.rs.*;

@Path("/")
public class Service {

    private final Serveur stream = new Serveur();

    @GET
    @Produces("text/plain")
    public String name() {
        return("RESTreaming");
    }

    @GET
    @Path("/play/{artist}/{title}")
    @Produces("text/plain")
    public String play(@PathParam("artist") String artist, @PathParam("title") String title) {
        return(stream.play(artist, title));
    }

    @GET
    @Path("/pause/{ref}")
    public void pause(@PathParam("ref") String ref) {
        stream.pause(ref);
    }

    @GET
    @Path("/resume/{ref}")
    public void resume(@PathParam("ref") String ref) {
        stream.resume(ref);
    }

    @GET
    @Path("/stop/{ref}")
    public void stop(@PathParam("ref") String ref) {
        stream.stop(ref);
    }

}