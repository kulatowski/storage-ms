package com.ku.storagems.azure.service;

import com.ku.storagems.dto.UserFiles;
import com.ku.storagems.dto.UserFilesWrapper;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.File;
import com.microsoft.graph.models.Folder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class AzureStorageServiceFixture {

    static final String SAMPLE_USER_ID = "4444";
    static final String MAIN_SEARCH_PATH = "/";
    static final String SAMPLE_FILE_NAME_4 = "dir2";

    private static final LocalDateTime SAMPLE_LOCAL_DATE_TIME = LocalDateTime.of(2021, 10, 10, 10, 10, 10);
    private static final OffsetDateTime SAMPLE_OFFSET_DATE_TIME = OffsetDateTime.of(SAMPLE_LOCAL_DATE_TIME, ZoneOffset.UTC);
    private static final String SAMPLE_ID_1 = "1";
    private static final String SAMPLE_ID_2 = "2";
    private static final String SAMPLE_ID_3 = "3";
    private static final String SAMPLE_ID_4 = "4";
    private static final String SAMPLE_ID_5 = "5";
    private static final String SAMPLE_FILE_NAME_1 = "file.txt";
    private static final String SAMPLE_FILE_NAME_2 = "file2.txt";
    private static final String SAMPLE_FILE_NAME_3 = "dir";
    private static final String SAMPLE_FILE_NAME_5 = "file5.txt";

    static UserFilesWrapper expectedUserFileWrapper() {
        return UserFilesWrapper.builder()
                .userFilesList(expectedUserFileList())
                .userId(SAMPLE_USER_ID)
                .searchPath(MAIN_SEARCH_PATH)
                .build();
    }

    static List<UserFiles> expectedUserFileList() {
        return Arrays.asList(
                createUserFile(SAMPLE_ID_1, SAMPLE_FILE_NAME_1, false, Collections.emptyList()),
                createUserFile(SAMPLE_ID_3, SAMPLE_FILE_NAME_3, true, Collections.emptyList()),
                createUserFile(SAMPLE_ID_2, SAMPLE_FILE_NAME_2, false, Collections.emptyList()),
                createUserFile(SAMPLE_ID_4, SAMPLE_FILE_NAME_4, true, List.of(sampleChildUserFile())));
    }

    static List<DriveItem> sampleDriveItemList() {
        return Arrays.asList(
                createDriveItem(SAMPLE_ID_1, SAMPLE_FILE_NAME_1, false),
                createDriveItem(SAMPLE_ID_2, SAMPLE_FILE_NAME_2, false),
                createDriveItem(SAMPLE_ID_4, SAMPLE_FILE_NAME_4, true),
                createDriveItem(SAMPLE_ID_3, SAMPLE_FILE_NAME_3, true));
    }

    static DriveItem sampleChildDriveItem() {
        return createDriveItem(SAMPLE_ID_5, SAMPLE_FILE_NAME_5, false);
    }

    private static UserFiles createUserFile(String id, String name, boolean isDirectory, List<UserFiles> childUserFileList) {
        return UserFiles.builder()
                .id(id)
                .name(name)
                .modifiedDateTime(SAMPLE_LOCAL_DATE_TIME)
                .isDirectory(isDirectory)
                .directoryFiles(childUserFileList)
                .build();
    }

    private static DriveItem createDriveItem(String id, String name, boolean isDirectory) {
        DriveItem driveItem = new DriveItem();
        driveItem.id = id;
        driveItem.name = name;
        driveItem.lastModifiedDateTime = SAMPLE_OFFSET_DATE_TIME;

        if (isDirectory) {
            driveItem.folder = new Folder();
        } else {
            driveItem.file = new File();
        }

        return driveItem;
    }

    private static UserFiles sampleChildUserFile() {
        return createUserFile(SAMPLE_ID_5, SAMPLE_FILE_NAME_5, false, Collections.emptyList());
    }
}
