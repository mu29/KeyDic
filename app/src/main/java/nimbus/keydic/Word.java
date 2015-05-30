package nimbus.keydic;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by InJung on 2015. 5. 30..
 */
public class Word {
    private String mLeft;
    private String mCenter;
    private String mRight;
    private String mExample;

    private static ArrayList<Word> wordList = null;

    public static Word get(int _index) {
        if (wordList == null)
            return null;

        return wordList.get(_index);
    }

    public static Word findWord(String _center) {
        for (Word word : wordList) {
            if (word.mCenter.equals(_center))
                return word;
        }

        return null;
    }

    public static String[] getAllWords() {
        String[] values = new String[wordList.size()];
        for (int i = 0; i < wordList.size(); i++)
            values[i] = wordList.get(i).getCenter();

        return values;
    }

    public static ArrayList<Word> findWords(String _left, String _center, String _right) {
        ArrayList<Word> words = new ArrayList<>();
        for (Word word : wordList) {
            if (!word.mCenter.equals(_center))
                continue;

            if (!_left.equals("") && word.mLeft.equals(_left))
                words.add(word);

            if (!_right.equals("") && word.mRight.equals(_right))
                words.add(word);

            if (_left.equals("") && _right.equals(""))
                words.add(word);
        }

        return words;
    }

    public static void init(final Context _context) {
        wordList = new ArrayList<Word>();
        String[] words = _context.getResources().getStringArray(R.array.words);
        for (int i = 0; i < words.length / 5; i++) {
            Word word = new Word(words[i * 5 + 1], words[i * 5 + 2], words[i * 5 + 3], words[i * 5 + 4]);
            wordList.add(word);
        }
    }

    public Word(String _left, String _center, String _right, String _example) {
        mLeft = _left.equals("blank") ? "" : _left;
        mCenter = _center;
        mRight = _right.equals("blank") ? "" : _right;
        mExample = _example;
    }

    public String getLeft() {
        return mLeft;
    }

    public String getCenter() {
        return mCenter;
    }

    public String getRight() {
        return mRight;
    }

    public String getExample() {
        return mExample;
    }
}
