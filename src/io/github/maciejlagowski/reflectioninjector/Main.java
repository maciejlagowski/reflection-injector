package io.github.maciejlagowski.reflectioninjector;

public class Main {
    public static void main(String[] args) throws Exception {
        Object[] controllers = new ReflectionInjector().scanAndConstruct().toArray();
        ((ExampleController)controllers[0]).print();
    }
}
