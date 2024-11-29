#include <cstdio>
#include <iostream>
#include <algorithm>
using namespace std;
struct Pair{ int a, b; double y;} p[1005];
bool cmp(Pair a, Pair b) {return a.y > b.y;}
int n, k;
bool check(double x) {
    for (int i = 0; i < n; i++) p[i].y = p[i].a*1.0 - x * p[i].b;
    sort(p, p + n, cmp);
    double f = 0;
    for (int i = 0; i < k; i++) f += p[i].y;
    return f<0;
}

int main() {
    freopen("input.txt", "r", stdin);
    while (scanf("%d %d", &n, &k) == 2 && !(n == k && n == 0)) {
        k = n-k; // 选出k对
        for (int i=0; i<n;i++) scanf("%d", &p[i].a);
        for (int i=0; i<n;i++) scanf("%d", &p[i].b);
        double L = 0, R =0;
        for (int i=0;i<n;i++) R += p[i].a;
        for (int i =0; i<50;i++) {
            double mid = L+(R-L)/2;
            if (check(mid)) R = mid;
            else L = mid;
        }
        printf("%d\n", (int)(100 * (L + 0.005)));
    }
    return 0;
}