#include <iostream>
#include <cstdio>
#include <algorithm>
using namespace std;

const int N = 100;
int dp1[N][N];
int dp0[N][N];
int n;
char operation[N];
int bools[N];

int caculate(int l, int r, char operation) {
    if (l > 1) l = 1;
    if (r > 1) r = 1;

    if (operation == '^') return l^r;
    if (operation == '&') return l&r;
    if (operation == '|') return l|r;
}

int main() {
    //@test
    freopen("input.txt", "r", stdin);
    cin>>n;
    for (int i=1;i<=n;i++) {
        char ch;
        cin>>ch;
        if (ch == 'F') bools[i]=0;
        else bools[i]=1;
    }
    for (int i=1;i<n;i++) cin>>operation[i];

    for (int i=n;i>=1;i--) {
        for (int j=i;j<=n;j++) {
            if (i == j) {dp1[i][j] = bools[i]; dp0[i][j] = !dp1[i][j];}
            for (int k=i;k<j;k++) {
                if (operation[k] == '&') {
                    // 左右两边都为1的时候
                    dp1[i][j] += dp1[i][k]*dp1[k+1][j]; 
                    // 有一遍为0的情况
                    dp0[i][j] += (dp0[i][k]*dp0[k+1][j])+(dp0[i][k]*dp1[k+1][j])+dp1[i][k]*dp0[k+1][j];
                }
                if (operation[k] == '|') {
                    // 有一边为1的时候
                    dp1[i][j] += (dp1[i][k]*dp0[k+1][j])+(dp0[i][k]*dp1[k+1][j])+dp1[i][k]*dp1[k+1][j]; 
                    // 全为0的时候
                    dp0[i][j] += dp0[i][k]*dp0[k+1][j];
                }
                if (operation[k] == '^') {
                    // 一个0一个1的时候
                    dp1[i][j] += dp1[i][k]*dp0[k+1][j]+dp0[i][k]*dp1[k+1][j];
                    // 全为0或1的时候
                    dp0[i][j] += dp0[i][k]*dp0[k+1][j]+dp1[i][k]*dp1[k+1][j];
                }
            }
        }
    }
    // @test
    cout << "dp1" << endl;
    for (int i=1;i<=n;i++) {
        for (int j=1;j<=n;j++) {
            cout << dp1[i][j] << "\t";
        }
        cout << endl;
    }
    cout << "dp0" << endl;
    for (int i=1;i<=n;i++) {
        for (int j=1;j<=n;j++) {
            cout << dp0[i][j] << "\t";
        }
        cout << endl;
    }

    cout << dp1[1][n];
    return 0;
}