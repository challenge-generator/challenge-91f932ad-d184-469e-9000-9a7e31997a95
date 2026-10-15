# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapterTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/payment/PaymentApplication.java` — `com.pragma.payment.domain.dto.PaymentRequest`: El import com.pragma.payment.domain.dto.PaymentRequest usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payment/PaymentApplication.java` — `com.pragma.payment.domain.dto.PaymentResponse`: El import com.pragma.payment.domain.dto.PaymentResponse usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payment/PaymentApplication.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/config/ErrorResponse.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.annotation.JsonInclude pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/config/ValidationErrorResponse.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.annotation.JsonInclude pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `io.swagger.v3`: El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getTransactionId`: Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getAmount`: Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getCurrency`: Se invoca `getCurrency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getOrigin`: Se invoca `getOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getId`: Se invoca `getId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getStatus`: Se invoca `getStatus` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java` — `Payment.getTransactionId`: Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java` — `Payment.getAmount`: Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java` — `Payment.getCurrency`: Se invoca `getCurrency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `Payment.getTransactionId`: Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `Payment.getAmount`: Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `FraudAnalysisResult.isApproved`: Se invoca `isApproved` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `FraudAnalysisResult.riskLevel`: Se invoca `riskLevel` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `FraudAnalysisResult.rejectionReasons`: Se invoca `rejectionReasons` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `Payment.getOrigin`: Se invoca `getOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java` — `Payment.getTransactionId`: Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java` — `Payment.getAmount`: Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java` — `Payment.getCurrency`: Se invoca `getCurrency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.amount`: Se invoca `amount` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.currency`: Se invoca `currency` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.origin`: Se invoca `origin` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getTransactionId`: Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getStatus`: Se invoca `getStatus` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.newStatus`: Se invoca `newStatus` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.failureReason`: Se invoca `failureReason` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getId`: Se invoca `getId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getAmount`: Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getCurrency`: Se invoca `getCurrency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getOrigin`: Se invoca `getOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getCreatedAt`: Se invoca `getCreatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getFailureReason`: Se invoca `getFailureReason` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — `Payment.isEmpty`: Se invoca `isEmpty` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapterTest.java` — `CreditOriginatorAdapter.processCredit`: Se invoca `processCredit` sobre `CreditOriginatorAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapterTest.java` — `Payment.getStatus`: Se invoca `getStatus` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.setCreatedAt`: Se invoca `setCreatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.getId`: Se invoca `getId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.getTransactionId`: Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.setId`: Se invoca `setId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Junior

### Brecha de conocimiento
Maneja correctamente las excepciones y depura el código para encontrar problemas cuando sea necesario.

### Misión / candidato
Candidato en etapa Junior, trabajando en consolidar sus habilidades fundamentales en desarrollo backend.

### Reto
- Tema: Manejo de Excepciones y Depuración Efectiva
- Seniority: junior-l1
- Tipo: practical
- Título: Implementación y Depuración de un Servicio de Procesamiento de Pagos
- Tiempo estimado: 6 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Implementación del Servicio de Pagos — objetivo: Crear un servicio que acepte solicitudes de pago y las procese correctamente. — entregable (NO resolver): Servicio que acepta y procesa solicitudes de pago, manejando excepciones de manera efectiva.
- Fase 2: Depuración y Mejora del Servicio — objetivo: Mejorar el servicio para proporcionar información depurada y útil en caso de excepciones. — entregable (NO resolver): Servicio mejorado con mecanismos de depuración y documentación actualizada.
- Fase 3: Evaluación y Optimización — objetivo: Evaluar el servicio y realizar optimizaciones basadas en los resultados de las pruebas. — entregable (NO resolver): Servicio optimizado con documentación actualizada que refleja las mejoras realizadas.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payment</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payment</name>
    <description>Servicio de procesamiento de pagos</description>

    <properties>
        <java.version>21</java.version>
        <springdoc-openapi.version>2.9.1</springdoc-openapi.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc-openapi.version}</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/payment/PaymentApplication.java ===
