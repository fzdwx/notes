import java.util.Arrays;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 20:38
 */
public class test {
    // 1 2 3 4 5 6
    static int[] arr = {3, 4, 1, 2, 5, 6};
    //    static int[] arr = genArr(9);
    static int[] lArr;

    public static void main(String[] args) {
        lArr = new int[arr.length >> 1];

        int find = 2;
        int i = find(find);
        System.out.println(arr[i] == find);
        System.out.println(Arrays.toString(arr));
    }

    private static int find(int find) {
        divide(0, arr.length);
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (find > arr[mid]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }

    private static void divide(int begin, int end) {
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        divide(begin, mid);
        divide(mid, end);
        merge(begin, mid, end);
    }

    private static void merge(int begin, int mid, int end) {
        int l1 = 0, l2 = mid - begin;
        int r = mid, a = begin;

        for (int i = l1; i < l2; i++) {
            lArr[i] = arr[i + begin];
        }

        while (l1 < l2) {
            if (r < end && arr[r] < lArr[l1]) {
                arr[a++] = arr[r++];
            } else {
                arr[a++] = lArr[l1++];
            }
        }
    }

    public static int[] genArr(int log) {
        int[] result = new int[log];
        for (int i = 0; i < log; i++) {
            result[i] = i;
        }
        for (int i = 0; i < log; i++) {
            int random = (int) (log * Math.random());
            int temp = result[i];
            result[i] = result[random];
            result[random] = temp;
        }
        return result;
    }
}
