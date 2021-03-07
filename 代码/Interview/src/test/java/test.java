import java.util.Arrays;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 20:38
 */
public class test {
    // 1 2 3 4 5 6
    static int[] arr = {3, 4, 1, 2, 5, 6};
    static int[] lArr;

    public static void main(String[] args) {
        lArr = new int[arr.length >> 1];
        quick(0, arr.length);
        System.out.println(Arrays.toString(arr));

    }

    private static void quick(int begin, int end) {
        if (end - begin < 2) return;
        int mid = q(begin, end);
        quick(begin, mid);
        quick(mid + 1, end);

    }

    private static int q(int begin, int end) {
        int q = arr[begin];
        end--;
        while (begin < end) {
            while (begin < end) {
                if (q < arr[end]) {
                    end--;
                } else {
                    arr[begin++] = arr[end];
                    break;
                }
            }
            while (begin < end) {
                if (q > arr[begin]) {
                    begin++;
                } else {
                    arr[end--] = arr[begin];
                    break;
                }
            }
        }
        arr[begin] = q;
        return begin;
    }

}
