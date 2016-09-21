package tw.edu.ntou.summerHomework.RssReaderWebApi.models;

import java.net.URL;
import java.util.Date;

public class Article {
	private String title, description, category;
	private URL link;
	private Date pubDate;

	public String getTitle() {
		return this.title;
	}

	public Article setTitle(String title) {
		this.title = title;
		return this;
	}

	public URL getLink() {
		return this.link;
	}

	public Article setLink(URL link) {
		this.link = link;
		return this;
	}

	public Date getPubDate() {
		return this.pubDate;
	}

	public Article setPubDate(Date pubDate) {
		this.pubDate = pubDate;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public Article setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getCategory() {
		return this.category;
	}

	public Article setCategory(String category) {
		this.category = category;
		return this;
	}
}
