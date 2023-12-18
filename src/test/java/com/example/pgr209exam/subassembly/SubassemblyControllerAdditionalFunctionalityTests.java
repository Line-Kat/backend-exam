package com.example.pgr209exam.subassembly;

import com.example.pgr209exam.model.*;
import com.example.pgr209exam.service.SubassemblyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SubassemblyControllerAdditionalFunctionalityTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubassemblyService subassemblyService;

    @Test
    public void addPart_newPartIsAddedToSubassembly() throws Exception {
        Subassembly subassembly = new Subassembly();
        subassembly.setSubassemblyId(1L);
        when(subassemblyService.addPart(anyLong(), any(Part.class))).thenReturn(subassembly);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/subassembly/1/add-part")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(subassemblyService, times(1)).addPart(anyLong(), any(Part.class));
    }
}
