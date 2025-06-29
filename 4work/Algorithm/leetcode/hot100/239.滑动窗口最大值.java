/*
 * @lc app=leetcode.cn id=239 lang=java
 *
 * [239] 滑动窗口最大值
 */

// @lc code=start

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    /**
     * 使用双端队列, 原理是在窗口中, 添加了一个新的元素以后, 
     * 如果这个元素比窗口中的最大元素都大, 那么窗口中的元素都不可能再成为窗口中的最大元素
     * 如果这个元素比窗口中的最大值小, 还是有可能成为后面窗口的最大值的
     * 但是如果我们再新进来的一个元素, 又比这个刚进来的元素大, 那么这个元素也不能可能成为新的最大的元素了
     * 综合这个过程我们能够得出我们只需要维护这个数组中的最大元素和其他可能成为最大值的所有元素
     * 再看到我们这个过程中有双端的入出队, 所以我们需要使用单调队列解决
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> dq = new ArrayDeque<>();
        int[] ans = new int[nums.length - k + 1];
        for (int right = 0; right < nums.length; right++) {
            // 1. 左边入队
            while (!dq.isEmpty() && nums[dq.getLast()] <= nums[right]) {
                dq.removeLast();
            }
            dq.addLast(right);

            // 2. 右边出队
            if (right - dq.getFirst() >= k) 
                dq.removeFirst();

            // 3. 记录答案
            if (right >= k-1) 
                ans[right - k + 1] = nums[dq.getFirst()];
        }
        return ans;
    }
}
// @lc code=end

