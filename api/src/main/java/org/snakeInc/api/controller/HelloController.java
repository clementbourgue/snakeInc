package org.snakeInc.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hello")
public class HelloController {

    @GetMapping
    public String HelloController(@RequestParam String name){
        return "Hello " + name;
    }

    @PostMapping
    public String post(@RequestBody BodyParam name){
        return "Post " + name;
    }

    private record BodyParam(String name){

    }
}
