#include <iostream>
#include <cstring>
#include <cstdio>
#include <algorithm>
using namespace std;

const int N = 10e5 + 5;
int dp[N][2];
int n=0;
int s[N];

int main() {
    freopen("input.txt", "r", stdin);
    while (cin>>s[++n]) ;
    n--;

    for (int i=1;i<=n;i++) {
        dp[i][0] = 1;
        dp[i][1] = 1;
        for (int j=1;j<i;j++) {
            if (s[i] > s[j]) dp[i][0] = max(dp[j][0]+1, dp[i][0]); // 递增
            else if (s[i] <= s[j]) dp[i][1] = max(dp[j][1]+1, dp[i][1]); // 递减
        }
    }
    for (int i=1;i<=n;i++) cout << dp[i][1] << " ";
    cout << endl;
    int up = 0; int down = 0;
    for (int i=1;i<=n;i++) {up = max(up, dp[i][0]); down = max(down, dp[i][1]);}
    cout << down << "\n" << up << endl;
    return 0;
}