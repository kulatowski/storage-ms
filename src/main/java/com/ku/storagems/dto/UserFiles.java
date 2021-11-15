package com.ku.storagems.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class UserFiles {

    private String id;
    private String name;
    private LocalDateTime modifiedDateTime;
    private boolean isDirectory;
    private List<UserFiles> directoryFiles;

    public void setDirectoryFiles(List<UserFiles> directoryFiles) {
        this.directoryFiles = directoryFiles;
    }
}
