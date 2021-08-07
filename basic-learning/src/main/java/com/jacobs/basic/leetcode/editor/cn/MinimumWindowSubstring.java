//给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
//
// 
//
// 注意： 
//
// 
// 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。 
// 如果 s 中存在这样的子串，我们保证它是唯一的答案。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "ADOBECODEBANC", t = "ABC"
//输出："BANC"
// 
//
// 示例 2： 
//
// 
//输入：s = "a", t = "a"
//输出："a"
// 
//
// 示例 3: 
//
// 
//输入: s = "a", t = "aa"
//输出: ""
//解释: t 中两个字符 'a' 均应包含在 s 的子串中，
//因此没有符合条件的子字符串，返回空字符串。 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length, t.length <= 105 
// s 和 t 由英文字母组成 
// 
//
// 
//进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？ Related Topics 哈希表 字符串 滑动窗口 
// 👍 1269 👎 0


package com.jacobs.basic.leetcode.editor.cn;

public class MinimumWindowSubstring {
    public static void main(String[] args) {
        Solution solution = new MinimumWindowSubstring().new Solution();
        System.out.println(solution.minWindow("ADOBECODEBANC", "ABC"));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String minWindow(String s, String t) {
            if (s == null || s.length() == 0 || t == null || t.length() == 0) {
                return "";
            }
            int[] rawCharMap = new int[256];
            int[] repCharMap = new int[256];
            char[] rawChars = s.toCharArray();
            char[] repChars = t.toCharArray();
            int repLen = repChars.length;
            for (int i = 0; i < repChars.length; i++) {
                repCharMap[repChars[i]] += 1;
            }
            int minLeft = 0;
            int minRight = 0;
            int minLen = Integer.MAX_VALUE;
            int left = 0;
            int right = 0;
            int matchedLen = 0;
            while (left <= right && right < rawChars.length) {
                char tmpChar = rawChars[right];
                if (repCharMap[tmpChar] <= 0) {
                    //刚开始left也可以跟着平移，在没找到第一个matched之前
                    if (repCharMap[rawChars[left]] <= 0) {
                        left++;
                    }
                    right++;
                    continue;
                }
                if (repCharMap[tmpChar] > 0) {
                    if (rawCharMap[tmpChar] < repCharMap[tmpChar]) {
                        matchedLen++;
                    }
                    rawCharMap[tmpChar] += 1;
                }
                // 如果还没全部match上的话，则right继续往右扩
                if (matchedLen < repLen) {
                    right++;
                    continue;
                }
                while (left < right) {
                    tmpChar = rawChars[left];
                    // 中间可能有根本不属于的case，需要跳过
                    if (rawCharMap[tmpChar] <= 0) {
                        left++;
                    } else if (rawCharMap[tmpChar] > repCharMap[tmpChar] && matchedLen >= repLen) {
                        left++;
                        rawCharMap[tmpChar] -= 1;
                    } else {
                        // 如果刚好在左边界没有多余的，则停止left缩小
                        break;
                    }
                }
                if (right - left < minLen && matchedLen >= repLen) {
                    minLen = right - left;
                    minLeft = left;
                    minRight = right;
                }
                right++;
            }
            if (matchedLen < repLen) {
                return "";
            }
            return s.substring(minLeft, minRight + 1);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)