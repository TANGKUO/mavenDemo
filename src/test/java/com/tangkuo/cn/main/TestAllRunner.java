package com.tangkuo.cn.main;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * 
 * @ClassName: TestAllRunner
 * @Description: (执行测试用例,测试套件意味着捆绑几个单元测试用例并且一起执行他们。在 JUnit 中，@RunWith 和 @Suite
 *               注释用来运行套件测试)
 * @author tangkuo
 * @date 2017年6月28日 下午8:37:34
 *
 */
public class TestAllRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(JunitTestSuite.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}

}
