package com.jacobs.basic.algorithm.leetcode.hard;

/**
 * @author lichao
 * Created on 2019-06-01
 */
public class ProblemsHard_01 {

    /**
     * 10. 正则表达式匹配
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配
     *
     *'.' 匹配任意单个字符
     *'*' 匹配零个或多个前面的那一个元素
     *所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串
     *link: https://leetcode-cn.com/problems/regular-expression-matching/description/
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        int m = s.length();
        int n = p.length();
        // dp[i][j]为s前i个字符s[0..i-1]和p前j个字符p[0..j-1]能否匹配
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // 空串与空正则表达式匹配
                if (i == 0 && j == 0) {
                    dp[i][j] = true;
                    continue;
                }
                // 空的正则表达式不能匹配长度>0的串
                if (j == 0) {
                    dp[i][j] = false;
                    continue;
                }
                if (p.charAt(j - 1) != '*') {
                    // p[j-1]为正常字符（非.非*），若s[i-1]==p[j-1]，
                    // p[j-1]为'.',则s[i-1]一定与'.'匹配
                    // 以上两种情况能否匹配取决于s[0..i-2]和p[0..j-2]能否匹配
                    if (i > 0 && (p.charAt(j - 1) == '.' || s.charAt(i - 1) == p.charAt(j - 1))) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                } else {
                    // p[j-1]为'*',代表p[j-2]=c可以重复0次或多次，他们是一个整体c*
                    // s[i-1]是0个c，能否匹配取决于s[0..i-1]和p[0..j-3]能否匹配
                    if (j - 2 >= 0) {
                        dp[i][j] |= dp[i][j - 2];
                    }
                    // s[i-1]是多个c中的最后一个，能否匹配取决于s[0..i-2]和p[0..j-1]能否匹配
                    if (i >= 1 && j >= 2) {
                        dp[i][j] |= dp[i - 1][j] && (p.charAt(j - 2) == '.' || p.charAt(j - 2) == s.charAt(i - 1));
                    }
                }
            }
        }
        return dp[m][n];
    }
}
