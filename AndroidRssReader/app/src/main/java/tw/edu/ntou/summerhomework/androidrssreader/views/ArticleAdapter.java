package tw.edu.ntou.summerhomework.androidrssreader.views;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tw.edu.ntou.summerhomework.androidrssreader.R;
import tw.edu.ntou.summerhomework.androidrssreader.models.domains.Article;

public class ArticleAdapter extends ArrayAdapter<Article> {
    private Context context;
    private int resource;
    private List<Article> articles;

    public ArticleAdapter(Context context, int resource, List<Article> articles) {
        super(context, resource, articles);
        this.context = context;
        this.resource = resource;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout articleView;
        final Article article = getItem(position);

        if (convertView == null) {
            articleView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater =
                    (LayoutInflater) getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, articleView, true);
        } else {
            articleView = (LinearLayout) convertView;
        }

        TextView titleView = (TextView) articleView.findViewById(R.id.title_text);
        TextView linkView = (TextView) articleView.findViewById(R.id.link_text);
        TextView pubDateView = (TextView) articleView.findViewById(R.id.pubDate_text);
        TextView descriptionView = (TextView) articleView.findViewById(R.id.description_text);
        TextView categoryView = (TextView) articleView.findViewById(R.id.category_text);

        Resources resources = this.context.getResources();
        titleView.setText(resources.getString(R.string.title_text) + article.getTitle());
        linkView.setText(resources.getString(R.string.link_text) + article.getLink().toString());
        pubDateView.setText(resources.getString(R.string.pubDate_text) + article.getLocaleDatetime());
        descriptionView.setText(resources.getString(R.string.description_text) + article.getDescription());
        categoryView.setText(resources.getString(R.string.category_text) + article.getCategory());

        return articleView;
    }
}
