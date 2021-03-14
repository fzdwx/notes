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

        quick(0, arr.length);
        int find = 2;
        int i = find(find);
        System.out.println(arr[i] == find);
        System.out.println(Arrays.toString(arr));
    }

    private static void quick(int begin, int end) {
        if (end - begin < 2) return;
        int mid = p(begin, end);

        quick(begin, mid);
        quick(mid + 1, end);
    }

    private static int p(int begin, int end) {
        int p = arr[begin];
        end--;
        while (begin < end) {

            while (begin < end) {
                if (p < arr[end]) {
                    end--;
                } else {
                    arr[begin++] = arr[end];
                    break;
                }
            }
            while (begin < end) {
                if (p > arr[begin]) {
                    begin++;
                } else {
                    arr[end--] = arr[begin];
                    break;
                }
            }
        }

        arr[begin] = p;
        return begin;
    }

    private static int find(int i) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (i >arr[mid]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
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
