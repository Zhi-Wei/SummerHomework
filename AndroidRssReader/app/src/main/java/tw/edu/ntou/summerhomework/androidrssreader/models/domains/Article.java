package tw.edu.ntou.summerhomework.androidrssreader.models.domains;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class Article implements Serializable {
    private String title, description, category;
    private URL link;
    private Date pubDate;

    public Article() { }

    public Article(String title, URL link, Date pubDate, String description, String category) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
        this.category = category;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getLink() {
        return this.link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public Date getPubDate() {
        return this.pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getLocaleDatetime() {
        return String.format(Locale.getDefault(), "%tF %<tR", this.pubDate);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
