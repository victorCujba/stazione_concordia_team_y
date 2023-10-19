package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.repository.CommentRepository;
import it.euris.stazioneconcordia.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    void shouldReturnLastComment(){
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

        List<Comment> comments = List.of(comment1,comment2,comment3);

        when(commentRepository.findAll()).thenReturn(comments);

        Comment lastComment = commentService.getLastComment();

        assertThat(lastComment)
                .isEqualTo(comment2);
    }
}