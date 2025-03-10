#include <iostream>
using namespace std;

    int dfs(int i) {
        if (i < 0) return 0;
        if (i == 0) return 1;
        return dfs(i - 1) 
    }

int main() {
    cout << "Hello, World!" << endl;
    return 0;
}