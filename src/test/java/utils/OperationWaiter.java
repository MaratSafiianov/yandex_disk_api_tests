package utils;

import api.operations.OperationsApi;
import io.restassured.response.Response;

public class OperationWaiter {

    public static String waitUntilFinished(
            OperationsApi api,
            String operationId,
            int timeoutSeconds
    ) throws InterruptedException {

        int waited = 0;

        while (waited < timeoutSeconds) {

            Response response =
                    api.getOperationStatus(operationId);

            String status =
                    response.jsonPath().getString("status");

            if ("success".equals(status) ||
                    "failed".equals(status)) {

                return status;
            }

            Thread.sleep(1000);
            waited++;
        }

        throw new RuntimeException(
                "Operation timeout: " + operationId
        );
    }
}
