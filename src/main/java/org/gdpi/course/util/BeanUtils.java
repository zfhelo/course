package org.gdpi.course.util;

import java.lang.reflect.Method;

/**
 * @author zhf
 */
public class BeanUtils {
    /**
     * 去掉类属性中前后的空格
     * @param targetObj
     */
    public static void trim(Object targetObj) {
        if (targetObj == null) {
            return;
        }
        for (Method m:targetObj.getClass().getMethods()) {
            if (m.getName().startsWith("set")) {
                try {
                    Method method =
                            targetObj.getClass().getMethod(m.getName().replace("set", "get"));
                    Class<?> type = method.getReturnType();

                    if (type != String.class) {
                        continue;
                    }
                    Object result = method.invoke(targetObj);

                    if (result == null) {
                        continue;
                    }
                    String value = (String)result;
                    m.invoke(targetObj, value.trim());

                } catch (Exception e) {
                }
            }

        }
    }
}
