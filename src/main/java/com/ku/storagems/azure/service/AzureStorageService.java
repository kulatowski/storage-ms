package com.ku.storagems.azure.service;

import com.ku.storagems.azure.config.AzureAppConfiguration;
import com.ku.storagems.azure.mapper.UserFilesMapper;
import com.ku.storagems.dto.UserFiles;
import com.ku.storagems.dto.UserFilesWrapper;
import com.ku.storagems.service.StorageService;
import com.microsoft.graph.models.DriveItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class AzureStorageService implements StorageService {

    private final UserFilesMapper userFilesMapper;
    private final GraphApiService graphApiService;
    private final AzureAppConfiguration azureAppConfiguration;

    @Override
    public UserFilesWrapper getUserFilesWrapperBasedOnConfiguration() {
        return getUserFilesListByPath(azureAppConfiguration.getSearchPath());
    }

    @Override
    public UserFilesWrapper getUserFilesWrapperBasedOnConfigurationByPath(String searchPath) {
        return getUserFilesListByPath(searchPath);
    }

    private UserFilesWrapper getUserFilesListByPath(String searchPath) {
        List<DriveItem> driveItemList = graphApiService.getUserDriveItemList(azureAppConfiguration.getUserId(), searchPath);
        List<UserFiles> userFilesList = userFilesMapper.mapList(driveItemList);

        if(isAnyDirectory(userFilesList)) {
            addChildFilesToDirectory(userFilesList, azureAppConfiguration.getSearchPath());
        }

        return UserFilesWrapper.builder()
                .userFilesList(userFilesList)
                .userId(azureAppConfiguration.getUserId())
                .searchPath(searchPath)
                .build();
    }

    private List<UserFiles> addChildFilesToDirectory(List<UserFiles> userFilesList, String path) {
        for (UserFiles userFile : userFilesList) {
            if (userFile.isDirectory()) {
                List<DriveItem> driveItemList = graphApiService.getUserDriveItemList(azureAppConfiguration.getUserId(), path+"/"+userFile.getName());
                List<UserFiles> childUserFilesList = userFilesMapper.mapList(driveItemList);
                userFile.setDirectoryFiles(childUserFilesList);

                if(isAnyDirectory(childUserFilesList)) {
                    addChildFilesToDirectory(childUserFilesList, path + "/" + userFile.getName());
                }
            }
        }

        return userFilesList;
    }

    private static boolean isAnyDirectory(List<UserFiles> userFiles) {
        return userFiles.stream().anyMatch(UserFiles::isDirectory);
    }
}
