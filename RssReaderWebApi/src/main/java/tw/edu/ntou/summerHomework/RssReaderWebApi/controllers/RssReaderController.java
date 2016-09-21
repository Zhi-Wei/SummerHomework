package tw.edu.ntou.summerHomework.RssReaderWebApi.controllers;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tw.edu.ntou.summerHomework.RssReaderWebApi.models.Article;
import tw.edu.ntou.summerHomework.RssReaderWebApi.repositories.RssRepository;
import tw.edu.ntou.summerHomework.RssReaderWebApi.services.RssService;
import tw.edu.ntou.summerHomework.RssReaderWebApi.services.interfaces.IRssService;

@Path("")
public class RssReaderController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response get(@QueryParam("url")
					     @DefaultValue("http://www.mobile01.com/rss/hottopics.xml")
					     String url)
	{
		String json = "";
		try {
			IRssService _rssService = new RssService(new RssRepository());
			Iterable<Article> articles = _rssService.getArticles(url);
			Gson gson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
					.setDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")
					.disableHtmlEscaping()
					.serializeNulls()
					.create();
			json = gson.toJson(articles);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(json, MediaType.APPLICATION_JSON + ";charset=utf-8").build();
	}
	
}
