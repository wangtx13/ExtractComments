/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractcomments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author apple
 */
public class ParseWords {

    public static StringBuffer splitAllCamelWords(StringBuffer originalWords) {
        StringBuffer outputWords = new StringBuffer();

        String[] allWords = originalWords.toString().split(" |\\(|\\[|\r\n");

        for (String word : allWords) {
            if (!word.equals("")) {
                String[] splitWords = splitCamelWords(word);
                if (splitWords.length > 1) {
                    outputWords.append(word);
                    outputWords.append(" ");
                }
                for (String aSplitWord : splitWords) {
                    outputWords.append(aSplitWord);
                    outputWords.append(" ");
                }
            }
        }

        return outputWords;
    }

    public static String[] splitCamelWords(String word) {
        String regEx = "[A-Z]";
        Pattern p1 = Pattern.compile(regEx);
        Matcher m1 = p1.matcher(word);

        /*判断首字母是否大写*/
        boolean startWithUpper = false;
        startWithUpper = Pattern.matches("[A-Z].*", word);

        /*按照句子结束符分割句子*/
        String[] words = p1.split(word);

        /*将句子结束符连接到相应的句子后*/
        if (words.length > 0) {
            int count = 0;
            while (count < words.length) {
                if (m1.find()) {
                    words[count + 1] = m1.group() + words[count + 1];
                }
                count++;
            }
        }

        /*去掉数字第一个空元素*/
        if (startWithUpper) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                list.add(words[i]);
            }
            list.remove(0);
            words = list.toArray(new String[1]);
        }

        return words;
    }
}
