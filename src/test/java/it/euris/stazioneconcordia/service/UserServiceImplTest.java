package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.UserRepository;
import it.euris.stazioneconcordia.service.impl.UserServiceImpl;
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
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void shouldReturnAUser() {

        User user1 = User
                .builder()
                .id(1L)
                .build();

        List<User> users = List.of(user1);

        when(userRepository.findAll()).thenReturn(users);

        List<User> returnedUsers = userService.findAll();

        assertThat(returnedUsers)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(user1);
    }

    @Test
    void shouldInsertAUser() {

        User user1 = User
                .builder()
                .id(null)
                .build();

        when(userRepository.save(any())).thenReturn(user1);

        User returnedUser = userService.insert(user1);
        assertThat(returnedUser.getId())
                .isEqualTo(user1.getId());
    }

//    @Test
//    void shouldNotInsertAnyUser() {
//        User user1 = User
//                .builder()
//                .id("1")
//                .build();
//
//
//        lenient().when(userRepository.save(any())).thenReturn(user1.getId() == null ? user1 : new IdMustBeNullException());
//
//        assertThrows(IdMustBeNullException.class, () -> userService.insert(user1));
//        assertThatThrownBy(() -> userService.insert(user1))
//                .isInstanceOf(IdMustBeNullException.class);
//
//    }


    @Test
    void shouldDeleteAUser() {
        //arrange
        Long id = 12L;

        doNothing().when(userRepository).deleteById(anyLong());
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertTrue(userService.deleteById(id));
        Mockito.verify(userRepository, times(1)).deleteById(id);
    }

//    @Test
//    void shouldNotUpdateAnyUser() {
//
//        User user1 = User
//                .builder()
//                .id(null)
//                .build();
//        lenient().when(userRepository.save(any())).thenReturn(user1.getId() != null ? user1 : new IdMustNotBeNullException());
//
//        assertThatThrownBy(() -> userService.update(user1))
//                .isInstanceOf(IdMustNotBeNullException.class);
//    }

    @Test
    void shouldUpdateAnyUser() {

        User user1 = User
                .builder()
                .id(1L)
                .fullName("test name")
                .bio("test bio")
                .build();
        when(userRepository.save(any())).thenReturn(user1);

        User returnedUser = userService.update(user1);
        assertThat(returnedUser.getFullName())
                .isEqualTo(user1.getFullName());
        assertThat(returnedUser.getBio())
                .isEqualTo(user1.getBio());
    }
}



