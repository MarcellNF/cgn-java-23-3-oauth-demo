package de.neuefische.backend.securedController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secured")
public class SecuredController {

    @GetMapping
    public String secured(){
        return "Hello from secured endpoint";
    }

}
