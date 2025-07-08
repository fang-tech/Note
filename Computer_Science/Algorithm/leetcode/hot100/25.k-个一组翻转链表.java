/*
 * @lc app=leetcode.cn id=25 lang=java
 *
 * [25] K 个一组翻转链表
 */

// @lc code=start

import javax.management.ListenerNotFoundException;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0 , head);

        ListNode list = dummy;
        ListNode pre = dummy, cur = dummy, nxt = head;
        ListNode begin = list;
        // 反转链表
        while (list != null) { 
            begin = list;
            // list向前走k步
            for (int i = 0; i < k && list != null; i++) {
                list = list.next;
            }
            if (list == null) break; // 说明不足k个节点, 可以直接返回, 不会再翻转了
            
            pre = begin; cur = pre.next; nxt = cur.next;
            // 翻转(begin, list]部分的链表
            while (list != cur) {
                pre = cur;
                cur = nxt;
                nxt = cur.next;

                cur.next = pre;
            }

            // 翻转头尾部分
            begin.next.next = nxt;
            list = begin.next;
            begin.next = cur;
        }
        return dummy.next;
    }

    private void print(ListNode head) {
        while (head != null) {
            System.out.print(" " + head.val);
            head = head.next;
        }
        System.out.println(" ");
    }
}
// @lc code=end

