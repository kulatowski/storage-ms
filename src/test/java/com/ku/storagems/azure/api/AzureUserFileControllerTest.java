package com.ku.storagems.azure.api;

import com.ku.storagems.dto.UserFilesWrapper;
import com.ku.storagems.exception.StorageNotFoundException;
import com.ku.storagems.exception.UserNotFoundException;
import com.ku.storagems.service.StorageService;
import com.microsoft.graph.http.GraphErrorResponse;
import com.microsoft.graph.http.GraphServiceException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;

@AutoConfigureMockMvc
@WebMvcTest(AzureUserFileController.class)
class AzureUserFileControllerTest {

    private static final String API_MAIN_PATH = "/api/azure/user/file";
    private static final String API_SEARCH_FILE_PATH = API_MAIN_PATH + "/path";
    private static final String PARAM_SEARCH_PATH = "searchPath";
    private static final String SAMPLE_SEARCH_PATH = "/test";
    private static final GraphServiceException SAMPLE_GRAPH_SERVICE_EXCEPTION = GraphServiceException.createFromResponse(SAMPLE_SEARCH_PATH, "/", Collections.emptyList(), "", new HashMap<>(), "", 500, new GraphErrorResponse(), true);


    @MockBean
    StorageService azureDriveStorageService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_200_when_get_based_on_configuration() throws Exception {
        //given
        Mockito.when(azureDriveStorageService.getUserFilesWrapperBasedOnConfiguration()).thenReturn(UserFilesWrapper.builder().build());
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(API_MAIN_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void should_return_200_when_get_by_path() throws Exception {
        //given
        Mockito.when(azureDriveStorageService.getUserFilesWrapperBasedOnConfigurationByPath(any())).thenReturn(UserFilesWrapper.builder().build());
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(API_SEARCH_FILE_PATH).param(PARAM_SEARCH_PATH, SAMPLE_SEARCH_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void should_return_404_when_user_not_found() throws Exception {
        //given
        Mockito.when(azureDriveStorageService.getUserFilesWrapperBasedOnConfiguration()).thenThrow(UserNotFoundException.class);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(API_MAIN_PATH))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_return_404_when_storage_not_found() throws Exception {
        //given
        Mockito.when(azureDriveStorageService.getUserFilesWrapperBasedOnConfiguration()).thenThrow(StorageNotFoundException.class);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(API_MAIN_PATH))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_return_404_when_user_file_not_found() throws Exception {
        //given
        Mockito.when(azureDriveStorageService.getUserFilesWrapperBasedOnConfigurationByPath(any())).thenThrow(UserNotFoundException.class);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(API_SEARCH_FILE_PATH).param(PARAM_SEARCH_PATH, SAMPLE_SEARCH_PATH))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_return_500_when_graph_service_error() throws Exception {
        //given
        Mockito.when(azureDriveStorageService.getUserFilesWrapperBasedOnConfiguration()).thenThrow(SAMPLE_GRAPH_SERVICE_EXCEPTION);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(API_MAIN_PATH))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}