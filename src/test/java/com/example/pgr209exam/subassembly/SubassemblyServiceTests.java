package com.example.pgr209exam.subassembly;

import com.example.pgr209exam.model.Part;
import com.example.pgr209exam.model.Subassembly;
import com.example.pgr209exam.repository.SubassemblyRepository;
import com.example.pgr209exam.service.SubassemblyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class SubassemblyServiceTests {
    @MockBean
    private SubassemblyRepository subassemblyRepository;

    @Autowired
    private SubassemblyService subassemblyService;

    @Test
    public void getSubassemblyById_whenExisting_shouldReturnSubassembly() {
        Subassembly subassembly = new Subassembly("subassemblyName");
        when(subassemblyRepository.findById(1L)).thenReturn(Optional.of(subassembly));

        Subassembly actualSubassembly = subassemblyService.getSubassemblyById(1L);

        Assertions.assertEquals(subassembly, actualSubassembly);
    }

    @Test
    public void createPart_addingNewPart_shouldReturnPart() {
        String subassemblyName = "subassemblyName";

        Subassembly subassembly = new Subassembly(subassemblyName);
        when(subassemblyRepository.save(subassembly)).thenReturn(subassembly);

        Subassembly returnedSubassembly = subassemblyService.createSubassembly(subassembly);

        Assertions.assertNotNull(returnedSubassembly);
        Assertions.assertEquals(subassemblyName, returnedSubassembly.getSubassemblyName());
    }

    @Test
    public void updateSubassembly_updateExistingSubassembly_shouldReturnUpdatedSubassembly() {
        String originalName = "Subassembly 1";
        String updatedName = "Subassembly 2";

        Subassembly subassembly = new Subassembly(originalName);
        when(subassemblyRepository.save(subassembly)).thenReturn(subassembly);

        Subassembly originalSubassembly = subassemblyService.createSubassembly(subassembly);

        Assertions.assertEquals(originalName, originalSubassembly.getSubassemblyName());

        originalSubassembly.setSubassemblyName(updatedName);
        when(subassemblyRepository.save(originalSubassembly)).thenReturn(originalSubassembly);
        Subassembly updatedSubassembly = subassemblyService.updateSubassembly(originalSubassembly);

        Assertions.assertEquals(updatedName, updatedSubassembly.getSubassemblyName());
    }

    @Test
    public void deleteSubassembly_deleteExistingSubassembly_shouldNotFail() {
        String subassemblyName = "Subassembly";

        Subassembly subassembly = new Subassembly(subassemblyName);
        when(subassemblyRepository.save(subassembly)).thenReturn(subassembly);
        Subassembly createdSubassembly = subassemblyService.createSubassembly(subassembly);

        Assertions.assertEquals(subassemblyName, createdSubassembly.getSubassemblyName());

        subassemblyService.deleteSubassemblyById(1L);

        Subassembly deletedSubassembly = subassemblyService.getSubassemblyById(1L);

        Assertions.assertNull(deletedSubassembly);
    }
}
