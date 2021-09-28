package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.format.DateTimeFormatter.ofPattern;

public class CardDeliveryTest {
    String meetingDay(int day) {
        return LocalDate.now().plusDays(day).format(ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldFillInDropDownListCity() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ма");
        $(".input__menu").find(withText("Магадан")).click();
        SelenideElement dateWindow = $("[placeholder='Дата встречи']");
        dateWindow.doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDay(3));
        $("[data-test-id='name'] input").setValue("Владимир Ленин");
        $("[data-test-id='phone'] input").setValue("+79101112134");
        $("[data-test-id='agreement']").click();
        $(By.className("button")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + meetingDay(3)));
    }

    @Test
    void shouldFillInDropDownListDate() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Магадан");
        $(".icon-button__content .icon_name_calendar").click();
        String meetingDay = LocalDate.now().plusDays(7).format(ofPattern("dd.MM.yyyy"));
        $(".calendar__day").click();
        $(".popup__content").setValue(meetingDay).click();
        $("[data-test-id='name'] input").setValue("Владимир Ленин");
        $("[data-test-id='phone'] input").setValue("+79101112134");
        $("[data-test-id='agreement']").click();
        $(By.className("button")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + meetingDay(7)));
    }
}
