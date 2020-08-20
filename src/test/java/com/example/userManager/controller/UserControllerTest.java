package com.example.userManager.controller;

import com.example.userManager.dao.User;
import com.example.userManager.service.UserService;
import com.example.userManager.to.UserTo;
import com.example.userManager.util.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.userManager.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/fill_users.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear_users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {
    private static final String REST_URL = UserController.REST_URL + '/';
    private static final String FIRST_NAME = "K";
    private static final String LAST_NAME = "V";

    @Autowired
    private UserController controller;
    @Autowired
    private UserService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = this.mockMvc.perform(get(REST_URL + USER_TO_1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        UserTo userTo = getToFromResult(result);
        assertEquals(USER_TO_1, userTo);
    }

    @Test
    public void testGetNotFound() throws Exception {
        this.mockMvc.perform(get(REST_URL + USER_TO_10_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {
        MvcResult result = this.mockMvc.perform(get(REST_URL + "all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<UserTo> userTos = getListFromResult(result);
        assertIterableEquals(USER_TOS, userTos);
    }

    @Test
    public void testGetAllByFirstName() throws Exception {
        MvcResult result = this.mockMvc.perform(get(REST_URL + "all")
                .param("firstName", FIRST_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<UserTo> userTos = getListFromResult(result);
        assertIterableEquals(getUserTosByFirstName(FIRST_NAME), userTos);
    }

    @Test
    public void testGetAllByLastName() throws Exception {
        MvcResult result = this.mockMvc.perform(get(REST_URL + "all")
                .param("lastName", LAST_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<UserTo> userTos = getListFromResult(result);
        assertIterableEquals(getUserTosByLastName(LAST_NAME), userTos);
    }

    @Test
    public void testGetAllByFirstNameAndLastName() throws Exception {
        MvcResult result = this.mockMvc.perform(get(REST_URL + "all")
                .param("firtName", FIRST_NAME)
                .param("lastName", LAST_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<UserTo> userTos = getListFromResult(result);
        assertIterableEquals(getUserTosByFirstNameAndLastName(FIRST_NAME, LAST_NAME), userTos);
    }

    @Test
    public void testCreate() throws Exception {
        User user = getNew();
        MvcResult result = this.mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(user)))
                .andExpect(status().isCreated())
                .andReturn();

        UserTo actualUserTo = getToFromResult(result);

        user.setId(actualUserTo.getId());
        user.setCreated(actualUserTo.getCreated());
        UserTo expectedUserTo = UserUtil.convertToTo(user);

        assertEquals(expectedUserTo, actualUserTo);
        List<UserTo> userTos = new ArrayList<>(USER_TOS);
        userTos.add(expectedUserTo);
        assertIterableEquals(userTos, service.getAll(null, null));
    }

    @Test
    public void testInvalidCreate() throws Exception {
        User user = getNew();
        user.setId(USER_TO_10_ID);
        this.mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = getForUpdate();
        int id = USER_TO_1_ID;
        MvcResult result = this.mockMvc.perform(put(REST_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(user)))
                .andExpect(status().isOk())
                .andReturn();

        UserTo actualUserTo = getToFromResult(result);

        user.setId(id);
        user.setCreated(actualUserTo.getCreated());
        user.setUpdated(actualUserTo.getUpdated());
        UserTo expectedUserTo = UserUtil.convertToTo(user);

        assertEquals(expectedUserTo, actualUserTo);
    }

    @Test
    public void testInvalidUpdate() throws Exception {
        User user = getForUpdate();
        this.mockMvc.perform(put(REST_URL + USER_TO_10_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        int id = USER_TO_1_ID;
        this.mockMvc.perform(delete(REST_URL + id))
                .andDo(print())
                .andExpect(status().isOk());

        List<UserTo> userTos = new ArrayList<>(USER_TOS);
        userTos.remove(--id);
        assertIterableEquals(userTos, service.getAll(null, null));
    }

    @Test
    public void testInvalidDelete() throws Exception {
        this.mockMvc.perform(delete(REST_URL + USER_TO_10_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private UserTo getToFromResult(MvcResult result) throws IOException {
        String content = result.getResponse().getContentAsString();
        return objectMapper.readValue(content, UserTo.class);
    }

    private List<UserTo> getListFromResult(MvcResult result) throws IOException {
        String content = result.getResponse().getContentAsString();
        return objectMapper.readValue(content, new TypeReference<>() {
        });
    }

    private String getJson(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + user + "'", e);
        }
    }
}