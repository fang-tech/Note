#include <iostream>

using namespace std;

struct node
{
    char value;
    node* l ;
    node* r;
    node(char value = '#', node* l = nullptr, node* r = nullptr) : value(value), l(l), r(r) {}
};

void remove_tree(node* root) {
    if (root == nullptr) return;
    remove_tree(root->l);
    remove_tree(root->r);
    delete root;
}

/**
 * 先序遍历
 */
void preorder(node* father) {
    if (father == nullptr) return;
    cout << father->value << " ";
    preorder(father->l);
    preorder(father->r);
}

/**
 * 中序遍历
 */
void inorder(node* father) {
    if (father == nullptr) return;
    inorder(father->l);
    cout << father->value << " ";
    inorder(father->r);
}

/**
 * 后序遍历
 */
void postorder(node* father) {
    if (father == nullptr) return;
    postorder(father->l);
    postorder(father->r);
    cout << father->value << " ";
}
int main() {
    node *A ,*B, *C, *D, *E, *F, *G, *H, *I;
    A = new node('A'); B = new node('B');
    C = new node('C'); D = new node('D');
    D = new node('D'); E = new node('E');
    F = new node('F'); G = new node('G');
    H = new node('H'); I = new node('I');
    E->l = B; E->r = G; B->l = A; B->r = D; D->l = C;
    G->l = F; G->r = I; I->l = H;
    node* root = E;

    cout << "Test : preorder() : " << endl;
    preorder(root);
    cout << "\n<========================>\n";

    cout << "Test : inorder() : " << endl;
    inorder(root);
    cout << "\n<========================>\n";

    cout << "Test : postorder() : " << endl;
    postorder(root);
    cout << "\n<========================>\n";
    return 0;
}