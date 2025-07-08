/*
 * @lc app=leetcode.cn id=21 lang=java
 *
 * [21] 合并两个有序链表
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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        ListNode newNode  = null;
        // 从两个牌堆里面摸牌, 比较牌堆底部的大小
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                newNode = new ListNode(list1.val);
                list1 = list1.next;
            } else {
                newNode = new ListNode(list2.val);
                list2 = list2.next;
            }
            cur.next = newNode;
            cur = newNode;
        }

        while (list2 != null) {
            newNode = new ListNode(list2.val);
            list2 = list2.next;
            cur.next = newNode;
            cur = newNode;
        }

        while (list1 != null) {
            newNode = new ListNode(list1.val);
            list1 = list1.next;
            cur.next = newNode;
            cur = newNode;
        }

        return dummy.next;
    }
}
// @lc code=end

