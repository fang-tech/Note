/*
 * @lc app=leetcode.cn id=42 lang=cpp
 *
 * [42] 接雨水
 */
#include <iostream>
#include <vector>
using namespace std;
// @lc code=start
class Solution {
public:
    int trap(vector<int>& height) {
        // 第一种做法, 需要额外的前缀数组和后缀数组
        // int n = height.size();
        // vector<int> pre(n, 0); // 前缀最大值
        // pre[0] = height[0];
        // for (int i=1;i<n;i++) {
        //     pre[i] = max(pre[i-1], height[i]);
        // }
        // vector<int> suf(n, 0); // 后缀最大值
        // suf[n-1] = height[n-1];
        // for (int i=n-2;i>=0;i--) {
        //     suf[i] = max(suf[i+1], height[i]);
        // }

        // int ans = 0;
        // for (int i=0;i<n;i++) {
        //     if (suf[i] > pre[i]) {
        //         // 后缀更大, 这个时候能接的水由前缀决定
        //         ans += pre[i] - height[i];
        //     } 
        //     else {
        //         ans += suf[i] - height[i];
        //     }
        // }

        // return ans;

        // 第二种做法, 在空间复杂度上进行了精简
        int n = height.size();
        int ans = 0;
        int l = 0;
        int r = n - 1;
        int pre_max = 0;
        int suf_max = 0;
        while (l <= r) {
            pre_max = max(pre_max, height[l]);
            suf_max = max(suf_max, height[r]);
            if (suf_max > pre_max){
                ans += pre_max - height[l];
                l++;
            }
            else {
                ans += suf_max - height[r];
                r--;
            }
        }

        return ans;
    }
};
// @lc code=end
int main (){
    vector<int> height = {0,1,0,2,1,0,1,3,2,1,2,1};
    Solution s;
    cout << s.trap(height);
    return 0;
}

