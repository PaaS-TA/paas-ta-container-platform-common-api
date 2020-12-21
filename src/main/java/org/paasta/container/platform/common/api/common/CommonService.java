package org.paasta.container.platform.common.api.common;

import org.paasta.container.platform.common.api.users.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Common Service 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.08.26
 */
@Service
public class CommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

    /**
     * Proc validator string
     *
     * @param reqObject the req object
     * @return the string
     */
    public String procValidator(Object reqObject) {
        String result = Constants.RESULT_STATUS_SUCCESS;

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(reqObject);

        for (ConstraintViolation<Object> violation : violations) {
            result = violation.getMessage();
        }

        return result;
    }

    /**
     * result model 설정(Sets result model)
     *
     * @param reqObject     the req object
     * @param resultCode    the result status code
     * @return the result model
     */
    public Object setResultModel(Object reqObject, String resultCode) {
        try {
            Class<?> aClass = reqObject.getClass();

            Method methodSetResultCode = aClass.getMethod("setResultCode", String.class);
            methodSetResultCode.invoke(reqObject, resultCode);

        } catch (NoSuchMethodException e) {
            LOGGER.error("NoSuchMethodException :: {}", e);
        } catch (IllegalAccessException e1) {
            LOGGER.error("IllegalAccessException :: {}", e1);
        } catch (InvocationTargetException e2) {
            LOGGER.error("InvocationTargetException :: {}", e2);
        }

        return reqObject;
    }


    /**
     * 중복 제거(remove distinct)
     *
     * @param target the target
     * @return the List
     */
    public static List<Users> distinctArray(List<Users> target){
        if(target != null){
            target = target.stream().filter(distinctByKey(o-> o.getUserId())).collect(Collectors.toList());
        }
        return target;
    }


    /**
     * key를 이용한 중복 제거(remove distinct with key value)
     *
     * @param keyExtractor the keyExtractor
     * @param <T> the T
     * @return the T
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
