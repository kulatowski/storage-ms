package com.ku.storagems.service;

import com.ku.storagems.dto.UserFilesWrapper;

public interface StorageService {

    /**
     * @return list of user files based on configuration
     */
    UserFilesWrapper getUserFilesWrapperBasedOnConfiguration();

    /**
     * @param searchPath custom path to search files
     * @return list of user files based on configuration and by custom path
     */
    UserFilesWrapper getUserFilesWrapperBasedOnConfigurationByPath(String searchPath);
}
