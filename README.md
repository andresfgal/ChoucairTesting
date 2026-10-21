# Reto Apps Móviles

Proyecto de automatización de pruebas para Choucair Testing en SauceDemo con Java, Gradle, Serenity BDD, Screenplay, Selenium y Cucumber.

## Requisitos

- JDK 17 o superior para ejecutar Gradle 9.8.1. Las fuentes están configuradas para Java 11.
- Acceso a internet para descargar dependencias y abrir `https://www.saucedemo.com/`.
- Chrome para los escenarios funcionales.
- Chrome, Edge y Firefox instalados para la prueba de responsividad. Selenium Manager descarga los drivers necesarios.

## Ejecutar pruebas

Desde PowerShell, en la raíz del proyecto:

```powershell
.\gradlew.bat --console=plain --warning-mode none clean test aggregate
```

Para ejecutar solamente los escenarios Cucumber:

```powershell
.\gradlew.bat --console=plain --warning-mode none test --tests "com.reto.runners.SauceDemoRunner" aggregate
```

Para ejecutar solamente la prueba responsiva:

```powershell
.\gradlew.bat --console=plain --warning-mode none test --tests "com.reto.responsiveness.ResponsiveLayoutTest"
```

El resumen de consola indica el total de pruebas y cuántas fallaron. Los escenarios funcionales están en `src/test/resources/features/saucedemo_compras.feature`:

- `@Positivo`: completar una compra.
- `@Negativo`: validar el rechazo del usuario bloqueado.
- `@Alterno`: agregar y remover un producto del carrito.

## Validación responsive

`ResponsiveLayoutTest` comprueba visibilidad de los controles principales y ausencia de desplazamiento horizontal en el login y el inventario:

| Navegador | Viewports |
| --- | --- |
| Chrome | 360 × 800, 768 × 1024, 1440 × 900 |
| Edge | 360 × 800, 768 × 1024, 1440 × 900 |
| Firefox | 500 × 800, 768 × 1024, 1440 × 900 |

Firefox headless usa 500 px como ancho mínimo práctico en este entorno. La prueba no reemplaza la validación en dispositivos físicos ni cubre Safari.

## Informes

Después de ejecutar la suite, Gradle y Serenity generan informes nuevos en:

- `target/site/serenity/index.html`: reporte Serenity con pasos y evidencias.
- `build/reports/tests/test/index.html`: resumen HTML de Gradle.
- `build/test-results/test/`: resultados XML por suite.

Los informes se regeneran al ejecutar `clean test aggregate`.
