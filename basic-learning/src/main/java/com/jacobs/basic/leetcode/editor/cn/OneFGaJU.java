//给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a ，b ，c ，使得 a + b + c = 0 ？请找出所有和为 0 且
//不重复 的三元组。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [-1,0,1,2,-1,-4]
//输出：[[-1,-1,2],[-1,0,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：nums = [0]
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 3000 
// -105 <= nums[i] <= 105 
// 
//
// 
//
// 注意：本题与主站 15 题相同：https://leetcode-cn.com/problems/3sum/ 
// Related Topics 数组 双指针 排序 
// 👍 35 👎 0


package com.jacobs.basic.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OneFGaJU {
    public static void main(String[] args) {
        Solution solution = new OneFGaJU().new Solution();
        solution.threeSum(new int[]{0, 0, 0});
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> resultList = new ArrayList<>();
            if (nums == null || nums.length == 0) {
                return resultList;
            }

            // 核心思想：固定一个数，再用双指针的方式
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                //跳过重复的记录
                if (i > 0 && nums[i] == nums[i - 1]) continue;
                int start = i + 1;
                int end = nums.length - 1;
                while (start < end) {
                    int sum = nums[i] + nums[start] + nums[end];
                    if (sum > 0) {
                        end--;
                    } else if (sum < 0) {
                        start++;
                    } else {
                        resultList.add(Arrays.asList(nums[i], nums[start], nums[end]));
                        while (start < end && nums[start] == nums[start + 1]) start++;
                        start++;
                        end--;
                    }
                }
            }

            return resultList;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}