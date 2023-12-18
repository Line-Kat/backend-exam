package com.example.pgr209exam.part;

import com.example.pgr209exam.model.Part;
import com.example.pgr209exam.repository.PartRepository;
import com.example.pgr209exam.service.PartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PartServiceTests {
    @MockBean
    private PartRepository partRepository;

    @Autowired
    private PartService partService;

    @Test
    public void testGetParts() {
        Page<Part> mockedPage = mock(Page.class);
        when(partRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);

        Page<Part> result = partService.getParts(PageRequest.of(0, 5));
        verify(partRepository, times(1)).findAll(any(Pageable.class));
        assertEquals(mockedPage, result);
    }
    @Test
    public void getPartById_whenExisting_shouldReturnPart() {
        Part part = new Part("Part");
        when(partRepository.findById(1L)).thenReturn(Optional.of(part));
        Part actualPart = partService.getPartById(1L);

        assertEquals(part, actualPart);
    }

    @Test
    public void createPart_addingNewPart_shouldReturnPart() {
        String partName = "Part";

        Part part = new Part(partName);
        when(partRepository.save(part)).thenReturn(part);
        Part returnedPart = partService.createPart(part);

        assertNotNull(returnedPart);
        assertEquals(part, returnedPart);
    }

    @Test
    public void updatePart_updateExistingPart_shouldReturnUpdatedPart() {
        Long partId = 1L;
        String existingPartName = "Part 1";
        String newPartName = "Part 2";

        Part existingPart = new Part();
        existingPart.setPartId(partId);
        existingPart.setPartName(existingPartName);

        when(partRepository.findById(partId)).thenReturn(Optional.of(existingPart));
        when(partRepository.save(any(Part.class))).thenAnswer(i -> i.getArgument(0));

        Part updatedFieldName = new Part();
        updatedFieldName.setPartName(newPartName);

        Part updatedPart = partService.updatePart(partId, updatedFieldName);

        assertNotNull(updatedPart);
        assertEquals(newPartName, updatedPart.getPartName());
    }

    @Test
    public void deletePart_deleteExistingPart_shouldNotFail() {
        String partName = "Part";

        Part part = new Part(partName);
        when(partRepository.save(part)).thenReturn(part);
        Part createdPart = partService.createPart(part);

        assertEquals(part, createdPart);

        partService.deletePartById(1L);
        Part deletedPart = partService.getPartById(1L);

        Assertions.assertNull(deletedPart);
    }
}
