package com.example.patienttrackingfirebaserealtimedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Support extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://drive.google.com/file/d/10alGef6DyCE97vGBh2diz-QNrBrr8mRG/view?usp=drivesdk ");


        //webView.asse
      //  webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);  }
        }
}