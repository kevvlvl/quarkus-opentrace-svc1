package com.kevvlvl.opentrace.svc1;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class GreetingsResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/health")
          .then()
             .statusCode(200)
             .body(containsString("Hello! Health check"));
    }

}