//给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,2,3,2]
//输出：3
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1,0,1,0,1,100]
//输出：100
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 3 * 104 
// -231 <= nums[i] <= 231 - 1 
// nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 
// 
//
// 
//
// 进阶：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？ 
//
// 
//
// 注意：本题与主站 137 题相同：https://leetcode-cn.com/problems/single-number-ii/ 
// Related Topics 位运算 数组 
// 👍 40 👎 0


package com.jacobs.basic.leetcode.editor.cn;

public class WGki4K {
    public static void main(String[] args) {
        Solution solution = new WGki4K().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int singleNumber(int[] nums) {
            // 由于每个元素都出现3次，必然每一位都为3的倍数，如果不是则说明目标元素在那个位上为1
            int result = 0;
            for (int i = 0; i < 32; i++) {
                int tmpSum = 0;
                for (int j = 0; j < nums.length; j++) {
                    //得到每一位上的值
                    tmpSum += (nums[j] >> i) & 1;
                }
                if (tmpSum % 3 != 0) {
                    //取或操作
                    result |= 1 << i;
                }
            }

            return result;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}