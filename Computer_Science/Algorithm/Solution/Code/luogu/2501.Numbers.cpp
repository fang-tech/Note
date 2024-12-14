#include <iostream>
#include <cstdio>
#include <algorithm>
using namespace std;

const int N = 3.5e4+5;
int a[N];
int dp[N];
int n;

int main() {
    freopen("input.txt", "r", stdin);
    cin>>n;
    for (int i=1;i<=n;i++) cin>>a[i];

    for (int i=1;i<=n;i++) {
        dp[i] = 1;
        for (int j=1;j<i;j++) {
            if (a[i] > a[j]) dp[i] = max(dp[i], dp[j]+1);
        }
    }

    int ans = *max_element(dp, dp+n);

    for (int i=1;i<=n;i++) cout << dp[i] << " ";
    cout << endl;
    return 0;
}