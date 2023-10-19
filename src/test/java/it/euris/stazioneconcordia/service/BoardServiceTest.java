package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.BoardRepository;
import it.euris.stazioneconcordia.service.impl.BoardServiceImpl;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @InjectMocks
    BoardServiceImpl boardService;

    @Test
    void shouldReturnABoard() {
        Board board1 = Board
                .builder()
                .id(1L)
                .build();
        Board board2 = Board
                .builder()
                .id(2L)
                .build();
        Board board3 = Board
                .builder()
                .id(3L)
                .build();

        List<Board> boards = List.of(board1, board2, board3);

        when(boardRepository.findAll()).thenReturn(boards);

        List<Board> returnedBoards = boardService.findAll();

        assertThat(returnedBoards).hasSize(3)
                .last().usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(board3);

    }

    @Test
    void shouldInsertABoardWhenIdIsNull() {
        Board board1 = Board
                .builder()
                .name("Board with null id")
                .build();

        when(boardRepository.save(any())).thenReturn(board1);

        Board returnedBoard = boardService.insert(board1);
        assertThat(returnedBoard.getName())
                .isEqualTo(board1.getName());
    }

    @Test
    void shouldNotInsertAboardWithIdNotNull() {
        Board board1 = Board
                .builder()
                .id(1L)
                .name("Board with id")
                .build();

        lenient().when(boardRepository.save(any())).thenReturn(board1);

        assertThrows(IdMustBeNullException.class, () -> boardService.insert(board1));

        assertThatThrownBy(() -> boardService.insert(board1))
                .isInstanceOf(IdMustBeNullException.class);

    }

    @Test
    void shouldUpdateABoardWhenBoardIdIsNotNull() {
        Board board1 = Board
                .builder()
                .id(1L)
                .name("Board with id")
                .build();

        when(boardRepository.save(any())).thenReturn(board1);

        Board returnedBoard = boardService.update(board1);
        assertThat(returnedBoard.getName()).isEqualTo(board1.getName());

    }


    @Test
    void shouldNotUpdateABoardWhenBoardIdIsNull() {
        Board board1 = Board
                .builder()
                .id(null)
                .name("Board with id")
                .build();

        lenient().when(boardRepository.save(any())).thenReturn(board1);

        assertThatThrownBy(() -> boardService.update(board1))
                .isInstanceOf(IdMustNotBeNullException.class);
    }


    @Test
    void shouldDeleteABordById() {
        Long id = 1L;

        doNothing().when(boardRepository).deleteById(id);
        when(boardRepository.findById(id)).thenReturn(Optional.empty());

        assertTrue(boardService.deleteById(id));
        Mockito.verify(boardRepository, times(1)).deleteById(1L);


    }


    @Test
    public void shouldReturnBoardByIdWhenBoardExists() {
        Long idBoard = 1L;
        Board existingBoard = Board.builder().id(idBoard).build();

        when(boardRepository.findById(idBoard)).thenReturn(Optional.of(existingBoard));
        Board foundBoard = boardService.findById(idBoard);

        assertEquals(existingBoard, foundBoard);
    }

    @Test
    public void shouldReturnNewBoardWhenBoarNotExists() {
        Long idBoard = 1L;
        Mockito.when(boardRepository.findById(idBoard)).thenReturn(Optional.empty());
        Board foundBoard = boardService.findById(idBoard);

        assertNotNull(foundBoard);
    }

}