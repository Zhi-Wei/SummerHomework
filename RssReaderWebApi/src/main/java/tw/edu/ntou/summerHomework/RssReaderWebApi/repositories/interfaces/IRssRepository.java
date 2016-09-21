package tw.edu.ntou.summerHomework.RssReaderWebApi.repositories.interfaces;

import java.net.MalformedURLException;
import tw.edu.ntou.summerHomework.RssReaderWebApi.models.Article;

public interface IRssRepository {
	Iterable<Article> getArticles(String requestUrl) throws MalformedURLException, Exception;
}
