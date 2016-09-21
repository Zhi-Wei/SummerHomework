package tw.edu.ntou.summerhomework.androidrssreader.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import tw.edu.ntou.summerhomework.androidrssreader.R;

public class WebViewActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra("link");

        this.webView = (WebView) findViewById(R.id.main_webView);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(url);
        finish();
    }
}
