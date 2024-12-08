#include <iostream>
#include <cstdio>
#include <algorithm>
#include <string>
using namespace std;

const int N = 100;
int dp[N][N];
string str1, str2;
int n, m;

int main() {
    //@test
    freopen("input.txt", "r", stdin);
    string temp;
    cin>>temp; str1 += " " + temp;
    cin>>temp; str2 += " " + temp;
    n = str1.length()-1; m = str2.length()-1;

    for (int i=1;i<=n;i++) dp[i][0] = i; 
    for (int i=1;i<=m;i++) dp[0][i] = i; 

    for (int i=1;i<=n;i++) {
        for (int j=1;j<=m;j++) {
            if (str1[i] == str2[j]) {
                dp[i][j] = dp[i-1][j-1]+1;
            }
            else {
                dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + 1;
            }
        }
    }

    //@test
    for (int i=0;i<=n;i++) {
        for (int j=0;j<=m;j++) {
            cout << dp[i][j] << "\t";
        }
        cout << endl;
    }


    cout << dp[n][m];
    return 0;
}