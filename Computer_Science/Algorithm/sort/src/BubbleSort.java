import java.util.Arrays;
import java.util.Random;

/**
 * <p>
 * BubbleSort
 * </p>
 *
 * @author Simon
 * @version 2025.05.23.1.0.0
 * @description 冒泡排序: 过程简单来说就是比较相邻的两个数字, 将大的(小的)放在前面, 这样过了一遍以后, 最大的数字就在最前面
 * @since 2025-05-23
 */

public class BubbleSort {
    public static void bubbleSort(int[] array){
        if (array == null || array.length == 0) {
            return ;
        }
        int len = array.length;
        // 维持的循环不变式: i是原先从[i,length-1]中最大的数字
        // [i, len - 1)就是需要选出最大的数字的范围
        for (int i = 0; i < len; i++) {
            // 将最大的数字移到最前面, 需要从后往前移
            for (int j = len-1; j > i; j--) {
                // 交换两个数字
                if (array[j-1] < array[j]) {
                    int temp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = temp;
                }
            }
        }
        check(array);
    }

    /**
     * 改进版, 如果在交换[j, i)中的数字的时候, 遍历了一遍, 一个数字都没有交换过的时候, 就说明已经排序好了
     * @param array
     */
    public static void bubbleSort2(int[] array){
        if (array == null || array.length == 0) {
            return ;
        }
        int len = array.length;
        boolean flag; // 有没有发生交换
        // 维持的循环不变式: i是原先从[i,length-1]中最大的数字
        // [i, len - 1)就是需要选出最大的数字的范围
        for (int i = 0; i < len; i++) {
            flag = false;
            // 将最大的数字移到最前面, 需要从后往前移
            for (int j = len-1; j > i; j--) {
                // 交换两个数字
                if (array[j-1] < array[j]) {
                    flag = true;
                    int temp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = temp;
                }
            }
            if (!flag) {
                System.out.println("没有需要排序的数了, 排序完成");
                break;
            }
        }
        check(array);
    }

    private static boolean check(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < array[i+1]) {
                System.out.println("排序失败(大到小) " + array[i] + "和 " + array[i+1] + "之间存在错误");
                return false;
            }
        }
        System.out.println("成功排序(大到小)");
        return true;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] array = new int[100];
        for (int i = 0; i < 100; i++) {
            array[i] = random.nextInt(100);
        }
        System.out.println("原来的数组是: " + Arrays.toString(array));
        BubbleSort.bubbleSort2(array);
        System.out.println("排序后的数组是: " + Arrays.toString(array));
        
    }
    
}
