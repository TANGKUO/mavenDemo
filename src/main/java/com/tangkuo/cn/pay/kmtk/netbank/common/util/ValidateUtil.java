package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.tangkuo.cn.pay.TtyException;

public class ValidateUtil {

	/**
	 * 验证器, 是线程安全的
	 */
	private static final Validator validator;

	/**
	 * 使用spring中定义的factory
	 * 
	 * @param validatorFactory
	 */
	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 方法说明：验证类第一个参数<br>
	 * 
	 * @param t
	 */
	public static <T> void validate(T t) throws TtyException {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<T> constraintViolation = constraintViolations.iterator().next();
			throw new TtyException(null, constraintViolation.getMessage());
		}
	}

	/**
	 * 方法说明：验证类第一个参数<br>
	 * 
	 * @param t
	 */
	public static <T> void validate(T t, Class<?>... checkType) throws TtyException {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, checkType);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<T> constraintViolation = constraintViolations.iterator().next();
			throw new TtyException(null, constraintViolation.getMessage());
		}
	}

	/**
	 * 方法说明：同时验证类多个参数<br>
	 * 
	 * @param t
	 */
	public static <T> void validateAll(T t) throws TtyException {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		if (!constraintViolations.isEmpty()) {
			StringBuilder validateError = new StringBuilder();
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				validateError.append(constraintViolation.getMessage()).append(";");
			}
			throw new TtyException(null, validateError.toString());
		}
	}

}