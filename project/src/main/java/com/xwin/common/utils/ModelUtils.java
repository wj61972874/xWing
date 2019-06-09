package com.xwin.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 模型工具类
 */
public class ModelUtils {

    /**
     * 将实体类转化成map
     *
     * @param model      实体类
     * @param ignoreNull 是否忽略实体类null属性
     * @return map
     */
    public static Map<String, Object> toMap(Object model, boolean ignoreNull) {

        Map<String, Object> modelMap = new HashMap<>(16, .75F);

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            String key = field.getName();

            // 跳过 serialVersionUID
            if (key.equals("serialVersionUID")) {
                continue;
            }

            Object value;
            try {
                value = field.get(model);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("无法获取 " + field.getName() + " 字段的值", e);
            }

            // 值为空时跳过
            if (ignoreNull && value == null) {
                continue;
            }
        }

        return modelMap;
    }
}
