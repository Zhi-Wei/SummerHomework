package tw.edu.ntou.summerhomework.androidrssreader.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tw.edu.ntou.summerhomework.androidrssreader.R;
import tw.edu.ntou.summerhomework.androidrssreader.common.StringHelper;
import tw.edu.ntou.summerhomework.androidrssreader.models.domains.Article;
import tw.edu.ntou.summerhomework.androidrssreader.models.services.RssService;
import tw.edu.ntou.summerhomework.androidrssreader.models.services.interfaces.IRssService;

public class MainActivity extends AppCompatActivity {

    private final Context context = MainActivity.this;
    private final IRssService rssService = new RssService();
    private EditText editText_view;
    private Button button_view;
    private ListView articles_view;
    private List<Article> mArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.processViews();
        this.processControllers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 註冊下拉式選單項目的 Click 事件。
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String requestUrl = "";
        int id = item.getItemId();
        switch (id) {
            case R.id.rss_url_mobile01:
                requestUrl = "http://www.mobile01.com/rss/hottopics.xml";
                break;
            case R.id.rss_url_businessWeekly:
                requestUrl = "http://bw.businessweekly.com.tw/feedsec.php?feedid=0";
                break;
            case R.id.rss_url_taipeiTravel:
                requestUrl = "http://www.travel.taipei/frontsite/tw/rssAction.do?method=doViewContent&menuId=2010101";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        this.editText_view.setText(requestUrl);
        return true;
    }

    private void processViews() {
        this.editText_view = (EditText) this.findViewById(R.id.editText);
        this.button_view = (Button) this.findViewById(R.id.button);
        this.articles_view = (ListView) this.findViewById(R.id.article_list);
    }

    private void processControllers() {
        // 註冊確定按鈕的 Click 事件。
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestUrl = editText_view.getText().toString().trim();
                if (StringHelper.isNullOrWhiteSpace(requestUrl)) {
                    Toast.makeText(context, R.string.required_url, Toast.LENGTH_LONG)
                            .show();
                } else {
                    // dismiss virtual keyboard.
                    dismissVirtualKeyboard(v);

                    // 取得 RSS 資料。
                    new DownloadArticlesTask().execute(requestUrl);
                }
            }
        };
        this.button_view.setOnClickListener(buttonListener);

        // 註冊 RSS 網址列的事件。
        TextView.OnEditorActionListener actionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // dismiss virtual keyboard.
                    dismissVirtualKeyboard(v);

                    // simulate button clicked
                    button_view.performClick();
                }
                return false;
            }
        };
        this.editText_view.setOnEditorActionListener(actionListener);

        // 註冊清單列表的 Click 事件。
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // dismiss virtual keyboard.
                dismissVirtualKeyboard(view);

                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("link", mArticles.get(position).getLink().toString());
                startActivity(intent);
            }
        };
        this.articles_view.setOnItemClickListener(itemListener);
    }

    private void dismissVirtualKeyboard(View v) {
        InputMethodManager inm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private class DownloadArticlesTask extends AsyncTask<String, Void, List<Article>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Resources resources = context.getResources();
            progressDialog = ProgressDialog.show(
                    context,
                    resources.getString(R.string.info),
                    resources.getString(R.string.loading_please_wait));
        }

        @Override
        protected List<Article> doInBackground(String... requestUrls) {
            List<Article> articles = new ArrayList<>();
            try {
                articles.addAll(rssService.getArticles(requestUrls[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return articles;
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            super.onPostExecute(articles);

            if (articles.isEmpty()) {
                Toast.makeText(context, R.string.error_message, Toast.LENGTH_LONG).show();
            }

            mArticles = articles;
            ArticleAdapter articleAdapter =
                    new ArticleAdapter(context, R.layout.single_article, articles);
            articles_view.setAdapter(articleAdapter);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

}