package com.tangkuo.cn.maths;

import javax.annotation.Resource;

import org.junit.Test;

import com.tangkuo.cn.maths.FindArrays;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: www.tk.com</p>   
 * @author   tangkuo
 * @date    2017年3月19日 下午5:55:06
 */
public class FindArraysTest {
	
	@Resource
	private FindArrays findArrays;
	
	@Test 
	public void testFindArrays(){
		int[][] matrix = { { 1, 2, 8, 9 }, { 2, 4, 9, 12 }, { 4, 7, 10, 13 }, { 6, 8, 11, 15 } };
		System.out.println(findArrays.find(matrix, 7)); // 要查找的数在数组中
		System.out.println(findArrays.find(matrix, 5)); // 要查找的数不在数组中
		System.out.println(findArrays.find(matrix, 1)); // 要查找的数是数组中最小的数字
		System.out.println(findArrays.find(matrix, 15)); // 要查找的数是数组中最大的数字
		System.out.println(findArrays.find(matrix, 0)); // 要查找的数比数组中最小的数字还小
		System.out.println(findArrays.find(matrix, 16)); // 要查找的数比数组中最大的数字还大
		System.out.println(findArrays.find(null, 16)); // 健壮性测试，输入空指针
	}
	
}
