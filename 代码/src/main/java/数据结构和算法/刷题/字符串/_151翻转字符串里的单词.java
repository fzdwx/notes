package 数据结构和算法.刷题.字符串;

import java.util.ArrayList;

/**
 * @author like
 * @date 2021-01-26 10:20
 * @contactMe 980650920@qq.com
 * @description
 */
public class _151翻转字符串里的单词 {

    public static void main(String[] args) {
        String s = "hello world  dd";
        System.out.println(reverseWord(s));
    }

    public static String reverseWord(String s) {
        if (s == null) return null;
        // 1.消除多余的空格
        char[] carr = s.toCharArray();
        int len = 0; // 保存字符的有效长度
        int cur = 0; // 当前存放字符的位置
        ArrayList<Integer> spaceIndex = new ArrayList<>();
        boolean space = true;// 标记当前遍历到的字符的前一个字符是否空格字符
        for (int i = 0; i < carr.length; i++) { // i:当前遍历到的字符
            if (carr[i] != ' ') {
                carr[cur] = carr[i];
                cur++;
                space = false;
            } else if (!space) { // carr[i]是空格，carr[i-1]是非空格
                carr[cur] = ' ';
                cur++;
                space = true;
            }
        }
        len = space ? (cur - 1) : cur;
        if (len < 0) return "";

        // 2.逆序
        reverse(carr, 0, len);
        int pi = -1;

        // 3.翻转单词
        for (int i = 0; i < len; i++) {
            if (carr[i] != ' ') continue;
            reverse(carr, pi + 1, i);
            pi = i;
        }
        // 4.反转最后一个单词
        reverse(carr, pi + 1, cur);
        return new String(carr, 0, len);
    }

    private static void reverse(char[] carr, int li, int ri) {
        ri--;
        while (li < ri) {
            char temp = carr[li];
            carr[li] = carr[ri];
            carr[ri] = temp;
            li++;
            ri--;
        }
    }
}
