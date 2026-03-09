package tests.operations;

import api.operations.OperationsApi;
import api.resources.ResourcesApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.base.BaseTest;
import utils.OperationWaiter;

public class AsyncOperationTest extends BaseTest {

    @Test
    public void shouldDeleteFolderAsync() throws Exception {

        ResourcesApi resourcesApi =
                new ResourcesApi(baseUrl, token);

        OperationsApi operationsApi =
                new OperationsApi(baseUrl, token);

        String folder = "/async_test";

        // создать папку
        resourcesApi.createFolder(folder);

        // удалить
        Response response =
                resourcesApi.deleteResource(folder);

        if (response.statusCode() == 202) {

            String href =
                    response.jsonPath().getString("href");

            String operationId =
                    href.substring(href.lastIndexOf("/") + 1);

            String status =
                    OperationWaiter.waitUntilFinished(
                            operationsApi,
                            operationId,
                            30
                    );

            Assertions.assertEquals("success", status);
        } else {
            Assertions.assertEquals(204, response.statusCode());
        }
    }
}
