package com.example.todolist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ToDoIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepo toDoRepo;

    @BeforeEach
    void setUp() {
        toDoRepo.deleteAll();
    }

    @Test
    public void whenGetToDos_thenStatus200() throws Exception {
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllPToDos_shouldReturnEmptyList_whenRepositoryIsEmpty()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void createToDo_shouldReturnCreatedToDo() throws Exception {
        String newToDoJson = """
            {
                "id": "1",
                "description": "Einkaufen",
                "status": "OPEN"
            }""";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newToDoJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Einkaufen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OPEN"));
    }

    @Test
    void updateToDo_shouldReturnUpdatedToDo() throws Exception {
        ToDo toDo = new ToDo("2", "Joggen", Status.OPEN);
        toDoRepo.save(toDo);

        String updatedToDoJson = """
            {
                "description": "Joggen",
                "status": "DONE"
            }""";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedToDoJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Joggen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DONE"));
    }

    @Test
    void deleteToDo_shouldReturnNoContentStatus() throws Exception {
        ToDo toDo = new ToDo("3", "Lesen", Status.OPEN);
        toDoRepo.save(toDo);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/3"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllProducts_shouldReturnListWithOneObject_whenOneObjectWasSavedInRepository() throws Exception {
        ToDo toDo = new ToDo("1", "Schwimmen", Status.OPEN);
        toDoRepo.save(toDo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                [
                                 {
                                     "id": "1",
                                     "description": "Schwimmen",
                                     "status": "OPEN"
                                 }
                                ]
                                """
                ));
    }
}


