package cloud.chat.common;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gx
 */
@Slf4j
public class ChineseCharacterHelper {

    private final static String ENGLISH = "abcdefghijklmnopqurstuvwxyzABCDEFGHIJKLMNOPQURSTUVWXYZ1234567890";

    /**
     * 提取汉字字符串的首字母
     *
     * @param characters 汉字字符串
     * @return
     */
    public static String getSpells(String characters) {
        try {
            return PinyinHelper.getShortPinyin(characters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 提取汉字字符串的拼音
     *
     * @param characters 汉字字符串
     * @return
     */
    public static String getAllSpells(String characters) {
        try {
            return PinyinHelper.convertToPinyinString(characters,"",PinyinFormat.WITHOUT_TONE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }


    /**
     * 转换为拼音的字符串的数字
     *
     * @param characters 汉字字符串可能包含英文
     * @return
     */
    public static List<String> getAllSpellsList(String characters) {
        StringBuffer spellsSb = new StringBuffer();
        StringBuffer stringBuffer = new StringBuffer();
        char[] chars = characters.toCharArray();
        for (char c:chars) {
            if (ENGLISH.contains(String.valueOf(c))){
                stringBuffer.append(c);
                spellsSb.append(c);
            }else {
                stringBuffer.append(getAllSpells(String.valueOf(c)));
                spellsSb.append(getSpells(String.valueOf(c)));
            }
        }
        List<String> result = new ArrayList<>();
        result.add(stringBuffer.toString());
        result.add(spellsSb.toString());
        return result;
    }

    public static void main(String[] args) {
        String ss="ds你sds他213";
        List<String> strings = getAllSpellsList(ss);
        for (String str:strings) {
            System.out.printf(str);
        }
    }
}


