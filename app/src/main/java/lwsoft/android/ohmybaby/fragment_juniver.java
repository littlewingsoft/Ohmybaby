package lwsoft.android.ohmybaby;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gilbert on 2015-04-15.
 */
public class fragment_juniver extends Fragment{

    private WebView mWebView ;
    private WebViewClient mWebViewClient ;
    private WebChromeClient mWebChromeClient ;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_juniver, container, false);
        // Inflate the layout for this fragment
        mWebView = (WebView ) v.findViewById(R.id.webView);
        mWebViewClient = new myWebViewClient();
        mWebView.setWebViewClient(mWebViewClient);

        mWebChromeClient = new myWebChromeClient();
        mWebView.setWebChromeClient(mWebChromeClient);

        setwebsetting(mWebView);
        mWebView.loadUrl("http://m.jr.naver.com/song/index.nhn");
        return v;
    }


    public static void setwebsetting(WebView webView){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
    }


    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            mWebView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                //LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                //mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            mWebView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    class myWebViewClient extends WebViewClient {

        Context context;
        private ArrayList<HashMap<String, String>> data;
        //public Net_HTMLParse net_htmlParse;
        Handler handler_complete = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                Toast.makeText(context, "complete!!!", Toast.LENGTH_LONG);
            }
        };


        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon)
        {

        }


        @Override
        public void onPageFinished(WebView view, String url)
        {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            context = view.getContext();
            return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
        }
        public void onUpdate(){

            /*
            for( int n=0; n< data.size();n++){
                HashMap<String,String> hs = data.get(n);

                Iterator<String> iter = hs.keySet().iterator();
                while(iter.hasNext()) {
                   // String key = iter.next();
                  //  String value = hs.get(key);
                  //  Log.i("tag_ddoba", "key : " + key + ", value : " + value);
                }

                //Log.i("tag_ddoba",  hs. );
            }*/
        }
    }
}
