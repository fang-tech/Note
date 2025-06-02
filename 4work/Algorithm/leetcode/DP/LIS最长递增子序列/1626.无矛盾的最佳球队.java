/*
 * @lc app=leetcode.cn id=1626 lang=java
 *
 * [1626] 无矛盾的最佳球队
 */

/**
 * @Solution: 
 * 既然球员在球队中的下标是不重要的, 我们就可以考虑排序添加额外的性质
 * 按照年龄排序, 如果年龄排序相同则按照分数排序(升序)
 *  i < j
 *  if ages[i] < ages[j] 则分数需要满足 scores[i] <= scores[j]
 *  if ages[i] == ages[j] 按照排序顺序 scores[i] <= scores[j]
 * 所以在排完序以后数组中, 需要满足scores[i] <= scores[j]才能组成一队球队
 * 
 * 最后我们就变成了求排完序以后数组中score的分数和最大递增子序列
 * f[i]: 以i球员为年龄最大的球队能得到的最大的分数
 * if (f[i] >= f[j]) f[i] = max{f[j]} + scores[i]
 */
// @lc code=start
class Solution {
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = ages.length;
        Integer[] idx = new Integer[n];
        for (int i = 0 ; i < n; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (i, j) -> ages[i] != ages[j] ? ages[i] - ages[j] : scores[i] - scores[j]);

        int ans = 0;
        int[] f = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i ;j++) {
                if (scores[idx[i]] >= scores[idx[j]])
                    f[i] = Math.max(f[j], f[i]);
            }
            f[i] += scores[idx[i]];
            ans = Math.max(ans, f[i]);
        }
        return ans;
    }
}
// @lc code=end

