package com.ergizgizer.htmlconverter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.Pair;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.css.CSSRuleList;


class AssetLoader extends AsyncTaskLoader<Document> {

    private static final String LOG_TAG = AssetLoader.class.getSimpleName();

    private AssetManager mAssetManager;

    public AssetLoader(Context context) {
        super(context);
        mAssetManager = context.getAssets();
    }

    @Override
    public Document loadInBackground() {
        loadAndSaveCss();
        return loadHtml();
    }

    private void loadAndSaveCss() {
        CSSRuleList cssRules = AssetUtils.readCssFromFile(mAssetManager);
        StyleManager.getInstance().saveRules(cssRules);
    }

    private Document loadHtml() {
        String html = AssetUtils.readFromFile(mAssetManager, AssetUtils.HTML_EXTENSION);
        Document document = Jsoup.parse(html);
        return document;
    }


}
