package com.test.branch;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class WebController {

    @Autowired
    private RepoService service;

    @GetMapping("/username/{username}")
    public ResponseEntity getUserGithubInfo(@PathVariable String username) {
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity response = new ResponseEntity<>("{ \"message\": \"invalid username or user does not exist.\"}", headers, HttpStatus.BAD_REQUEST);
        try {
            GithubInfo info = service.getGithubInfo(username);
            if (info != null) {
                response = new ResponseEntity<GithubInfo>(info, headers, HttpStatus.OK);                
            }
        } catch (Exception e) {
            response = new ResponseEntity<String>("{ \"message\": \"we messed up, please try later.\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}