/*
 * @lc app=leetcode.cn id=23 lang=java
 *
 * [23] 合并 K 个升序链表
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
    public ListNode mergeKLists(ListNode[] lists) {
        return merge(lists, -1, lists.length-1);
    }

    // 将(left, right]之间的链表合并
    private ListNode merge(ListNode[] lists, int left, int right) {
        if (right - left == 0) return null;
        if (right - left == 1) return lists[right];

        // 将lists分成两部分
        int mid = (right + left) / 2;

        ListNode leftList = merge(lists, left, mid);
        ListNode rightList = merge(lists, mid,  right);

        return mergeTwoLists(leftList, rightList);
    }



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

