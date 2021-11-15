package com.ku.storagems.azure.api;

import com.ku.storagems.api.UserFileController;
import com.ku.storagems.dto.UserFilesWrapper;
import com.ku.storagems.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/azure/user/file")
@RequiredArgsConstructor
public class AzureUserFileController implements UserFileController {

    private final StorageService azureStorageService;

    @Override
    public ResponseEntity<UserFilesWrapper> getUserDriveFiles() {
        UserFilesWrapper userDriveFileWrapper = azureStorageService.getUserFilesWrapperBasedOnConfiguration();
        return ResponseEntity.ok(userDriveFileWrapper);
    }

    @Override
    public ResponseEntity<UserFilesWrapper> getUserDriveFilesBySearchPath(@RequestParam("searchPath") String searchPath) {
        UserFilesWrapper userDriveFileWrapper = azureStorageService.getUserFilesWrapperBasedOnConfigurationByPath(searchPath);
        return ResponseEntity.ok(userDriveFileWrapper);
    }
}
