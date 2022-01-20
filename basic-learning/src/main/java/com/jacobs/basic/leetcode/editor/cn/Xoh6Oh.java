//给定两个整数 a 和 b ，求它们的除法的商 a/b ，要求不得使用乘号 '*'、除号 '/' 以及求余符号 '%' 。
//
// 
//
// 注意： 
//
// 
// 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2 
// 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231, 231−1]。本题中，如果除法结果溢出，则返回 231 − 1 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：a = 15, b = 2
//输出：7
//解释：15/2 = truncate(7.5) = 7
// 
//
// 示例 2： 
//
// 
//输入：a = 7, b = -3
//输出：-2
//解释：7/-3 = truncate(-2.33333..) = -2 
//
// 示例 3： 
//
// 
//输入：a = 0, b = 1
//输出：0 
//
// 示例 4： 
//
// 
//输入：a = 1, b = 1
//输出：1 
//
// 
//
// 提示: 
//
// 
// -231 <= a, b <= 231 - 1 
// b != 0 
// 
//
// 
//
// 注意：本题与主站 29 题相同：https://leetcode-cn.com/problems/divide-two-integers/ 
//
// 
// Related Topics 位运算 数学 
// 👍 69 👎 0


package com.jacobs.basic.leetcode.editor.cn;

public class Xoh6Oh {
    public static void main(String[] args) {
        Solution solution = new Xoh6Oh().new Solution();
        System.out.println(solution.divide(-2147483648, 2));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int divide(int a, int b) {
            // 边界条件，防止被溢出
            if (a == Integer.MIN_VALUE && b == -1) {
                return Integer.MAX_VALUE;
            }
            // 为了防止计算溢出，统一转换成负数进行计算
            // 对于 32 位 int 来讲，最大的正数为 2^31-1,最小的负数为 -2^31,如果将负数转化为正数会溢出，所以可以将正数都转化为负数计算
            int positiveFlag = 2;
            if (a > 0) {
                positiveFlag--;
                a = -a;
            }
            if (b > 0) {
                positiveFlag--;
                b = -b;
            }
            return positiveFlag == 1 ? -divideInternal(a, b) : divideInternal(a, b);
        }

        private int divideInternal(int a, int b) {
            int divideResult = 0;
            // 运用减法，以除数指数形式递增
            while (a <= b) {
                // 进入循环则至少为1
                int tmpDivideResult = 1;
                int tmp_result = b;
                // tmp_result >= Integer.MIN_VALUE >> 1：
                // 必须保证tmp_result不会被溢出，如果会溢出则需要拆分成余数继续重复计算
                while (tmp_result >= Integer.MIN_VALUE >> 1 && a <= tmp_result << 1) {
                    tmp_result += tmp_result;
                    // 指数
                    tmpDivideResult += tmpDivideResult;
                }
                // 一轮之后调整余数
                a = a - tmp_result;
                divideResult += tmpDivideResult;
            }

            return divideResult;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}