package com.jacobs.jersey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lichao on 2017/4/19.
 */
public final class RuntimeAnnotations {

  private static final Constructor<?> AnnotationInvocationHandler_constructor;
  private static final Constructor<?> AnnotationData_constructor;
  private static final Method Class_annotationData;
  private static final Field Class_classRedefinedCount;
  private static final Field AnnotationData_annotations;
  private static final Field AnnotationData_declaredAnotations;
  private static final Method Atomic_casAnnotationData;
  private static final Class<?> Atomic_class;

  public RuntimeAnnotations() {
  }

  public static <T extends Annotation> void putAnnotation(Class<?> c, Class<T> annotationClass,
      Map<String, Object> valuesMap) {
    putAnnotation(c, annotationClass, annotationForMap(annotationClass, valuesMap));
  }

  public static <T extends Annotation> void putAnnotation(Class<?> c, Class<T> annotationClass,
      T annotation) {
    try {
      Object annotationData;
      Object newAnnotationData;
      do {
        int e = Class_classRedefinedCount.getInt(c);
        annotationData = Class_annotationData.invoke(c, new Object[0]);
        newAnnotationData = createAnnotationData(c, annotationData, annotationClass, annotation, e);
      } while (!((Boolean) Atomic_casAnnotationData
          .invoke(Atomic_class, new Object[]{c, annotationData, newAnnotationData}))
          .booleanValue());

    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException var6) {
      throw new IllegalStateException(var6);
    }
  }

  private static <T extends Annotation> Object createAnnotationData(Class<?> c,
      Object annotationData, Class<T> annotationClass, T annotation, int classRedefinedCount)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Map annotations = (Map) AnnotationData_annotations.get(annotationData);
    Map declaredAnnotations = (Map) AnnotationData_declaredAnotations.get(annotationData);
    LinkedHashMap newDeclaredAnnotations = new LinkedHashMap(annotations);
    newDeclaredAnnotations.put(annotationClass, annotation);
    LinkedHashMap newAnnotations;
    if (declaredAnnotations == annotations) {
      newAnnotations = newDeclaredAnnotations;
    } else {
      newAnnotations = new LinkedHashMap(annotations);
      newAnnotations.put(annotationClass, annotation);
    }

    return AnnotationData_constructor.newInstance(
        new Object[]{newAnnotations, newDeclaredAnnotations, Integer.valueOf(classRedefinedCount)});
  }

  public static <T extends Annotation> T annotationForMap(final Class<T> annotationClass,
      final Map<String, Object> valuesMap) {
    return (T) AccessController.doPrivileged((PrivilegedAction) () -> {
      InvocationHandler handler;
      try {
        handler = (InvocationHandler) RuntimeAnnotations.AnnotationInvocationHandler_constructor
            .newInstance(new Object[]{annotationClass, new HashMap(valuesMap)});
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException var3) {
        throw new IllegalStateException(var3);
      }

      return (Annotation) Proxy
          .newProxyInstance(annotationClass.getClassLoader(), new Class[]{annotationClass},
              handler);
    });
  }

  static {
    try {
      Class e = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
      AnnotationInvocationHandler_constructor = e
          .getDeclaredConstructor(new Class[]{Class.class, Map.class});
      AnnotationInvocationHandler_constructor.setAccessible(true);
      Atomic_class = Class.forName("java.lang.Class$Atomic");
      Class AnnotationData_class = Class.forName("java.lang.Class$AnnotationData");
      AnnotationData_constructor = AnnotationData_class
          .getDeclaredConstructor(new Class[]{Map.class, Map.class, Integer.TYPE});
      AnnotationData_constructor.setAccessible(true);
      Class_annotationData = Class.class.getDeclaredMethod("annotationData", new Class[0]);
      Class_annotationData.setAccessible(true);
      Class_classRedefinedCount = Class.class.getDeclaredField("classRedefinedCount");
      Class_classRedefinedCount.setAccessible(true);
      AnnotationData_annotations = AnnotationData_class.getDeclaredField("annotations");
      AnnotationData_annotations.setAccessible(true);
      AnnotationData_declaredAnotations = AnnotationData_class
          .getDeclaredField("declaredAnnotations");
      AnnotationData_declaredAnotations.setAccessible(true);
      Atomic_casAnnotationData = Atomic_class.getDeclaredMethod("casAnnotationData",
          new Class[]{Class.class, AnnotationData_class, AnnotationData_class});
      Atomic_casAnnotationData.setAccessible(true);
    } catch (NoSuchMethodException | SecurityException | NoSuchFieldException | ClassNotFoundException var2) {
      throw new IllegalStateException(var2);
    }
  }
}
