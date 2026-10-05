package br.com.librarysystem.services;

import br.com.librarysystem.dao.UserRecord;
import br.com.librarysystem.model.entities.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void shouldReturnTheSpecifiedUser() {
        // Arrange
        UserService userService = new UserService();
        // Act
        User user = userService.userLogin("testertest@test.com", "12345678");
        // Assert
        assertNotNull(user);
    }

}