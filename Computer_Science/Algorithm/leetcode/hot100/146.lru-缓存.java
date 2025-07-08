/*
 * @lc app=leetcode.cn id=146 lang=java
 *
 * [146] LRU 缓存
 */

// @lc code=start

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Map;

class LRUCache {

    private static class Node {
        int key, val;
        Node prev, next;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private final int capacity;
    private final Node dummy = new Node(0, 0); // 哨兵节点
    private final Map<Integer, Node> keyToNode = new HashMap<>(); // 用于建立key和node之间的索引关系

    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummy.prev = dummy;
        dummy.next = dummy;
    }
    
    public int get(int key) {
        Node node = getNode(key);
        return  node == null
                ? -1
                : node.val;
    }
    
    public void put(int key, int value) {
        Node node = getNode(key);
        if (node != null) { // 如果节点已经存在了
            // 更新value
            node.val = value;
            return ;
        }
        // 将节点添加到链表头
        node = new Node(key, value);
        pushFront(node);
        // 将节点添加到map和链表中
        keyToNode.put(key, node);
        // 如果数量超过了capacity, 删除末尾元素
        if (keyToNode.size() > capacity) {
            Node backendNode = dummy.prev;
            removeNode(backendNode);
            keyToNode.remove(backendNode.key);
        }
    }

    // 根据key获取Node
    private Node getNode(int key) {
        // 没有这个节点
        if (!keyToNode.containsKey(key)) {
            return null;
        }
        Node node = keyToNode.get(key);
        // 将节点提到链表头
        removeNode(node);
        pushFront(node);
        return node;
    }

    // 将节点放入链表的头部
    private void pushFront(Node node) {
        // 将节点添加到头部
        node.prev = dummy;
        node.next = dummy.next;
        node.prev.next = node;
        node.next.prev = node;
    }

    // 移除节点
    private void removeNode(Node node) {
        // 因为有哨兵节点, 所有这里pre和nxt都不会是空
        // 在链表为空的时候, 两个的值都是dummy
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }   
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
// @lc code=end

