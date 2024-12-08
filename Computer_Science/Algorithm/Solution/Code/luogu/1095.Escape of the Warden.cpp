#include <iostream>
#include <cstdio>
using namespace std;

const int N = 3 * 10e5 + 5;
int dp[2];
int T, S, M;

int main() {
    freopen("input.txt", "r", stdin);
    cin>>M>>S>>T;
    for (int i=1;i<=T;i++) {
        dp[0] += 17;
        if (M>=10) dp[1]+=60, M-=10;
        else M+=4;
        if (dp[1] >= dp[0]) dp[0] = dp[1];
        if (dp[0] >= S) {cout << "Yes\n" << i; break;}
        // cout << dp[0] << " ";
    }
    if (dp[0] < S) cout << "No\n" << dp[0];
    return 0;
}