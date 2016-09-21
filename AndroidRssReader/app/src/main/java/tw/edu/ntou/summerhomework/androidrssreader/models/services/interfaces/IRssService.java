package tw.edu.ntou.summerhomework.androidrssreader.models.services.interfaces;

import java.util.Collection;

import tw.edu.ntou.summerhomework.androidrssreader.models.domains.Article;

public interface IRssService {
    Collection<Article> getArticles(String requestUrl) throws Exception;
}
