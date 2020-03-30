package com.example.cheatapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DobanziFragment extends Fragment {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate ( R.layout.fragment_dobanzi, container, false );
        WebView webView= v.findViewById (R.id.webView1);
        webView.getSettings().setJavaScriptEnabled ( true );
        webView.setWebViewClient (new WebViewClient ());
        webView.loadUrl ("https://www.bnr.ro/curs_mobil.aspx");
        return v;
    }
}
