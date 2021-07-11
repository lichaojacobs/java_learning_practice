//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
//
// 
//
// 示例 1: 
//
// 
//输入: s = "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
//
// 示例 4: 
//
// 
//输入: s = ""
//输出: 0
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 5 * 104 
// s 由英文字母、数字、符号和空格组成 
// 
// Related Topics 哈希表 字符串 滑动窗口 
// 👍 5680 👎 0


package com.jacobs.basic.leetcode.editor.cn;

public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        Solution solution = new LongestSubstringWithoutRepeatingCharacters().new Solution();
        System.out.println(solution.lengthOfLongestSubstring(""));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int[] charMap = new int[256];

        public int lengthOfLongestSubstring(String s) {
            if (s == null || s.equals("")) {
                return 0;
            }
            // 记录最长无重复字串长度
            int maxLength = 0;
            int startI = 0;
            int endJ = 0;
            char[] charArr = s.toCharArray();
            int charLen = charArr.length;
            while (endJ < charLen) {
                int index = charArr[endJ];
                if (charMap[index] != 0) {
                    while (startI < endJ) {
                        int backIndex = charArr[startI];
                        //回退保存的记录
                        charMap[backIndex] = 0;
                        if (charArr[endJ] == charArr[startI++]) {
                            break;
                        }
                    }
                }
                // 更新一下maxLength
                maxLength = Math.max(endJ - startI + 1, maxLength);
                // 最后标记为出现过
                charMap[index] = 1;
                endJ++;
            }

            return maxLength;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}