package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(firstMeetingDate);
        $("[placeholder=\"Город\"]").setValue(validUser.getCity());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[name='name']").setValue(validUser.getName());
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $x("//div[text()= 'Успешно!']").should(Condition.visible, Duration.ofSeconds(15));
        $x("//div[contains(text(), 'Встреча успешно запланирована')]").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(secondMeetingDate);
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='replan-notification']").should(Condition.visible);
        $$("button").find(Condition.exactText("Перепланировать")).click();
        $x("//div[text()= 'Успешно!']").should(Condition.visible, Duration.ofSeconds(15));
        $x("//div[contains(text(), 'Встреча успешно запланирована')]").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15));

    }
}
