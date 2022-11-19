package io.github.maciejlagowski.reflectioninjector;

import io.github.maciejlagowski.reflectioninjector.annotations.Instance;

@Instance
public class ExampleService {

    public void print() {
        System.out.println("Service doing something important");
    }
}
