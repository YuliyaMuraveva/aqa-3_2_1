package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AppDeadlineTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void tidyUp() { DbHelper.cleanCodes(); }

    @Test
    void shouldValidLogin() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        String verificationCode = DbHelper.getVerificationCode(authInfo.getLogin());
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldInvalidLogin() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidPass();
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldBeBlocked() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidPass();
        loginPage.login(authInfo);
        loginPage.login(authInfo);
        loginPage.isBlocked(authInfo);
    }
}
