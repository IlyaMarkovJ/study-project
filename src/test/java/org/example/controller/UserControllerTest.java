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
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAllUsersTest() {
        // create user
        UserResponse createResponse = createAndCheckUser();

        // get all users
        Map<String, UserResponse> userStorage = new HashMap<>();
        userStorage.put(createResponse.id, createResponse);
        UserResponse getAllUsersResponse = webTestClient.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertThat(userStorage).containsEntry(createResponse.id, createResponse);
    }

    @Test
    void getUserTest() {
        // create user
        UserResponse createResponse = createAndCheckUser();

        // get user
        UserResponse getUserResponse = webTestClient.get()
                .uri("/api/v1/users/" + createResponse.id)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, getUserResponse.id);
        assertEquals(createResponse.login, getUserResponse.login);
        assertEquals(createResponse.name, getUserResponse.name);
        assertEquals(createResponse.lastName, getUserResponse.lastName);
        assertEquals(createResponse.amount, getUserResponse.amount);
        assertEquals(createResponse.creationDate.toLocalDate(), getUserResponse.creationDate.toLocalDate());
    }

    @Test
    void createUserTest() {
        createAndCheckUser();
    }

    @Test
    void updateBalanceTest() {
        // create user
        UserResponse createResponse = createAndCheckUser();

        // update balance
        createAndCheckUser().amount = createAndCheckUser().amount + createResponse.amount;

        UserResponse updateBalanceResponse = webTestClient.post()
                .uri("/api/v1/users/" + createResponse.id + "/up-balance/50.0")
                .bodyValue(createAndCheckUser())
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(createResponse.id, updateBalanceResponse.id);
        assertEquals(createResponse.login, updateBalanceResponse.login);
        assertEquals(createResponse.creationDate.toLocalDate(), updateBalanceResponse.creationDate.toLocalDate());
        ;
        assertEquals(createResponse.name, updateBalanceResponse.name);
        assertEquals(createResponse.lastName, updateBalanceResponse.lastName);

        assertNotEquals(createResponse.amount, updateBalanceResponse.amount);
    }

    @Test
    void updateUserTest() {
        // create user
        UserResponse createResponse = createAndCheckUser();

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
        assertEquals(createResponse.login, updateResponse.login);
        assertEquals(createResponse.amount, updateResponse.amount);
        assertEquals(createResponse.creationDate.toLocalDate(), updateResponse.creationDate.toLocalDate());

        assertEquals(updateRequest.name, updateResponse.name);
        assertEquals(updateRequest.lastName, updateResponse.lastName);
    }


    @Test
    void deleteUserTest() {
        // create user
        UserResponse createResponse = createAndCheckUser();

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

    private UserResponse createAndCheckUser() {
        String postfix = UUID.randomUUID().toString();

        UserCreateRequest request = new UserCreateRequest();
        request.login = "testLogin_" + postfix;
        request.name = "testName_" + postfix;
        request.lastName = "testLastName_" + postfix;
        request.amount = 10.0;

        UserResponse response = webTestClient.post()
                .uri("/api/v1/users")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponse.class)
                .getResponseBody()
                .blockFirst();

        assertNotNull(response.id);
        assertEquals(request.login, response.login);
        assertEquals(request.name, response.name);
        assertEquals(request.lastName, response.lastName);
        assertEquals(request.amount, response.amount);
        assertEquals(LocalDate.now(), response.creationDate.toLocalDate());

        return response;
    }
}