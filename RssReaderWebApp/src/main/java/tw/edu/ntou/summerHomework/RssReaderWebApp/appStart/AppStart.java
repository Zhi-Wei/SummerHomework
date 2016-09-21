package tw.edu.ntou.summerHomework.RssReaderWebApp.appStart;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class AppStart extends ResourceConfig {
	public AppStart() {
		packages("tw.edu.ntou.summerHomework.RssReaderWebApp");
		register(JspMvcFeature.class);
		property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/view");
	}
}
