package com.example.countries;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CountryApiTests {

    @Autowired
    MockMvc mvc;

    private static String json(String name, String capital, String region, String sub, String area) {
        return """
                {"name":"%s","capital":"%s","region":"%s","subRegion":"%s","area":%s}
                """.formatted(name, capital, region, sub, area);
    }

    @Test
    void crudAndSorting() throws Exception {
        mvc.perform(post("/api/countries").contentType(MediaType.APPLICATION_JSON)
                        .content(json("Moçambique", "Maputo", "Africa", "Eastern Africa", "801590")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        mvc.perform(post("/api/countries").contentType(MediaType.APPLICATION_JSON)
                        .content(json("Angola", "Luanda", "Africa", "Middle Africa", "1246700")))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/countries?sortBy=name&direction=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Angola"));

        mvc.perform(get("/api/countries?sortBy=area&direction=desc"))
                .andExpect(jsonPath("$[0].name").value("Angola"));

        mvc.perform(put("/api/countries/1").contentType(MediaType.APPLICATION_JSON)
                        .content(json("Moçambique", "Maputo", "Africa", "Southeast Africa", "801590")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subRegion").value("Southeast Africa"));

        mvc.perform(delete("/api/countries/1")).andExpect(status().isNoContent());
        mvc.perform(get("/api/countries/1")).andExpect(status().isNotFound());
    }

    @Test
    void validationAndInvalidSort() throws Exception {
        mvc.perform(post("/api/countries").contentType(MediaType.APPLICATION_JSON)
                        .content(json("", "X", "Y", "Z", "-5")))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/countries?sortBy=population"))
                .andExpect(status().isBadRequest());
    }
}
