import java.util.Random;

public class OptimizedQuickSort {

    private static Random random = new Random();
    private static final int INSERTION_SORT_THRESHOLD = 10; // 小数组阈值

    /**
     * 优化版快速排序主方法
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSortOptimized(arr, 0, arr.length - 1);
    }

    /**
     * 内部优化快排方法
     */
    private static void quickSortOptimized(int[] arr, int low, int high) {
        while (low < high) {
            // 优化1: 小数组使用插入排序
            if (high - low + 1 < INSERTION_SORT_THRESHOLD) {
                insertionSort(arr, low, high);
                break;
            }

            // 优化2: 三数取中选择基准值
            int pivotIndex = medianOfThree(arr, low, high);

            // 优化3: 三路分区处理重复元素
            int[] partitionResult = threeWayPartition(arr, low, high, pivotIndex);
            int lt = partitionResult[0]; // 小于基准值的右边界
            int gt = partitionResult[1]; // 大于基准值的左边界

            // 优化4: 尾递归优化 - 总是递归较小的部分, 尾递归优化主要是为了优化空间, 减少了栈帧
            if (lt - low < high - gt) {
                quickSortOptimized(arr, low, lt);
                low = gt; // 尾递归优化，用循环处理较大部分
            } else {
                quickSortOptimized(arr, gt, high);
                high = lt; // 尾递归优化，用循环处理较大部分
            }
        }
    }

    /**
     * 优化1: 插入排序 - 处理小数组更高效
     */
    private static void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * 优化2: 三数取中法选择基准值
     */
    private static int medianOfThree(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        // 将三个元素排序，使得 arr[low] <= arr[mid] <= arr[high]
        if (arr[mid] < arr[low]) {
            swap(arr, low, mid);
        }
        if (arr[high] < arr[low]) {
            swap(arr, low, high);
        }
        if (arr[high] < arr[mid]) {
            swap(arr, mid, high);
        }

        // 将中位数放到倒数第二个位置
        swap(arr, mid, high - 1);
        return high - 1;
    }

    /**
     * 优化3: 三路分区 - 高效处理重复元素
     * 返回 [lt, gt]，其中 lt 是小于基准值的右边界，gt 是大于基准值的左边界
     */
    private static int[] threeWayPartition(int[] arr, int low, int high, int pivotIndex) {
        int pivot = arr[pivotIndex];

        int lt = low;     // arr[low...lt-1] < pivot
        int i = low;      // arr[lt...i-1] == pivot
        int gt = high + 1; // arr[gt...high] > pivot

        while (i < gt) {
            if (arr[i] < pivot) {
                swap(arr, lt++, i++);
            } else if (arr[i] > pivot) {
                swap(arr, i, --gt);
            } else {
                i++;
            }
        }

        return new int[]{lt, gt};
    }

    /**
     * 优化4: 迭代版快排 - 避免递归栈溢出
     */
    public static void iterativeQuickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        // 使用栈模拟递归
        int[] stack = new int[arr.length * 2];
        int top = -1;

        // 初始范围入栈
        stack[++top] = 0;
        stack[++top] = arr.length - 1;

