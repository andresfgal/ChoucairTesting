package com.reto.questions;
import com.reto.userinterfaces.SauceDemoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class MensajeConfirmacion implements Question<String> {

    public static MensajeConfirmacion deCompra() {
        return new MensajeConfirmacion();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(SauceDemoUI.TEXT_CONFIRMATION).answeredBy(actor);
    }
}