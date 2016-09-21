package tw.edu.ntou.summerHomework.RssReaderWebApi.services;

import java.net.MalformedURLException;
import tw.edu.ntou.summerHomework.RssReaderWebApi.models.Article;
import tw.edu.ntou.summerHomework.RssReaderWebApi.repositories.interfaces.IRssRepository;
import tw.edu.ntou.summerHomework.RssReaderWebApi.services.interfaces.IRssService;

public class RssService implements IRssService {

	private IRssRepository _rssRepository;
	
	public RssService(IRssRepository rssRepository)
	{
		if (rssRepository == null) {
			throw new NullPointerException("rssRepository");
		}
		this._rssRepository = rssRepository;
	}
	
	@Override
	public Iterable<Article> getArticles(String requestUrl) throws MalformedURLException, Exception {
		return this._rssRepository.getArticles(requestUrl);
	}

}
