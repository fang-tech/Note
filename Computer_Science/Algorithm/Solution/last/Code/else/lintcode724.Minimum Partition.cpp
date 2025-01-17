#include <vector>
#include <iostream>
#include <utility>
using namespace std;

class Solution {
public:
    /**
     * @param nums: the given array
     * @return: the minimum difference between their sums 
     */
    int findMin(vector<int> &nums) {
        int sum = 0;
        for (int val : nums) sum += val;
        int C = sum / 2;
        vector<int>& c = nums;
        vector<int>& w = nums;
        vector<int> dp(C+5, 0);
        for (int i=1;i<=nums.size();i++) {
            for (int j=C;j>=c[i-1];j--) {
                dp[j] = max(dp[j], dp[j-c[i-1]] + w[i-1]);
            }
        }
        int rest = sum - dp[C];
        return abs(rest - (sum - rest));
    }
};

int main() {
    freopen("input.txt", "r", stdin);
    int num; vector<int> nums;
    while(cin>>num) nums.push_back(num);
    Solution s;
    cout << s.findMin(nums);
}