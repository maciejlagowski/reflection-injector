package io.github.maciejlagowski.reflectioninjector;

import io.github.maciejlagowski.reflectioninjector.annotations.Autowired;
import io.github.maciejlagowski.reflectioninjector.annotations.Controller;

@Controller
public class ExampleController {

    private final ExampleService service;

    @Autowired
    public ExampleController(ExampleService service) {
        this.service = service;
    }

    public void print() {
        service.print();
    }
}
