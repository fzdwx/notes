package 数据结构和算法.算法.回溯;

/**
 * @author like
 * @date 2021-01-15 9:37
 * @contactMe 980650920@qq.com
 * @description
 */
public class eightQueen {
    /*
     * 1.暴力破解
     *   从64个格子中选出8个格子摆放皇后，检查每一种摆法的可行性
     * 一共c(8 64)种摆法
     * 2.根据题意减小暴力程度
     *   很显然，每一行只能放一个皇后，共有8^8种摆法
     * 3.回溯法
     *   选择不同的岔路口
     * */

    static int[] cols;  // index是row，元素是col
    static int ways;

    public static void main(String[] args) {
        placeQueues(4);
    }

    public static void placeQueues(int n) {
        if (n < 1) return;
        cols = new int[n];
        place(0);
    }

    /** 从第几行开始放皇 */
    private static void place(int row) {
        if (row == cols.length) { ways++;show();return; }
        for (int col = 0; col < cols.length; col++) {
            if (isValid(row, col)) { // 找到能放的地方
                cols[row] = col; // 在第row行第col列摆放
                place(row + 1); // 摆放下一列
            }
        }
    }

    /** 判断第row行的第col列能否摆放 */
    private static boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (cols[i] == col) return false;  // 属于col列
            if (row - i == Math.abs(col - cols[i])) return false; // 属于一条斜线
        }
        return true;
    }

    private static void show() {
        for (int row : cols) {
            for (int col = 0; col < cols.length; col++) {
                if (cols[row] == col) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("=====================");
        cols = new int[cols.length];
    }
}
