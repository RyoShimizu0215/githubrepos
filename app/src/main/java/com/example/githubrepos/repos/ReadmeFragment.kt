package com.example.githubrepos.repos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.githubrepos.R

class ReadmeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("SLY", "ReadmeFragment onCreateView")
        super.onCreateView(inflater, container, savedInstanceState)
        container?.removeAllViews()
        val root = inflater.inflate(R.layout.readme_frag, container, false)

        with(root) {
            val mWebView = findViewById<WebView>(R.id.web_view)
            mWebView.setWebViewClient(WebViewClient())
            Log.i("SLY", "ReadmeFragment onCreateView loadUrl() start")
            mWebView.loadUrl(arguments?.getString("URL"))
        }
        return root

    }
}