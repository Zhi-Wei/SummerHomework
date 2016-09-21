package tw.edu.ntou.summerHomework.RssReader.services.interfaces;

import java.net.MalformedURLException;
import tw.edu.ntou.summerHomework.RssReader.models.Article;

public interface IRssReader {
	Iterable<Article> getItem(String requestUrl) throws MalformedURLException, Exception;
}
