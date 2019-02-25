public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public int[] twoSum(int[] nums, int target) {

        int[] res = new int[2];
        int[]  bucketPos = new int[100000];
        int[]  bucketNeg = new int[100000];



        return res;
    }



    public int[] twoSum2(int[] nums, int target) {

        int[] res = new int[2];
        // 数组已经排好序，左指针指小值，右指针指大值
        int i = 0, j = nums.length - 1;
        while (i < j) {
            // 获取当前数的和，与目标比较
            int sum = nums[i] + nums[j];
            if (sum == target) {
                res[0] = i + 1;
                res[1] = j + 1;
                break;
            } else if (sum > target) {
                j--; // 当前值大则大数减小
            } else {
                i++; // 当前值小则小数增大
            }
        }
        return res;
    }
}
