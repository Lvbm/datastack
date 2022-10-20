package com.he3cloud.datastack.service.server.system.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class HomeController {

    @ApiIgnore
    @GetMapping("/")
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui.html");
    }

    @ApiOperation("test")
    @ResponseBody
    @GetMapping("/test")
    public ResponseEntity<String> test() throws Exception {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
