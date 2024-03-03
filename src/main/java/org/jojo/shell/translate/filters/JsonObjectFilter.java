package org.jojo.shell.translate.filters;

/**
 * @author jinxin
 * @version 0.1.0
 * @date 2024/3/2 21:49
 * @since 1.0.0
 */
public interface JsonObjectFilter {

    String getDescription();

    String getFieldName();

    Boolean needFilter();
}
