package com.example.pgr209exam.subassembly;

import com.example.pgr209exam.model.Subassembly;
import com.example.pgr209exam.repository.SubassemblyRepository;
import com.example.pgr209exam.service.SubassemblyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
    public void createPart_addingNewPart_shouldReturnSubassembly() {
        String subassemblyName = "subassemblyName";
        Subassembly subassembly = new Subassembly(subassemblyName);
        when(subassemblyRepository.save(subassembly)).thenReturn(subassembly);
        Subassembly returnedSubassembly = subassemblyService.createSubassembly(subassembly);

        Assertions.assertNotNull(returnedSubassembly);
        Assertions.assertEquals(subassemblyName, returnedSubassembly.getSubassemblyName());
    }

    @Test
    public void updateSubassembly_updateExistingSubassembly_shouldReturnUpdatedSubassembly() {
        Long subassemblyId = 1L;
        String existingSubassemblyName = "Subassembly 1";
        String newSubassemblyName = "Subassembly 2";

        Subassembly existingSubassembly = new Subassembly();
        existingSubassembly.setSubassemblyId(subassemblyId);
        existingSubassembly.setSubassemblyName(existingSubassemblyName);

        when(subassemblyRepository.findById(subassemblyId)).thenReturn(Optional.of(existingSubassembly));
        when(subassemblyRepository.save(any(Subassembly.class))).thenAnswer(i -> i.getArgument(0));

        Subassembly updatedFieldName = new Subassembly();
        updatedFieldName.setSubassemblyName(newSubassemblyName);

        Subassembly updatedSubassembly = subassemblyService.updateSubassembly(subassemblyId, updatedFieldName);

        assertNotNull(updatedSubassembly);
        assertEquals(newSubassemblyName, updatedSubassembly.getSubassemblyName());

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
