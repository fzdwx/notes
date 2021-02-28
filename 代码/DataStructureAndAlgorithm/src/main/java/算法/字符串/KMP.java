package 算法.字符串;

/**
 * @author like
 * @date 2021-01-24 15:51
 * @contactMe 980650920@qq.com
 * @description
 */
public class KMP {

    private static int indexOf2(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tLen = textChars.length;
        if (tLen <= 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int pLen = patternChars.length;
        if (pLen <= 0) return -1;

        // next 表
        int[] next = next(pattern);

        int ti = 0, pi = 0, lenDelta = tLen - pLen;
        while (pi < pLen && ti - pi < lenDelta) {
            if (textChars[ti] == patternChars[pi]) {
                ti++;
                pi++;
            } else {
                pi = next[pi];
            }
        }
        return (pi == pLen) ? (ti - pi) : -1;
    }

    private static int[] next(String pattern) {
        char[] chars = pattern.toCharArray();
        int[] next = new int[chars.length];
        next[0] = -1;
        int i = 0;
        int n = -1;
        int iMax = chars.length - 1;
        while (i < iMax) {
            if (n < 0 || chars[i] == chars[n]) {
                next[++i] = ++n;
            } else {
                n = next[n];
            }
        }
        return next;
    }
}
