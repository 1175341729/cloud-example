package com.springcloud.example.common.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  日期转换工具
 *  release
 */
public class StringToDateConverter implements Converter<String,Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_FORMAT_2 = "yyyy/MM/dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT_2 = "yyyy/MM/dd";
    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = source.trim();
        try {
            SimpleDateFormat formatter;
            if (source.contains("-")) {
                if (source.contains(":")) {
                    formatter = new SimpleDateFormat(DATE_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
                }
                return formatter.parse(source);
            } else if (source.contains("/")) {
                if (source.contains(":")) {
                    formatter = new SimpleDateFormat(DATE_FORMAT_2);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT_2);
                }
                return formatter.parse(source);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", source));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", source));
    }

}
