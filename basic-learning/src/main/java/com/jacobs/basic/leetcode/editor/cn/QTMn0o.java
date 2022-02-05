//给定一个整数数组和一个整数 k ，请找到该数组中和为 k 的连续子数组的个数。
//
// 
//
// 示例 1 : 
//
// 
//输入:nums = [1,1,1], k = 2
//输出: 2
//解释: 此题 [1,1] 与 [1,1] 为两种不同的情况
// 
//
// 示例 2 : 
//
// 
//输入:nums = [1,2,3], k = 3
//输出: 2
// 
//
// 
//
// 提示: 
//
// 
// 1 <= nums.length <= 2 * 104 
// -1000 <= nums[i] <= 1000 
// 
// -107 <= k <= 107 
// 
// 
//
// 
//
// 注意：本题与主站 560 题相同： https://leetcode-cn.com/problems/subarray-sum-equals-k/ 
// Related Topics 数组 哈希表 前缀和 
// 👍 34 👎 0


package com.jacobs.basic.leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map;

public class QTMn0o {
    public static void main(String[] args) {
        Solution solution = new QTMn0o().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        // 数组中有正有负，因此不能像滑动窗口那样操作
        public int subarraySum(int[] nums, int k) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            // sum[i,j]=sum[0,j]-sum[0,i];
            // key preSum; value: 出现相同preSum的次数
            Map<Integer, Integer> preSumMap = new HashMap<>();
            int preSum = 0;
            int result = 0;
            // 初始化0
            preSumMap.put(0, 1);
            for (int i = 0; i < nums.length; i++) {
                preSum += nums[i];
                result += preSumMap.getOrDefault(preSum - k, 0);
                // 更新map
                preSumMap.put(preSum, preSumMap.getOrDefault(preSum, 0) + 1);
            }
            return result;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}