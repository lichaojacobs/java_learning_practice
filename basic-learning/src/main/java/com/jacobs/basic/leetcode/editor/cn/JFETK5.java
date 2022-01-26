//给定两个 01 字符串 a 和 b ，请计算它们的和，并以二进制字符串的形式输出。
//
// 输入为 非空 字符串且只包含数字 1 和 0。 
//
// 
//
// 示例 1: 
//
// 
//输入: a = "11", b = "10"
//输出: "101" 
//
// 示例 2: 
//
// 
//输入: a = "1010", b = "1011"
//输出: "10101" 
//
// 
//
// 提示： 
//
// 
// 每个字符串仅由字符 '0' 或 '1' 组成。 
// 1 <= a.length, b.length <= 10^4 
// 字符串如果不是 "0" ，就都不含前导零。 
// 
//
// 
//
// 注意：本题与主站 67 题相同：https://leetcode-cn.com/problems/add-binary/ 
// Related Topics 位运算 数学 字符串 模拟 
// 👍 14 👎 0


package com.jacobs.basic.leetcode.editor.cn;

public class JFETK5 {
    public static void main(String[] args) {
        Solution solution = new JFETK5().new Solution();
        System.out.println(solution.addBinary("1010", "1011"));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String addBinary(String a, String b) {
            if (a.equals("0")) {
                return b;
            }
            if (b.equals("0")) {
                return a;
            }
            char[] aChars = a.toCharArray();
            char[] bChars = b.toCharArray();

            int aIndex = aChars.length - 1;
            int bIndex = bChars.length - 1;
            StringBuilder result = new StringBuilder();
            int tmpResult = 0;
            while (aIndex >= 0 || bIndex >= 0) {
                int tmpA = 0;
                if (aIndex >= 0) {
                    tmpA = aChars[aIndex--] - '0';
                }
                int tmpB = 0;
                if (bIndex >= 0) {
                    tmpB = bChars[bIndex--] - '0';
                }
                tmpResult = tmpResult + tmpA + tmpB;
                result.append(tmpResult % 2 == 1 ? '1' : '0');
                tmpResult = tmpResult / 2;
            }
            if (tmpResult == 1) {
                result.append("1");
            }

            return result.reverse().toString();
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}