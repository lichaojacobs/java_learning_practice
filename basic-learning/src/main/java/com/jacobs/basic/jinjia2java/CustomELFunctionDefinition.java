package com.jacobs.basic.jinjia2java;

import java.lang.reflect.Method;

import com.hubspot.jinjava.lib.fn.ELFunctionDefinition;

public class CustomELFunctionDefinition extends ELFunctionDefinition {

    private String namespace;
    private String localName;
    private Method method;

    public CustomELFunctionDefinition(String namespace, String localName, Class<?> methodClass,
            String methodName, Class<?>... parameterTypes) {
        super(namespace, localName, methodClass, methodName, parameterTypes);
        this.namespace = namespace;
        this.localName = localName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getLocalName() {
        return localName;
    }

    @Override
    public String getName() {
        return namespace + "." + localName;
    }

    public Method getMethod() {
        return method;
    }

}
