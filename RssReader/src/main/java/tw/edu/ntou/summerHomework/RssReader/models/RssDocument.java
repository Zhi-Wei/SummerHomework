package tw.edu.ntou.summerHomework.RssReader.models;

public class RssDocument {
	private String _title;
	private String _link;
	private String _pubDate;
	private String _description;
	private String _category;
	
	public String getTitle() {
		return this._title;
	}

	public RssDocument setTitle(String title) {
		this._title = title;
		return this;
	}

	public String getLink() {
		return this._link;
	}

	public RssDocument setLink(String link) {
		this._link = link;
		return this;
	}

	public String getPubDate() {
		return this._pubDate;
	}

	public RssDocument setPubDate(String pubDate) {
		this._pubDate = pubDate;
		return this;
	}

	public String getDescription() {
		return this._description;
	}

	public RssDocument setDescription(String description) {
		this._description = description;
		return this;
	}

	public String getCategory() {
		return this._category;
	}

	public RssDocument setCategory(String category) {
		this._category = category;
		return this;
	}
}
