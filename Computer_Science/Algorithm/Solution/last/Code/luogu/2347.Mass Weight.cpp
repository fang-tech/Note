#include <iostream>
#include <algorithm>
#include <cstdio>
using namespace std;
int a[8];
int dp[1005];
int mass[8] = {0,1,2,3,5,10,20,0};
int new_n;
int new_w[100];

int main() {
    // freopen("input.txt", "r", stdin);
    for(int i=1;i<=6;i++) cin>>a[i];

    // 二进制拆分
    for (int i=1;i<=6;i++) {
        for (int j=1;j<a[i];j<<=1) {
            a[i] -= j;
            new_w[++new_n] = j*mass[i];
        }
        if (a[i]) {
            new_w[++new_n] = a[i]*mass[i];
        }
    }

    // 0/1背包, 计算所有可能得出的重量
    // dp[i] => 能否得出的重量, i : 重量
    dp[0] = 1;
    for (int i=1;i<=new_n;i++) {
        for (int j=1000;j>=new_w[i];j--) {
            if (dp[j-new_w[i]]) dp[j] = 1;
        }
    }
    int ans = 0;
    for (int i=1;i<=1000;i++) {
        if (dp[i]) ans++;
    }
    cout<<"Total="<<ans;
    return 0;
}