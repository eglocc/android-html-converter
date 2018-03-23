package com.ergizgizer.htmlconverter;

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;

import java.util.HashMap;
import java.util.Map;

class StyleManager {

    private static final StyleManager ourInstance = new StyleManager();

    static StyleManager getInstance() {
        return ourInstance;
    }

    private StyleManager() {
    }

    private Map<String, Map<String, String>> mStyleSheet = new HashMap<>();

    Map<String, Map<String, String>> getStyleSheet() {
        return mStyleSheet;
    }

    void saveRules(CSSRuleList rules) {
        for (int i=0; i<rules.getLength(); i++) {
            final CSSRule rule = rules.item(i);
            if (rule instanceof CSSStyleRule) {
                final CSSStyleRule styleRule = (CSSStyleRule) rule;
                saveRule(styleRule);
            }
        }
    }

    private void saveRule(CSSStyleRule rule) {
        final String selector = rule.getSelectorText();
        final CSSStyleDeclaration styleDeclaration = rule.getStyle();
        Map<String, String> properties = new HashMap<>();
        for (int i=0; i<styleDeclaration.getLength(); i++) {
            final String propertyName = styleDeclaration.item(i);
            final String propertyValue = styleDeclaration.getPropertyValue(propertyName);
            properties.put(propertyName, propertyValue);
        }
        mStyleSheet.put(selector, properties);
    }
}
