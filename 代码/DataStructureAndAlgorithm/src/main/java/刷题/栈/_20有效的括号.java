package 刷题.栈;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author like
 * @date 2020-12-13 13:49
 * @contactMe 980650920@qq.com
 * @description https://leetcode-cn.com/problems/valid-parentheses/
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']'的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 */
public class _20有效的括号 {

    public static void main(String[] args) {
        String s = "([])";
        //        System.out.println(isValid(s));
        System.out.println(isValidUseMap(s));
    }

    public static boolean isValidUseMap(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>(3);
        map.put('(', ')');
        map.put('[', '}');
        map.put('{', '}');

        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                stack.push(c);
                if (i == s.length() - 1) flag = false;

            } else {
                if (stack.isEmpty()) return false;
                Character key = stack.pop();
                // 这时候c是value
                flag = c == map.get(key);
            }
        }
        return flag;
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        int length = s.length();
        for (int i = 0; i < length; i++) {

            char string = s.charAt(i);
            if ('(' == string || '[' == string || '{' == string) {
                stack.push(string);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char left = stack.pop();
                if ('(' == (left) && ')' != string) {
                    return false;
                }
                if ('[' == (left) && ']' != (string)) {
                    return false;
                }
                if ('{' == (left) && '}' != (string)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();

    }
}
