#include <queue>
#include <iostream>
using namespace std;

const int N = 1e5 + 5;

struct Node
{
    char value;
    int lson, rson;
}tree[N];

int index = 1;  // tree[0] 用于做null的标识, 
                // 如果某个节点的lson或者rson是0, 说明就是没有子节点了
int newNode(char val) {
    tree[index].value = val;
    tree[index].lson = 0;
    tree[index].rson = 0;
    return index++;
}
/**
 * breif : 如果l_r==1 将child节点插入到father的左节点, 反之右节点
 */
void Insert(int father, int child, int l_r) {
    if (l_r) tree[father].lson = child;
    else tree[father].rson = child;
}

int buildtree() { // 作为测试代码构建一颗二叉树
    int A = newNode('A'); int B = newNode('B');
    int C = newNode('C'); int D = newNode('D');
    int E = newNode('E'); int F = newNode('F');
    int G = newNode('G'); int H = newNode('H');
    int I = newNode('I');

    Insert(E, B, 1); Insert(E, G, 0);
    Insert(B, A, 1); Insert(B, D, 0);
    Insert(D, C, 1); Insert(G, F, 1);
    Insert(G, I, 0); Insert(I, H, 1);
    int root = E;
    return root;
}
int main() {
    int root = buildtree();
    queue<int> q;
    q.push(root);
    while(!q.empty()) {
        int temp = q.front();
        cout << tree[temp].value << " ";
        q.pop();
        if (tree[temp].lson != 0) q.push(tree[temp].lson);
        if (tree[temp].rson != 0) q.push(tree[temp].rson);
    }
    return 0;
}
