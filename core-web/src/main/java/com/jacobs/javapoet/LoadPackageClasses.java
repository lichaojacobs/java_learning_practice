package com.jacobs.javapoet;

import com.jacobs.aspects.AutoGenerateRowMapper;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

/**
 * Created by lichao on 2017/7/30.
 */
//@Configuration
@Slf4j
public class LoadPackageClasses implements BeanFactoryPostProcessor {

  public void postProcessBeanFactory(ConfigurableListableBeanFactory applicationContext)
      throws BeansException {
    String[] beanNames = applicationContext
        .getBeanNamesForAnnotation(AutoGenerateRowMapper.class);
    if (beanNames != null) {
      Object entrance = applicationContext.getBean(beanNames[0]);

      AutoGenerateRowMapper autoGenerateRowMapper = entrance
          .getClass().getAnnotation(AutoGenerateRowMapper.class);
      if (autoGenerateRowMapper != null) {
        LoadPackageClasses loadPackageClasses = new LoadPackageClasses(
            new String[]{autoGenerateRowMapper.scanPackage()},
            autoGenerateRowMapper.annotationFilter());
        try {
          loadPackageClasses.getClassSet()
              .forEach(aClass -> GenerateRowMapper
                  .generateRowmapper(aClass, autoGenerateRowMapper.outPath()));
        } catch (Exception ex) {
          log.error("LoadPackageClasses postProcessBeanFactory error: ", ex);
        }
      }
    }
  }

  private static final String RESOURCE_PATTERN = "/**/*.class";

  private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

  private List<String> packagesList = new LinkedList<>();

  private List<TypeFilter> typeFilters = new LinkedList<>();

  private Set<Class<?>> classSet = new HashSet<>();


  public LoadPackageClasses() throws Exception {

  }


  public LoadPackageClasses(String[] packagesToScan,
      Class<? extends Annotation>... annotationFilter) {
    if (packagesToScan != null) {
      for (String packagePath : packagesToScan) {
        this.packagesList.add(packagePath);
      }
    }
    if (annotationFilter != null) {
      for (Class<? extends Annotation> annotation : annotationFilter) {
        typeFilters.add(new AnnotationTypeFilter(annotation, false));
      }
    }
  }

  public Set<Class<?>> getClassSet() throws IOException, ClassNotFoundException {
    this.classSet.clear();
    if (!this.packagesList.isEmpty()) {
      for (String pkg : this.packagesList) {
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
            ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
        Resource[] resources = this.resourcePatternResolver.getResources(pattern);
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
            this.resourcePatternResolver);
        for (Resource resource : resources) {
          if (resource.isReadable()) {
            MetadataReader reader = readerFactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            if (matchesEntityTypeFilter(reader, readerFactory)) {
              this.classSet.add(Class.forName(className));
            }
          }
        }
      }
    }
    return this.classSet;
  }

  private boolean matchesEntityTypeFilter(MetadataReader reader,
      MetadataReaderFactory readerFactory) throws IOException {
    if (!this.typeFilters.isEmpty()) {
      for (TypeFilter filter : this.typeFilters) {
        if (filter.match(reader, readerFactory)) {
          return true;
        }
      }
    }
    return false;
  }
}
