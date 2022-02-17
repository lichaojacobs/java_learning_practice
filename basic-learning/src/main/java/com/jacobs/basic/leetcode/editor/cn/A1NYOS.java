//给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [0,1]
//输出: 2
//说明: [0, 1] 是具有相同数量 0 和 1 的最长连续子数组。 
//
// 示例 2: 
//
// 
//输入: nums = [0,1,0]
//输出: 2
//说明: [0, 1] (或 [1, 0]) 是具有相同数量 0 和 1 的最长连续子数组。 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// nums[i] 不是 0 就是 1 
// 
//
// 
//
// 注意：本题与主站 525 题相同： https://leetcode-cn.com/problems/contiguous-array/ 
// Related Topics 数组 哈希表 前缀和 
// 👍 34 👎 0


package com.jacobs.basic.leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map;

public class A1NYOS {
    public static void main(String[] args) {
        Solution solution = new A1NYOS().new Solution();
    }

    //由于「00 和 11 的数量相同」等价于「11 的数量减去 00 的数量等于 00」
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int findMaxLength(int[] nums) {
            int maxLength = 0;
            // 哈希表存储的是 counter 的每个取值第一次出现的下标
            Map<Integer, Integer> map = new HashMap<>();
            int counter = 0;
            // 初始化，由于0本身数组长度不成立，则初始value为-1
            map.put(counter, -1);
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 1) {
                    counter++;
                } else {
                    counter--;
                }
                // 从counter 又回到 counter，说明中间一段数组0,1数量相等，即为我们需要的结果
                if (map.containsKey(counter)) {
                    int prevIndex = map.get(counter);
                    maxLength = Math.max(maxLength, i - prevIndex);
                } else {
                    map.put(counter, i);
                }
            }
            return maxLength;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}