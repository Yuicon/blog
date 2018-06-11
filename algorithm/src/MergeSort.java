import java.util.Arrays;
import java.util.List;

/**
 * @author Yuicon
 * @Date 2018/6/11
 */
public class MergeSort {

    /**
     * 待排序的列表
     */
    private static List<Integer> list = Arrays.asList(9, 4, 1, 5, 7, 2, 8, 6, 3, 9, 2);

    public static void main(String[] args) {
        int[] temp = new int[list.size()];
        sort(0, list.size() - 1, temp);
        Arrays.stream(temp).forEach(System.out::println);
    }


    private static void sort(int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            //左边归并排序，使得左子序列有序
            sort(left, mid, temp);
            //右边归并排序，使得右子序列有序
            sort(mid + 1, right, temp);
            //将两个有序子数组合并操作
            merge(left, mid, right, temp);
        }
    }

    private static void merge(int left, int mid, int right, int[] temp) {
        int i = left;
        int k = left, j = mid + 1;
        //将两个子序列较小的元素填充进temp的左边
        while (k <= mid && j <= right) {
            if (list.get(k) <= list.get(j)) {
                temp[i++] = list.get(k++);
            } else {
                temp[i++] = list.get(j++);
            }
        }
        //将左边剩余元素填充进temp中
        while (i <= right && k <= mid) {
            temp[i++] = list.get(k++);
        }
        //将右序列剩余元素填充进temp中
        while (i <= right && j <= right) {
            temp[i++] = list.get(j++);
        }
        //将temp中的元素全部拷贝到原数组中
        for (int p = left; p <= right; p++) {
            list.set(p, temp[p]);
        }
    }

}
