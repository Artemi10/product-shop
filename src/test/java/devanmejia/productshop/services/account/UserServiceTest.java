package devanmejia.productshop.services.account;

import devanmejia.productshop.models.account.user.User;
import devanmejia.productshop.services.user.UserServiceImpl;
import devanmejia.productshop.transfer.account.AccountForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    void generate_new_user_if_user_is_over18() throws ParseException {
        User user = User.builder()
                .login("devanmejia13")
                .firstName("Lyakh")
                .lastName("Artemi")
                .birthDate(DATE_FORMAT.parse("03.02.2003")).build();
        AccountForm form = AccountForm.builder()
                .userLogin("devanmejia13")
                .userFirstName("Lyakh")
                .userLastName("Artemi")
                .userBirthDate(DATE_FORMAT.parse("03.02.2003")).build();
        Assertions.assertEquals(user, userService.generateNewUser(form));
    }

    @Test
    void throw_exception_if_user_is_under18() throws ParseException {
        AccountForm form = AccountForm.builder()
                .userLogin("devanmejia13")
                .userFirstName("Lyakh")
                .userLastName("Artemi")
                .userBirthDate(DATE_FORMAT.parse("03.02.2013")).build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.generateNewUser(form));
    }

    @Test
    void throw_exception_if_user_has_already_been_registered() throws ParseException {
        AccountForm form = AccountForm.builder()
                .userLogin("devanmejia")
                .userFirstName("Lyakh")
                .userLastName("Artemi")
                .userBirthDate(DATE_FORMAT.parse("03.02.2003")).build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.generateNewUser(form));
    }

    @Test
    void isAdultTest() throws ParseException {
        User adultUser = User.builder()
                .login("devanmejia13")
                .firstName("Lyakh")
                .lastName("Artemi")
                .birthDate(DATE_FORMAT.parse("03.02.2003")).build();
        User minorUser = User.builder()
                .login("devanmejia13")
                .firstName("Lyakh")
                .lastName("Artemi")
                .birthDate(DATE_FORMAT.parse("03.02.2013")).build();
        Assertions.assertTrue(userService.isAdult(adultUser));
        Assertions.assertFalse(userService.isAdult(minorUser));
    }

    @Test
    void getAgeTest() throws ParseException {
        User user = User.builder()
                .login("devanmejia13")
                .firstName("Lyakh")
                .lastName("Artemi")
                .birthDate(DATE_FORMAT.parse("03.02.2003")).build();
        Assertions.assertEquals(18, userService.getAge(user));
    }
}
