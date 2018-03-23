package com.ergizgizer.htmlconverter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

class UIManager {

    private static final String LOG_TAG = UIManager.class.getSimpleName();

    private static final UIManager ourInstance = new UIManager();

    static UIManager getInstance() {
        return ourInstance;
    }

    private UIManager() {
    }

    void updateUI(Context context, View view, Document document) {
        Elements elements = document.body().children();
        FrameLayout root = view.findViewById(R.id.root_container);
        convertElements(context, elements, root);
    }

    private void convertElements(Context context, Elements elements, ViewGroup parent) {
        Element element = elements.first();
        while (element != null) {
            String tagName = element.tagName();
            switch (tagName) {
                case "div":
                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    parent.addView(layout);
                    convertElements(context, element.children(), layout);
                    element = element.nextElementSibling();
                    break;
                case "p":
                    TextView textView = new TextView(context);
                    textView.setText(element.text());
                    String selector = element.cssSelector();
                    applyStyleRules(textView, tagName, selector);
                    parent.addView(textView);
                    element = element.nextElementSibling();
                    break;
                default: element = element.nextElementSibling();

            }
        }
    }

    private Map<String, String> getStyles(String selector) {
        return StyleManager.getInstance().getStyleSheet().get(selector);
    }

    private void applyStyleRules(View view, String tagName, String selector) {
        String key = findSelectorWithTagName(tagName, selector);

        if (key != null) {
            Map<String, String> properties = getStyles(key);
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                switch (entry.getKey()) {
                    case "background":
                    case "background-color":
                        view.setBackgroundColor(Color.parseColor(entry.getValue()));
                        break;
                    case "color":
                        if (view instanceof TextView)
                            ((TextView) view).setTextColor(Color.parseColor(entry.getValue()));
                        break;
                    case "font-size":
                        if (view instanceof TextView)
                            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(entry.getValue()));
                        break;

                }
            }
        }
    }

    private String findSelectorWithTagName(String tagName, String selector) {
        String[] selectors = selector.split(" > ");
        Integer index = null;
        for (int i = 0; i<selectors.length; i++) {
            if (selectors[i].contains(tagName)) {
                index = i;
                break;
            }
        }

        String key = null;
        try {
            if (!tagName.contains(":")) key = selectors[index].split(":")[0];
            else key = selectors[index];
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Catched NullPointerException for key.", e);
        }

        return key;
    }
}
