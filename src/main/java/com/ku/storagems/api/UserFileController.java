package com.ku.storagems.api;

import com.ku.storagems.dto.UserFilesWrapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserFileController {

    /**
     * @return json http response of list of user files based on configuration
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserFilesWrapper> getUserDriveFiles();

    /**
     * @param searchPath custom path to search files
     * @return json http response of list of user files based on configuration and by custom path
     */
    @GetMapping(value = "/path", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserFilesWrapper> getUserDriveFilesBySearchPath(@RequestParam("searchPath") String searchPath);
}
