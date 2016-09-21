package tw.edu.ntou.summerHomework.RssReaderWebApi.services.interfaces;

import java.net.MalformedURLException;
import tw.edu.ntou.summerHomework.RssReaderWebApi.models.Article;

public interface IRssService {
	Iterable<Article> getArticles(String requestUrl) throws MalformedURLException, Exception;
}
