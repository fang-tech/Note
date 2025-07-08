/*
 * @lc app=leetcode.cn id=148 lang=java
 *
 * [148] 排序链表
 */

// @lc code=start
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
    // 归并排序的标准写法
    public ListNode sortList(ListNode head) {
        // 只有一个节点的时候, 已经排好序了
        if (head == null || head.next == null) return head;
        // 分割数组
        ListNode head2 = middleNode(head);

        // 分治两个前半部分的链表和后半部分的链表
        head = sortList(head);
        head2 = sortList(head2);

        // 合并两个链表
        return mergeTwoList(head, head2);
    }

    // 找到中间节点
    private ListNode middleNode (ListNode head) {
        ListNode pre = head;
        ListNode fast = head, slow = head;

        while (fast != null && fast.next != null) {
            pre = slow;
            fast = fast.next.next;
            slow = slow.next;
        }
        // 断开两个链表
        pre.next = null;
        return slow;
    }

    // 合并两个有序链表
    private ListNode mergeTwoList(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {    
                cur.next = new ListNode(list1.val);
                list1 = list1.next;
            } else {
                cur.next = new ListNode(list2.val);
                list2 = list2.next;
            }
            cur = cur.next;
        }

        if (list1 != null) {
            cur.next = list1;
        } 
        if (list2 != null) {
            cur.next = list2;
        }

        return dummy.next;
    }
}
// @lc code=end

