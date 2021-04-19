package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement notification = $(".notification__content");

    public void login(DataHelper.AuthInfo info) {
        loginField.sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        loginField.setValue(info.getLogin());
        passwordField.sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login(info);
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        login(info);
        notification.shouldBe(Condition.visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    public void isBlocked(DataHelper.AuthInfo info) {
        login(info);
        loginField.shouldBe(readonly);
        loginButton.shouldBe(hidden);
    }
}