        while (top >= 0) {
            int high = stack[top--];
            int low = stack[top--];

            if (low < high) {
                if (high - low + 1 < INSERTION_SORT_THRESHOLD) {
                    insertionSort(arr, low, high);
                    continue;
                }

                int pivotIndex = medianOfThree(arr, low, high);
                int[] partitionResult = threeWayPartition(arr, low, high, pivotIndex);
                int lt = partitionResult[0];
                int gt = partitionResult[1];

                // 将子数组范围入栈
                if (low < lt) {
                    stack[++top] = low;
                    stack[++top] = lt;
                }
                if (gt < high) {
                    stack[++top] = gt;
                    stack[++top] = high;
                }
            }
        }
    }

    /**
     * 优化5: 并行快排（多线程版本）
     */
    public static void parallelQuickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        parallelQuickSort(arr, 0, arr.length - 1, 0);
    }

    private static void parallelQuickSort(int[] arr, int low, int high, int depth) {
        if (low < high) {
            // 当数组足够大且递归深度不太深时使用并行
            if (high - low > 1000 && depth < 4) {
                int pivotIndex = medianOfThree(arr, low, high);
                int[] partitionResult = threeWayPartition(arr, low, high, pivotIndex);
                int lt = partitionResult[0];
                int gt = partitionResult[1];

                // 使用线程池并行处理两个子数组
                Thread leftThread = new Thread(() ->
                        parallelQuickSort(arr, low, lt, depth + 1));
                Thread rightThread = new Thread(() ->
                        parallelQuickSort(arr, gt, high, depth + 1));

                leftThread.start();
                rightThread.start();

                try {
                    leftThread.join();
                    rightThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                // 对小数组或深递归使用普通快排
                quickSortOptimized(arr, low, high);
            }
        }
    }

    /**
     * 优化6: 内省排序 - 防止最坏情况
     */
    public static void introsort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        int maxDepth = (int)(2 * Math.log(arr.length) / Math.log(2));
        introsortHelper(arr, 0, arr.length - 1, maxDepth);
    }

    private static void introsortHelper(int[] arr, int low, int high, int depthLimit) {
        if (high - low + 1 < INSERTION_SORT_THRESHOLD) {
            insertionSort(arr, low, high);
        } else if (depthLimit == 0) {
            // 递归深度过深，切换到堆排序
            heapSort(arr, low, high);
        } else {
            int pivotIndex = medianOfThree(arr, low, high);
            int[] partitionResult = threeWayPartition(arr, low, high, pivotIndex);
            int lt = partitionResult[0];
            int gt = partitionResult[1];

            introsortHelper(arr, low, lt, depthLimit - 1);
            introsortHelper(arr, gt, high, depthLimit - 1);
        }
    }

    /**
     * 堆排序 - 内省排序的后备算法
     */
    private static void heapSort(int[] arr, int low, int high) {
        int n = high - low + 1;
        // 构建最大堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, low, n, i);
        }

        // 逐个提取元素
        for (int i = n - 1; i > 0; i--) {
            swap(arr, low, low + i);
            heapify(arr, low, i, 0);
        }
    }

    private static void heapify(int[] arr, int offset, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[offset + left] > arr[offset + largest]) {
            largest = left;
        }

        if (right < n && arr[offset + right] > arr[offset + largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, offset + i, offset + largest);
            heapify(arr, offset, n, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void printArray(int[] arr, String message) {
        System.out.println(message);
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    /**
     * 测试各种优化版本
     */
    public static void main(String[] args) {
        // 测试数据
        int[] original = {64, 34, 25, 12, 22, 11, 90, 88, 76, 50, 42, 25, 11, 90};

        System.out.println("=== 快速排序优化技术演示 ===");
        printArray(original, "原始数组:");

        // 测试优化版快排
        int[] arr1 = original.clone();
        long start = System.nanoTime();
        quickSort(arr1);
        long end = System.nanoTime();
        printArray(arr1, "优化版快排结果:");
        System.out.println("耗时: " + (end - start) + " 纳秒\n");

        // 测试迭代版快排
        int[] arr2 = original.clone();
        start = System.nanoTime();
        iterativeQuickSort(arr2);
        end = System.nanoTime();
        printArray(arr2, "迭代版快排结果:");
        System.out.println("耗时: " + (end - start) + " 纳秒\n");

        // 测试内省排序
        int[] arr3 = original.clone();
        start = System.nanoTime();
        introsort(arr3);
        end = System.nanoTime();
        printArray(arr3, "内省排序结果:");
        System.out.println("耗时: " + (end - start) + " 纳秒\n");

        // 测试大数组性能
        System.out.println("=== 大数组性能测试 ===");
        testLargeArray();
    }

    private static void testLargeArray() {
        int size = 100000;
        int[] largeArray = new int[size];
        Random rand = new Random(42);

        // 生成随机数组
        for (int i = 0; i < size; i++) {
            largeArray[i] = rand.nextInt(size);
        }

        // 测试各种优化
        int[] arr1 = largeArray.clone();
        long start = System.nanoTime();
        quickSort(arr1);
        long end = System.nanoTime();
        System.out.println("优化版快排 (" + size + " 元素): " + (end - start) / 1_000_000 + " 毫秒");

        int[] arr2 = largeArray.clone();
        start = System.nanoTime();
        iterativeQuickSort(arr2);
        end = System.nanoTime();
        System.out.println("迭代版快排 (" + size + " 元素): " + (end - start) / 1_000_000 + " 毫秒");

        int[] arr3 = largeArray.clone();
        start = System.nanoTime();
        introsort(arr3);
        end = System.nanoTime();
        System.out.println("内省排序 (" + size + " 元素): " + (end - start) / 1_000_000 + " 毫秒");
    }
}
