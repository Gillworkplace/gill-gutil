package com.gill.gutil.validation;

import java.util.Set;

import com.gill.gutil.log.ILogger;
import com.gill.gutil.log.LoggerFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * ValidatorUtil 校验工具类
 *
 * @author gill
 * @version 2022/11/24 14:20
 **/
public class ValidatorUtil {

    private static final ILogger log = LoggerFactory.getLogger(ValidatorUtil.class);

    private static Validator VALIDATOR;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            VALIDATOR = factory.getValidator();
        } catch (Exception e) {
            log.error("validator util init failed. reason: {}", e.getMessage());
        }
    }

    /**
     * 校验是否满足规则
     *
     * @param bean 实例
     * @return 是否
     * @param <T> 泛型
     */
    public static <T> boolean validate(T bean) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(bean);
        return violations.isEmpty();
    }

    /**
     * 校验
     *
     * @param bean 实例
     * @param groups 分组
     * @return 校验结果
     * @param <T> 泛型
     */
    public static <T> String validateWithStr(T bean, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(bean, groups);
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<T> violation : violations) {
            sb.append(violation.getMessage()).append(";");
        }
        return sb.toString();
    }
}
