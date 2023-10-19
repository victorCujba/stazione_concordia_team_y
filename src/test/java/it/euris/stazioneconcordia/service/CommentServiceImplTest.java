package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CommentRepository;
import it.euris.stazioneconcordia.service.impl.CommentServiceImpl;
import org.assertj.core.api.recursive.comparison.ComparingSnakeOrCamelCaseFields;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    void shouldReturnLastComment() {
        Comment comment1 = Comment
                .builder()
                .id(1L)
                .date(LocalDateTime.now().minusDays(1L))
                .build();

        Comment comment2 = Comment
                .builder()
                .id(2L)
                .date(LocalDateTime.now().minusHours(1L))
                .build();

        Comment comment3 = Comment
                .builder()
                .id(3L)
                .date(LocalDateTime.now().minusHours(15L))
                .build();

        List<Comment> comments = List.of(comment1, comment2, comment3);

        when(commentRepository.findAll()).thenReturn(comments);

        Comment lastComment = commentService.getLastComment();

        assertThat(lastComment)
                .isEqualTo(comment2);
    }

    @Test
    void shouldReturnAComment() {

        Comment comment = Comment
                .builder()
                .id(1L)
                .commentBody("Test body")
                .build();

        List<Comment> comments = List.of(comment);

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> returnedCourses = commentService.findAll();

        assertThat(returnedCourses)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(comment);
    }

    @Test
    void shouldInsertAComment(){

        Comment comment = Comment
                .builder()
                .id(null)
                .commentBody("Test body")
                .date(LocalDateTime.parse("2023-10-18T12:00:00"))
                .build();

        when(commentRepository.save(any())).thenReturn(comment);

        Comment returnedCustomer = commentService.insert(comment);
        assertThat(returnedCustomer.getCommentBody())
                .isEqualTo(comment.getCommentBody());
        assertThat(returnedCustomer.getDate())
                .isEqualTo(comment.getDate());
    }

    @Test
    void shouldNotInsertAnyComment(){

        Comment comment = Comment
                .builder()
                .id(1L)
                .commentBody("Test body")
                .date(LocalDateTime.parse("2023-10-18T12:00:00"))
                .build();
        lenient().when(commentRepository.save(any())).thenReturn(comment);

        assertThrows(IdMustBeNullException.class, () -> commentService.insert(comment));

        assertThatThrownBy(() -> commentService.insert(comment))
                .isInstanceOf(IdMustBeNullException.class);

    }

    @Test
    void shouldUpdateAComment(){

        Comment comment = Comment
                .builder()
                .id(1L)
                .commentBody("Test body")
                .date(LocalDateTime.parse("2023-10-18T12:00:00"))
                .build();

        when(commentRepository.save(any())).thenReturn(comment);

        Comment returnedCourse = commentService.update(comment);
        assertThat(returnedCourse.getCommentBody())
                .isEqualTo(comment.getCommentBody());
        assertThat(returnedCourse.getDate())
                .isEqualTo(comment.getDate());
    }

    @Test
    void shouldNotUpdateAnyComment(){

        Comment course = Comment
                .builder()
                .id(null)
                .commentBody("Test body")
                .date(LocalDateTime.parse("2023-10-18T12:00:00"))
                .build();

        lenient().when(commentRepository.save(any())).thenReturn(course);

        assertThatThrownBy(() -> commentService.update(course))
                .isInstanceOf(IdMustNotBeNullException.class);
    }

    @Test
    void shouldDeleteAComment() {

        Long id = 1L;

        doNothing().when(commentRepository).deleteById(anyLong());
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        assertTrue(commentService.deleteById(id));
        Mockito.verify(commentRepository, times(1)).deleteById(id);
    }
}