# Informe de pruebas

**Proyecto:** Reto Apps Móviles
**Aplicación evaluada:** SauceDemo (`https://www.saucedemo.com/`)
**Fecha de ejecución:** 21 de octubre de 2026
**Resultado general:** pruebas funcionales y responsivas aprobadas; Lighthouse identifica oportunidades de mejora no bloqueantes.

## Resumen

- Pruebas funcionales: **3 aprobadas, 0 fallidas**.
- Pruebas de responsividad: **1 aprobada, 0 fallidas** en tres navegadores.
- Lighthouse móvil: rendimiento **75**, accesibilidad **100**, mejores prácticas **77**, SEO **63**.
- Lighthouse escritorio: rendimiento **94**, accesibilidad **100**, mejores prácticas **77**, SEO **63**.

## Resultados funcionales

| Caso | Resultado | Duración |
| --- | --- | ---: |
| Compra exitosa de un producto | Aprobado | 32,502 s |
| Login con usuario bloqueado | Aprobado | 20,317 s |
| Remover producto desde el carrito | Aprobado | 19,399 s |

Los tres casos terminaron sin errores ni omisiones. La duración total de los casos Cucumber fue de 72,177 s.

## Resultados no funcionales

### Responsividad y compatibilidad

La prueba automática revisó las pantallas de login e inventario, comprobando visibilidad de controles y ausencia de desplazamiento horizontal.

| Navegador | Viewports validados | Resultado |
| --- | --- | --- |
| Chrome | 360 × 800, 768 × 1024, 1440 × 900 | Aprobado |
| Edge | 360 × 800, 768 × 1024, 1440 × 900 | Aprobado |
| Firefox | 500 × 800, 768 × 1024, 1440 × 900 | Aprobado |

Firefox headless no permitió un ancho menor a 500 px en este entorno. La prueba no cubre Safari ni dispositivos físicos y no recorre todos los pasos de compra en cada viewport.

### Lighthouse

Las mediciones son de la página pública de SauceDemo en Chrome; los perfiles móvil y escritorio usan condiciones distintas de emulación y red, por lo que sus tiempos no son una comparación directa del mismo entorno físico.

| Métrica | Móvil | Escritorio |
| --- | ---: | ---: |
| Rendimiento | 75/100 | 94/100 |
| Accesibilidad | 100/100 | 100/100 |
| Mejores prácticas | 77/100 | 77/100 |
| SEO | 63/100 | 63/100 |
| First Contentful Paint | 3,1 s | 0,7 s |
| Largest Contentful Paint | 3,4 s | 0,7 s |
| Speed Index | 6,9 s | 2,4 s |
| Total Blocking Time | 230 ms | 0 ms |
| Cumulative Layout Shift | 0,003 | 0,001 |

### Hallazgos y recomendaciones

1. **Rendimiento móvil:** es el área con mayor oportunidad. Lighthouse estima que reducir JavaScript sin uso ahorraría 102 KiB y hasta 370 ms en móvil (60 ms en escritorio). Recomendación: dividir el JavaScript por rutas y aplazar código que no sea necesario para el primer render.
2. **Accesibilidad:** puntuación 100 en ambos perfiles, sin auditorías automáticas fallidas. Complementar con revisión manual de teclado, lector de pantalla y estados de foco.
3. **Mejores prácticas:** Lighthouse detectó un listener `unload` obsoleto y dos respuestas `401` de telemetría hacia `events.backtrace.io`. Revisar la telemetría con el proveedor del sitio y migrar el listener obsoleto a una alternativa moderna.
4. **SEO:** Lighthouse encontró `<meta name="robots" content="noindex">`, que impide indexar la página. Es apropiado para un sitio de demostración; si se aplica a un sitio de producción que debe aparecer en buscadores, retirar `noindex` y revisar sitemap y metadatos.

Los hallazgos de Lighthouse corresponden al sitio externo de SauceDemo y no son cambios que este proyecto de pruebas pueda aplicar directamente.

## Informes de detalle

Los reportes detallados se generan localmente y se excluyen de Git mediante `.gitignore`:

- Serenity: `target/site/serenity/index.html`.
- Gradle HTML: `build/reports/tests/test/index.html`.
- Resultados XML: `build/test-results/test/`.
- Lighthouse: `build/reports/lighthouse/` (HTML y JSON para móvil y escritorio).

Para regenerar los resultados funcionales y responsivos, ejecuta el comando siguiente. Las mediciones Lighthouse se vuelven a obtener con Lighthouse CLI para móvil y escritorio.

## Reproducción

Desde PowerShell, en la raíz del proyecto:

```powershell
.\gradlew.bat --console=plain --warning-mode none clean test aggregate
```
