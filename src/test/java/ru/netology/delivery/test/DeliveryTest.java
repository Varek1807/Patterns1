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
    // private static Faker faker;

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
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        // String planningDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(firstMeetingDate);
        $("[placeholder=\"Город\"]").setValue(validUser.getCity());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[name='name']").setValue(validUser.getName());
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $x("//div[text()= 'Успешно!']").should(Condition.visible, Duration.ofSeconds(15));
        $x("//div[contains(text(), 'Встреча успешно запланирована')]").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        // String planningDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(secondMeetingDate);
        //$("[placeholder=\"Город\"]").setValue(validUser.getCity());
        //$("[name='phone']").setValue(validUser.getPhone());
        //$("[name='name']").setValue(validUser.getName());
        //$("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='replan-notification']").should(Condition.visible);
        $$("button").find(Condition.exactText("Перепланировать")).click();
        $x("//div[text()= 'Успешно!']").should(Condition.visible, Duration.ofSeconds(15));
        $x("//div[contains(text(), 'Встреча успешно запланирована')]").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15));

    }
}
