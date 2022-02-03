### 解题思路
可以用一个 int 型整数记录某个字符串中出现的字符。如果字符串包含 'a'，那么整数最右边的数位为 1，如果字符串包含 'b'，那么整数从右边起倒数第 2 位为 1。这样做的好处就是能更快地判定两个字符串是否包含相同的字符。如果两个字符串包含相同的字符，那么两个整数的与运算将不等于 0。反之，如果两个字符串不包含相同的字符，那么两个整数的与运算将等于 0 。
![image.png](https://pic.leetcode-cn.com/1642600177-jECTYO-image.png)

### 代码

```java
class Solution {
    public int maxProduct(String[] words) {
        int res = 0, len = words.length;
        int[] nums = new int[len];
        for(int i = 0; i < len; i++){
            for(int j = 0, size = words[i].length(); j < size; j++){
                nums[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }
        for(int i = 0; i < len; i++){
            for(int j = i + 1; j < len; j++){
                if((nums[i] & nums[j]) == 0) res = Math.max(res, words[i].length() * words[j].length());
            }
        }
        return res;
    }
}
```