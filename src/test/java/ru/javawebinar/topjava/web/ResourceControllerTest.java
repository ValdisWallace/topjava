package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    void testStyleCss() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print());
    }
}
