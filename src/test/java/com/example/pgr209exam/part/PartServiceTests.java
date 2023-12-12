package com.example.pgr209exam.part;

import com.example.pgr209exam.model.Part;
import com.example.pgr209exam.repository.PartRepository;
import com.example.pgr209exam.service.PartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PartServiceTests {
    @MockBean
    private PartRepository partRepository;

    @Autowired
    private PartService partService;

    @Test
    public void getPartById_whenExisting_shouldReturnPart() {
        Part part = new Part("Part");
        when(partRepository.findById(1L)).thenReturn(Optional.of(part));

        Part actualPart = partService.getPartById(1L);

        Assertions.assertEquals(part, actualPart);
    }

    @Test
    public void createPart_addingNewPart_shouldReturnPart() {
        String partName = "Part";

        Part part = new Part(partName);
        when(partRepository.save(part)).thenReturn(part);

        Part returnedPart = partService.createPart(part);

        Assertions.assertNotNull(returnedPart);
        Assertions.assertEquals(partName, returnedPart.getPartName());
    }

    @Test
    public void updatePart_updateExistingPart_shouldReturnUpdatedPart() {
        String originalName = "Part 1";
        String updatedName = "Part 2";

        Part part = new Part(originalName);
        when(partRepository.save(part)).thenReturn(part);

        Part originalPart = partService.createPart(part);

        Assertions.assertEquals(originalName, originalPart.getPartName());

        originalPart.setPartName(updatedName);
        when(partRepository.save(originalPart)).thenReturn(originalPart);
        Part updatedPart = partService.updatePart(originalPart);

        Assertions.assertEquals(updatedName, updatedPart.getPartName());
    }

    @Test
    public void deletePart_deleteExistingPart_shouldNotFail() {
        String partName = "Part";

        Part part = new Part(partName);
        when(partRepository.save(part)).thenReturn(part);
        Part createdPart = partService.createPart(part);

        Assertions.assertEquals(partName, createdPart.getPartName());

        partService.deletePartById(1L);

        Part deletedPart = partService.getPartById(1L);

        Assertions.assertNull(deletedPart);
    }
}
