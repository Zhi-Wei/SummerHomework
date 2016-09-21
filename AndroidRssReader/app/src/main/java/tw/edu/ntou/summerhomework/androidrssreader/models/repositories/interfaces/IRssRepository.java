package tw.edu.ntou.summerhomework.androidrssreader.models.repositories.interfaces;

import java.io.IOException;
import java.util.Collection;

import tw.edu.ntou.summerhomework.androidrssreader.models.domains.Article;

public interface IRssRepository {
    Collection<Article> getArticles(String requestUrl) throws IOException;
}
