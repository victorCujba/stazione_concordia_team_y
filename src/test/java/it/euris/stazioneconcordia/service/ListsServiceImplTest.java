package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.ListsRepository;
import it.euris.stazioneconcordia.service.impl.ListsServiceImpl;
import org.assertj.core.api.recursive.comparison.ComparingSnakeOrCamelCaseFields;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListsServiceImplTest {
    @Mock
    ListsRepository listsRepository;
    @InjectMocks
    ListsServiceImpl listsService;

    @Test
    void shouldReturnALists() {

        Lists list = Lists
                .builder()
                .id("1")
                .build();

        List<Lists> lists = List.of(list);

        when(listsRepository.findAll()).thenReturn(lists);

        List<Lists> returnedLists = listsRepository.findAll();

        assertThat(returnedLists)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(list);
    }

    @Test
    void shouldInsertALists() {

        Lists list = Lists
                .builder()
                .id(null)
                .build();


        when(listsRepository.save(any())).thenReturn(list);

        Lists returnedLists = listsService.insert(list);
        assertThat(returnedLists.getId())
                .isEqualTo(list.getId());
    }

    @Test
    void shouldNotInsertAnyLists() {
        Lists list = Lists
                .builder()
                .id("1")
                .build();


        lenient().when(listsRepository.save(any())).thenReturn(list);

        assertThrows(IdMustBeNullException.class, () -> listsService.insert(list));

        assertThatThrownBy(() -> listsService.insert(list))
                .isInstanceOf(IdMustBeNullException.class);

    }

    @Test
    void shouldDeleteALists() {
        //arrange
        String id = "12";

        doNothing().when(listsRepository).deleteById(anyString());
        when(listsRepository.findById(id)).thenReturn(Optional.empty());
        assertTrue(listsService.deleteById(id));
        Mockito.verify(listsRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldNotUpdateAnyLists() {

        Lists list = Lists
                .builder()
                .id(null)
                .build();
        lenient().when(listsRepository.save(any())).thenReturn(list);

        assertThatThrownBy(() -> listsService.update(list))
                .isInstanceOf(IdMustNotBeNullException.class);
    }

    @Test
    void shouldUpdateAnyLists() {

        Lists list = Lists
                .builder()
                .id("1")
                .name("test name")
                .build();
        when(listsRepository.save(any())).thenReturn(list);

        Lists returnedLists = listsService.update(list);
        assertThat(returnedLists.getName())
                .isEqualTo(list.getName());
        assertThat(returnedLists.getId())
                .isEqualTo(list.getId());
    }

}