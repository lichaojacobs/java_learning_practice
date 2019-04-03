package com.jacobs.javapoet;

/**
 * Created by lichao on 2017/8/8.
 */
//@AutoService(Processor.class)
public class GenerateMapperProcessor {
  //extends AbstractProcessor
//  private Filer filer;
//
//  @Override
//  public synchronized void init(ProcessingEnvironment processingEnv) {
//    super.init(processingEnv);
//    filer = processingEnv.getFiler();
//  }
//
//  @Override
//  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
////    annotations.stream().filter(annotation -> annotation.getQualifiedName().toString().equals(
////        MapperProcessorAnnotation.class.getCanonicalName())).forEach(annotation -> {
////      roundEnv.getElementsAnnotatedWith(MapperProcessorAnnotation.class).forEach(type -> {
////        GenerateRowMapper.generateRowmapper(type.getClass(), "core/src/main/java/");
////      });
////    });
////
////    return true;
//
//    MethodSpec main = MethodSpec.methodBuilder("main") //main代表方法名
//        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)//Modifier 修饰的关键字
//        .addParameter(String[].class, "args") //添加string[]类型的名为args的参数
//        .addStatement("$T.out.println($S)", System.class,
//            "Hello World")//添加代码，这里$T和$S后面会讲，这里其实就是添加了System,out.println("Hello World");
//        .build();
//    TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")//HelloWorld是类名
//        .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
//        .addMethod(main)  //在类中添加方法
//        .build();
//    JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
//        .build();
//
//    try {
//      javaFile.writeTo(System.out);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    return true;
//  }
}
