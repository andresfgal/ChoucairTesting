package com.reto.questions;
import com.reto.userinterfaces.SauceDemoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;

public class EstadoCarrito implements Question<Boolean> {

    public static EstadoCarrito estaVacio() {
        return new EstadoCarrito();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return !Visibility.of(SauceDemoUI.CART_ITEM).answeredBy(actor);
    }
}