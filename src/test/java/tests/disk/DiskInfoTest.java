package tests.disk;

import api.disk.DiskApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.base.BaseTest;

public class DiskInfoTest extends BaseTest {

    @Test
    public void shouldGetDiskInfo() {

        DiskApi diskApi = new DiskApi(baseUrl, token);

        Response response = diskApi.getDiskInfo();

        Assertions.assertAll(
                () -> Assertions.assertEquals(200, response.statusCode()),
                () -> Assertions.assertNotNull(response.jsonPath().get("total_space"))
        );
    }
}
