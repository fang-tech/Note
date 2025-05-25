import java.util.Random;

/*
 * @lc app=leetcode.cn id=215 lang=java
 *
 * [215] 数组中的第K个最大元素
 */

// @lc code=start
class Solution {

    private static final Random random = new Random();

    public int findKthLargest(int[] nums, int k) {
        int ans = quickSort(nums, 0, nums.length-1, k-1);
        // System.out.println(Arrays.toString(nums));
        return ans;
    }

    public int quickSort(int[] nums, int l, int r, int k) {
        if (l < r) {
            int randomIndex = random.nextInt(r - l + 1) + l;
            int[] partitionResult = threeWayPartition(nums, l, r, randomIndex);

            int lt = partitionResult[0];
            int gt = partitionResult[1];
            // 只需要递归存在答案的那一边就行了
            if (lt < k && k < gt) { // 也就是第k大的元素在相等的区间里, 这个时候就找到了
                return nums[k];
            } else if (lt >= k) { // 第k大的元素在小于基准值的区间, 继续在小于基准值的区间里面找
                return quickSort(nums, l, lt, k);
            } else { // 第k大的元素在大于基准值的区间, 继续在小于基准值的区间里面找
                return quickSort(nums, gt, r, k);
            }
        }
        return nums[l];
    }

    public int[] threeWayPartition(int[] nums, int l, int r, int pivotIndex) {
        int pivot = nums[pivotIndex];

        int lt = l; // [l, lt-1] < pivot
        int i = l; // [lt, i] == pivot
        int gt = r + 1; // [i, gt-1] > pivot

        while (i < gt) {
            if (nums[i] > pivot) {
                swap(nums, i++, lt++);
            } else if (nums[i] < pivot) {
                swap(nums, i, --gt);
            } else {
                i++;
            }
        }

        return new int[]{lt, gt};
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
}
// @lc code=end

