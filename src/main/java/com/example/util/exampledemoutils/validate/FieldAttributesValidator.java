package com.example.util.exampledemoutils.validate;

import com.example.util.exampledemoutils.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public final class FieldAttributesValidator {
    public static final String ISO_DATE_FORMAT = "ISO_DATE_FORMAT";

    private static final String ERROR_MANDATORY = "%s cannot be null";
    private static final String ERROR_MIN_LENGTH = "%s minimum length is %s";
    private static final String ERROR_MAX_LENGTH = "%s maximum length is %s";
    private static final String ERROR_SIZE = "%s length must be %s";
    private static final String ERROR_MIN_VALUE = "%s must be greater than or equal to %s";
    private static final String ERROR_MAX_VALUE = "%s must be less than or equal to %s";
    private static final String ERROR_ISO_DATE = "%s date format must be ISO 8601";
    private static final String ERROR_DATE_FORMAT = "%s date format must be %s";
    private static final String ERROR_INVALID_DATE = "%s invalid date";
    private static final String ERROR_VALUE = "%s value must be %s";

    public static String validate(Object object) {
        Field[] fields = object.getClass()
                .getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            FieldAttributes attribute = field.getAnnotation(FieldAttributes.class);
            String fieldName = getFieldName(field);
            if (attribute != null) {
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                }

                if (!attribute.nullable() && (value == null || StringUtils.isEmpty(value.toString()))) {
                    return String.format(ERROR_MANDATORY, fieldName);
                }

                if (value == null)
                    continue;

                if (attribute.min() > value.toString()
                        .length())
                    return String.format(ERROR_MIN_LENGTH, fieldName, attribute.min());

                if (attribute.max() < value.toString()
                        .length())
                    return String.format(ERROR_MAX_LENGTH, fieldName, attribute.max());

                if (attribute.size() > 0 && attribute.size() != value.toString()
                        .length())
                    return String.format(ERROR_SIZE, fieldName, attribute.size());

                if (isNumericType(field) && attribute.minValue() > Double.valueOf(value.toString()))
                    return String.format(ERROR_MIN_VALUE, fieldName, attribute.minValue());

                if (isNumericType(field) && attribute.maxValue() < Double.valueOf(value.toString()))
                    return String.format(ERROR_MAX_VALUE, fieldName, attribute.maxValue());

                if (attribute.dateFormat().length > 0) {
                    try {
                        if (attribute.dateFormat()[0].equals(ISO_DATE_FORMAT)) {
                            if (!DateUtil.isISO8601(value.toString()))
                                return String.format(ERROR_ISO_DATE, fieldName);
                        } else {
                            if (new SimpleDateFormat(attribute.dateFormat()[0]).parse(value.toString()) == null)
                                return String.format(ERROR_DATE_FORMAT, fieldName, attribute.dateFormat()[0]);

                        }
                    } catch (java.text.ParseException ex) {
                        return String.format(ERROR_INVALID_DATE, fieldName);
                    }
                }

                if (attribute.allowableValues().length > 0) {
                    if (!Arrays.stream(attribute.allowableValues()).anyMatch(value.toString()::equals)) {
                        return String.format(ERROR_VALUE, fieldName, String.join(",", attribute.allowableValues()));
                    }
                }
            }
        }

        return null;
    }

    private static boolean isNumericType(Field f) {
        return f.getType()
                .equals(Integer.class) || f.getType()
                .equals(Double.class) || f.getType()
                .equals(Float.class);
    }

    private static String getFieldName(Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if (jsonProperty != null) {
            return jsonProperty.value();
        } else {
            return field.getName();
        }
    }
}
