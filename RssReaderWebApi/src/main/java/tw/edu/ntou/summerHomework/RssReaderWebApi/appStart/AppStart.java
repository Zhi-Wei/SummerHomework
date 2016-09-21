package tw.edu.ntou.summerHomework.RssReaderWebApi.appStart;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("RssReader")
public class AppStart extends ResourceConfig {
	public AppStart() {
		packages("tw.edu.ntou.summerHomework.RssReaderWebApi");
	}
}
