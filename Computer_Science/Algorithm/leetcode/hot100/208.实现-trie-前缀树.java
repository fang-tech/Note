/*
 * @lc app=leetcode.cn id=208 lang=java
 *
 * [208] 实现 Trie (前缀树)
 */

// @lc code=start
class Trie {

    class Node {
        Node[] son = new Node[26];
        boolean end; // this node is some words' end
    }

    private Node root;

    public Trie() {
        root = new Node();
    }
    
    public void insert(String word) {
        Node cur = root;
        for (char ch : word.toCharArray()) {
            ch -= 'a';
            // not have the next node
            if (cur.son[ch] == null) {
                cur.son[ch] = new Node();
            }
            cur = cur.son[ch];
        }
        cur.end = true; // this position is a word's end
    }
    
    public boolean search(String word) {
        return find(word) == 1;
    }
    
    public boolean startsWith(String prefix) {
        return find(prefix) != 0;
    }

    private int find(String word) {
        Node cur = root;
        for (char ch : word.toCharArray()) {
            ch -= 'a';
            if (cur.son[ch] == null) // not have the word
                return 0;
            cur = cur.son[ch];
        }
        // if cur is a word end, return 1
        // else means the word is a prefix
        return cur.end ? 1 : 2;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
// @lc code=end

