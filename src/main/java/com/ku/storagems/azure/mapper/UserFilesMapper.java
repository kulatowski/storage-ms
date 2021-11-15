package com.ku.storagems.azure.mapper;

import com.ku.storagems.dto.UserFiles;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserFilesMapper {

    default List<UserFiles> mapList(List<DriveItem> driveItemList) {
        if (driveItemList.isEmpty()) {
            return Collections.emptyList();
        }
        return driveItemList.stream().map(this::map).collect(Collectors.toList());
    }

    @Mapping(target = "modifiedDateTime", source = "lastModifiedDateTime", qualifiedByName = "mapModifiedDateTime")
    @Mapping(target = "isDirectory", source = "folder", qualifiedByName = "mapIsDirectory")
    UserFiles map(DriveItem driveItem);

    @Named("mapModifiedDateTime")
    default LocalDateTime mapModifiedDateTime(OffsetDateTime lastModifiedDateTime) {
        return lastModifiedDateTime.toLocalDateTime();
    }

    @Named("mapIsDirectory")
    default boolean mapIsDirectory(Folder folder) {
        return folder != null;
    }
}
