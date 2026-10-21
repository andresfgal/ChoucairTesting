package com.reto.tasks;

import com.reto.userinterfaces.SauceDemoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AgregarProductoAlCarrito implements Task {

    private final String producto;

    public AgregarProductoAlCarrito(String producto) {
        this.producto = producto;
    }

    public static AgregarProductoAlCarrito conNombre(String producto) {
        return Tasks.instrumented(AgregarProductoAlCarrito.class, producto);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var addButton = SauceDemoUI.buttonAddToCart(producto);
        actor.attemptsTo(
            WaitUntil.the(addButton, isVisible()),
            WaitUntil.the(addButton, isEnabled())
        );

        WebElement button = addButton.resolveFor(actor).getElement();
        ((JavascriptExecutor) BrowseTheWeb.as(actor).getDriver())
            .executeScript("arguments[0].click()", button);

        actor.attemptsTo(
            WaitUntil.the(SauceDemoUI.buttonRemoveFromCart(producto), isVisible())
        );
    }
}