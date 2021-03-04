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
        for (int i = 0; i < arr.length; i++) {
            int idx = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[idx]) {
                    idx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[idx];
            arr[idx] = temp;
        }
        System.out.println(Arrays.toString(arr));

    }
}
