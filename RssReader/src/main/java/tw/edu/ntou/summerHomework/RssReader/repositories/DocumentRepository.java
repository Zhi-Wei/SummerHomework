package tw.edu.ntou.summerHomework.RssReader.repositories;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tw.edu.ntou.summerHomework.RssReader.models.RssDocument;
import tw.edu.ntou.summerHomework.RssReader.repositories.interfaces.IDocumentRepository;

public class DocumentRepository implements IDocumentRepository {

	@Override
	public Iterable<RssDocument> getRssDocuments(URL url) throws IOException {
		Document document = Jsoup.connect(url.toString()).get();
		Elements items = document.select("item");

		Collection<RssDocument> rssDocuments = new ArrayList<RssDocument>();
		items.forEach(item -> rssDocuments.add(this.mapToRssDocument(item)));
		return rssDocuments;
	}

	private RssDocument mapToRssDocument(Element item) {
		RssDocument rssDocument = new RssDocument();
		if (item != null) {
			rssDocument.setTitle(item.select("title").text())
                       .setLink(item.select("link").text())
                       .setPubDate(item.select("pubDate").text())
                       .setDescription(item.select("description").text())
                       .setCategory(item.select("category").text());
		}
		return rssDocument;
	}

}
