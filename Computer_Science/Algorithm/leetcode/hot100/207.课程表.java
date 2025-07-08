/*
 * @lc app=leetcode.cn id=207 lang=java
 *
 * [207] 课程表
 */

// @lc code=start

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Solution {
    


    // BFSTop
    public boolean canFinish1(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        List<List<Integer>> adjacency = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) 
            adjacency.add(new ArrayList<>());
        // Get the indegree and adjacency of erery course
        for (int[] row : prerequisites) {
            indegrees[row[0]]++;
            adjacency.get(row[1]).add(row[0]);
        }
        Deque<Integer> que = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) 
            if (indegrees[i] == 0)
                que.addLast(i);

        // BFSTop
        while (!que.isEmpty()) {
            int node = que.removeFirst();
            numCourses--;
            // after remove the node indegree equals 0 
            // add its nxt node to the que if the nxt indegree alse equals 0
            for (Integer nxt : adjacency.get(node)) {
                if (--indegrees[nxt] == 0)
                    que.addLast(nxt);
            }
        }
        return numCourses == 0;
    }
}
// @lc code=end

