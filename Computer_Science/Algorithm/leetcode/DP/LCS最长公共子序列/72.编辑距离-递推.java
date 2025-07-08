/*
 * @lc app=leetcode.cn id=72 lang=java
 *
 * [72] 编辑距离
 */

// @lc code=start
class Solution {
    public int minDistance(String word1, String word2) {
        int n = word1.length(); int m = word2.length();
        char[] str1 = word1.toCharArray(); char[] str2 = word2.toCharArray();
        int[][] f = new int[2][m+1];

        // 初始化
        // 其中一个数组长度为0的时候, 
        // 这个时候需要的操作数就是将另一个数组完全删除的操作数
        // 也就是数组的元素数量
        for (int x = 0; x < m; x++) 
            f[0][x+1] = x+1;
        for (int i = 0 ;i < n; i++) {
            // 因为会被覆盖掉, 所以初始化只能在使用前进行
            f[(i+1)%2][0] = i+1;
            for (int j = 0; j < m; j++) {
                if (str1[i] == str2[j]) 
                    f[(i+1) % 2][j+1] = f[i%2][j];
                else 
                    f[(i+1) % 2][j+1] = Math.min(Math.min(f[i%2][j+1], f[(i+1) % 2][j]), f[i%2][j]) + 1;
            }
        }

        return f[n%2][m];
    }
}
// @lc code=end

