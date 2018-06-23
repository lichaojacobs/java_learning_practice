package com.jacobs.basic.algorithm.string;

/**
 * @author lichao
 * @date 2018/06/23
 */
public class KMP {

    public static void main(String[] args) {
        KMP kmp = new KMP();
        System.out.println(kmp.indexKMP("abcdefgab", "aaaaaaaab"));
    }

    /**
     * j值多少，取决于当前字符之前的串的前后缀的相似度
     *
     * aaaaaaaab -> 012345678
     *
     * ababaaaba -> 011234223
     */
    public int[] getNext(String pattern) {
        char[] patternChars = pattern.toCharArray();
        int[] nextArr = new int[patternChars.length];

        nextArr[0] = -1;
        int i = 1;
        int j = -1;
        //不需要判读最后一个字符，是它的前缀
        while (i < patternChars.length - 1) {
            if (j == -1 || patternChars[i] == patternChars[j]) {
                i++;
                j++;
                nextArr[i] = j;
            } else {
                //前面的累计的作废，重新计算前缀的相似度
                j = nextArr[j];
            }
        }

        return nextArr;
    }

    /**
     * 字符串匹配，返回在主串里的位置
     */
    public int indexKMP(String s, String pattern) {
        if (s == null || "".equals(s) || pattern == null || pattern.equals("")) {
            return -1;
        }

        int[] nextArr = getNext(pattern);
        int i = 0;//主串下标值
        int j = 0;//匹配串下标值

        while (i < s.length() && j < nextArr.length) {
            //判断了回退到-1的情况，防止数组越界
            if (j == -1 || (s.charAt(i) == pattern.charAt(j))) {
                i++;
                j++;
            } else {
                j = nextArr[j];//退回合适的位置
            }
        }

        if (j == nextArr.length) {
            //说明全部匹配了
            return i - nextArr.length;
        } else {
            return 0;
        }
    }
}
