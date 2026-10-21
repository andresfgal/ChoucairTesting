package com.reto.questions;
import com.reto.userinterfaces.SauceDemoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class MensajeError implements Question<String> {

    public static MensajeError deLogin() {
        return new MensajeError();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(SauceDemoUI.TEXT_ERROR_MESSAGE).answeredBy(actor);
    }
}