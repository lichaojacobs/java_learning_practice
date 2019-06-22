package com.jacobs.hive.rowmapper;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author lichao
 * Created on 2019-06-20
 */
public class HiveRowMapper<T> extends HiveBeanPropertyRowMappper<T> {

    private List<HiveRowMapperPlugin> mapperPlugins;

    private HiveRowMapper(Class<T> tClass, List<HiveRowMapperPlugin> mapperPlugins) {
        super(tClass);
        this.mapperPlugins = mapperPlugins;
    }

    @Override
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd)
            throws SQLException {
        Object object = rs.getObject(index);
        return mapperPlugins.stream()
                .filter(mapperPlugin -> mapperPlugin.test(pd))
                .map(mapperPlugin -> mapperPlugin.getColumnValue(object, pd))
                .findFirst()
                .orElse(super.getColumnValue(rs, index, pd));
    }

    public static <T> HiveRowMapper<T> getDefault(Class<T> tClass) {
        return HiveRowMapper.<T>builder().tClass(tClass)
                .mapperPlugins(HiveRowMapperPlugin.JSON_PLUGIN)
                .mapperPlugins(HiveRowMapperPlugin.JSON_OBJECT_PLUGIN)
                .mapperPlugins(HiveRowMapperPlugin.LIST_PLUGIN)
                .mapperPlugins(HiveRowMapperPlugin.MAP_PLUGIN)
                .mapperPlugins(HiveRowMapperPlugin.ENUM_PLUGIN)
                .build();
    }

    public static <T> HiveRowMapper.HiveRowMapperBuilder<T> builder() {
        return new HiveRowMapper.HiveRowMapperBuilder<>();
    }

    /**
     * RowMapper 构造类
     */
    public static class HiveRowMapperBuilder<T> {

        private Class<T> tClass;
        private ArrayList<HiveRowMapperPlugin> mapperPlugins;

        HiveRowMapperBuilder() {
        }

        public HiveRowMapper.HiveRowMapperBuilder<T> tClass(Class<T> clzz) {
            this.tClass = clzz;
            return this;
        }

        public HiveRowMapper.HiveRowMapperBuilder<T> mapperPlugins(HiveRowMapperPlugin mapperPlugin) {
            if (this.mapperPlugins == null) {
                this.mapperPlugins = new ArrayList();
            }
            this.mapperPlugins.add(mapperPlugin);
            return this;
        }

        public HiveRowMapper<T> build() {
            try {
                return new HiveRowMapper<>(this.tClass, mapperPlugins == null ? Lists.newArrayList()
                        : mapperPlugins);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        public String toString() {
            return "HiveRowMapperBuilder{" +
                    "tClass=" + tClass +
                    ", mapperPlugins=" + mapperPlugins +
                    '}';
        }
    }
}
