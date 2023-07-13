package utils;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: shaco
 * Date: 2022/7/22
 * Desc: IK分词器工具
 */
public class SplitWorldUtil {
    public static List<String> analyse(String text) {
        ArrayList<String> resStr = new ArrayList<>();

        StringReader reader = new StringReader(text);

        IKSegmenter ikSegmenter = new IKSegmenter(reader, true);
        try {
            Lexeme lexeme = null;
            while ((lexeme = ikSegmenter.next()) != null) {
                String keyword = lexeme.getLexemeText();
                resStr.add(keyword);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resStr;
    }

}
