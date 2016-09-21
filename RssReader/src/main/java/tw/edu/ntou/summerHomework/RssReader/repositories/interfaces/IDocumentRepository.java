package tw.edu.ntou.summerHomework.RssReader.repositories.interfaces;

import java.io.IOException;
import java.net.URL;
import tw.edu.ntou.summerHomework.RssReader.models.RssDocument;

public interface IDocumentRepository {
	Iterable<RssDocument> getRssDocuments(URL url) throws IOException;
}
