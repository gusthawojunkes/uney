package solutions.wo.it.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import solutions.wo.it.data.core.exceptions.EncryptException;

import static org.junit.jupiter.api.Assertions.*;
import static solutions.wo.it.security.Password.encrypt;

@DisplayName("Password encryptor test cases")
class PasswordTest {

    @ParameterizedTest(name = "When `password` is \"{0}\" then must throw an exception")
    @NullSource @EmptySource @ValueSource(strings = { "   ", " " })
    @DisplayName("Empty password parameter should throw an encryption exception")
    void emptyPasswordParameterMustThrowAnEncryptException(String password) {
        EncryptException exception = assertThrows(EncryptException.class, () -> {
            encrypt(password);
        });

        assertEquals("The received password for encryption cannot be empty!", exception.getMessage());
    }

    @DisplayName("When the given password is valid, then the method must return a not null value")
    @ParameterizedTest(name = "When `password` is \"{0}\" then the result must not be null")
    @ValueSource(strings = {"1234", "aseweqw", "123$@!lL;^^", "$str0NgP4$$"})
    void encryptMethodMustNotBeNull(String password) throws EncryptException {
        String encryptedPassword = Password.encrypt(password);
        assertNotNull(encryptedPassword);
    }

    @Test
    @DisplayName("The given password must be encrypted correctly")
    void givenPasswordMustBeEncrypted() throws EncryptException {
        String encryptedPassword = "jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=";

        String password = Password.encrypt("123456");

        assertEquals(encryptedPassword, password);
    }
}