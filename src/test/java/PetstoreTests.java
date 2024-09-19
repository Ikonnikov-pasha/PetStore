import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PetstoreTests {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/store/order";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    // Positive Test: Place an Order
    @Test
    @DisplayName("Positive Test: Place an Order")
    public void placeOrderPositive() {
        String orderJson = "{\"id\": 1, \"petId\": 1, \"quantity\": 1, \"shipDate\": \"2023-09-18T00:00:00Z\", \"status\": \"placed\", \"complete\": true}";

        Response response = given()
                .contentType("application/json")
                .body(orderJson)
                .when()
                .post();

        response.then().statusCode(200);
    }

    // Positive Test: Get Order by ID
    @Test
    @DisplayName("Positive Test: Get Order by ID")
    public void getOrderByIdPositive() {
        Response response = given()
                .pathParam("orderId", 1)
                .when()
                .get("/{orderId}");

        response.then().statusCode(200);
    }

    // Negative Test: Get Order by Invalid ID
    @Test
    @DisplayName("Negative Test: Get Order by Invalid ID")
    public void getOrderByIdNegative() {
        Response response = given()
                .pathParam("orderId", -1)
                .when()
                .get("/{orderId}");

        response.then().statusCode(404);
    }

    // Positive Test: Delete Order by ID
    @Test
    @DisplayName("Positive Test: Delete Order by ID")
    public void deleteOrderPositive() {
        Response response = given()
                .pathParam("orderId", 1)
                .when()
                .delete("/{orderId}");

        response.then().statusCode(200);
    }

    // Negative Test: Delete Order by Invalid ID
    @Test
    @DisplayName("Negative Test: Delete Order by Invalid ID")
    public void deleteOrderNegative() {
        Response response = given()
                .pathParam("orderId", -1)
                .when()
                .delete("/{orderId}");

        response.then().statusCode(404);
    }

    // Test: Get Order with Non-Numeric ID
    @Test
    @DisplayName("Negative Test: Get Order with Non-Numeric ID")
    public void getOrderWithNonNumericId() {
        Response response = given()
                .pathParam("orderId", "abc") // Invalid ID
                .when()
                .get("/{orderId}");

        response.then().statusCode(404);
    }

    // Test: Delete Non-Existent Order
    @Test
    @DisplayName("Negative Test: Delete Non-Existent Order")
    public void deleteNonExistentOrder() {
        Response response = given()
                .pathParam("orderId", 9999) // Assuming this order does not exist
                .when()
                .delete("/{orderId}");

        response.then().statusCode(404);
    }

    // Test: Get Inventory (Positive Test)
    @Test
    @DisplayName("Positive Test: Get Inventory")
    public void getInventoryPositive() {
        Response response = given()
                .when()
                .get("https://petstore.swagger.io/v2/store/inventory");

        response.then().statusCode(200);
    }

    // Test: Get Order by ID that Exceeds Maximum Value
    @Test
    @DisplayName("Negative Test: Get Order by ID that Exceeds Maximum Value")
    public void getOrderByIdExceedsMaxValue() {
        Response response = given()
                .pathParam("orderId", 999999) // Exceeding maximum limit
                .when()
                .get("/{orderId}");

        response.then().statusCode(404);
    }
}
