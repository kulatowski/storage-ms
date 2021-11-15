package com.ku.storagems.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserFilesWrapper {

    private List<UserFiles> userFilesList;
    private String userId;
    private String searchPath;
}
