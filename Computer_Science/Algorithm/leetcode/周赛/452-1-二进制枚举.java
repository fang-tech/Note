class Solution {
    
    /**
     * 使用二进制枚举
     * 时间复杂度: O(n2^n), 有2^n个方案要遍历, 每个方案需要n
     * 空间复杂度: O(1)
     * @param nums
     * @param target
     * @return
     */
    public boolean checkEqualPartitions(int[] nums, long target) {
        int u = (1 << nums.length) - 2; // 1110, 表示前三位都给m1, 最后一位给m2
        // 遍历所有的分配方案
        for (int i = 1; i <= u; i++) {
            // 计算现在的分配方案
            long m1 = 1L; long m2 = 1L;
            for (int j = 0; j < nums.length; j++) {
                if (m1 > target || m2 > target) break;
                // 计算出来第j位是不是1, 也就是分配给m1
                if ((i >> j & 1) == 1) {
                    m1 *= nums[j];
                } else {
                    m2 *= nums[j];
                }
            }
            if (m1 == target && m2 == target) 
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{40,15,92,65,42,7,80,17,46,68,78,48};
        long target = 4098931200L;
        Solution s = new Solution();
        boolean ans = s.checkEqualPartitions(nums, target);
        System.out.println(ans);
    }
}