package com.ku.storagems.azure.service;

import com.ku.storagems.dto.UserFiles;
import com.ku.storagems.dto.UserFilesWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ku.storagems.azure.service.AzureStorageServiceFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AzureStorageServiceTest {

    @MockBean
    private GraphApiService graphApiService;

    @Autowired
    private AzureStorageService userDriveFileService;

    @Test
    void should_return_and_verify_user_files() {
        //given
        when(graphApiService.getUserDriveItemList(SAMPLE_USER_ID, MAIN_SEARCH_PATH)).thenReturn(sampleDriveItemList());
        when(graphApiService.getUserDriveItemList(SAMPLE_USER_ID, MAIN_SEARCH_PATH+"/"+SAMPLE_FILE_NAME_4)).thenReturn(List.of(sampleChildDriveItem()));

        UserFilesWrapper expectedWrapper = expectedUserFileWrapper();

        //when
        UserFilesWrapper wrapper = userDriveFileService.getUserFilesWrapperBasedOnConfiguration();

        //then
        assertEquals(expectedWrapper.getUserId(), wrapper.getUserId());
        assertEquals(expectedWrapper.getSearchPath(), wrapper.getSearchPath());
        assertEquals(expectedWrapper.getUserFilesList().size(), wrapper.getUserFilesList().size());

        //and
        List<UserFiles> sortedExpectedUserFilesList = expectedWrapper.getUserFilesList()
                .stream()
                .sorted(Comparator.comparing(UserFiles::getId))
                .collect(Collectors.toList());

        List<UserFiles> sortedActualUserFilesList = wrapper.getUserFilesList()
                .stream()
                .sorted(Comparator.comparing(UserFiles::getId))
                .collect(Collectors.toList());

        //then
        compareLists(sortedExpectedUserFilesList, sortedActualUserFilesList);
    }

    private void compareLists(List<UserFiles> expectedList, List<UserFiles> actualList) {
        for(int i=0; i<expectedList.size(); i++) {
            UserFiles expected = expectedList.get(i);
            UserFiles actual = actualList.get(i);

            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getModifiedDateTime(), actual.getModifiedDateTime());

            if(expected.isDirectory()) {
                assertTrue(actual.isDirectory());
                compareLists(expected.getDirectoryFiles(), actual.getDirectoryFiles());
            } else {
                assert expected.isDirectory() == actual.isDirectory();
            }
        }
    }
}