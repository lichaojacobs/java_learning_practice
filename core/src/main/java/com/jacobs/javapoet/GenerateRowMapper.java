package com.jacobs.javapoet;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import javax.lang.model.element.Modifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

/**
 * Created by lichao on 2017/7/30.
 */
@Slf4j
public class GenerateRowMapper {

  public static void generateRowmapper(Class clazz, String outPath) {
    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);

    MethodSpec.Builder mapperBuilder = MethodSpec.methodBuilder("mapRow")
        .addModifiers(Modifier.PUBLIC)
        .returns(TypeName.get(clazz))
        .addParameter(ResultSet.class, "rs")
        .addParameter(int.class, "rowNum")
        .addException(SQLException.class)
        .addStatement("$T model= new $T()", clazz, clazz);

    Arrays.stream(pds).forEach(pd -> {
      if (pd.getWriteMethod() != null) {
        if (String.class.isAssignableFrom(pd.getPropertyType())) {
          mapperBuilder.addStatement("model.$N(rs.getString($S))", pd.getWriteMethod().getName(),
              underscoreName(pd.getName()));
        } else if (Integer.class.isAssignableFrom(pd.getPropertyType())) {
          mapperBuilder.addStatement("model.$N(rs.getInt($S))", pd.getWriteMethod().getName(),
              underscoreName(pd.getName()));
        }
      }
    });

    TypeSpec element = TypeSpec
        .classBuilder(String.format("%sRowmapper", clazz.getSimpleName()))
        .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
        .addSuperinterface(
            ParameterizedTypeName
                .get(ClassName.get(RowMapper.class), ClassName.get(clazz)))
        .addMethod(mapperBuilder.addStatement("return model").build())
        .build();

    JavaFile javaFile = JavaFile.builder("com.example.javapoet", element)
        .build();
    try {
      javaFile.writeTo(new File(outPath));
      javaFile.writeTo(System.out);
    } catch (Exception ex) {
      log.error("generateRowmapper error ", ex);
    }
  }

  private static String underscoreName(String name) {
    if (!StringUtils.hasLength(name)) {
      return "";
    } else {
      StringBuilder result = new StringBuilder();
      result.append(lowerCaseName(name.substring(0, 1)));

      for (int i = 1; i < name.length(); ++i) {
        String s = name.substring(i, i + 1);
        String slc = lowerCaseName(s);
        if (!s.equals(slc)) {
          result.append("_").append(slc);
        } else {
          result.append(s);
        }
      }

      return result.toString();
    }
  }

  private static String lowerCaseName(String name) {
    return name.toLowerCase(Locale.US);
  }
}
