package com.mphj.todo.utils;

import java.util.ArrayList;
import java.util.List;

public class TimeExtractor {

    private static final long MIN = 60 * 1000;
    public static List<KnownWord> knownWords = new ArrayList<>();

    static {
        knownWords.add(new KnownWord(new String[]{"فردا"}, 24 * 60 * MIN));
        knownWords.add(new KnownWord(new String[]{"ربع"}, 15 * MIN));
        knownWords.add(new KnownWord(new String[]{"نیم"}, 30 * MIN));
        knownWords.add(new KnownWord(new String[]{"یه ساعت", "1 ساعت", "یک ساعت"}, 60 * MIN));
        knownWords.add(new KnownWord(new String[]{"دو ساعت", "2 ساعت", "۲ ساعت"}, 2 * 60 * MIN));
    }

    public static long getTimeOffset(String sentence) {
        for (KnownWord knownWord : knownWords) {
            for (String tag : knownWord.words) {
                if (sentence.contains(tag))
                    return knownWord.offset;
            }
        }
        return -1;
    }

    public static class KnownWord {
        public String[] words;
        public long offset;

        public KnownWord(String[] words, long offset) {
            this.offset = offset;
            this.words = words;
        }
    }

}
