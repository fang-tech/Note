import java.util.Random;

/**
 * <p>
 * QuickSort
 * </p>
 *
 * @author Simon
 * @version 2025.05.23.1.0.0
 * @description 快速排序的实现
 * @since 2025-05-23
 */

public class QuickSort {
    private static final Random random = new Random(42);

    public static void quickSort(int[] array, int model){
        if (array == null || array.length == 0)
            return;
        switch (model){
            case 1: quickSort1(array, 0, array.length - 1);
            case 2: quickSort2(array, 0, array.length - 1);
            case 3: quickSort3(array, 0, array.length - 1);
        }
//        check(array);
    }

    /**
     * 使用了三路分区优化和尾递归优化的快速排序, 拥有更好的重复元素排序性能, 同时随机基准值
     * @param array 待排序数组
     * @param l 需要排序的部分的左边界
     * @param r 需要排序的部分的右边界
     */
    private static void quickSort3(int[] array, int l, int r) {
        while (l < r) {
            int randomIndex = random.nextInt(r - l + 1) + l;

            int[] partitionResult = threeWayPartition(array, l, r, randomIndex);
            int lt = partitionResult[0]; // 小于基准值的范围区间 [low, lt]
            int gt = partitionResult[1]; // 大于基准值的范围区间 [rt, high]

            // 尾递归优化, 用来减少递归深度, 能保证递归深度只有logn
            if (r - gt < lt - l) { // 优先递归较小部分
                quickSort3(array, gt, r);
                r = lt; // 在下次循环的时候递归较大部分
            } else {
                quickSort3(array, l, lt);
                l = gt; // 在下次循环的时候递归较大部分
            }
        }
    }

    /**
     * 三路分区 - 高效处理重复元素
     * @param arr 待排序的元素组
     * @param l 需要排序的部分的左边界
     * @param r 需要排序的部分的左边界
     * @param pivotIndex 基准值的index
     * @return {lt, gt}, lt是小于基准值的左边界, rt是大于基准值的右边界
     */
    private static int[] threeWayPartition(int[] arr, int l , int r, int pivotIndex) {
        int pivot = arr[pivotIndex];

        int lt = l; // [l, lt-1] < pivot
        int i = l; // [lt, i-1] == pivot
        int gt = r + 1; // [i, gt-1] > pivot

        while (i < gt) {
            if (arr[i] < pivot) {
                swap(arr, i++, lt++);
            } else if (arr[i] > pivot) {
                swap(arr, i, --gt);
            } else {
                i++;
            }
        }

        return new int[]{lt, gt};
    }

    private static void quickSort1(int[] array, int l, int r) {
        if (l < r) {
            int pivotIndex = partition(array, l, r);
            quickSort1(array, l, pivotIndex - 1);
            quickSort1(array, pivotIndex + 1, r);
        }
    }
    private static void quickSort2(int[] array, int l, int r) {
        if (l < r) {
            int pivotIndex = randomPartition(array, l, r);
            quickSort1(array, l, pivotIndex - 1);
            quickSort1(array, pivotIndex + 1, r);
        }
    }

    private static int randomPartition(int[] array, int l, int r) {
        int randomIndex = random.nextInt(r - l + 1) + l;
        swap(array, randomIndex, r);
        return partition(array, l, r);
    }

    /**
     * 并将比基准元素的小的元素放在基准元素的左边, 比基准元素大的元素放在基准元素右边
     * @param array 需要分区的数组, 边界是[l, r]
     * @param l 数组中需要分区的子数组的左边界
     * @param r 数组中需要分区的子数组的右边界
     * @return 基准元素的位置
     */
    private static int partition(int[] array, int l, int r) {
        int pivotIndex = r;
        int pivot = array[pivotIndex];
        int p = l - 1; // 指向小于基准的元素的最后位置
        // 将比基准元素小的元素都移到基准元素的左边
        for (int i = l; i < r; i++) {
            // 如果当前元素小于等于基准值
            if (array[i] <= pivot) {
                p++;
                swap(array, p, i);
            }
        }
        swap(array, p + 1, r);
        return p + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static boolean check(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i+1]) {
                System.out.println("排序失败(大到小) " + array[i] + "和 " + array[i+1] + "之间存在错误");
                return false;
            }
        }
        System.out.println("成功排序(大到小)");
        return true;
    }

    private static void time(int[] array, int model){
        long start = System.nanoTime();
        QuickSort.quickSort(array, model);
        long end = System.nanoTime();
        switch (model) {
            case 1: {
                System.out.print("普通");
                break;
            }

            case 2: {
                System.out.print("随机");
                break;
            }

            case 3: {
                System.out.print("三路分区");
                break;
            }
        }
        System.out.println("快排 (" + array.length + " 元素): " + (end - start) / 1_000 + " 微秒");
    }

    public static void main(String[] args) {
        Random random = new Random(43);
        System.out.println("\n================= 随机数组测试 =================\n");
        final int size = 1_5_000;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        time(array.clone(), 1);
        time(array.clone(), 2);
        time(array.clone(), 3);

        System.out.println("\n================= 元素相同数组测试 =================\n");
        int[] sameArray = new int[size];

        time(sameArray.clone(), 1);
        time(sameArray.clone(), 2);
        time(sameArray.clone(), 3);

    }
}
