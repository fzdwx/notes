package 数据结构和算法.算法.贪心.分治;

/**
 * @author like
 * @date 2021-01-16 15:20
 * @contactMe 980650920@qq.com
 * @description
 */
public class 最大子序列和 {

    public static void main(String[] args) {
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        System.out.println(maxSubarraySum1(array));
        System.out.println(maxSubArraySum(array));
    }

    public static int maxSubarraySum1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return maxSubarraySum1(nums, 0, nums.length);
    }

    public static int maxSubArraySum(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(sum, max);
            }
        }
        return max;
    }

    private static int maxSubarraySum1(int[] nums, int start, int end) {
        if (end - start < 2) return nums[start];
        int mid = (start + end) >> 1;

        int sum = 0;
        int leftMax = Integer.MIN_VALUE;
        for (int i = mid - 1; i >= start; i--) {
            sum += nums[i];
            leftMax = Math.max(leftMax, sum);
        }

        sum = 0;
        int rightMax =Integer.MIN_VALUE ;
        for (int i = mid; i < end; i++) {
            sum += nums[i];
            rightMax = Math.max(rightMax, sum);
        }
        return Math.max(leftMax+rightMax,Math.max(maxSubarraySum1(nums, start, mid), maxSubarraySum1(nums, mid, end)));
    }
}
