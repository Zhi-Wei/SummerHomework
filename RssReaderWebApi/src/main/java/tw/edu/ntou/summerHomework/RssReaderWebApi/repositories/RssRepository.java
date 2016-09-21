package tw.edu.ntou.summerHomework.RssReaderWebApi.repositories;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import tw.edu.ntou.summerHomework.RssReader.repositories.DocumentRepository;
import tw.edu.ntou.summerHomework.RssReader.services.RssReader;
import tw.edu.ntou.summerHomework.RssReader.services.interfaces.IRssReader;
import tw.edu.ntou.summerHomework.RssReaderWebApi.models.Article;
import tw.edu.ntou.summerHomework.RssReaderWebApi.repositories.interfaces.IRssRepository;

public class RssRepository implements IRssRepository {

	private IRssReader _rssReader;

	public RssRepository() {
		this._rssReader = new RssReader(new DocumentRepository());
	}

	@Override
	public Iterable<Article> getArticles(String requestUrl) throws MalformedURLException, Exception {
		Iterable<tw.edu.ntou.summerHomework.RssReader.models.Article> sourceArticles =
				this._rssReader.getItem(requestUrl);
		Collection<Article> articles = new ArrayList<Article>();
		sourceArticles.forEach(x -> articles.add(
				new Article()
					.setTitle(x.getTitle())
					.setLink(x.getLink())
					.setPubDate(x.getPubDate())
					.setDescription(x.getDescription())
					.setCategory(x.getCategory())));
		return articles;
	}

}
