# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación y Depuración de un Servicio de Procesamiento de Pagos**.

| | |
|---|---|
| Tema | Manejo de Excepciones y Depuración Efectiva |
| Nivel | junior-l1 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | capas estándar (dominio, aplicación, infraestructura) |
| Tiempo estimado | 6 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.projectlombok:lombok n/a
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.9.1
- org.postgresql:postgresql n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.springframework.boot:spring-boot-starter-test n/a
- org.mockito:mockito-core n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Implementación del Servicio de Pagos**: Servicio que acepta y procesa solicitudes de pago, manejando excepciones de manera efectiva.
- **Fase 2 — Depuración y Mejora del Servicio**: Servicio mejorado con mecanismos de depuración y documentación actualizada.
- **Fase 3 — Evaluación y Optimización**: Servicio optimizado con documentación actualizada que refleja las mejoras realizadas.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapterTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (53)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/payment/PaymentApplication.java` — `com.pragma.payment.domain.dto.PaymentRequest`
      El import com.pragma.payment.domain.dto.PaymentRequest usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/payment/PaymentApplication.java` — `com.pragma.payment.domain.dto.PaymentResponse`
      El import com.pragma.payment.domain.dto.PaymentResponse usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/payment/PaymentApplication.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/config/ErrorResponse.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.annotation.JsonInclude pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/config/ValidationErrorResponse.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.annotation.JsonInclude pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getTransactionId`
      Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getAmount`
      Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getCurrency`
      Se invoca `getCurrency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getOrigin`
      Se invoca `getOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getId`
      Se invoca `getId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `Payment.getStatus`
      Se invoca `getStatus` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java` — `Payment.getTransactionId`
      Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java` — `Payment.getAmount`
      Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java` — `Payment.getCurrency`
      Se invoca `getCurrency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `Payment.getTransactionId`
      Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `Payment.getAmount`
      Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `FraudAnalysisResult.isApproved`
      Se invoca `isApproved` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `FraudAnalysisResult.riskLevel`
      Se invoca `riskLevel` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `FraudAnalysisResult.rejectionReasons`
      Se invoca `rejectionReasons` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java` — `Payment.getOrigin`
      Se invoca `getOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java` — `Payment.getTransactionId`
      Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java` — `Payment.getAmount`
      Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java` — `Payment.getCurrency`
      Se invoca `getCurrency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.amount`
      Se invoca `amount` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.currency`
      Se invoca `currency` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.origin`
      Se invoca `origin` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getTransactionId`
      Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getStatus`
      Se invoca `getStatus` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.newStatus`
      Se invoca `newStatus` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `UpdatePaymentStatusRequest.failureReason`
      Se invoca `failureReason` sobre `UpdatePaymentStatusRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getId`
      Se invoca `getId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getAmount`
      Se invoca `getAmount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getCurrency`
      Se invoca `getCurrency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getOrigin`
      Se invoca `getOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getCreatedAt`
      Se invoca `getCreatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `Payment.getFailureReason`
      Se invoca `getFailureReason` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — `Payment.isEmpty`
      Se invoca `isEmpty` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapterTest.java` — `CreditOriginatorAdapter.processCredit`
      Se invoca `processCredit` sobre `CreditOriginatorAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapterTest.java` — `Payment.getStatus`
      Se invoca `getStatus` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.setCreatedAt`
      Se invoca `setCreatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.getId`
      Se invoca `getId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.getTransactionId`
      Se invoca `getTransactionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java` — `Payment.setId`
      Se invoca `setId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (19)

- `pom.xml`
- `src/main/java/com/pragma/payment/PaymentApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/payment/domain/Payment.java`
- `src/main/java/com/pragma/payment/domain/PaymentServicePort.java`
- `src/main/java/com/pragma/payment/domain/exception/CreditOriginatorException.java`
- `src/main/java/com/pragma/payment/domain/exception/FraudDetectionException.java`
- `src/main/java/com/pragma/payment/domain/exception/PaymentGatewayException.java`
- `src/main/java/com/pragma/payment/application/PaymentService.java`
- `src/main/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapter.java`
- `src/main/java/com/pragma/payment/infrastructure/adapter/FraudDetectionAdapter.java`
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentGatewayAdapter.java`
- `src/main/java/com/pragma/payment/infrastructure/config/ErrorResponse.java`
- `src/main/java/com/pragma/payment/infrastructure/config/ValidationErrorResponse.java`
- `src/main/java/com/pragma/payment/infrastructure/config/GlobalExceptionHandler.java`
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java`
- `src/test/java/com/pragma/payment/application/PaymentServiceTest.java`
- `src/test/java/com/pragma/payment/infrastructure/adapter/CreditOriginatorAdapterTest.java`
- `src/test/java/com/pragma/payment/infrastructure/controller/PaymentControllerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/payment`
- `src/main/java/com/pragma/payment/domain`
- `src/main/java/com/pragma/payment/domain/exception`
- `src/main/java/com/pragma/payment/application`
- `src/main/java/com/pragma/payment/infrastructure`
- `src/main/java/com/pragma/payment/infrastructure/adapter`
- `src/main/java/com/pragma/payment/infrastructure/config`
- `src/main/java/com/pragma/payment/infrastructure/controller`
- `src/main/resources`
- `src/test/java/com/pragma/payment`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **capas estándar (dominio, aplicación, infraestructura)**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Junior
- Brecha que el reto ataca: Maneja correctamente las excepciones y depura el código para encontrar problemas cuando sea necesario.
- Mision: Candidato en etapa Junior, trabajando en consolidar sus habilidades fundamentales en desarrollo backend.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
