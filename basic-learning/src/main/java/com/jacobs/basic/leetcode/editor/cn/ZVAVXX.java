//给定一个正整数数组 nums和整数 k ，请找出该数组内乘积小于 k 的连续的子数组的个数。
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [10,5,2,6], k = 100
//输出: 8
//解释: 8 个乘积小于 100 的子数组分别为: [10], [5], [2], [6], [10,5], [5,2], [2,6], [5,2,6]。
//需要注意的是 [10,5,2] 并不是乘积小于100的子数组。
// 
//
// 示例 2: 
//
// 
//输入: nums = [1,2,3], k = 0
//输出: 0 
//
// 
//
// 提示: 
//
// 
// 1 <= nums.length <= 3 * 104 
// 1 <= nums[i] <= 1000 
// 0 <= k <= 106 
// 
//
// 
//
// 注意：本题与主站 713 题相同：https://leetcode-cn.com/problems/subarray-product-less-than-
//k/ 
// Related Topics 数组 滑动窗口 
// 👍 38 👎 0


package com.jacobs.basic.leetcode.editor.cn;

public class ZVAVXX {
    public static void main(String[] args) {
        Solution solution = new ZVAVXX().new Solution();
        System.out.println(solution.numSubarrayProductLessThanK(new int[]{10, 5, 2, 6}, 100));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int numSubarrayProductLessThanK(int[] nums, int k) {
            int result = 0;
            int multiplyResult = 1;
            int start = 0;
            int end = 0;
            while (end < nums.length) {
                multiplyResult *= nums[end];
                while (multiplyResult >= k && start < end) {
                    multiplyResult /= nums[start];
                    start++;
                }
                if (multiplyResult < k) {
                    //直接算得子数组个数
                    result += end - start + 1;
                }
                end++;
            }

            return result;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}