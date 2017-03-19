package com.tangkuo.cn.maths;

import javax.annotation.Resource;

import org.junit.Test;

import com.tangkuo.cn.maths.BinaryTreeNode;

public class BinaryTreeNodesTest {
	@Resource
	private static BinaryTreeNode binaryTreeNode;
	
	@Test
	public void testBinaryTreeNode(){
		test1();
		System.out.println();
		test2();
		System.out.println();
		test3();
		System.out.println();
		test4();
		System.out.println();
		test5();
		System.out.println();
		test6();
		System.out.println();
		test7();
	}
	
	 // 普通二叉树  
	 //              1  
	 //           /     \  
	 //          2       3  
	 //         /       / \  
	 //        4       5   6  
	 //         \         /  
	 //          7       8  
	 private static void test1() {  
	     int[] preorder = {1, 2, 4, 7, 3, 5, 6, 8};  
	     int[] inorder = {4, 7, 2, 1, 5, 3, 8, 6};  
	     BinaryTreeNode root = binaryTreeNode.construct(preorder, inorder);  
	     binaryTreeNode.printTree(root);  
	 }  
	 // 所有结点都没有右子结点  
	 //            1  
	 //           /  
	 //          2  
	 //         /  
	 //        3  
	 //       /  
	 //      4  
	 //     /  
	 //    5  
	 private static void test2() {  
	     int[] preorder = {1, 2, 3, 4, 5};  
	     int[] inorder = {5, 4, 3, 2, 1};  
	     BinaryTreeNode root = binaryTreeNode.construct(preorder, inorder);  
	     binaryTreeNode.printTree(root);  
	 }  
	 // 所有结点都没有左子结点  
	 //            1  
	 //             \  
	 //              2  
	 //               \  
	 //                3  
	 //                 \  
	 //                  4  
	 //                   \  
	 //                    5  
	 private static void test3() {  
	     int[] preorder = {1, 2, 3, 4, 5};  
	     int[] inorder = {1, 2, 3, 4, 5};  
	     BinaryTreeNode root = binaryTreeNode.construct(preorder, inorder);  
	     binaryTreeNode.printTree(root);  
	 }  
	 // 树中只有一个结点  
	 private static void test4() {  
	     int[] preorder = {1};  
	     int[] inorder = {1};  
	     BinaryTreeNode root = binaryTreeNode.construct(preorder, inorder);  
	     binaryTreeNode.printTree(root);  
	 }  
	 // 完全二叉树  
	 //              1  
	 //           /     \  
	 //          2       3  
	 //         / \     / \  
	 //        4   5   6   7  
	 private static void test5() {  
	     int[] preorder = {1, 2, 4, 5, 3, 6, 7};  
	     int[] inorder = {4, 2, 5, 1, 6, 3, 7};  
	     BinaryTreeNode root = binaryTreeNode.construct(preorder, inorder);  
	     binaryTreeNode.printTree(root);  
	 }  
	 // 输入空指针  
	 private static void test6() {  
		 binaryTreeNode.construct(null, null);  
	 }  
	 // 输入的两个序列不匹配  
	 private static void test7() {  
	     int[] preorder = {1, 2, 4, 5, 3, 6, 7};  
	     int[] inorder = {4, 2, 8, 1, 6, 3, 7};  
	     BinaryTreeNode root = binaryTreeNode.construct(preorder, inorder);  
	     binaryTreeNode.printTree(root);  
	 }  
}
