@SauceDemo
Característica: Pruebas de compra y flujo de usuario en SauceDemo Móvil
  Como analista de pruebas de la empresa ACME
  Quiero realizar pruebas sobre la aplicación web responsiva de SauceDemo
  Para asegurar la calidad y correcto funcionamiento en navegadores móviles.

  @Positivo
  Escenario: Compra exitosa de un producto
    Dado que el usuario abre la aplicación en el navegador móvil
    Cuando inicia sesión con el usuario "standard_user" y contraseña "secret_sauce"
    Y agrega el producto "Sauce Labs Backpack" al carrito
    Y finaliza la compra ingresando el nombre "Andres", apellido "Gonzalez" y código postal "110731"
    Entonces debería ver el mensaje de confirmación "Thank you for your order!"

  @Negativo
  Escenario: Intento de inicio de sesión con usuario bloqueado
    Dado que el usuario abre la aplicación en el navegador móvil
    Cuando intenta iniciar sesión con el usuario "locked_out_user" y contraseña "secret_sauce"
    Entonces debería visualizar el mensaje de error "Epic sadface: Sorry, this user has been locked out."

  @Alterno
  Escenario: Remover productos desde la vista del carrito de compras
    Dado que el usuario abre la aplicación en el navegador móvil
    Cuando inicia sesión con el usuario "standard_user" y contraseña "secret_sauce"
    Y agrega el producto "Sauce Labs Backpack" al carrito
    Y remueve el producto "Sauce Labs Backpack" desde el carrito
    Entonces el carrito de compras no debe contener productos
