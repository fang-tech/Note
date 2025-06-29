/*
 * @lc app=leetcode.cn id=56 lang=java
 *
 * [56] 合并区间
 */

// @lc code=start

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    /**
     * 将数组按照左端点进行排序, 然后进行区间合并
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> ans = new ArrayList<>(2);
        for (int[] pos : intervals) {
            int m = ans.size();
            // 当前节点的起点在上一个节点的终的左边, 可能可以合并
            if (m > 0 && pos[0] <= ans.get(m-1)[1]) {
                ans.get(m-1)[1] = Math.max(ans.get(m-1)[1], pos[1]);
            } else { // 不能合并
                ans.add(pos);
            }
        }

        return ans.toArray(new int[ans.size()][]);
    }
}
// @lc code=end

