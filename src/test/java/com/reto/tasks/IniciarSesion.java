package com.reto.tasks;
import com.reto.userinterfaces.SauceDemoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class IniciarSesion implements Task {

    private final String usuario;
    private final String clave;

    public IniciarSesion(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public static IniciarSesion conCredenciales(String usuario, String clave) {
        return Tasks.instrumented(IniciarSesion.class, usuario, clave);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(usuario).into(SauceDemoUI.INPUT_USERNAME),
                Enter.theValue(clave).into(SauceDemoUI.INPUT_PASSWORD),
                Click.on(SauceDemoUI.BUTTON_LOGIN)
        );
    }
}