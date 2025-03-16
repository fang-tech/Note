package Java.Sort;

import java.util.Arrays;

class QuickSort {
    
    /**
     * 在区间内选定一个基准值, 
     * 将所有小于基准值的值移向基准值左边, 大于的移向右边
     * @param array 数组
     * @param low 区间的左边界
     * @param high 区间的右边界
     * @return 基准值的index
     */
    public static int partitrion(int[] array, int low, int high){
        int privot = array[high]; // 基准值
        int index = low; // 最后指向基准值在的位置
        // [low, high]
        for (int i = low; i < high; i++) {
            // 将所有小于基准值的值移向基准值的左边(数组的最左边)
            if (array[i] <= privot) {
                // swap array[i] and array[pointer]
                int temp = array[i];
                array[i] = array[index];
                array[index] = temp;
                index++;
            }
            // System.out.println(Arrays.toString(array));
        }
        // swap array[index] and array[high] (the last elem)
        int temp = array[index];
        array[index] = array[high];
        array[high] = temp;
        return index;
    }

    /**
     * 快速排序
     * @param array 数据数组, 传入的范围是左闭右开的
     * @param low 包括这个元素
     * @param high 不包括这个元素
     */
    public static void quickSort(int[] array, int low, int high) {
        if (low >= high) return ;
        int position = partitrion(array, low, high);
        quickSort(array, low, position - 1);
        quickSort(array, position + 1, high);
    }

    public static void main(String[] args) {
        int[] array = {9, 4, 6, 8, 3, 10, 5, 2, 7, 1}; // 创建一个包含10个元素的无序数组
        System.out.println("排序前: " + Arrays.toString(array));
        quickSort(array, 0, array.length - 1);  // 调用快速排序
        System.out.println("排序后: " + Arrays.toString(array));
    }
}