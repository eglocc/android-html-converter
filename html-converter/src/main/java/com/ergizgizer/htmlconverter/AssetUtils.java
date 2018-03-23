package com.ergizgizer.htmlconverter;

import android.content.res.AssetManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.steadystate.css.parser.CSSOMParser;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

public class AssetUtils {

    private static final String LOG_TAG = AssetUtils.class.getSimpleName();

    private static final String FILE_NAME = "index";
    static final String HTML_EXTENSION = "html";
    private static final String CSS_EXTENSION =  "css";

    @Nullable
    static String readFromFile(AssetManager assetManager, String fileExtension) {
        String fileName = FILE_NAME + "." + fileExtension;
        try {
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception while reading from the file " + fileName);
        }

        return null;
    }

    @Nullable
    static CSSRuleList readCssFromFile(AssetManager assetManager) {
        String css = AssetUtils.readFromFile(assetManager, CSS_EXTENSION);
        InputSource source = new InputSource(new StringReader(css));
        CSSOMParser parser = new CSSOMParser();
        try {
            CSSStyleSheet sheet = parser.parseStyleSheet(source);
            return sheet.getCssRules();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception while reading css from the file index." + CSS_EXTENSION);
        }
        return null;
    }
}
