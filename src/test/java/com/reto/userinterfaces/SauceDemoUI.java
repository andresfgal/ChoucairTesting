package com.reto.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import java.util.Locale;

public class SauceDemoUI {
    // Login
    public static final Target INPUT_USERNAME = Target.the("Campo nombre de usuario")
            .located(By.id("user-name"));
    public static final Target INPUT_PASSWORD = Target.the("Campo contraseña")
            .located(By.id("password"));
    public static final Target BUTTON_LOGIN = Target.the("Boton iniciar sesión")
            .located(By.id("login-button"));

    // Inventario y Carrito
    public static Target buttonAddToCart(String product) {
        return Target.the("Boton agregar " + product + " al carrito")
                                .located(By.id("add-to-cart-" + productId(product)));
    }
    public static final Target BUTTON_CART = Target.the("Icono del carrito de compras")
            .located(By.className("shopping_cart_link"));
    public static final Target CART_ITEMS = Target.the("Productos del carrito")
            .located(By.className("cart_item"));
    public static final Target BUTTON_CHECKOUT = Target.the("Boton Checkout")
            .located(By.id("checkout"));

    public static Target buttonRemoveFromCart(String product) {
        return Target.the("Boton remover " + product + " del carrito")
                                .located(By.id("remove-" + productId(product)));
        }

        private static String productId(String product) {
                return product.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-");
    }

    // Formulario de Checkout
    public static final Target INPUT_FIRST_NAME = Target.the("Campo Nombre")
            .located(By.id("first-name"));
    public static final Target INPUT_LAST_NAME = Target.the("Campo Apellido")
            .located(By.id("last-name"));
    public static final Target INPUT_POSTAL_CODE = Target.the("Campo Código Postal")
            .located(By.id("postal-code"));
    public static final Target BUTTON_CONTINUE = Target.the("Boton Continuar")
            .located(By.id("continue"));
    public static final Target BUTTON_FINISH = Target.the("Boton Finalizar Compra")
            .located(By.id("finish"));

    // Validaciones
    public static final Target TEXT_CONFIRMATION = Target.the("Mensaje de confirmación de compra")
            .located(By.className("complete-header"));

    // Localizador del mensaje de error (Caso Negativo)
    public static final Target TEXT_ERROR_MESSAGE = Target.the("Mensaje de error de login")
            .located(By.xpath("//h3[@data-test='error']"));

    // Localizadores para el Caso Alterno
    public static final Target BUTTON_REMOVE_FROM_CART = Target.the("Boton remover producto del carrito")
            .located(By.xpath("//button[text()='Remove']"));
    public static final Target CART_ITEM = Target.the("Elemento en el carrito")
            .located(By.className("cart_item"));
}