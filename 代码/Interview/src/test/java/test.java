import java.util.Arrays;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 20:38
 */
public class test {
    static int[] arr = {3, 4, 1, 2, 5, 6};
    static int[] lArr;

    public static void main(String[] args) {
        lArr = new int[arr.length >> 1];
        divide(0, arr.length);
        System.out.println(Arrays.toString(arr));
    }

    private static void divide(int begin, int end) {
        if (end - begin < 2) {
            return;
        }
        int mid = (begin + end) >> 1;
        divide(begin, mid);
        divide(mid, end);
        merge(begin, mid, end);
    }

    private static void merge(int begin, int mid, int end) {
        int l1 = 0, l2 = mid - begin;
        int r1 = mid;
        int a1 = begin;

        for (int i = l1; i < l2; i++) {
            lArr[i] = arr[i + begin];
        }
        while (l1 < l2) {
            if (r1 < end && arr[r1] < lArr[l1]) {
                arr[a1++] = arr[r1++];
            } else {
                arr[a1++]=lArr[l1++];
            }
        }
    }


}
