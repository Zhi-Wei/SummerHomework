package tw.edu.ntou.summerHomework.RssReader.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import tw.edu.ntou.summerHomework.RssReader.common.Retrier;
import tw.edu.ntou.summerHomework.RssReader.models.Article;
import tw.edu.ntou.summerHomework.RssReader.models.RssDocument;
import tw.edu.ntou.summerHomework.RssReader.repositories.interfaces.IDocumentRepository;
import tw.edu.ntou.summerHomework.RssReader.services.interfaces.IRssReader;

public class RssReader implements IRssReader {

	private IDocumentRepository _documentRepository;
	private final DateFormat _dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	
	public RssReader(IDocumentRepository documentRepository)
	{
		if (documentRepository == null) {
			throw new NullPointerException("documentRepository");
		}
		this._documentRepository = documentRepository;
	}
	
	@Override
	public Iterable<Article> getItem(String requestUrl) throws MalformedURLException, Exception {
		Collection<Article> articles = new ArrayList<Article>();
		Retrier<Iterable<RssDocument>> retrier = new Retrier<Iterable<RssDocument>>();

		URL url = new URL(requestUrl);
		Iterable<RssDocument> rssDocuments =
				retrier.TryWithDelay(() -> this.getRssDocuments(url), 3, 500);
		if (rssDocuments == null) {
			return articles;
		}

		rssDocuments.forEach(rssDoc -> articles.add(this.mapToArticle(rssDoc)));
		return articles;
	}

	private Iterable<RssDocument> getRssDocuments(URL url) {
		try {
			return this._documentRepository.getRssDocuments(url);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private Article mapToArticle(RssDocument rssDoc) {
		String title = rssDoc.getTitle(),
               description = rssDoc.getDescription(),
               category = rssDoc.getCategory();
		URL link = null;
		Date pubDate = null;

		try {
			link = new URL(rssDoc.getLink());
		} catch (Exception e) {
		}
		try {
			pubDate = this._dateFormat.parse(rssDoc.getPubDate());
		} catch (Exception e) {
		}

		return new Article(this._dateFormat)
							.setTitle(title)
							.setLink(link)
							.setPubDate(pubDate)
							.setDescription(description)
							.setCategory(category);
	}

}
