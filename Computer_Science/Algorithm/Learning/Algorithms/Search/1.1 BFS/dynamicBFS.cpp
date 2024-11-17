#include <iostream>
#include <queue>
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
    queue<node*> q;
    // remove_tree(root);
    q.push(root);
    while(!q.empty()) {
        node* current = q.front();
        cout << current->value << " ";
        if (current->l) q.push(current->l);
        if (current->r) q.push(current->r);
        q.pop();
    }
    return 0;
}
