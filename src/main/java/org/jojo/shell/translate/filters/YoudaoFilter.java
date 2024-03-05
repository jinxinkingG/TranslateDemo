package org.jojo.shell.translate.filters;

/**
 * @author jinxin
 * @version 0.1.0
 * @date 2024/3/2 21:52
 * @since 1.0.0
 */
public class YoudaoFilter implements JsonObjectFilter{

    private final String description;

    private final String fieldName;

    private final Boolean needFilter;

    public YoudaoFilter(String description, String fieldName, Boolean needFilter) {
        this.description = description;
        this.fieldName= fieldName;
        this.needFilter = needFilter;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public Boolean needFilter(){
        return this.needFilter;
    }
}
