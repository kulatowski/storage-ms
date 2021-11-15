package com.ku.storagems.azure.service;

import com.ku.storagems.exception.UserFileNotFoundException;
import com.ku.storagems.exception.StorageNotFoundException;
import com.microsoft.graph.http.GraphServiceException;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.requests.DriveCollectionPage;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class GraphApiService {

    private static final String ONE_DRIVE_NAME = "OneDrive";

    private final GraphServiceClient graphClient;

    public List<DriveItem> getUserDriveItemList(String userId, String searchPath) {
        String driveId = getAzureDriveId(userId);

        return getDriveItemList(driveId, userId, searchPath);
    }

    private String getAzureDriveId(String userId) {
        DriveCollectionPage driveList = graphClient.users(userId).drives().buildRequest().get();

        if (driveList == null || driveList.getCurrentPage().isEmpty()) {
            log.error("Error during getting drive for user {} due to drive not found", userId);
            throw new StorageNotFoundException("User " + userId + " has no drive available");
        }

        Drive drive = driveList.getCurrentPage().stream()
                .filter(drv -> drv.name != null && drv.name.equals(ONE_DRIVE_NAME))
                .findFirst()
                .orElseThrow(() -> new StorageNotFoundException("User " + userId + " has no drive available"));

        return drive.id;
    }

    private List<DriveItem> getDriveItemList(String driveId, String userId, String path) {
        try {
            DriveItemCollectionPage driveItemCollectionPage = graphClient.users(userId)
                    .drives(driveId)
                    .root()
                    .itemWithPath(path)
                    .children()
                    .buildRequest()
                    .get();

            return driveItemCollectionPage.getCurrentPage();
        } catch (GraphServiceException exception) {
            log.error("Error during getting drive item list for user {} and path {} due to {}", userId, path, exception.getResponseMessage());
            throw new UserFileNotFoundException("Error during getting drive files due to: " + exception.getResponseMessage());
        }
    }

}
