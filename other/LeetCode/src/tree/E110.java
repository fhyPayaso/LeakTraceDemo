package tree;

import config.TreeNode;

/**
 * @author : fhyPayaso
 * @since : 2019-02-26 12:34
 */
class E110 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(new E110().isBalanced(root));
    }

    public boolean res = true;

    public boolean isBalanced(TreeNode root) {
        getDeep(root);
        return res;
    }

    public int getDeep(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getDeep(root.left);
        int right = getDeep(root.right);
        if (res) { // 结果只能改变一次
            res = Math.abs(left - right) <= 1;
        }
        return Math.max(left, right) + 1;
    }

//
//    class Deep {
//        public int val;
//
//        public Deep(int val) {
//            this.val = val;
//        }
//    }
//
//    public boolean isBalanced(TreeNode root) {
//        // 始终维护最大深度差，保证引用指向一个对象
//        Deep deep = new Deep(0);
//        getDeep(root, deep);
//        return deep.val <= 1;
//    }
//
//    public int getDeep(TreeNode root, Deep deep) {
//        if (root == null) {
//            return 0;
//        }
//        int left = getDeep(root.left, deep);
//        int right = getDeep(root.right, deep);
//        // 更新深度差
//        deep.val = Math.max(Math.abs(left - right), Math.abs(deep.val));
//        return Math.max(left, right) + 1;
//    }

}
