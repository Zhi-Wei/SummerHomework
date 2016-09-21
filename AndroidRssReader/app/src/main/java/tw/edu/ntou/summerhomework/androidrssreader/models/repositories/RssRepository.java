package tw.edu.ntou.summerhomework.androidrssreader.models.repositories;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import tw.edu.ntou.summerhomework.androidrssreader.models.domains.Article;
import tw.edu.ntou.summerhomework.androidrssreader.models.repositories.interfaces.IRssRepository;

public class RssRepository implements IRssRepository {
    private static String resourceUrl;
    private static Type articlesType;

    public RssRepository() {
        resourceUrl = "http://10.0.2.2:8080/RssReaderWebApi/RssReader?url=";
        articlesType = new TypeToken<Collection<Article>>() {
        }.getType();
    }

    @Override
    public Collection<Article> getArticles(String requestUrl) throws IOException {
        Collection<Article> articles = new ArrayList<>();
        HttpURLConnection urlConnection = getHttpURLConnection(requestUrl);
        byte[] data = getData(urlConnection.getInputStream());
        String jsonString = getJsonString(data, urlConnection.getContentEncoding());
        articles = this.convertToArticles(jsonString);
        return articles;
    }

    private HttpURLConnection getHttpURLConnection(String requestUrl) throws IOException {
        URL url = new URL(resourceUrl + requestUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        return urlConnection;
    }

    private byte[] getData(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }

    private String getJsonString(byte[] data, String encoding) throws UnsupportedEncodingException {
        encoding = encoding == null ? "UTF-8" : encoding;
        return new String(data, encoding);
    }

    private Collection<Article> convertToArticles(String jsonString) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")
                .disableHtmlEscaping()
                .serializeNulls()
                .create();
        return gson.fromJson(jsonString, articlesType);
    }
}
