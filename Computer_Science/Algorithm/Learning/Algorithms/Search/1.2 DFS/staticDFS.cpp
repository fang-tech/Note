#include <iostream>

using namespace std;

const int N = 1e5 + 5;
struct node
{
    int l, r;
    char value;
}tree[N];
int idx = 1;

int newNode(char value) {
    tree[idx].value = value;
    tree[idx].l = 0;
    tree[idx].r = 0;
    return idx++;
}

void Insert(int father, int child, bool l_r) {
    if (l_r) tree[father].l = child;
    else tree[father].r = child;
    return;
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
/**
 * 用于获取DFS的遍历中, 节点被第一次访问的时候的时间戳
 * 这里的时间是对于计时器而言的"时间", 不是常规意义上的时间
 */
int dfn[N] = {0};
int dfn_timer = 1;
void dfn_order(int father) {
    if (father == 0) return;
    dfn[father] = dfn_timer++;
    cout << "dfn[" << tree[father].value << "] = " << dfn[father] << " ; ";
    dfn_order(tree[father].l);
    dfn_order(tree[father].r);
}

/**
 * 获取DFS遍历中的DFS序
 */
int visit_timer = 0;
void visit_order(int father) {
    if (father == 0) return;
    printf("visit[%c] = %d; ", tree[father].value, ++visit_timer);
    visit_order(tree[father].l);
    visit_order(tree[father].r);
    printf("visit[%c] = %d; ", tree[father].value, ++visit_timer);
}

/**
 * 获取树的各节点的深度
 */
int deep[N] = {0};
int deep_timer = 0;
void deep_node(int father) {
    if (father == 0) return;
    deep[father] = ++deep_timer;
    printf("deep[%c] = %d; ", tree[father].value, deep[father]);
    deep_node(tree[father].l);
    deep_node(tree[father].r);
    deep_timer--;
}

/** 
 * 获取树上各个节点的子节点的个数
 */
int num[N] = {0};
int num_node(int father) {
    if (father == 0) return 0;
    num[father] = num_node(tree[father].l) + num_node(tree[father].r) + 1; // 加上节点自己
    printf("num[%c] = %d; ", tree[father].value, num[father]);
    return num[father];
}

/**
 * 先序输出, 中序输出, 后续输出在前面的代码中都有体现, 就不再写了
 */

int main() {
    int root = buildtree();
    cout << "Test : dfn_order() : " << endl;
    dfn_order(root);
    cout << "\n<======================>" << endl;

    cout << "Test : visit_order() : " << endl;
    visit_order(root);
    cout << "\n<======================>" << endl;

    cout << "Test : deep_node() : " << endl;
    deep_node(root);
    cout << "\n<======================>" << endl;


    cout << "Test : num_node() : " << endl;
    num_node(root);
    cout << "\n<======================>" << endl;
    return 0;
}