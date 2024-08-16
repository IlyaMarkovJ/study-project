package org.example.controller;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAllUsersTest() {
        // create users
        // user 1
        UserCreateRequest createRequest1 = new UserCreateRequest();
        createRequest1.login = "testLoginGetAllUsers1";
        createRequest1.name = "testNameGetAllUsers1";
        createRequest1.lastName = "testLastNameGetAllUsers1";
        createRequest1.amount = 0.0;

        UserResponse createResponse1 = createUser(createRequest1);

        // user 2
        UserCreateRequest createRequest2 = new UserCreateRequest();
        createRequest2.login = "testLoginGetAllUsers2";
        createRequest2.name = "testNameGetAllUsers2";
        createRequest2.lastName = "testLastNameGetAllUsers2";
        createRequest2.amount = 0.0;

        UserResponse createResponse2 = createUser(createRequest2);

        // get all users
        Map<String, UserResponse> userStorage = new HashMap<>();
        userStorage.put(createResponse1.id, createResponse1);
        userStorage.put(createResponse2.id, createResponse2);
        UserResponse getAllUsersResponse = webTestClient.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertThat(userStorage).containsEntry(createResponse1.id, createResponse1);
        assertThat(userStorage).containsEntry(createResponse2.id, createResponse2);
    }

    @Test
    void getUserTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginGet";
        createRequest.name = "testNameGet";
        createRequest.lastName = "testLastNameGet";
        createRequest.amount = 0.0;

        UserResponse createResponse = createUser(createRequest);

        // get user
        UserResponse getUserResponse = webTestClient.get()
                .uri("/api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, getUserResponse.id);
        assertEquals(createRequest.login, getUserResponse.login);
        assertEquals(createRequest.name, getUserResponse.name);
        assertEquals(createRequest.lastName, getUserResponse.lastName);
        assertEquals(createRequest.amount, getUserResponse.amount);
        assertEquals(createResponse.creationDate, getUserResponse.creationDate);
    }

    @Test
    void userCounterTest() {
        // create users
        // user 1
        UserCreateRequest createRequest1 = new UserCreateRequest();
        createRequest1.login = "testLoginGetAllUsers1";
        createRequest1.name = "testNameGetAllUsers1";
        createRequest1.lastName = "testLastNameGetAllUsers1";
        createRequest1.amount = 0.0;

        UserResponse createResponse1 = createUser(createRequest1);

        // user 2
        UserCreateRequest createRequest2 = new UserCreateRequest();
        createRequest2.login = "testLoginGetAllUsers2";
        createRequest2.name = "testNameGetAllUsers2";
        createRequest2.lastName = "testLastNameGetAllUsers2";
        createRequest2.amount = 0.0;

        UserResponse createResponse2 = createUser(createRequest2);

        // get user counter
        Map<String , UserResponse> userStorage = new HashMap<>();
        userStorage.put(createResponse1.id, createResponse1);
        userStorage.put(createResponse2.id, createResponse2);
        UserResponse getUserCounter = webTestClient.get()
                .uri("/api/v1/users/counter")
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

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
    void updateBalanceTest() {
        // create user
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.login = "testLoginUpdateBalance";
        createRequest.name = "testNameUpdateBalance";
        createRequest.lastName = "testLastNameUpdateBalance";
        createRequest.amount = 50.0;

        UserResponse createResponse = createUser(createRequest);


        // update balance
        createRequest.amount = createRequest.amount + createResponse.amount;

        UserResponse updateBalanceResponse = webTestClient.post()
                .uri("/api/v1/users/" + createResponse.id + "/up-balance/50.0")
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

        UserResponse updateResponse = webTestClient.put()
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
        UserResponse getResponse = webTestClient.get()
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