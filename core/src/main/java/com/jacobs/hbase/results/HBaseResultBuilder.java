package com.jacobs.hbase.results;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 16/9/19.
 */
@Slf4j
@Data
public class HBaseResultBuilder<T> {
  private Class<T> mappedClass;
  private Map<String, PropertyDescriptor> mappedFields;
  private Set<String> mappedProperties;
  HashSet populatedProperties;
  private BeanWrapper beanWrapper;
  private Result result;
  private String columnFamilyName;
  private T t;


  public HBaseResultBuilder(String columnFamilyName, Result result, Class<T> clazz) {
    this.columnFamilyName = columnFamilyName;
    this.result = result;
    this.mappedClass = clazz;
    mappedFields = new HashMap<>();
    mappedProperties = new HashSet<>();
    populatedProperties = new HashSet<>();
    this.t = BeanUtils.instantiate(clazz);
    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
    PropertyDescriptor[] var3 = pds;
    int var4 = pds.length;
    for (int var5 = 0; var5 < var4; ++var5) {
      PropertyDescriptor pd = var3[var5];
      if (pd.getWriteMethod() != null) {
        this.mappedFields.put(this.lowerCaseName(pd.getName()), pd);
        String underscoredName = this.underscoreName(pd.getName());
        if (!this.lowerCaseName(pd.getName())
            .equals(underscoredName)) {
          this.mappedFields.put(underscoredName, pd);
        }
        this.mappedProperties.add(pd.getName());
      }
    }
    beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(t);
  }

  public HBaseResultBuilder reNewTargetObject() {
    this.t = BeanUtils.instantiate(mappedClass);
    beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(t);
    return this;
  }

  private String underscoreName(String name) {
    if (!StringUtils.hasLength(name)) {
      return "";
    } else {
      StringBuilder result = new StringBuilder();
      result.append(this.lowerCaseName(name.substring(0, 1)));

      for (int i = 1; i < name.length(); ++i) {
        String s = name.substring(i, i + 1);
        String slc = this.lowerCaseName(s);
        if (!s.equals(slc)) {
          result.append("_")
              .append(slc);
        } else {
          result.append(s);
        }
      }

      return result.toString();
    }
  }

  private String lowerCaseName(String name) {
    return name.toLowerCase(Locale.US);
  }

  public HBaseResultBuilder build(String columnName) {
    byte[] value = result.getValue(columnFamilyName.getBytes(), columnName.getBytes());
    if (value == null || value.length == 0) {
      return this;
    } else {
      String field = this.lowerCaseName(columnName.replaceAll(" ", ""));
      PropertyDescriptor pd = this.mappedFields.get(field);
      if (pd == null) {
        log.error("HBaseResultBuilder error: can not find property: " + field);
      } else {
        beanWrapper.setPropertyValue(pd.getName(), Bytes.toString(value));
        populatedProperties.add(pd.getName());
      }
    }
    return this;
  }

  public T fetch() {
    if (CollectionUtils.isNotEmpty(populatedProperties)) {
      return this.t;
    } else {
      return null;
    }
  }

}
