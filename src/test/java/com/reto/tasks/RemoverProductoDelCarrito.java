package com.reto.tasks;
import com.reto.userinterfaces.SauceDemoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class RemoverProductoDelCarrito implements Task {
    private final String product;

    public RemoverProductoDelCarrito(String product) {
        this.product = product;
    }

    public static RemoverProductoDelCarrito conNombre(String product) {
        return Tasks.instrumented(RemoverProductoDelCarrito.class, product);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(SauceDemoUI.BUTTON_CART),
            Click.on(SauceDemoUI.buttonRemoveFromCart(product))
        );
    }
}