# Implementación y Depuración de un Servicio de Procesamiento de Pagos

El servicio de procesamiento de pagos en un entorno de banca debe manejar múltiples tipos de excepciones que pueden ocurrir durante la transacción. El sistema interactúa con un 'originador de créditos', un'motor antifraude' y un 'gateway de pagos'. El objetivo es implementar un servicio que pueda manejar correctamente las excepciones y proporcionar información depurada para la resolución de problemas.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Manejo de Excepciones y Depuración Efectiva |
| **Nivel** | junior-l1 |
| **Tipo** | practical |
| **Tiempo estimado** | 6 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Implementación del Servicio de Pagos

**Objetivo:** Crear un servicio que acepte solicitudes de pago y las procese correctamente.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Identifica las posibles excepciones que pueden ocurrir durante el procesamiento de un pago.
- Implementa el servicio para manejar estas excepciones de manera adecuada.
- Documenta las excepciones manejadas y cómo se tratan.

**Entregable:** Servicio que acepta y procesa solicitudes de pago, manejando excepciones de manera efectiva.

<details>
<summary>Pistas de conocimiento</summary>

- Considera las posibles causas de fallo en cada interacción con los servicios externos.
- Piensa en cómo podrías mejorar la información proporcionada al usuario en caso de error.

</details>

### Fase 2: Depuración y Mejora del Servicio

**Objetivo:** Mejorar el servicio para proporcionar información depurada y útil en caso de excepciones.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Añade mecanismos de depuración al servicio para capturar información detallada cuando ocurre una excepción.
- Mejora la documentación para incluir detalles sobre cómo depurar y resolver problemas comunes.
- Prueba el servicio con diferentes escenarios de excepción para asegurar que la información depurada es útil.

**Entregable:** Servicio mejorado con mecanismos de depuración y documentación actualizada.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de logs y trazas para capturar información detallada.
- Piensa en cómo podrías hacer que la información depurada sea más accesible para los desarrolladores.

</details>

### Fase 3: Evaluación y Optimización

**Objetivo:** Evaluar el servicio y realizar optimizaciones basadas en los resultados de las pruebas.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Realiza pruebas exhaustivas del servicio en diferentes escenarios de excepción.
- Identifica áreas de mejora y realiza las optimizaciones necesarias.
- Documenta las optimizaciones realizadas y los resultados obtenidos.

**Entregable:** Servicio optimizado con documentación actualizada que refleja las mejoras realizadas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de pruebas unitarias y de integración para validar las optimizaciones.
- Piensa en cómo podrías mejorar la eficiencia del servicio en caso de excepciones.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es una excepción y por qué es importante manejarlas en un servicio de procesamiento de pagos?
- **paraQueSirve**: ¿Para qué sirve la depuración en el contexto de un servicio de procesamiento de pagos?
- **comoSeUsa**: ¿Cómo se pueden usar los logs y trazas para depurar un servicio de procesamiento de pagos?
- **erroresComunes**: ¿Cuáles son los errores comunes que pueden ocurrir durante el procesamiento de un pago y cómo se pueden manejar?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de un servicio de procesamiento de pagos que maneje excepciones de manera efectiva?

## Criterios de Evaluacion

- Implementación correcta del servicio de procesamiento de pagos.
- Manejo efectivo de excepciones en el servicio.
- Mecanismos de depuración adecuados para capturar información detallada en caso de excepciones.
- Documentación actualizada que refleje las mejoras y optimizaciones realizadas.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
