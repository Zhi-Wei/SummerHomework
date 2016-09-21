package tw.edu.ntou.summerHomework.RssReader.servicesTest;

import static org.junit.Assert.*;
import java.net.MalformedURLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tw.edu.ntou.summerHomework.RssReader.models.Article;
import tw.edu.ntou.summerHomework.RssReader.repositories.DocumentRepository;
import tw.edu.ntou.summerHomework.RssReader.services.RssReader;

public class RssReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetItem() {
		Iterable<Article> articles = null;
		String rssUrl = //"http://www.mobile01.com/rss/hottopics.xml";
		 "http://udn.com/udnrss/BREAKINGNEWS1.xml";
			//	"http://cms.www.gov.tw/rssfeed/rss.aspx?code=62";
		try {
			articles = new RssReader(new DocumentRepository()).getItem(rssUrl);
			articles.forEach(a -> System.out.println(a));
		} catch (MalformedURLException e) {
			System.out.println("輸入的網址有誤，請重新確認。");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		assertNotNull(articles);
	}

}