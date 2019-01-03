package com.mphj.todo.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.mphj.todo.utils.spans.CustomBackgroundSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.widget.AppCompatEditText;


public class RichEditText extends AppCompatEditText implements TextWatcher {


    public static Pattern patternPr3 = Pattern.compile("\\!\\!");
    public static Pattern patternPr2 = Pattern.compile("\\!\\!\\!");
    public static Pattern patternPr1 = Pattern.compile("\\!\\!\\!\\!");
    public static Pattern patternPrs = Pattern.compile("(\\!+)");
    public static Pattern patternTag = Pattern.compile("#(\\w+)");

    public RichEditText(Context context) {
        super(context);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        removeSpans(ForegroundColorSpan.class);
        removeSpans(CustomBackgroundSpan.class);
        String s1 = s.toString();
        Matcher pr1 = patternPr1.matcher(s1);
        Matcher pr2 = patternPr2.matcher(s1);
        Matcher pr3 = patternPr3.matcher(s1);
        Matcher tag = patternTag.matcher(s1);
        if (pr1.find()) {
            getText().setSpan(
                    priority1Span(),
                    pr1.start(),
                    pr1.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (pr2.find()) {
            getText().setSpan(
                    priority2Span(),
                    pr2.start(),
                    pr2.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else if (pr3.find()) {
            getText().setSpan(
                    priority3Span(),
                    pr3.start(),
                    pr3.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        while (tag.find()) {
            getText().setSpan(
                    new CustomBackgroundSpan(Color.parseColor("#297cde"), 10, getTypeface(), getTextSize()),
                    tag.start(),
                    tag.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public int getPriority() {
        Matcher pr = patternPrs.matcher(getText().toString());
        String lastPriority = null;
        while (pr.find()) {
            for (int i = 0; i < pr.groupCount(); i++) {
                lastPriority = pr.group(i);
            }
        }
        return (lastPriority == null) ? -1 : 5 - lastPriority.length();
    }

    public List<String> getTags() {
        Matcher tag = patternTag.matcher(getText().toString());
        List<String> tags = new ArrayList<>();
        while (tag.find()) {
            for (int i = 0; i < tag.groupCount(); i++) {
                tags.add(tag.group(i));
            }
        }
        return tags;
    }

    private void removeSpans(Class<? extends CharacterStyle> type) {
        CharacterStyle[] spans = getText().getSpans(0, getText().length(), type);
        for (CharacterStyle span : spans) {
            getText().removeSpan(span);
        }
    }

    private ForegroundColorSpan priority1Span() {
        return new ForegroundColorSpan(Color.parseColor("#c62828"));
    }

    private ForegroundColorSpan priority2Span() {
        return new ForegroundColorSpan(Color.parseColor("#EF6C00"));
    }

    private ForegroundColorSpan priority3Span() {
        return new ForegroundColorSpan(Color.parseColor("#FDD835"));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
