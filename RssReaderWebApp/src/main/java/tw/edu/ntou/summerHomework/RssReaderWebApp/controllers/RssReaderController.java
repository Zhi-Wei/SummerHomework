package tw.edu.ntou.summerHomework.RssReaderWebApp.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;

@Path("")
@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
public class RssReaderController {

	@GET
	@Path("")
	public Viewable index()
	{
		return new Viewable("/index");
	}

}
