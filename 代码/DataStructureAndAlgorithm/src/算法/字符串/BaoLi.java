package 算法.字符串;

/**
 * @author like
 * @date 2021-01-24 10:34
 * @contactMe 980650920@qq.com
 * @description
 */
public class BaoLi {

    public static void main(String[] args) {
        indexOf("hello world", "or");
    }

    private static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tLen = textChars.length;
        if (tLen <= 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int pLen = patternChars.length;
        if (pLen <= 0) return -1;

        int tiMax = tLen - pLen;  // ti 能到的最大位置
        for (int ti = 0; ti <= tiMax; ti++) {
            int pi = 0;
            for (; pi < pLen; pi++) {
                if (textChars[ti + pi] != patternChars[pi]) break;
            }
            // 退出到这里有2中情况，1、循环完了，找到了。2、
            if (pi == pLen) {
                return ti;
            }
        }
        return -1;
    }

    private static int indexOf2(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tLen = textChars.length;
        if (tLen <= 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int pLen = patternChars.length;
        if (pLen <= 0) return -1;

        int ti = 0, pi = 0, lenDelta = tLen - pLen;
        //        while (pi < pLen && ti < tLen) {
        while (pi < pLen && ti - pi < lenDelta) {
            if (textChars[ti] == patternChars[pi]) {
                ti++;
                pi++;
            } else {
                ti -= pi - 1;
                pi = 0;
            }
        }
        return (pi == pLen) ? (ti - pi) : -1;
    }
}
