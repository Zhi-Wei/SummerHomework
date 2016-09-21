package tw.edu.ntou.summerHomework.RssReader.models;

import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

public class Article {
	private DateFormat _dateFormat;
	private String _title, _description, _category;
	private URL _link;
	private Date _pubDate;
	
	public Article(DateFormat dateFormat){
		this._dateFormat = dateFormat;
	}

	public String getTitle() {
		return this._title;
	}

	public Article setTitle(String title) {
		this._title = title;
		return this;
	}

	public URL getLink() {
		return this._link;
	}

	public Article setLink(URL link) {
		this._link = link;
		return this;
	}

	public Date getPubDate() {
		return this._pubDate;
	}

	public Article setPubDate(Date pubDate) {
		this._pubDate = pubDate;
		return this;
	}

	public String getDescription() {
		return this._description;
	}

	public Article setDescription(String description) {
		this._description = description;
		return this;
	}

	public String getCategory() {
		return this._category;
	}

	public Article setCategory(String category) {
		this._category = category;
		return this;
	}

	@Override
	public String toString() {
		String title = String.format("Title: %s%n", this.getTitle());
		String link = "Link: %n";
		if (this.getLink() != null) {
			link = String.format("Link: %s%n", this.getLink().toString());
		}
		String pubDate = "PubDate: %n";
		if (this.getPubDate() != null) {
			pubDate = String.format("PubDate: %s%n", this._dateFormat.format(this.getPubDate()));
		}
		String description = String.format("Description: %s%n", this.getDescription());
		String category = String.format("Category: %s%n", this.getCategory());
		return title + link + pubDate + description + category;
	}
}
