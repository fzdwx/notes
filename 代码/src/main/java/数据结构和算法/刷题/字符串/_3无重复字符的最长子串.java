package 数据结构和算法.刷题.字符串;

/**
 * @author like
 * @date 2021-01-26 11:05
 * @contactMe 980650920@qq.com
 * @description
 */
public class _3无重复字符的最长子串 {

    public int lengthOfLongestSubstring(String s) {
        if (s == null) { return 0;}
        char[] carr = s.toCharArray();
        if (carr.length == 0) return 0;

        // 1.保存每個字符上一此出現的位置
        int[] prevIdx = new int[128];
        for (int i = 0; i < prevIdx.length; i++) {
            prevIdx[i] = -1;
        }
        prevIdx[carr[0]] = 0;
        int pi; // i位置字符上次出現的位置
        int li = 0; // 以i-1位置字符結尾的最長不重複字串的開始索引
        int max = 1;
        for (int i = 0; i < carr.length; i++) {
            pi = prevIdx[carr[i]];
            if (li <= pi) {
                li = pi + 1;
            }
            // 存储这个字符出现的位置
            prevIdx[carr[i]] = i;
            // 求出最长不重复子串的长度
            max = Math.max(max, i - li + 1);
        }
        return max;
    }
}
