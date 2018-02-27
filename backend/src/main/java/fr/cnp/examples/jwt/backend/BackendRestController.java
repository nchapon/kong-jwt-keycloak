package fr.cnp.examples.jwt.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nchapon on 22/12/17.
 */
@RestController
public class BackendRestController {

    @GetMapping("/secured")
    public Message getSecured(){
        return new Message("Hello user");
    }

    @GetMapping("/admin")
    public Message getAdmin(){
        return new Message("Hello admin");
    }

    @GetMapping("/403")
    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    public Message accessDenied(){
        return new Message("Access Denied");
    }




}
