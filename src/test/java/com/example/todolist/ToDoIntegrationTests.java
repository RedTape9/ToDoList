package com.example.todolist;


import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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


    @DirtiesContext
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
