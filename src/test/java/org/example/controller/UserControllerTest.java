package org.example.controller;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void gerUserTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginGet";
        createRequest.name = "testNameGet";
        createRequest.lastName = "testLastNameGet";
        createRequest.amount = 0.0;

        UserResponse createResponse = createUser(createRequest);

        // get user
        UserResponse getResponse =  webTestClient.get()
                .uri("/api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, getResponse.id);
        assertEquals(createRequest.login, getResponse.login);
        assertEquals(createRequest.name, getResponse.name);
        assertEquals(createRequest.lastName, getResponse.lastName);
        assertEquals(createRequest.amount, getResponse.amount);
        assertEquals(createResponse.creationDate, getResponse.creationDate);
    }

    @Test
    void createUserTest() {
        UserCreateRequest request = new UserCreateRequest();
        request.login = "testLoginCreate";
        request.name = "testNameCreate";
        request.lastName = "testLastNameCreate";
        request.amount = 0.0;

        UserResponse response = createUser(request);

        assertNotNull(response.id);
        assertEquals(request.login, response.login);
        assertEquals(request.name, response.name);
        assertEquals(request.lastName, response.lastName);
        assertEquals(request.amount, response.amount);
        assertEquals(LocalDate.now(), response.creationDate.toLocalDate());
    }

    @Test
    void updateUserTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginUpdate";
        createRequest.name = "testNameUpdate";
        createRequest.lastName = "testLastNameUpdate";
        createRequest.amount = 0.0;

        UserResponse createResponse = createUser(createRequest);

        // update user
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.name = "testNameAfterUpdate";
        updateRequest.lastName = "testLastNameAfterUpdate";

        UserResponse updateResponse =  webTestClient.put()
                .uri("/api/v1/users/" + createResponse.id)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, updateResponse.id);
        assertEquals(createRequest.login, updateResponse.login);
        assertEquals(createRequest.amount, updateResponse.amount);
        assertEquals(createResponse.creationDate, updateResponse.creationDate);

        assertEquals(updateRequest.name, updateResponse.name);
        assertEquals(updateRequest.lastName, updateResponse.lastName);
    }

    @Test
    void updateBalanceTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginUpdateBalance";
        createRequest.name = "testNameUpdateBalance";
        createRequest.lastName = "testLastNameUpdateBalance";
        createRequest.amount = 321.123;

        UserResponse createResponse = createUser(createRequest);


        // update balance
        createRequest.amount = createRequest.amount + createResponse.amount;

        UserResponse updateBalanceResponse =  webTestClient.post()
                .uri("/api/v1/users/" + createResponse.id + "/up-balance/321.123")
                .bodyValue(createRequest)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, updateBalanceResponse.id);
        assertEquals(createResponse.login, updateBalanceResponse.login);
        assertEquals(createResponse.creationDate, updateBalanceResponse.creationDate);
        assertEquals(createResponse.name, updateBalanceResponse.name);
        assertEquals(createResponse.lastName, updateBalanceResponse.lastName);

        assertNotEquals(createResponse.amount, updateBalanceResponse.amount);
    }

    @Test
    void deleteUserTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginDelete";
        createRequest.name = "testNameDelete";
        createRequest.lastName = "testLastNameDelete";
        createRequest.amount = 0.0;

        UserResponse createResponse = createUser(createRequest);

        // delete user
        webTestClient.delete()
                .uri("/api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isOk();

        // get user
        UserResponse getResponse =  webTestClient.get()
                .uri("/api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isNotFound()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();
    }

    private UserResponse createUser(UserCreateRequest request) {

        UserResponse response = webTestClient.post()
                .uri("/api/v1/users")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        return response;
    }
}