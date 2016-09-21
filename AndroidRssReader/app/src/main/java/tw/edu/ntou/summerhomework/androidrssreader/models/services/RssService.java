package tw.edu.ntou.summerhomework.androidrssreader.models.services;

import java.util.Collection;

import tw.edu.ntou.summerhomework.androidrssreader.models.domains.Article;
import tw.edu.ntou.summerhomework.androidrssreader.models.repositories.RssRepository;
import tw.edu.ntou.summerhomework.androidrssreader.models.repositories.interfaces.IRssRepository;
import tw.edu.ntou.summerhomework.androidrssreader.models.services.interfaces.IRssService;

public class RssService implements IRssService {
    private IRssRepository rssRepository;

    public RssService() {
        this.rssRepository = new RssRepository();
    }

    @Override
    public Collection<Article> getArticles(String requestUrl) throws Exception {
        Collection<Article> returnValue = null;
        int maxRetries = 3;
        int delayInMilliseconds = 300;
        int numTries = 0;
        boolean succeeded = false;

        while (numTries < maxRetries) {
            try {
                returnValue = this.rssRepository.getArticles(requestUrl);
                succeeded = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                numTries++;
            }

            if (succeeded) {
                return returnValue;
            }

            try {
                Thread.sleep(delayInMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Exception("執行方法失敗。");
    }
}
