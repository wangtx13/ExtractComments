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

    public static StringBuffer parseAllWords(StringBuffer originalWords) {
        StringBuffer outputWords = new StringBuffer();

        String[] allWords = originalWords.toString().split(" |\\(|\\[|\r\n|\\.");

        for (String word : allWords) {
            if (!word.equals("")) {
                String[] splitWords = splitCamelWords(word);
                if (splitWords.length > 1) {
                    outputWords.append(word);
                    outputWords.append(" ");
                }
                for (String aSplitWord : splitWords) {
                    String parsedWords = removeStopWords(aSplitWord);
                    if (parsedWords != null) {
                        outputWords.append(parsedWords);
                        outputWords.append(" ");
                    }
                }
            }
        }

        return outputWords;
    }

    public static String[] splitCamelWords(String word) {
        if (!word.contains("_")) {
            /*XML、DOM、JHotDraw不分割*/
            word = word.replace("XML", "Xml");
            word = word.replace("DOM", "Dom");
            word = word.replace("JHotDraw", "Jhotdraw");

            /*正则表达式：分隔符为大写字母*/
            String regEx = "[A-Z]";
            Pattern p1 = Pattern.compile(regEx);
            Matcher m1 = p1.matcher(word);

            /*判断首字母是否大写*/
            boolean startWithUpper = false;
            startWithUpper = Pattern.matches("[A-Z].*", word);

            /*按照句子结束符分割句子，并存入list*/
            String[] words = p1.split(word);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                list.add(words[i]);
            }

            /*将句子结束符连接到相应的句子后*/
            int count = 0;
            while (m1.find()) {
                if (count + 1 < words.length) {
                    list.set(count + 1, m1.group() + list.get(count + 1));
                    ++count;
                } else {
                    list.add(m1.group());
                }
            }

            /*首字母大写且所有字符并非全部大写，去掉list中第一个空字符串*/
            if (startWithUpper && words.length != 0) {
                list.remove(0);
            }
            /*拷贝list到一个新数组*/
            String[] result = list.toArray(new String[1]);
            return result;
        } else {
            String[] result = new String[1];
            result[0] = word;
            return result;
        }
    }

    public static String removeStopWords(String word) {
        String stopList = "public class";
        if (stopList.contains(word)) {
            word = null;
        }
        return word;
    }
}
