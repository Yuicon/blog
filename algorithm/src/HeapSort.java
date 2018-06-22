import java.util.Arrays;
import java.util.List;

/**
 * @author Yuicon
 * @Date 2018/6/21
 */
public class HeapSort {

    /**
     * 待排序的列表
     */
    private static List<Integer> list = Arrays.asList(9, 4, 1, 5, 7, 20, 8, 6, 3, 9, 2, 10, 193, 31);

    public static void main(String[] args) {
        adjustHeap(list.size() - 1);
        for (int j = list.size() - 1; j > 0; j--) {
            //将顶点交换至有序区
            swap(0, j);
            //重新构建大顶堆
            adjustHeap(j - 1);
        }
        System.out.println(list);
    }

    /**
     * 构建大顶堆
     *
     * @param e 结束索引
     */
    private static void adjustHeap(int e) {
        //从最后一个非叶子结点从下至上，从右至左调整结构
        for (int i = (e + 1) / 2 - 1; i >= 0; i--) {
            fix(i, e);
        }
    }

    /**
     * 调整大顶堆
     *
     * @param i 非叶子结点索引
     * @param e 结束索引
     */
    private static void fix(int i, int e) {
        int left = 2 * i + 1, right = 2 * i + 2;
        //如果右节点存在,将大的子节点交换至左节点并向下调整大顶堆
        if (right <= e && list.get(right) > list.get(left)) {
            swap(left, right);
            fix(right, e);
        }
        //如果左节点存在且大于父节点,进行交换并向下调整大顶堆
        if (left <= e && list.get(i) < list.get(left)) {
            swap(i, left);
            fix(left, e);
        }
    }

    /**
     * 交换元素
     *
     * @param i 元素索引
     * @param j 另一个元素索引
     */
    private static void swap(int i, int j) {
        list.set(i, list.get(i) + list.get(j));
        list.set(j, list.get(i) - list.get(j));
        list.set(i, list.get(i) - list.get(j));
    }

}
