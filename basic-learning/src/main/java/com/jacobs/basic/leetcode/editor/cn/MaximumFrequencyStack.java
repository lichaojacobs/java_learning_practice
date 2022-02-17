//设计一个类似堆栈的数据结构，将元素推入堆栈，并从堆栈中弹出出现频率最高的元素。
//
// 实现 FreqStack 类: 
//
// 
// FreqStack() 构造一个空的堆栈。 
// void push(int val) 将一个整数 val 压入栈顶。 
// int pop() 删除并返回堆栈中出现频率最高的元素。
// 
// 如果出现频率最高的元素不只一个，则移除并返回最接近栈顶的元素。 
// 
// 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：
//["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"
//],
//[[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
//输出：[null,null,null,null,null,null,null,5,7,5,4]
//解释：
//FreqStack = new FreqStack();
//freqStack.push (5);//堆栈为 [5]
//freqStack.push (7);//堆栈是 [5,7]
//freqStack.push (5);//堆栈是 [5,7,5]
//freqStack.push (7);//堆栈是 [5,7,5,7]
//freqStack.push (4);//堆栈是 [5,7,5,7,4]
//freqStack.push (5);//堆栈是 [5,7,5,7,4,5]
//freqStack.pop ();//返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,5,7,4]。
//freqStack.pop ();//返回 7 ，因为 5 和 7 出现频率最高，但7最接近顶部。堆栈变成 [5,7,5,4]。
//freqStack.pop ();//返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,4]。
//freqStack.pop ();//返回 4 ，因为 4, 5 和 7 出现频率最高，但 4 是最接近顶部的。堆栈变成 [5,7]。 
//
// 
//
// 提示： 
//
// 
// 0 <= val <= 109 
// push 和 pop 的操作数不大于 2 * 104。 
// 输入保证在调用 pop 之前堆栈中至少有一个元素。 
// 
// Related Topics 栈 设计 哈希表 有序集合 
// 👍 206 👎 0


package com.jacobs.basic.leetcode.editor.cn;

import java.util.HashMap;
import java.util.Stack;

public class MaximumFrequencyStack {
    public static void main(String[] args) {
//        Solution solution = new MaximumFrequencyStack().new Solution();
        byte[] test = new byte[0];
        System.out.println(test);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class FreqStack {
        // 最大频次
        int maxFreq = 0;
        // 记录每个val出现的频次
        HashMap<Integer, Integer> valToFreq = new HashMap<>();
        // 记录每个频次对应的val列表，维护先后顺序
        HashMap<Integer, Stack<Integer>> freqToVals = new HashMap<>();

        public FreqStack() {

        }

        public void push(int val) {
            int freq = valToFreq.getOrDefault(val, 0) + 1;
            valToFreq.put(val, freq);
            freqToVals.putIfAbsent(freq, new Stack<>());
            freqToVals.get(freq).push(val);
            // 更新最大值
            maxFreq = Math.max(freq, maxFreq);
        }

        public int pop() {
            Stack<Integer> valStack = freqToVals.get(maxFreq);
            int popVal = valStack.pop();
            // 更新频率
            valToFreq.put(popVal, valToFreq.get(popVal) - 1);
            // 更新集合
            if (valStack.isEmpty()) {
                // 如果 maxFreq 对应的元素空了
                // 怎么保证,maxFreq-1能取到？
                // 因为既然存在maxFreq，那pop出来不就存在maxFreq-1了？
                maxFreq--;
            }
            return popVal;
        }
    }

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
//leetcode submit region end(Prohibit modification and deletion)

}