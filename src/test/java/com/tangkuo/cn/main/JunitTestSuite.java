package com.tangkuo.cn.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.tangkuo.cn.maths.BinaryTreeNodesTest;
import com.tangkuo.cn.maths.FindArraysTest;

/**
 * 
 * @ClassName: JunitTestSuite
 * @Description: (Junit-套件测试)
 * @author tangkuo
 * @date 2017年6月28日 下午8:10:06
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ BinaryTreeNodesTest.class, FindArraysTest.class })
public class JunitTestSuite {

}
