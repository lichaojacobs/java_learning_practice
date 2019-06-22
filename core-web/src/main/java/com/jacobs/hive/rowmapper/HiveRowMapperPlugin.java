package com.jacobs.hive.rowmapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Primitives;

/**
 * @author lichao
 * Created on 2019-06-20
 */
public class HiveRowMapperPlugin {

    private static final Function<Object, String> BYTES_TO_UTF8STRING =
            bytes -> bytes instanceof String ? bytes.toString() : new String((byte[]) bytes, Charset.forName("UTF-8"));

    private static final Function<PropertyDescriptor, Class> PD_TO_GENERIC =
            pd -> getCollectionGeneric(pd.getReadMethod());


    private final Predicate<PropertyDescriptor> predicate;
    private final ColumnValue columnValue;

    private HiveRowMapperPlugin(Predicate<PropertyDescriptor> predicate,
            ColumnValue columnValue) {
        this.predicate = predicate;
        this.columnValue = columnValue;
    }

    boolean test(PropertyDescriptor pd) {
        return predicate.test(pd);
    }

    Object getColumnValue(Object object, PropertyDescriptor pd) {
        return columnValue.get(object, pd);
    }

    public static MapperPluginsBuilder of(Predicate<PropertyDescriptor> predicate) {
        return new MapperPluginsBuilder(predicate);
    }

    public static MapperPluginsBuilder ofNot(Predicate<PropertyDescriptor> predicate) {
        return of(predicate.negate());
    }

    public static MapperPluginsBuilder of(Class clazz) {
        return of(pd -> clazz.isAssignableFrom(pd.getPropertyType()));
    }

    @FunctionalInterface
    public interface ColumnValue {

        Object get(Object object, PropertyDescriptor pd);
    }

    public static class MapperPluginsBuilder {

        private Predicate<PropertyDescriptor> predicate;

        public MapperPluginsBuilder(Predicate<PropertyDescriptor> predicate) {
            this.predicate = predicate;
        }

        public HiveRowMapperPlugin columnValue(ColumnValue columnValue) {
            return new HiveRowMapperPlugin(predicate, columnValue);
        }
    }

    static final HiveRowMapperPlugin JSON_PLUGIN =
            HiveRowMapperPlugin.ofNot(pd -> pd.getPropertyType().isPrimitive()
                    || Primitives.isWrapperType(pd.getPropertyType())
                    || String.class.isAssignableFrom(pd.getPropertyType())
                    || Date.class.isAssignableFrom(pd.getPropertyType()))
                    .columnValue((object, pd) ->
                            Optional.ofNullable(object)
                                    .map(BYTES_TO_UTF8STRING)
                                    .map(json -> JSONObject.parseObject(json, pd.getPropertyType()))
                                    .orElse(null));

    static final HiveRowMapperPlugin JSON_OBJECT_PLUGIN =
            HiveRowMapperPlugin.of(JSONObject.class)
                    .columnValue((object, pd) ->
                            Optional.ofNullable(object)
                                    .map(BYTES_TO_UTF8STRING)
                                    .map(s -> JSONObject.parseObject(s, JSONObject.class))
                                    .orElse(new JSONObject()));

    static final HiveRowMapperPlugin LIST_PLUGIN =
            HiveRowMapperPlugin.of(List.class)
                    .columnValue((object, pd) ->
                            Optional.ofNullable(object)
                                    .map(BYTES_TO_UTF8STRING)
                                    .map(json -> JSONObject.parseObject(json, PD_TO_GENERIC.apply(pd)))
                                    .orElse(new ArrayList<>()));

    static final HiveRowMapperPlugin MAP_PLUGIN =
            HiveRowMapperPlugin.of(Map.class)
                    .columnValue((object, pd) ->
                            Optional.ofNullable(object)
                                    .map(BYTES_TO_UTF8STRING)
                                    .map(json -> JSONObject.parseObject(json, Map.class))
                                    .orElse(new HashMap<>()));

    static final HiveRowMapperPlugin ENUM_PLUGIN =
            HiveRowMapperPlugin.of(Enum.class)
                    .columnValue((o, pd) -> {
                        try {
                            if (o == null) {
                                return null;
                            }
                            if (o instanceof Number) {
                                Number number = (Number) o;
                                Method method = pd.getPropertyType()
                                        .getMethod("valueByIndex", Integer.TYPE);
                                return method.invoke(null, number.intValue());
                            } else {
                                String val = o.toString();
                                Method method = pd.getPropertyType().getMethod("fromString", String.class);
                                return method.invoke(null, val);
                            }
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(
                                    "getColumnValue error, NoSuchMethod : valueByIndex or fromString", e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(
                                    "getColumnValue error, InvocationTargetException ", e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(
                                    "getColumnValue error, IllegalAccessException ", e);
                        }
                    });

    private static Class<?> getCollectionGeneric(Method method) {
        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            Type fc = method.getGenericReturnType();
            if (fc == null) {
                return Object.class;
            }
            if (fc instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) fc;
                return (Class) pt.getActualTypeArguments()[0];
            }
            return Object.class;
        }
        return Object.class;
    }
}
