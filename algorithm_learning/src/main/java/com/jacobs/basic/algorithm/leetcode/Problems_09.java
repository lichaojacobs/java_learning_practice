package com.jacobs.basic.algorithm.leetcode;

/**
 * @author lichao
 * @date 2018/06/19
 */
public class Problems_09 {

    public static void main(String[] args) {
        System.out.println(compareVersion("0.1", "1.1"));
    }

    //    Compare two version numbers version1 and version2.
    //    If version1 > version2 return 1; if version1 < version2 return -1;otherwise return 0.
    //
    //    You may assume that the version strings are non-empty and contain only digits and the . character.
    //    The . character does not represent a decimal point and is used to separate number sequences.
    //    For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.
    //
    //    Example 1:
    //
    //    Input: version1 = "0.1", version2 = "1.1"
    //    Output: -1
    //    Example 2:
    //
    //    Input: version1 = "1.0.1", version2 = "1"
    //    Output: 1
    //    Example 3:
    //
    //    Input: version1 = "7.5.2.4", version2 = "7.5.3"
    //    Output: -1
    public static int compareVersion(String version1, String version2) {
        String[] v1Strs = version1.split("\\.");
        String[] v2Strs = version2.split("\\.");

        int v1Length = v1Strs.length;
        int v2Length = v2Strs.length;
        int result = 0;
        int index = 0;

        while (index < v1Length && index < v2Length) {
            int minusResult = Integer.valueOf(v1Strs[index]) - Integer.valueOf(v2Strs[index]);
            if (minusResult > 0) {
                result = 1;
            } else if (minusResult < 0) {
                result = -1;
            } else {
                result = 0;
            }
            if (result != 0) {
                return result;
            }

            index++;
        }

        while (result == 0 && index < v1Length) {
            if (Integer.valueOf(v1Strs[index++]) > 0) {
                result = 1;
                break;
            }
        }

        while (result == 0 && index < v2Length) {
            if (Integer.valueOf(v2Strs[index++]) > 0) {
                result = -1;
                break;
            }
        }

        return result;
    }
}