package com.pragma.payment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pragma.payment.application.PaymentService;
import com.pragma.payment.domain.dto.PaymentRequest;
import com.pragma.payment.domain.dto.PaymentResponse;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
public class PaymentApplication {
    private static final Logger logger = LoggerFactory.getLogger(PaymentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner demo(PaymentService paymentService) {
        return args -> {
            logger.debug("Iniciando demostración del servicio de pagos...");

            PaymentRequest request = new PaymentRequest(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new BigDecimal("100.00"),
                "USD",
                "Compra de prueba"
            );

            try {
                PaymentResponse response = paymentService.processPayment(request);
                logger.debug("Pago procesado exitosamente: {}", response);
            } catch (Exception e) {
                logger.error("Error al procesar el pago de demostración: {}", e.getMessage(), e);
            }
        };
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: payment-service
  profiles:
    active: dev
  datasource:
    url: jdbc:postgresql://localhost:5432/payment_db
    username: payment_user
    password: payment_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

logging:
  level:
    root: INFO
    com.pragma.payment: DEBUG
    org.springframework.web: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE

resilience4j:
  circuitbreaker:
    instances:
      fraudDetection:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
      paymentGateway:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operationsSorter: method

// === ARCHIVO: src/main/java/com/pragma/payment/domain/Payment.java ===
package com.pragma.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
    private UUID id;

    @NotNull(message = "El ID de la transacción no puede ser nulo")
    private String transactionId;

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    private BigDecimal amount;

    @NotNull(message = "La moneda no puede ser nula")
    @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres")
    private String currency;

    @NotNull(message = "El estado del pago no puede ser nulo")
    private PaymentStatus status;

    @NotNull(message = "El origen del pago no puede ser nulo")
    private String origin;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private String failureReason;

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REJECTED
    }

    public Payment() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    public Payment(UUID id, String transactionId, BigDecimal amount, String currency, 
                  PaymentStatus status, String origin, LocalDateTime createdAt, 
                  LocalDateTime updatedAt, String failureReason) {
        this.id = id != null ? id : UUID.randomUUID();
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.status = status != null ? status : PaymentStatus.PENDING;
        this.origin = origin;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.failureReason = failureReason;
    }

    public void markAsCompleted() {
        this.status = PaymentStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
        this.failureReason = null;
    }

    public void markAsFailed(String reason) {
        this.status = PaymentStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
        this.failureReason = reason;
    }

    public void markAsRejected(String reason) {
        this.status = PaymentStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
        this.failureReason = reason;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/PaymentServicePort.java ===
package com.pragma.payment.domain;




import com.pragma.payment.domain.exception.PaymentGatewayException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import java.util.List;
import java.util.UUID;

public interface PaymentServicePort {
    Payment processPayment(Payment payment) throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException;
    
    Payment getPaymentById(UUID paymentId);
    
    List<Payment> getPaymentsByStatus(Payment.PaymentStatus status);
    
    Payment updatePaymentStatus(UUID paymentId, Payment.PaymentStatus newStatus, String failureReason);
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/exception/CreditOriginatorException.java ===
package com.pragma.payment.domain.exception;

public class CreditOriginatorException extends Exception {
    private final String transactionId;
    private final String originatorCode;

    public CreditOriginatorException(String message, String transactionId, String originatorCode) {
        super(message);
        this.transactionId = transactionId;
        this.originatorCode = originatorCode;
    }

    public CreditOriginatorException(String message, Throwable cause, String transactionId, String originatorCode) {
        super(message, cause);
        this.transactionId = transactionId;
        this.originatorCode = originatorCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOriginatorCode() {
        return originatorCode;
    }

    public String getErrorDetails() {
        return String.format("Transaction ID: %s, Originator Code: %s, Error: %s", 
                transactionId, originatorCode, getMessage());
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/exception/FraudDetectionException.java ===
package com.pragma.payment.domain.exception;

public class FraudDetectionException extends Exception {
    private final String transactionId;
    private final String fraudScore;
    private final String riskLevel;

    public FraudDetectionException(String message, String transactionId, String fraudScore, String riskLevel) {
        super(message);
        this.transactionId = transactionId;
        this.fraudScore = fraudScore;
        this.riskLevel = riskLevel;
    }

    public FraudDetectionException(String message, Throwable cause, String transactionId, String fraudScore, String riskLevel) {
        super(message, cause);
        this.transactionId = transactionId;
        this.fraudScore = fraudScore;
        this.riskLevel = riskLevel;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getFraudScore() {
        return fraudScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getErrorDetails() {
        return String.format("Transaction: %s | Fraud Score: %s | Risk Level: %s | Message: %s",
                transactionId, fraudScore, riskLevel, getMessage());
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/exception/PaymentGatewayException.java ===
package com.pragma.payment.domain.exception;

public class PaymentGatewayException extends Exception {
    private final String transactionId;
    private final String gatewayCode;
    private final String gatewayMessage;
    private final boolean isRetryable;

    public PaymentGatewayException(String message, String transactionId, String gatewayCode, String gatewayMessage) {
        super(message);
        this.transactionId = transactionId;
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.isRetryable = isRetryableError(gatewayCode);
    }

    public PaymentGatewayException(String message, Throwable cause, String transactionId, 
                                    String gatewayCode, String gatewayMessage) {
        super(message, cause);
        this.transactionId = transactionId;
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.isRetryable = isRetryableError(gatewayCode);
    }

    private boolean isRetryableError(String code) {
        return "TIMEOUT".equals(code) || "CONNECTION_ERROR".equals(code) || "503".equals(code);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public String getGatewayMessage() {
        return gatewayMessage;
    }

    public boolean isRetryable() {
        return isRetryable;
    }

    public String getErrorDetails() {
        return String.format("Transaction: %s | Gateway Code: %s | Gateway Message: %s | Retryable: %s | Cause: %s",
                transactionId, gatewayCode, gatewayMessage, isRetryable, getCause() != null ? getCause().getMessage() : "N/A");
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/application/PaymentService.java ===
package com.pragma.payment.application;


import com.pragma.payment.domain.PaymentStatus;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.PaymentServicePort;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentServicePort paymentPort;

    public PaymentService(PaymentServicePort paymentPort) {
        this.paymentPort = paymentPort;
    }

    public Payment processPayment(Payment payment) {
        logger.info("Iniciando procesamiento de pago para transactionId: {}", payment.getTransactionId());
        logger.debug("Detalles del pago: amount={}, currency={}, origin={}", 
                payment.getAmount(), payment.getCurrency(), payment.getOrigin());
        
        try {
            Payment processedPayment = paymentPort.processPayment(payment);
            logger.info("Pago procesado exitosamente. PaymentId: {}, Status: {}", 
                    processedPayment.getId(), processedPayment.getStatus());
            return processedPayment;
        } catch (CreditOriginatorException e) {
            logger.error("Error del originador de créditos para transactionId: {}. Code: {}, Details: {}",
                    e.getTransactionId(), e.getOriginatorCode(), e.getErrorDetails(), e);
            payment.markAsFailed("CREDIT_ORIGINATOR: " + e.getOriginatorCode() + " - " + e.getMessage());
            return paymentPort.updatePaymentStatus(payment.getId(), payment.getStatus(), e.getMessage());
        } catch (FraudDetectionException e) {
            logger.error("Transacción bloqueada por motor antifraude. TransactionId: {}. Risk: {}, Details: {}",
                    e.getTransactionId(), e.getRiskLevel(), e.getErrorDetails(), e);
            payment.markAsRejected("FRAUD_DETECTED: " + e.getRiskLevel() + " - " + e.getMessage());
            return paymentPort.updatePaymentStatus(payment.getId(), payment.getStatus(), e.getMessage());
        } catch (PaymentGatewayException e) {
            logger.error("Error del gateway de pagos para transactionId: {}. Code: {}, Retryable: {}, Details: {}",
                    e.getTransactionId(), e.getGatewayCode(), e.isRetryable(), e.getErrorDetails(), e);
            payment.markAsFailed("GATEWAY: " + e.getGatewayCode() + " - " + e.getMessage());
            return paymentPort.updatePaymentStatus(payment.getId(), payment.getStatus(), e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado procesando pago para transactionId: {}. Error: {}",
                    payment.getTransactionId(), e.getMessage(), e);
            payment.markAsFailed("UNEXPECTED_ERROR: " + e.getMessage());
            return paymentPort.updatePaymentStatus(payment.getId(), payment.getStatus(), e.getMessage());
        }
    }

    public Payment getPaymentById(UUID paymentId) {
        logger.debug("Obteniendo pago con ID: {}", paymentId);
        Payment payment = paymentPort.getPaymentById(paymentId);
        if (payment == null) {
            logger.warn("Pago no encontrado con ID: {}", paymentId);
        }
        return payment;
    }

    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        logger.debug("Obteniendo pagos con estado: {}", status);
        List<Payment> payments = paymentPort.getPaymentsByStatus(status);
        logger.info("Se encontraron {} pagos con estado {}", payments.size(), status);
        return payments;
    }

    public Payment updatePaymentStatus(UUID paymentId, Payment.PaymentStatus newStatus, String failureReason) {
        logger.info("Actualizando estado del pago {} a {}", paymentId, newStatus);
        return paymentPort.updatePaymentStatus(paymentId, newStatus, failureReason);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java ===
package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CreditOriginatorAdapter {

    private static final Logger log = LoggerFactory.getLogger(CreditOriginatorAdapter.class);
    private static final String ORIGINATOR_CODE = "CREDIT_ORIG";
    private static final BigDecimal MAX_CREDIT_AMOUNT = new BigDecimal("50000.00");
    private static final BigDecimal MIN_CREDIT_AMOUNT = new BigDecimal("10.00");

    public Payment processCreditRequest(Payment payment) throws CreditOriginatorException {
        log.debug("Iniciando solicitud de crédito para transacción: {}", payment.getTransactionId());
        
        if (payment == null) {
            throw new CreditOriginatorException(
                "Payment no puede ser nulo",
                null,
                ORIGINATOR_CODE
            );
        }

        if (payment.getAmount() == null) {
            throw new CreditOriginatorException(
                "El monto del pago es obligatorio",
                payment.getTransactionId(),
                ORIGINATOR_CODE
            );
        }

        if (payment.getAmount().compareTo(MIN_CREDIT_AMOUNT) < 0) {
            String errorMsg = String.format(
                "El monto mínimo para crédito es %s, monto enviado: %s",
                MIN_CREDIT_AMOUNT,
                payment.getAmount()
            );
            log.warn("Crédito rechazado por monto mínimo: {}", errorMsg);
            throw new CreditOriginatorException(errorMsg, payment.getTransactionId(), ORIGINATOR_CODE);
        }

        if (payment.getAmount().compareTo(MAX_CREDIT_AMOUNT) > 0) {
            String errorMsg = String.format(
                "El monto máximo para crédito es %s, monto enviado: %s",
                MAX_CREDIT_AMOUNT,
                payment.getAmount()
            );
            log.warn("Crédito rechazado por monto máximo: {}", errorMsg);
            throw new CreditOriginatorException(errorMsg, payment.getTransactionId(), ORIGINATOR_CODE);
        }

        if (payment.getCurrency() == null || !isValidCurrency(payment.getCurrency())) {
            String errorMsg = String.format(
                "Moneda no soportada: %s. Monedas válidas: USD, EUR, COP",
                payment.getCurrency()
            );
            log.warn("Crédito rechazado por moneda inválida: {}", errorMsg);
            throw new CreditOriginatorException(errorMsg, payment.getTransactionId(), ORIGINATOR_CODE);
        }

        log.info("Crédito aprobado para transacción: {}, monto: {} {}", 
            payment.getTransactionId(), 
            payment.getAmount(), 
            payment.getCurrency());
        
        return payment;
    }

    public String getOriginatorTransactionId(UUID paymentId) {
        return "CR-" + paymentId.toString();
    }

    public boolean validateCreditStatus(String originatorTransactionId) {
        log.debug("Validando estado del crédito: {}", originatorTransactionId);
        return originatorTransactionId != null && originatorTransactionId.startsWith("CR-");
    }

    public void cancelCredit(String originatorTransactionId) throws CreditOriginatorException {
        if (originatorTransactionId == null || originatorTransactionId.isEmpty()) {
            throw new CreditOriginatorException(
                "ID de transacción de crédito no válido para cancelación",
                null,
                ORIGINATOR_CODE
            );
        }
        log.info("Crédito cancelado: {}", originatorTransactionId);
    }

    private boolean isValidCurrency(String currency) {
        return "USD".equalsIgnoreCase(currency) || 
               "EUR".equalsIgnoreCase(currency) || 
               "COP".equalsIgnoreCase(currency);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java ===
package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.FraudDetectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class FraudDetectionAdapter {

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionAdapter.class);
    private static final String FRAUD_ENGINE_CODE = "FRAUD_DET";
    private static final BigDecimal HIGH_RISK_AMOUNT = new BigDecimal("10000.00");
    private static final int MAX_DAILY_TRANSACTIONS = 10;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public FraudAnalysisResult analyzeTransaction(Payment payment) throws FraudDetectionException {
        log.debug("Iniciando análisis antifraude para transacción: {}", payment.getTransactionId());
        
        if (payment == null) {
            throw new FraudDetectionException(
                "Payment no puede ser nulo para análisis antifraude",
                null,
                FRAUD_ENGINE_CODE
            );
        }

        if (payment.getAmount() == null) {
            throw new FraudDetectionException(
                "Monto requerido para análisis antifraude",
                payment.getTransactionId(),
                FRAUD_ENGINE_CODE
            );
        }

        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            String errorMsg = "El monto debe ser mayor a cero para análisis antifraude";
            log.warn("Transacción rechazada por monto inválido: {}", errorMsg);
            throw new FraudDetectionException(errorMsg, payment.getTransactionId(), FRAUD_ENGINE_CODE);
        }

        boolean isHighRisk = evaluateRiskLevel(payment);
        boolean isPatternSuspicious = detectSuspiciousPattern(payment);
        boolean isVelocityExceeded = checkVelocityLimit(payment);

        FraudAnalysisResult result = new FraudAnalysisResult(
            payment.getTransactionId(),
            !isHighRisk && !isPatternSuspicious && !isVelocityExceeded,
            isHighRisk ? "Monto alto detectado" : null,
            isPatternSuspicious ? "Patrón sospechoso detectado" : null,
            isVelocityExceeded ? "Límite de velocidad excedido" : null
        );

        log.info("Análisis antifraude completado para {}: aprobado={}, riesgo={}",
            payment.getTransactionId(),
            result.isApproved(),
            result.riskLevel());

        if (!result.isApproved()) {
            throw new FraudDetectionException(
                "Transacción rechazada por motor antifraude: " + result.rejectionReasons(),
                payment.getTransactionId(),
                FRAUD_ENGINE_CODE
            );
        }

        return result;
    }

    public boolean validatePayerIdentification(String identification) throws FraudDetectionException {
        if (identification == null || identification.trim().isEmpty()) {
            throw new FraudDetectionException(
                "Identificación del pagador requerida",
                null,
                FRAUD_ENGINE_CODE
            );
        }

        if (identification.length() < 5 || identification.length() > 20) {
            throw new FraudDetectionException(
                "Identificación con longitud inválida",
                null,
                FRAUD_ENGINE_CODE
            );
        }

        return true;
    }

    public boolean validatePayeeIdentification(String identification) throws FraudDetectionException {
        if (identification == null || identification.trim().isEmpty()) {
            throw new FraudDetectionException(
                "Identificación del beneficiario requerida",
                null,
                FRAUD_ENGINE_CODE
            );
        }
        return true;
    }

    private boolean evaluateRiskLevel(Payment payment) {
        return payment.getAmount().compareTo(HIGH_RISK_AMOUNT) >= 0;
    }

    private boolean detectSuspiciousPattern(Payment payment) {
        if (payment.getOrigin() == null) {
            return true;
        }
        return payment.getOrigin().contains("TEST") || payment.getOrigin().contains("MOCK");
    }

    private boolean checkVelocityLimit(Payment payment) {
        return false;
    }

    public record FraudAnalysisResult(
        String transactionId,
        boolean approved,
        String highRiskReason,
        String suspiciousPatternReason,
        String velocityExceededReason
    ) {
        public String riskLevel() {
            if (!approved) return "HIGH";
            if (highRiskReason != null) return "MEDIUM";
            return "LOW";
        }

        public String rejectionReasons() {
            StringBuilder reasons = new StringBuilder();
            if (highRiskReason != null) reasons.append(highRiskReason).append("; ");
            if (suspiciousPatternReason != null) reasons.append(suspiciousPatternReason).append("; ");
            if (velocityExceededReason != null) reasons.append(velocityExceededReason).append("; ");
            return reasons.toString();
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java ===
package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PaymentGatewayAdapter {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayAdapter.class);
    private static final String GATEWAY_CODE = "PAY_GATEWAY";
    private static final BigDecimal GATEWAY_FEE_PERCENT = new BigDecimal("0.029");
    private static final BigDecimal MIN_TRANSACTION_AMOUNT = new BigDecimal("1.00");
    private static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("100000.00");

    public GatewayResponse processPayment(Payment payment) throws PaymentGatewayException {
        log.debug("Procesando pago a través del gateway para transacción: {}", payment.getTransactionId());
        
        validatePayment(payment);

        BigDecimal gatewayFee = calculateGatewayFee(payment.getAmount());
        BigDecimal totalAmount = payment.getAmount().add(gatewayFee);

        String gatewayTransactionId = generateGatewayTransactionId(payment.getTransactionId());
        
        boolean authorizationResult = authorizeTransaction(payment);
        
        if (!authorizationResult) {
            log.warn("Autorización denegada por gateway para transacción: {}", payment.getTransactionId());
            throw new PaymentGatewayException(
                "Autorización denegada por el gateway de pagos",
                payment.getTransactionId(),
                GATEWAY_CODE,
                "AUTH_DENIED"
            );
        }

        boolean captureResult = captureTransaction(payment, totalAmount);
        
        if (!captureResult) {
            log.error("Captura fallida para transacción: {}", payment.getTransactionId());
            throw new PaymentGatewayException(
                "Error al capturar el pago en el gateway",
                payment.getTransactionId(),
                GATEWAY_CODE,
                "CAPTURE_FAILED"
            );
        }

        log.info("Pago procesado exitosamente. Gateway ID: {}, Fee: {}", 
            gatewayTransactionId, 
            gatewayFee);

        return new GatewayResponse(
            gatewayTransactionId,
            true,
            "APPROVED",
            gatewayFee,
            totalAmount,
            LocalDateTime.now()
        );
    }

    public GatewayResponse refundPayment(String gatewayTransactionId, BigDecimal amount) throws PaymentGatewayException {
        if (gatewayTransactionId == null || gatewayTransactionId.isEmpty()) {
            throw new PaymentGatewayException(
                "ID de transacción de gateway requerido para reembolso",
                null,
                GATEWAY_CODE,
                "INVALID_TRANSACTION_ID"
            );
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentGatewayException(
                "Monto de reembolso inválido",
                null,
                GATEWAY_CODE,
                "INVALID_AMOUNT"
            );
        }

        log.info("Procesando reembolso para gateway ID: {}, monto: {}", gatewayTransactionId, amount);
        
        String refundId = "REF-" + UUID.randomUUID().toString();
        
        return new GatewayResponse(
            refundId,
            true,
            "REFUNDED",
            BigDecimal.ZERO,
            amount,
            LocalDateTime.now()
        );
    }

    public boolean verifyTransactionStatus(String gatewayTransactionId) throws PaymentGatewayException {
        if (gatewayTransactionId == null || gatewayTransactionId.isEmpty()) {
            throw new PaymentGatewayException(
                "ID de transacción de gateway requerido para verificación",
                null,
                GATEWAY_CODE,
                "INVALID_TRANSACTION_ID"
            );
        }

        log.debug("Verificando estado de transacción: {}", gatewayTransactionId);
        return true;
    }

    private void validatePayment(Payment payment) throws PaymentGatewayException {
        if (payment.getAmount() == null) {
            throw new PaymentGatewayException(
                "Monto de pago requerido",
                payment.getTransactionId(),
                GATEWAY_CODE,
                "MISSING_AMOUNT"
            );
        }

        if (payment.getAmount().compareTo(MIN_TRANSACTION_AMOUNT) < 0) {
            throw new PaymentGatewayException(
                String.format("Monto mínimo de transacción es %s", MIN_TRANSACTION_AMOUNT),
                payment.getTransactionId(),
                GATEWAY_CODE,
                "AMOUNT_TOO_LOW"
            );
        }

        if (payment.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            throw new PaymentGatewayException(
                String.format("Monto máximo de transacción es %s", MAX_TRANSACTION_AMOUNT),
                payment.getTransactionId(),
                GATEWAY_CODE,
                "AMOUNT_TOO_HIGH"
            );
        }

        if (payment.getCurrency() == null || payment.getCurrency().isEmpty()) {
            throw new PaymentGatewayException(
                "Moneda de pago requerida",
                payment.getTransactionId(),
                GATEWAY_CODE,
                "MISSING_CURRENCY"
            );
        }
    }

    private BigDecimal calculateGatewayFee(BigDecimal amount) {
        return amount.multiply(GATEWAY_FEE_PERCENT).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private String generateGatewayTransactionId(String paymentTransactionId) {
        return "GW-" + paymentTransactionId + "-" + System.currentTimeMillis();
    }

    private boolean authorizeTransaction(Payment payment) {
        return payment.getAmount().compareTo(new BigDecimal("5000.00")) < 0;
    }

    private boolean captureTransaction(Payment payment, BigDecimal totalAmount) {
        return true;
    }

    public record GatewayResponse(
        String gatewayTransactionId,
        boolean success,
        String status,
        BigDecimal fee,
        BigDecimal totalAmount,
        LocalDateTime processedAt
    ) {}
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/config/ErrorResponse.java ===
package com.pragma.payment.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    private final String transactionId;
    private final Map<String, List<String>> validationErrors;
    private final String developerMessage;

    public ErrorResponse(int status, String error, String message, String path) {
        if (status < 100 || status >= 600) {
            throw new IllegalArgumentException("Codigo de estado HTTP invalido: " + status);
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje de error no puede ser nulo o vacio");
        }
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.transactionId = null;
        this.validationErrors = null;
        this.developerMessage = null;
    }

    private ErrorResponse(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.path = builder.path;
        this.timestamp = builder.timestamp != null ? builder.timestamp : LocalDateTime.now();
        this.transactionId = builder.transactionId;
        this.validationErrors = builder.validationErrors;
        this.developerMessage = builder.developerMessage;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public boolean hasValidationErrors() {
        return validationErrors != null && !validationErrors.isEmpty();
    }

    public boolean hasTransactionId() {
        return transactionId != null && !transactionId.isBlank();
    }

    public boolean hasDeveloperMessage() {
        return developerMessage != null && !developerMessage.isBlank();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int status;
        private String error;
        private String message;
        private String path;
        private LocalDateTime timestamp;
        private String transactionId;
        private Map<String, List<String>> validationErrors;
        private String developerMessage;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder validationErrors(Map<String, List<String>> validationErrors) {
            this.validationErrors = validationErrors;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ErrorResponse build() {
            if (message == null || message.isBlank()) {
                throw new IllegalStateException("El mensaje de error es requerido");
            }
            return new ErrorResponse(this);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/config/ValidationErrorResponse.java ===
package com.pragma.payment.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    private final List<FieldError> errors;

    public ValidationErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.errors = new ArrayList<>();
    }

    private ValidationErrorResponse(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.path = builder.path;
        this.timestamp = builder.timestamp != null ? builder.timestamp : LocalDateTime.now();
        this.errors = builder.errors != null ? builder.errors : new ArrayList<>();
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void addFieldError(String field, String message, String code) {
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("El nombre del campo no puede ser nulo o vacio");
        }
        this.errors.add(new FieldError(field, message, code));
    }

    public void addFieldErrors(List<FieldError> fieldErrors) {
        if (fieldErrors != null) {
            this.errors.addAll(fieldErrors);
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public int getErrorCount() {
        return errors.size();
    }

    public List<String> getAllErrorMessages() {
        return errors.stream()
                .map(FieldError::getMessage)
                .collect(Collectors.toList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class FieldError {
        private final String field;
        private final String message;
        private final String code;

        public FieldError(String field, String message, String code) {
            if (field == null || field.isBlank()) {
                throw new IllegalArgumentException("El nombre del campo no puede ser nulo o vacio");
            }
            this.field = field;
            this.message = message;
            this.code = code;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }
    }

    public static class Builder {
        private int status = 400;
        private String error = "Bad Request";
        private String message = "Validacion fallida";
        private String path;
        private LocalDateTime timestamp;
        private List<FieldError> errors;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder errors(List<FieldError> errors) {
            this.errors = errors;
            return this;
        }

        public ValidationErrorResponse build() {
            if (path == null || path.isBlank()) {
                throw new IllegalStateException("La ruta de la peticion es requerida");
            }
            return new ValidationErrorResponse(this);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/config/GlobalExceptionHandler.java ===
package com.pragma.payment.infrastructure.config;



import com.pragma.payment.domain.Payment;
import com.pragma.payment.infrastructure.controller.ErrorResponse;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CreditOriginatorException.class)
    public ResponseEntity<ErrorResponse> handleCreditOriginatorException(
            CreditOriginatorException ex, WebRequest request) {
        logger.error("Error del originador de creditos: transactionId={}, code={}, details={}",
                ex.getTransactionId(), ex.getOriginatorCode(), ex.getErrorDetails(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_GATEWAY.value())
                .error("Credit Originator Error")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .transactionId(ex.getTransactionId())
                .developerMessage("El originador de creditos rechazo la transaccion. Verificar el codigo del originador: " 
                        + ex.getOriginatorCode())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
    }

    @ExceptionHandler(FraudDetectionException.class)
    public ResponseEntity<ErrorResponse> handleFraudDetectionException(
            FraudDetectionException ex, WebRequest request) {
        logger.error("Error en detector de fraude: transactionId={}, riskLevel={}, details={}",
                ex.getTransactionId(), ex.getRiskLevel(), ex.getErrorDetails(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error("Fraud Detection Error")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .transactionId(ex.getTransactionId())
                .developerMessage("Transaccion marcada como fraude. Nivel de riesgo: " + ex.getRiskLevel())
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(PaymentGatewayException.class)
    public ResponseEntity<ErrorResponse> handlePaymentGatewayException(
            PaymentGatewayException ex, WebRequest request) {
        logger.error("Error del gateway de pagos: transactionId={}, gatewayCode={}, details={}",
                ex.getTransactionId(), ex.getGatewayCode(), ex.getErrorDetails(), ex);

        HttpStatus status = determineHttpStatus(ex.getErrorCode());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .error("Payment Gateway Error")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .transactionId(ex.getTransactionId())
                .developerMessage("Error del gateway de pagos. Codigo de error: " + ex.getErrorCode())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("Error de validacion en la peticion: {}", request.getDescription(false));

        List<ValidationErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationErrorResponse.FieldError(
                        error.getField(),
                        error.getDefaultMessage() != null ? error.getDefaultMessage() : "Campo invalido",
                        error.getCode()))
                .collect(Collectors.toList());

        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Los datos de la solicitud no son validos")
                .path(request.getDescription(false).replace("uri=", ""))
                .errors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.warn("Tipo de argumento invalido: {} - valor proporcionado: {}",
                ex.getName(), ex.getValue());

        String message = String.format("El parametro '%s' tiene un formato invalido. Se esperaba un tipo '%s'",
                ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Type Mismatch")
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        logger.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Error inesperado no manejado: {}", request.getDescription(false), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Ocurrio un error interno en el servidor. Por favor, contacte al administrador")
                .path(request.getDescription(false).replace("uri=", ""))
                .developerMessage(ex.getClass().getName() + ": " + ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private HttpStatus determineHttpStatus(String errorCode) {
        if (errorCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return switch (errorCode) {
            case "GATEWAY_TIMEOUT" -> HttpStatus.GATEWAY_TIMEOUT;
            case "SERVICE_UNAVAILABLE" -> HttpStatus.SERVICE_UNAVAILABLE;
            case "CONFLICT" -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_GATEWAY;
        };
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java ===
package com.pragma.payment.infrastructure.controller;


import com.pragma.payment.domain.PaymentStatus;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.PaymentServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Pagos", description = "API para procesamiento de pagos")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentServicePort paymentService;

    public PaymentController(PaymentServicePort paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(summary = "Procesar un pago", description = "Procesa una solicitud de pago validando el originador de creditos, el detector de fraude y el gateway de pagos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago procesado exitosamente",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida - datos de entrada incorrectos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "400"),
            @ApiResponse(responseCode = "422", description = "Transaccion rechazada por fraude",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "422"),
            @ApiResponse(responseCode = "502", description = "Error del originador de creditos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "502"),
            @ApiResponse(responseCode = "503", description = "Gateway de pagos no disponible",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "503"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "500")
    })
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        logger.info("Recibida solicitud de pago: amount={}, currency={}, origin={}",
                request.amount(), request.currency(), request.origin());

        Payment payment = new Payment(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                request.amount(),
                request.currency(),
                Payment.PaymentStatus.PENDING,
                request.origin()
        );

        Payment processedPayment = paymentService.processPayment(payment);
        logger.info("Pago procesado exitosamente: transactionId={}, status={}",
                processedPayment.getTransactionId(), processedPayment.getStatus());

        PaymentResponse response = toPaymentResponse(processedPayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "Obtener un pago por ID", description = "Recupera los detalles de un pago especifica usando su identificador unico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "404"),
            @ApiResponse(responseCode = "400", description = "Formato de ID invalido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "400")
    })
    public ResponseEntity<PaymentResponse> getPaymentById(
            @Parameter(description = "ID unico del pago", required = true)
            @PathVariable UUID paymentId) {
        logger.debug("Buscando pago con ID: {}", paymentId);

        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(toPaymentResponse(payment));
    }

    @GetMapping
    @Operation(summary = "Listar pagos por estado", description = "Recupera todos los pagos que coinciden con el estado especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagos recuperada exitosamente",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Estado de pago invalido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "400")
    })
    public ResponseEntity<List<PaymentResponse>> getPaymentsByStatus(
            @Parameter(description = "Estado del pago (PENDING, COMPLETED, FAILED, REJECTED)", required = true)
            @RequestParam Payment.PaymentStatus status) {
        logger.info("Buscando pagos con estado: {}", status);

        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        List<PaymentResponse> responses = payments.stream()
                .map(this::toPaymentResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{paymentId}/status")
    @Operation(summary = "Actualizar estado del pago", description = "Actualiza el estado de un pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "404"),
            @ApiResponse(responseCode = "400", description = "Datos de actualizacion invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "400")
    })
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @Parameter(description = "ID unico del pago", required = true)
            @PathVariable UUID paymentId,
            @RequestBody UpdatePaymentStatusRequest request) {
        logger.info("Actualizando estado del pago {} a {}", paymentId, request.newStatus());

        Payment updatedPayment = paymentService.updatePaymentStatus(
                paymentId,
                request.newStatus(),
                request.failureReason()
        );

        return ResponseEntity.ok(toPaymentResponse(updatedPayment));
    }

    private PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId().toString(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus().name(),
                payment.getOrigin(),
                payment.getCreatedAt().toString(),
                payment.getUpdatedAt() != null ? payment.getUpdatedAt().toString() : null,
                payment.getFailureReason()
        );
    }

    public record PaymentRequest(
            @Parameter(description = "Monto del pago", required = true, example = "100.00")
            BigDecimal amount,
            @Parameter(description = "Moneda del pago (USD, EUR, etc.)", required = true, example = "USD")
            String currency,
            @Parameter(description = "Origen del pago", required = true, example = "ONLINE_STORE")
            String origin
    ) {}

    public record PaymentResponse(
            String id,
            String transactionId,
            BigDecimal amount,
            String currency,
            String status,
            String origin,
            String createdAt,
            String updatedAt,
            String failureReason
    ) {}

    public record UpdatePaymentStatusRequest(
            @Parameter(description = "Nuevo estado del pago", required = true)
            Payment.PaymentStatus newStatus,
            @Parameter(description = "Razon del fallo (requerido si el estado es FAILED)")
            String failureReason
    ) {}

    public record ErrorResponse(
            int status,
            String error,
            String message,
            String path
    ) {}
}

// === ARCHIVO: src/test/java/com/pragma/payment/application/PaymentServiceTest.java ===
package com.pragma.payment.application;


import com.pragma.payment.domain.PaymentStatus;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.PaymentServicePort;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para PaymentService - Superficie de práctica")
class PaymentServiceTest {

    @Mock
    private PaymentServicePort paymentServicePort;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment(
            UUID.randomUUID(),
            "TXN-001",
            new BigDecimal("1000.00"),
            "USD",
            Payment.PaymentStatus.PENDING,
            "online"
        );
    }

    @Test
    @DisplayName("Debe procesar pago exitosamente cuando pasa todas las validaciones")
    void shouldProcessPaymentSuccessfully() throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException {
        // Given: un pago válido que pasa todas las validaciones
        when(paymentServicePort.processPayment(any(Payment.class))).thenReturn(payment);

        // When: se procesa el pago
        Payment result = paymentService.processPayment(payment);

        // Then: el pago fue procesado
        assertNotNull(result);
        verify(paymentServicePort, times(1)).processPayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe lanzar CreditOriginatorException cuando el originador falla")
    void shouldThrowCreditOriginatorException() throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException {
        // Given: el originador de crédito rechaza la transacción
        when(paymentServicePort.processPayment(any(Payment.class)))
            .thenThrow(new CreditOriginatorException(
                "Credit originator rejected transaction",
                "TXN-001",
                "CREDIT_REJECTED"
            ));

        // When & Then: se propaga la excepción
        assertThrows(CreditOriginatorException.class, () -> {
            paymentService.processPayment(payment);
        });
    }

    @Test
    @DisplayName("Debe lanzar FraudDetectionException cuando el motor antifraude detecta fraude")
    void shouldThrowFraudDetectionException() throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException {
        // Given: el motor antifraude detecta actividad sospechosa
        when(paymentServicePort.processPayment(any(Payment.class)))
            .thenThrow(new FraudDetectionException(
                "Suspicious activity detected",
                "HIGH_RISK"
            ));

        // When & Then: se propaga la excepción
        assertThrows(FraudDetectionException.class, () -> {
            paymentService.processPayment(payment);
        });
    }

    @Test
    @DisplayName("Debe lanzar PaymentGatewayException cuando el gateway falla")
    void shouldThrowPaymentGatewayException() throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException {
        // Given: el gateway de pagos no puede procesar la transacción
        when(paymentServicePort.processPayment(any(Payment.class)))
            .thenThrow(new PaymentGatewayException(
                "Gateway timeout",
                "TIMEOUT"
            ));

        // When & Then: se propaga la excepción
        assertThrows(PaymentGatewayException.class, () -> {
            paymentService.processPayment(payment);
        });
    }

    @Test
    @DisplayName("Debe obtener pago por ID correctamente")
    void shouldGetPaymentById() {
        // Given: existe un pago con el ID especificado
        UUID paymentId = UUID.randomUUID();
        when(paymentServicePort.getPaymentById(paymentId)).thenReturn(payment);

        // When: se busca el pago por ID
        Payment result = paymentService.getPaymentById(paymentId);

        // Then: se retorna el pago
        assertNotNull(result);
        verify(paymentServicePort, times(1)).getPaymentById(paymentId);
    }

    @Test
    @DisplayName("Debe obtener pagos por estado correctamente")
    void shouldGetPaymentsByStatus() {
        // Given: existen pagos con estado COMPLETED
        List<Payment> payments = List.of(payment);
        when(paymentServicePort.getPaymentsByStatus(Payment.PaymentStatus.COMPLETED))
            .thenReturn(payments);

        // When: se buscan pagos por estado
        List<Payment> result = paymentService.getPaymentsByStatus(Payment.PaymentStatus.COMPLETED);

        // Then: se retornan los pagos
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Debe actualizar estado del pago correctamente")
    void shouldUpdatePaymentStatus() {
        // Given: el pago existe y se puede actualizar
        UUID paymentId = UUID.randomUUID();
        when(paymentServicePort.updatePaymentStatus(
            eq(paymentId),
            eq(Payment.PaymentStatus.COMPLETED),
            isNull()
        )).thenReturn(payment);

        // When: se actualiza el estado del pago
        Payment result = paymentService.updatePaymentStatus(
            paymentId,
            Payment.PaymentStatus.COMPLETED,
            null
        );

        // Then: el pago fue actualizado
        assertNotNull(result);
        verify(paymentServicePort, times(1)).updatePaymentStatus(
            paymentId,
            Payment.PaymentStatus.COMPLETED,
            null
        );
    }
}

// === ARCHIVO: src/test/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapterTest.java ===
package com.pragma.payment.infrastructure.adapter;


import com.pragma.payment.domain.PaymentStatus;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para CreditOriginatorAdapter - Superficie de práctica")
class CreditOriginatorAdapterTest {

    @Mock
    private CreditOriginatorClient creditOriginatorClient;

    @InjectMocks
    private CreditOriginatorAdapter creditOriginatorAdapter;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment(
            UUID.randomUUID(),
            "TXN-002",
            new BigDecimal("5000.00"),
            "USD",
            Payment.PaymentStatus.PENDING,
            "credit_originator"
        );
    }

    @Test
    @DisplayName("Debe procesar crédito exitosamente cuando el originador acepta")
    void shouldProcessCreditSuccessfully() throws CreditOriginatorException {
        // Given: el originador de crédito acepta la solicitud
        CreditOriginatorResponse response = new CreditOriginatorResponse(
            "TXN-002",
            "APPROVED",
            "Credit approved successfully"
        );
        when(creditOriginatorClient.requestCredit(any(CreditOriginatorRequest.class)))
            .thenReturn(response);

        // When: se procesa el crédito
        Payment result = creditOriginatorAdapter.processCredit(payment);

        // Then: el pago se marca como completado
        assertNotNull(result);
        assertEquals(Payment.PaymentStatus.COMPLETED, result.getStatus());
        verify(creditOriginatorClient, times(1)).requestCredit(any(CreditOriginatorRequest.class));
    }

    @Test
    @DisplayName("Debe lanzar CreditOriginatorException cuando el originador rechaza")
    void shouldThrowExceptionWhenCreditRejected() throws CreditOriginatorException {
        // Given: el originador rechaza el crédito
        CreditOriginatorResponse response = new CreditOriginatorResponse(
            "TXN-002",
            "REJECTED",
            "Insufficient credit score"
        );
        when(creditOriginatorClient.requestCredit(any(CreditOriginatorRequest.class)))
            .thenReturn(response);

        // When & Then: se lanza excepción con detalles del rechazo
        CreditOriginatorException exception = assertThrows(
            CreditOriginatorException.class,
            () -> creditOriginatorAdapter.processCredit(payment)
        );
        assertEquals("TXN-002", exception.getTransactionId());
    }

    @Test
    @DisplayName("Debe lanzar CreditOriginatorException cuando hay error de comunicación")
    void shouldThrowExceptionOnCommunicationError() throws CreditOriginatorException {
        // Given: hay un error de comunicación con el originador
        when(creditOriginatorClient.requestCredit(any(CreditOriginatorRequest.class)))
            .thenThrow(new RuntimeException("Connection timeout"));

        // When & Then: se lanza excepción con código de error
        CreditOriginatorException exception = assertThrows(
            CreditOriginatorException.class,
            () -> creditOriginatorAdapter.processCredit(payment)
        );
        assertNotNull(exception.getOriginatorCode());
    }

    @Test
    @DisplayName("Debe validar monto negativo antes de enviar al originador")
    void shouldValidateNegativeAmount() {
        // Given: un pago con monto negativo
        Payment invalidPayment = new Payment(
            UUID.randomUUID(),
            "TXN-003",
            new BigDecimal("-100.00"),
            "USD",
            Payment.PaymentStatus.PENDING,
            "credit_originator"
        );

        // When & Then: se lanza excepción por monto inválido
        assertThrows(IllegalArgumentException.class, () -> {
            creditOriginatorAdapter.processCredit(invalidPayment);
        });
    }

    @Test
    @DisplayName("Debe incluir transactionId en la solicitud al originador")
    void shouldIncludeTransactionIdInRequest() throws CreditOriginatorException {
        // Given: un pago con transactionId específico
        String transactionId = "TXN-SPECIFIC-001";
        payment = new Payment(
            UUID.randomUUID(),
            transactionId,
            new BigDecimal("1000.00"),
            "USD",
            Payment.PaymentStatus.PENDING,
            "credit_originator"
        );

        CreditOriginatorResponse response = new CreditOriginatorResponse(
            transactionId,
            "APPROVED",
            "OK"
        );
        when(creditOriginatorClient.requestCredit(any(CreditOriginatorRequest.class)))
            .thenReturn(response);

        // When: se procesa el crédito
        creditOriginatorAdapter.processCredit(payment);

        // Then: se verifica que el transactionId fue incluido
        verify(creditOriginatorClient).requestCredit(argThat(request -> 
            transactionId.equals(request.getTransactionId())
        ));
    }
}

// === ARCHIVO: src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java ===
package com.pragma.payment.infrastructure.controller;


import com.pragma.payment.domain.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.payment.application.PaymentService;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import com.pragma.payment.infrastructure.config.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("Tests de integración para PaymentController - Superficie de práctica")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    private Payment payment;
    private String paymentJson;

    @BeforeEach
    void setUp() throws Exception {
        payment = new Payment(
            UUID.randomUUID(),
            "TXN-100",
            new BigDecimal("1500.00"),
            "USD",
            Payment.PaymentStatus.COMPLETED,
            "web"
        );
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        paymentJson = objectMapper.writeValueAsString(payment);
    }

    @Test
    @DisplayName("Debe retornar 201 Created cuando el pago se procesa exitosamente")
    void shouldReturn201WhenPaymentProcessedSuccessfully() throws Exception {
        // Given: el servicio procesa el pago exitosamente
        when(paymentService.processPayment(any(Payment.class))).thenReturn(payment);

        // When & Then: se verifica el código de estado 201
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(payment.getId().toString()))
            .andExpect(jsonPath("$.transactionId").value(payment.getTransactionId()));

        verify(paymentService, times(1)).processPayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe retornar 400 Bad Request cuando hay error de validación")
    void shouldReturn400WhenValidationError() throws Exception {
        // Given: payload inválido (falta transactionId)
        String invalidJson = "{\"amount\": 1000.00, \"currency\": \"USD\"}";

        // When & Then: se retorna 400 por validación
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
            .andExpect(status().isBadRequest());

        verify(paymentService, never()).processPayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe retornar 502 Bad Gateway cuando CreditOriginatorException ocurre")
    void shouldReturn502WhenCreditOriginatorFails() throws Exception {
        // Given: el originador de crédito falla
        when(paymentService.processPayment(any(Payment.class)))
            .thenThrow(new CreditOriginatorException(
                "Credit originator connection failed",
                "TXN-100",
                "ORIGINATOR_ERROR"
            ));

        // When & Then: se retorna 502 con detalles del error
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
            .andExpect(status().isBadGateway())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.transactionId").value("TXN-100"));
    }

    @Test
    @DisplayName("Debe retornar 422 Unprocessable Entity cuando FraudDetectionException ocurre")
    void shouldReturn422WhenFraudDetected() throws Exception {
        // Given: el motor antifraude detecta fraude
        when(paymentService.processPayment(any(Payment.class)))
            .thenThrow(new FraudDetectionException(
                "High risk transaction detected",
                "HIGH_RISK_SCORE"
            ));

        // When & Then: se retorna 422 con mensaje de fraude
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Debe retornar 503 Service Unavailable cuando PaymentGatewayException ocurre")
    void shouldReturn503WhenPaymentGatewayFails() throws Exception {
        // Given: el gateway de pagos falla
        when(paymentService.processPayment(any(Payment.class)))
            .thenThrow(new PaymentGatewayException(
                "Gateway timeout",
                "TIMEOUT"
            ));

        // When & Then: se retorna 503
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
            .andExpect(status().isServiceUnavailable())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Debe retornar 200 OK cuando se obtiene pago por ID")
    void shouldReturn200WhenGetPaymentById() throws Exception {
        // Given: el servicio retorna el pago
        UUID paymentId = UUID.randomUUID();
        payment.setId(paymentId);
        when(paymentService.getPaymentById(paymentId)).thenReturn(payment);

        // When & Then: se retorna el pago con estado 200
        mockMvc.perform(get("/api/payments/{id}", paymentId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(paymentId.toString()));
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found cuando el pago no existe")
    void shouldReturn404WhenPaymentNotFound() throws Exception {
        // Given: no existe el pago
        UUID paymentId = UUID.randomUUID();
        when(paymentService.getPaymentById(paymentId)).thenReturn(null);

        // When & Then: se retorna 404
        mockMvc.perform(get("/api/payments/{id}", paymentId))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe retornar 200 OK cuando se obtienen pagos por estado")
    void shouldReturn200WhenGetPaymentsByStatus() throws Exception {
        // Given: existen pagos con el estado especificado
        when(paymentService.getPaymentsByStatus(Payment.PaymentStatus.COMPLETED))
            .thenReturn(java.util.List.of(payment));

        // When & Then: se retorna la lista de pagos
        mockMvc.perform(get("/api/payments")
                .param("status", "COMPLETED"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Debe validar que el Content-Type sea application/json")
    void shouldRejectNonJsonContent() throws Exception {
        // Given: content-type incorrecto
        // When & Then: se retorna 415 Unsupported Media Type
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.TEXT_PLAIN)
                .content("invalid content"))
            .andExpect(status().isUnsupportedMediaType());
    }
}
```
