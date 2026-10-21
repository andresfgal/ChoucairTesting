package com.reto.stepdefinitions;

import com.reto.questions.EstadoCarrito;
import com.reto.questions.MensajeConfirmacion;
import com.reto.questions.MensajeError;
import com.reto.tasks.AbrirPagina;
import com.reto.tasks.AgregarProductoAlCarrito;
import com.reto.tasks.CompletarCompra;
import com.reto.tasks.IniciarSesion;
import com.reto.tasks.RemoverProductoDelCarrito;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class SauceDemoStepDefinitions {

    @Before
    public void setStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    // --- PASOS COMPARTIDOS / INICIALES ---

    @Dado("que el usuario abre la aplicación en el navegador móvil")
    public void abrirAplicacion() {
        theActorCalled("Analista QA").wasAbleTo(
                AbrirPagina.en("https://www.saucedemo.com/")
        );
    }

    @Cuando("inicia sesión con el usuario {string} y contraseña {string}")
    public void iniciarSesion(String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(usuario, clave)
        );
    }

    // --- CASO POSITIVO ---

    @Y("agrega el producto {string} al carrito")
    public void agregarProducto(String producto) {
        theActorInTheSpotlight().attemptsTo(
                AgregarProductoAlCarrito.conNombre(producto)
        );
    }

    @Y("finaliza la compra ingresando el nombre {string}, apellido {string} y código postal {string}")
    public void finalizarCompra(String nombre, String apellido, String zipCode) {
        theActorInTheSpotlight().attemptsTo(
                CompletarCompra.conDatos(nombre, apellido, zipCode)
        );
    }

    @Entonces("debería ver el mensaje de confirmación {string}")
    public void verificarConfirmacion(String mensajeEsperado) {
        theActorInTheSpotlight().should(
                seeThat(MensajeConfirmacion.deCompra(), equalTo(mensajeEsperado))
        );
    }

    // --- CASO NEGATIVO ---

    @Cuando("intenta iniciar sesión con el usuario {string} y contraseña {string}")
    public void intentarIniciarSesion(String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(usuario, clave)
        );
    }

    @Entonces("debería visualizar el mensaje de error {string}")
    public void verificarMensajeError(String mensajeEsperado) {
        theActorInTheSpotlight().should(
                seeThat(MensajeError.deLogin(), equalTo(mensajeEsperado))
        );
    }

    // --- CASO ALTERNO ---

    @Y("remueve el producto {string} desde el carrito")
    public void removerProductoDelCarrito(String producto) {
        theActorInTheSpotlight().attemptsTo(
                RemoverProductoDelCarrito.conNombre(producto)
        );
    }

    @Entonces("el carrito de compras no debe contener productos")
    public void verificarCarritoVacio() {
        theActorInTheSpotlight().should(
                seeThat(EstadoCarrito.estaVacio(), equalTo(true))
        );
    }
}