package com.reto.tasks;

import java.time.Duration;

import com.reto.userinterfaces.SauceDemoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CompletarCompra implements Task {

    private final String nombre;
    private final String apellido;
    private final String zipCode;

    public CompletarCompra(String nombre, String apellido, String zipCode) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.zipCode = zipCode;
    }

    public static CompletarCompra conDatos(String nombre, String apellido, String zipCode) {
        return Tasks.instrumented(CompletarCompra.class, nombre, apellido, zipCode);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Click.on(SauceDemoUI.BUTTON_CART),
            WaitUntil.the(SauceDemoUI.CART_ITEMS, isVisible()),
            WaitUntil.the(SauceDemoUI.BUTTON_CHECKOUT, isVisible()),
            Click.on(SauceDemoUI.BUTTON_CHECKOUT)
        );

        actor.attemptsTo(WaitUntil.the(SauceDemoUI.INPUT_FIRST_NAME, isVisible()));
        enterAndVerify(actor, SauceDemoUI.INPUT_FIRST_NAME, nombre);
        enterAndVerify(actor, SauceDemoUI.INPUT_LAST_NAME, apellido);
        enterAndVerify(actor, SauceDemoUI.INPUT_POSTAL_CODE, zipCode);

        actor.attemptsTo(
            Click.on(SauceDemoUI.BUTTON_CONTINUE),
            WaitUntil.the(SauceDemoUI.BUTTON_FINISH, isVisible()),
            Click.on(SauceDemoUI.BUTTON_FINISH)
        );
        }

        private void enterAndVerify(Actor actor, Target target, String value) {
        actor.attemptsTo(Enter.theValue(value).into(target));
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .ignoring(StaleElementReferenceException.class)
            .until(
            currentDriver -> value.equals(target.resolveFor(actor).getAttribute("value"))
        );
    }
}