# Memoria del desarrollo del sistema Concesionario

**Estudiante:** [NOMBRE DEL ESTUDIANTE]  
**Asignatura:** [NOMBRE DE LA ASIGNATURA]  
**Laboratorio:** [NÚMERO O NOMBRE DEL LABORATORIO]  
**Fecha:** [FECHA]

---

## Índice

1. Introducción  
2. Análisis de requisitos  
3. Proceso de desarrollo  
4. Diseño orientado a objetos  
5. Organización de la aplicación  
6. Casos de uso  
7. Decisiones y compensaciones del diseño  
8. Comparación con BibliotecaPOO  
9. Comprobación del UML  
10. Limitaciones y recomendaciones  
11. Pruebas propuestas  
12. Conclusiones  
13. Referencias  

---

## 1. Introducción

### 1.1. Contexto académico

Este trabajo corresponde al Laboratorio #1 de Programación Avanzada en la Universidad Internacional de La Rioja (UNIR). El enunciado, archivo `colgii24T2lab1.docx`, pide practicar el diseño e implementación de clases en Java y elaborar un diagrama UML. La entrega incluye diagrama, código fuente y memoria.

El enunciado no fija un dominio: no habla de vehículos ni de ventas. El concesionario es la aplicación concreta elegida para ese encargo. Se desarrolló en NetBeans, aunque el texto menciona Eclipse. Como referencia de estructura se consultó `BibliotecaPOO`.

### 1.2. Problema que se pretende resolver

El programa modela, de forma reducida, el inventario y las ventas financiadas de un concesionario: alta de automóviles y motocicletas, registro de clientes y vendedores, consulta de disponibilidad, venta a plazos y pago de cuotas.

No es un sistema comercial. No hay varios usuarios, ni persistencia, ni interfaz gráfica. Los datos viven en memoria mientras la aplicación está en ejecución. El interés del ejercicio está en cómo se reparte el comportamiento entre clases.

### 1.3. Objetivos generales y específicos

El objetivo general es aplicar abstracción, encapsulamiento, herencia y polimorfismo a un problema pequeño con reglas propias. En concreto: convertir entidades en clases Java; extraer lo común de automóvil y motocicleta a una superclase abstracta; centralizar inventario, personas y ventas en una coordinadora; exponer las operaciones por un menú de consola; y comprobar que el UML refleja, al menos en lo esencial, el código.

---

## 2. Análisis de requisitos

### 2.1. Requisitos extraídos del enunciado

De `colgii24T2lab1.docx` se extraen, de forma literal, estos requisitos académicos:

- Haber estudiado los temas 1 y 2.
- Diseñar e implementar clases en Java.
- Elaborar un diagrama de clases UML.
- Entregar diagrama, código y memoria.

El enunciado no lista operaciones de negocio ni pide persistencia, pruebas automatizadas o interfaz gráfica. Las funcionalidades del concesionario salen del código de `Concesionario/src/concesionario/`, no de un requisito explícito de la actividad.

### 2.2. Restricciones del proyecto

El laboratorio se centra en el diseño de clases, no en arquitecturas por capas. El entorno es un IDE de escritorio (aquí, NetBeans). La solución debe poder explicarse con un UML de clases. No se exige guardar datos entre ejecuciones. El código refuerza esa última restricción: todo está en `ArrayList` en memoria y desaparece al cerrar el programa.

### 2.3. Funcionalidades principales (según el código)

A partir de `Main.java` y `Concesionario.java`:

1. Registrar cliente (cédula y nombre), sin duplicar cédula.
2. Registrar vendedor, con la misma comprobación.
3. Registrar automóvil (placa, modelo, precio y exhibición).
4. Registrar motocicleta (placa, modelo y precio).
5. Listar vehículos disponibles.
6. Listar vehículos vendidos, con comprador y vendedor si existe la venta.
7. Realizar una venta financiada (cliente, vendedor, placa, fecha y cuotas).
8. Registrar el pago de una cuota por placa.
9. Consultar el historial de ventas, con saldo y estado.

Reglas que el código sí implementa: un automóvil de exhibición no se vende (`Automovil.puedeVenderse()`); una motocicleta sí, si no está vendida; un vehículo vendido no se vuelve a vender; el número de cuotas debe ser mayor que cero; no se paga una venta ya saldada ni con fecha anterior a la venta.

No se afirman aquí requisitos de impuestos, recambios, varios locales o roles de usuario. El código no los contiene.

---

## 3. Proceso de desarrollo

### 3.1. Cómo se abordó el proyecto

El proceso se puede reconstruir en cuatro pasos, alineados con el laboratorio.

**Análisis.** Se identificaron vehículo, automóvil, motocicleta, cliente, vendedor y venta, y se separó lo que es un dato (placa, precio, cédula) de lo que es una regla (no vender un coche de exhibición, no vender dos veces, calcular el saldo).

**Diseño.** Esas ideas pasaron al diagrama: `Vehiculo` abstracta, subclases `Automovil` y `Motocicleta`, `Venta` relacionando personas y vehículo, y `Concesionario` guardando las listas. El archivo `UML/UML.png` recoge esa decisión.

**Implementación.** Cada clase del diagrama se tradujo a un `.java` del paquete `concesionario`. `Main` no aparece en el UML de dominio: lee el teclado y llama a `Concesionario`.

**Comprobación.** No hay pruebas unitarias. La comprobación prevista es ejecutar el menú y observar las reglas: un coche de exhibición no se vende, un vehículo vendido no se repite, una cuota de más se rechaza.

### 3.2. Del enunciado a las clases Java

El enunciado solo pide diseñar e implementar clases. El paso intermedio fue elegir un dominio con herencia real. Un concesionario encaja porque automóvil y motocicleta comparten placa, modelo, precio y estado, pero no la regla de elegibilidad.

| Concepto | Clase Java |
| --- | --- |
| Vehículo del inventario | `Vehiculo` (abstracta) |
| Coche, con posible exhibición | `Automovil` |
| Moto, vendible si está libre | `Motocicleta` |
| Comprador | `Cliente` |
| Empleado de la venta | `Vendedor` |
| Venta a plazos | `Venta` |
| Operaciones del local | `Concesionario` |
| Menú de consola | `Main` |

`BibliotecaPOO` sirvió de plantilla estructural. Donde un libro de consulta no se presta, un automóvil de exhibición no se vende.

### 3.3. Dificultades que exigieron más razonamiento

Tres puntos no se resuelven con “crear la clase y poner getters”.

El primero es **cuándo un vehículo puede venderse**. No basta el flag `vendido`: un automóvil de exhibición no está vendido y, aun así, no está a la venta. Por eso existen `puedeVenderse()`, que depende del tipo, y `estaDisponible()`, que combina esa regla con el estado (`Vehiculo.java`, líneas 23-27).

El segundo es **dónde marcar el vehículo como vendido**. El código lo hace en el constructor de `Venta` (`Venta.java`, líneas 25-42): si no está disponible, excepción; si sí, se registra y se llama a `marcarVendido()`. Así `Main` no puede olvidar actualizar el inventario.

El tercero es **la financiación**: valor de cada cuota, cuotas pagadas y saldo viven en `Venta`; el menú solo pide la fecha y muestra el resultado. Otras decisiones —`null` si no hay placa, precios negativos, unicidad en `Main`— se analizan en la sección 7.

---

## 4. Diseño orientado a objetos

### 4.1. Diagrama UML

El diagrama está en `UML/UML.png`. Muestra siete clases de dominio: `Vehiculo`, `Automovil`, `Motocicleta`, `Cliente`, `Vendedor`, `Venta` y `Concesionario`. No incluye `Main`.

La herencia se dibuja con flecha triangular hacia `Vehiculo`. `Concesionario` agrega las cuatro listas (rombo vacío). `Venta` se asocia con cliente, vehículo y vendedor. Esa lectura coincide con el código en lo esencial. Los desajustes de detalle se enumeran en la sección 9.

**Figura 1.** Diagrama de clases UML del sistema Concesionario (`UML/UML.png`).

### 4.2. Clase Vehiculo

`Vehiculo` (`Vehiculo.java`) es abstracta. Encapsula `placa`, `modelo`, `precio` y `vendido` (líneas 11-14). El constructor (líneas 16-21) deja `vendido` en `false`. Declara `puedeVenderse()` abstracto (línea 23). `estaDisponible()` (líneas 25-27) combina `!vendido` con esa regla. `marcarVendido()` (líneas 29-31) cambia el estado. No hay *setters* de placa ni de precio. Responsabilidad: inventario vendible y, con las subclases, elegibilidad.

### 4.3. Clase Automovil

`Automovil` (`Automovil.java`) añade `exhibicion` (línea 5). `puedeVenderse()` (líneas 12-16) devuelve `!exhibicion`. La nota del UML está aquí, no en `Concesionario`. No hay método para sacar el coche de exhibición.

### 4.4. Clase Motocicleta

`Motocicleta` (`Motocicleta.java`) no añade atributos. `puedeVenderse()` (líneas 9-14) siempre devuelve `true`. Sigue haciendo falta `estaDisponible()`, porque una moto vendida no debe venderse otra vez.

### 4.5. Clase Cliente

`Cliente` (`Cliente.java`) guarda `cedula` y `nombre`. Solo constructor y getters. No conoce las ventas.

### 4.6. Clase Vendedor

`Vendedor` (`Vendedor.java`) tiene los mismos campos. Se modeló aparte (líneas 4-6): quien vende no es quien compra, aunque los datos se parezcan.

### 4.7. Clase Venta

`Venta` (`Venta.java`) relaciona `cliente`, `vehiculo` y `vendedor`, y guarda `fechaVenta`, `precioTotal`, `numeroCuotas`, `valorCuota`, `cuotasPagadas` y `fechaUltimoPago`. El constructor (líneas 22-43) rechaza un vehículo no disponible y un número de cuotas ≤ 0, copia el precio, calcula `valorCuota` y marca el vehículo como vendido. `registrarPago` (líneas 45-54) incrementa las cuotas y rechaza pagos si la venta está saldada o la fecha es anterior a la venta. `getSaldoPendiente()` (líneas 64-66) multiplica cuotas pendientes por el valor de cada una.

### 4.8. Clase Concesionario

`Concesionario` (`Concesionario.java`) guarda cuatro listas (líneas 9-12) y ofrece altas, búsquedas y `realizarVenta` (líneas 89-94). `vehiculosDisponibles()` y `vehiculosVendidos()` (líneas 60-78) filtran el inventario. `buscarVentaPorPlaca` (líneas 80-87) localiza la financiación. No pinta menús: es el punto de entrada del dominio.

### 4.9. Clase Main

`Main` (`Main.java`) muestra el menú, lee datos y llama a `Concesionario`. Evita duplicados de cédula y placa (líneas 85-88 y 119-122) y captura las excepciones de venta y pago (líneas 222-234 y 254-264). Es la interfaz de usuario; en la práctica también concentra la unicidad.

### 4.10. Relaciones

Herencia: `Automovil` y `Motocicleta` son un `Vehiculo`. Agregación: `Concesionario` contiene las listas. Asociación: `Venta` conoce cliente, vendedor y vehículo. Dependencia de uso: `Main` usa `Concesionario` y, para imprimir, también los tipos del dominio.

### 4.11. Conceptos de POO en el código

**Abstracción.** `Vehiculo` reúne lo común y deja en las subclases lo que cambia.  
**Encapsulamiento.** Atributos privados. `vendido` solo cambia por `marcarVendido()`. El saldo se calcula.  
**Herencia.** Las subclases reutilizan constructor, getters y `estaDisponible()`.  
**Polimorfismo.** `Concesionario` guarda `List<Vehiculo>` y llama a `estaDisponible()`, que llama a `puedeVenderse()` de la instancia real. No hay un `if` por tipo dentro de `vehiculosDisponibles()`.

El primer fragmento muestra la regla distinta de cada subclase:

```java
// Automovil.java, líneas 12-16
@Override
public boolean puedeVenderse() {
    return !exhibicion;
}

// Motocicleta.java, líneas 9-14
@Override
public boolean puedeVenderse() {
    return true;
}
```

El segundo muestra cómo `Venta` protege el invariante y actualiza el inventario al nacer la transacción:

```java
// Venta.java, líneas 25-42
if (!vehiculo.estaDisponible()) {
    throw new IllegalStateException(
        "El vehículo no está disponible para la venta.");
}
if (numeroCuotas <= 0) {
    throw new IllegalArgumentException(
        "El número de cuotas debe ser mayor a cero.");
}
vehiculo.marcarVendido();
```

`Main` no duplica esa lógica: si el vehículo está en exhibición o ya se vendió, recibe la excepción y muestra el motivo.

---

## 5. Organización de la aplicación

### 5.1. Tres bloques

Todo está en el paquete `concesionario`:

1. **Entidades:** `Vehiculo`, `Automovil`, `Motocicleta`, `Cliente`, `Vendedor` y `Venta`.
2. **Coordinadora:** `Concesionario`, con listas y operaciones.
3. **Presentación:** `Main`, menú y teclado.

No hay paquetes `modelo` / `vista` / `controlador`. Para ocho clases, un solo paquete es razonable. Separar capas ahora añadiría archivos sin cambiar el comportamiento.

### 5.2. Cohesión y acoplamiento

Las entidades son cohesivas: `Cliente` no calcula cuotas y `Motocicleta` no busca clientes. `Venta` concentra la financiación. `Concesionario` tiene cohesión de fachada: hace varias cosas, todas operaciones del local.

El acoplamiento es bajo entre entidades y más alto hacia `Concesionario`. `Main` depende de la coordinadora y de tipos concretos porque los construye y los imprime. `vehiculosDisponibles()` no pregunta el tipo; en cambio, `mostrarVehiculosDisponibles` (`Main.java`, línea 161) usa `instanceof` para escribir “Automóvil” o “Motocicleta”. Ahí la presentación sí conoce la jerarquía.

### 5.3. ¿Main conoce demasiados detalles?

Sí, en parte. No calcula saldos ni marca vehículos, y eso está bien. Pero construye `Automovil` y `Motocicleta`, decide que cédula y placa no se repiten, distingue tipos con `instanceof` y formatea todas las pantallas.

Para un laboratorio es aceptable. Si creciera, la unicidad debería pasar a `Concesionario` y un método `descripcion()` en `Vehiculo` evitaría el `instanceof`. No haría falta un MVC completo, ni *factories* ni repositorios: en un proyecto de este tamaño esos nombres suelen tapar el diseño.

---

## 6. Casos de uso

El actor es, en todos los casos, quien usa el menú. El programa no distingue roles.

### 6.1. Registrar un vehículo

- **Actor:** usuario del menú. **Objetivo:** alta de automóvil (opción 3) o motocicleta (opción 4).
- **Precondiciones:** ninguna.
- **Flujo principal:** se pide la placa; si no existe, modelo y precio; en automóvil, si es de exhibición; se llama a `agregarVehiculo`.
- **Errores o alternativas:** placa repetida (`Main.java`, líneas 119-122 y 140-143). Precio no numérico: `leerDecimal` insiste (líneas 298-308).
- **Resultado esperado:** el vehículo queda en la lista. Un automóvil de exhibición no aparecerá como disponible.

### 6.2. Registrar un cliente

- **Actor:** usuario del menú. **Objetivo:** alta de un comprador.
- **Precondiciones:** ninguna.
- **Flujo principal:** se pide la cédula; si no existe, el nombre; se llama a `registrarCliente`.
- **Errores o alternativas:** cédula ya registrada. No se valida que cédula o nombre estén vacíos.
- **Resultado esperado:** el cliente puede usarse en una venta.

### 6.3. Registrar un vendedor

- **Actor:** usuario del menú. **Objetivo:** alta del empleado que gestiona la venta.
- **Precondiciones:** ninguna.
- **Flujo principal:** igual que el cliente, sobre la lista de vendedores.
- **Errores o alternativas:** cédula duplicada; tampoco hay validación de campos vacíos.
- **Resultado esperado:** el vendedor puede asociarse a una `Venta`.

### 6.4. Realizar una venta

- **Actor:** usuario del menú. **Objetivo:** vender un vehículo disponible a plazos.
- **Precondiciones:** existen el cliente, el vendedor y un vehículo disponible.
- **Flujo principal:** se buscan las tres entidades; se piden fecha y cuotas; `realizarVenta` construye `Venta` y la guarda.
- **Errores o alternativas:** entidad no encontrada; vehículo de exhibición o ya vendido; cuotas ≤ 0; fecha mal escrita.
- **Resultado esperado:** se muestran precio, cuotas y valor de cada una; el vehículo pasa a vendidos.

### 6.5. Buscar o consultar vehículos

- **Actor:** usuario del menú. **Objetivo:** ver disponibles (opción 5) o vendidos (opción 6).
- **Precondiciones:** ninguna.
- **Flujo principal:** se recorre `vehiculosDisponibles()` o `vehiculosVendidos()`. En vendidos se busca la venta por placa.
- **Errores o alternativas:** listas vacías, con mensaje específico. Un vendido sin venta asociada se lista sin comprador.
- **Resultado esperado:** solo salen disponibles los que cumplen `estaDisponible()`. Si la placa no existe, `buscarVehiculo` devuelve `null` (`Concesionario.java`, líneas 51-58).

### 6.6. Intentar vender un vehículo que ya fue vendido

- **Actor:** usuario del menú. **Objetivo:** otra venta sobre la misma placa.
- **Precondiciones:** esa placa ya tiene venta; `vendido` es `true`.
- **Flujo principal:** se localizan cliente, vendedor y vehículo; se intenta `realizarVenta`.
- **Errores o alternativas:** el constructor de `Venta` lanza `IllegalStateException` porque `estaDisponible()` es falso. `Main` muestra el motivo.
- **Resultado esperado:** no se añade una segunda venta. Vender un automóvil de exhibición produce el mismo mensaje, aunque `vendido` siga en `false`.

---

## 7. Decisiones y compensaciones del diseño

Toda decisión gana unas cualidades y pierde otras. En un laboratorio conviene dejar constancia de esa tensión, no presentar cada elección como la única correcta.

### 7.1. Clase abstracta Vehiculo frente a interfaz o a un campo de tipo

**Situación.** Dos tipos con datos comunes y una regla distinta.

**Alternativas.** Una interfaz `Vendible`; una sola clase con un `String tipo`; o la clase abstracta usada.

**Solución.** `Vehiculo` abstracta, con estado compartido y `puedeVenderse()` abstracto.

**Ventajas.** El estado común no se duplica. El compilador impide instanciar un vehículo genérico. Un tercer tipo no obliga a tocar los `if` de `Concesionario`.

**Desventajas.** La herencia es rígida: el objeto no cambia de tipo. Una motocicleta de exhibición no está prevista.

**Si el proyecto creciera.** La clase abstracta seguiría siendo razonable mientras los tipos compartan datos. El campo `tipo` sería más corto al principio y más caro después.

### 7.2. Herencia frente a composición

**Situación.** Automóvil y motocicleta se parecen; cliente y vendedor también; la venta relaciona personas y vehículo.

**Alternativas.** Heredar siempre, componer siempre, o mezclar.

**Solución.** Herencia donde hay “es un”. Asociación donde hay “tiene un” (`Venta` tiene un `Cliente`). Cliente y vendedor no heredan de una `Persona`.

**Ventajas.** La jerarquía expresa `puedeVenderse()`. `Venta` no hereda de forma artificial. Separar los roles evita tratarlos como el mismo objeto.

**Desventajas.** `Cliente` y `Vendedor` duplican campos y getters. Una `Persona` reduciría repetición, a costa de sugerir que ambos roles son intercambiables.

**Si el proyecto creciera.** Una `Persona` tendría sentido con más roles. La venta debería seguir siendo composición.

### 7.3. ArrayList en memoria frente a archivos o base de datos

**Situación.** Había que guardar vehículos, personas y ventas.

**Alternativas.** Listas en memoria, archivo o base de datos.

**Solución.** `ArrayList` en el constructor de `Concesionario` (líneas 14-19).

**Ventajas.** Cero configuración. El estudiante se concentra en objetos y referencias. Las búsquedas se escriben con un `for`.

**Desventajas.** Al terminar el proceso se pierde todo. No hay historial real ni acceso concurrente —innecesario en una consola de un usuario.

**Si el proyecto creciera.** Un archivo JSON sería el siguiente paso, sin cambiar el dominio. Una base de datos solo compensaría con muchos registros. Meter JPA aquí desplazaría el objetivo.

### 7.4. Concesionario como coordinadora frente a separar inventario, clientes y ventas

**Situación.** Varias colecciones y varias operaciones.

**Alternativas.** Una clase por colección, o una sola fachada.

**Solución.** `Concesionario` concentra las cuatro listas y los métodos de alta, búsqueda y venta.

**Ventajas.** `Main` habla con un solo objeto. Realizar una venta no obliga a coordinar tres servicios desde el menú. El UML cabe en una página.

**Desventajas.** La clase crece con cada consulta nueva. Mezcla responsabilidades que, en un programa mayor, se separarían.

**Si el proyecto creciera.** Entonces sí convendría partir. Con poco más de cien líneas, la fachada única es más clara.

### 7.5. Búsquedas que devuelven null frente a Optional o excepciones

**Situación.** `buscarVehiculo`, `buscarCliente`, `buscarVendedor` y `buscarVentaPorPlaca` pueden no encontrar nada.

**Alternativas.** `null`, `Optional` o una excepción.

**Solución.** Devolver `null` y que `Main` compruebe.

**Ventajas.** Es el estilo más simple en un curso de objetos. El menú imprime “no encontrado” sin un `try`.

**Desventajas.** Si se olvida el `if`, aparece un `NullPointerException`. `Optional` haría visible en el tipo que el resultado puede faltar. Lanzar excepción por “no está” mezcla un caso esperado con un error.

**Si el proyecto creciera.** `Optional` sería la opción más limpia en Java moderno, sobre todo si el código se usara fuera del menú.

### 7.6. Validaciones en constructores, métodos o Main

**Situación.** Hay datos mal formados (cuotas ≤ 0, fechas incoherentes) y duplicados (placa, cédula).

**Alternativas.** Validar todo en `Main`, todo en el dominio, o repartir.

**Solución.** Reparto desigual. `Venta` valida disponibilidad, cuotas y fechas de pago. `Main` valida unicidad y formato. Los constructores de `Cliente`, `Vendedor` y `Vehiculo` no rechazan cadenas vacías ni precios negativos.

**Ventajas.** Las reglas que afectan al estado quedan junto al objeto. El menú puede insistir cuando el usuario escribe “abc” en un precio.

**Desventajas.** La unicidad no está en el dominio: otro punto de entrada podría registrar dos veces la misma placa. Un precio negativo es legal para el constructor.

**Si el proyecto creciera.** Placa única, precio > 0 y cédula no vacía deberían vivir en el dominio. `Main` se quedaría con el formato de entrada.

### 7.7. Getters de listas internas frente a copias defensivas

**Situación.** `getVehiculos()`, `getClientes()`, `getVendedores()` y `getVentas()` (`Concesionario.java`, líneas 96-110) devuelven la lista real.

**Alternativas.** La misma lista, una copia, o `Collections.unmodifiableList`.

**Solución.** Se devuelve la referencia interna. `Main` recorre `getVentas()` (línea 274). `vehiculosDisponibles()` sí crea una lista nueva.

**Ventajas.** Es inmediato y evita copiar en cada consulta, suficiente si nadie llama a `clear()` desde fuera.

**Desventajas.** Quien tenga la referencia puede borrar el inventario o añadir un vehículo sin pasar por `agregarVehiculo`. El encapsulamiento de las colecciones es incompleto.

**Si el proyecto creciera.** Habría que devolver copias o vistas no modificables. Aquí el único cliente es `Main`, y el riesgo es limitado.

### 7.8. Aplicación de consola frente a interfaz gráfica

**Situación.** Había que interactuar con el usuario.

**Alternativas.** `Scanner` y `System.out`; Swing o JavaFX; una página web.

**Solución.** Menú de consola en `Main`.

**Ventajas.** El tiempo se invierte en las clases, no en botones. El enunciado pide diseño de clases e UML, no una GUI. Depurar un `println` es más simple que un *listener*.

**Desventajas.** La experiencia es tosca. Un error de tipeo obliga a repetir el flujo. En Windows hizo falta forzar UTF-8 (`Main.java`, líneas 14-18) para mostrar tildes.

**Si el proyecto creciera.** Una GUI podría reutilizar `Concesionario` sin reescribir `Venta`. No era el objetivo de este laboratorio.

Un hilo común recorre estas ocho decisiones: se priorizó la claridad para aprender POO frente a la robustez de un programa de producción. Esa prioridad es coherente con el enunciado. Dejaría de serlo si se presentara esta consola como un sistema de gestión real.

---

## 8. Comparación con BibliotecaPOO

### 8.1. Estructura que se repite

Ambos proyectos siguen el mismo esqueleto: superclase abstracta con un método que las subclases redefinen; dos subclases, una con restricción extra y otra sin ella; una transacción que cambia el estado del recurso; una coordinadora con `ArrayList` y búsquedas que devuelven `null`; un `Main` con menú y `Scanner`. `Concesionario` reutiliza esa forma y cambia el vocabulario del dominio.

### 8.2. Responsabilidades equivalentes

| BibliotecaPOO | Concesionario | Papel común |
| --- | --- | --- |
| `MaterialBiblioteca` | `Vehiculo` | Recurso abstracto con disponibilidad |
| `Libro` (no préstamo si es consulta) | `Automovil` (no venta si es exhibición) | Subtipo restringido |
| `Revista` | `Motocicleta` | Subtipo sin restricción extra |
| `Usuario` | `Cliente` (y, en parte, `Vendedor`) | Persona de la operación |
| `Prestamo` | `Venta` | Transacción que marca el recurso |
| `Biblioteca` | `Concesionario` | Fachada con listas |
| `Main` | `Main` | Menú de consola |

### 8.3. Lo que cambia por el dominio

En la biblioteca el material vuelve: existe `marcarDevuelto()`, hay fecha límite y sanción. En el concesionario el vehículo no se devuelve: `marcarVendido()` no tiene inversa. La transacción se cierra con cuotas, no con una devolución. Aparece `Vendedor`, que la biblioteca no necesita. `Prestamo` valida la sanción del usuario; `Venta` valida cuotas y fechas de pago.

`Main` del concesionario fuerza UTF-8. Su `leerEntero` devuelve `-1` si falla, en lugar de insistir como el de la biblioteca. Es una diferencia menor, pero visible.

### 8.4. Ventajas y riesgos de reutilizar la estructura

La ventaja es pedagógica: entendida la biblioteca, el concesionario se lee en poco tiempo. El riesgo es copiar sin preguntarse si la analogía aguanta. Un préstamo y una venta no son lo mismo: si se hubiera copiado `marcarDevuelto()` al vehículo, el modelo mentiría. Aquí la venta es definitiva y el estado de “pagada” vive en `Venta`. Otro riesgo es arrastrar los mismos huecos: getters de listas internas, `null` en búsquedas, unicidad solo en `Main`.

---

## 9. Comprobación del UML

Se comparó `UML/UML.png` con `Concesionario/src/concesionario/`. El diagrama acierta el diseño general y se queda corto en varios detalles.

### 9.1. Coincidencias

Existen las mismas clases de dominio. `Vehiculo` es abstracta y declara `puedeVenderse()`. Atributos básicos: `placa`, `modelo`, `precio`, `vendido`. `Automovil` tiene `exhibicion`; `Motocicleta` no añade campos. `Cliente` y `Vendedor` tienen `cedula` y `nombre`. `Concesionario` agrega las cuatro listas. `Venta` se asocia con las tres entidades y tiene `fechaVenta`, `numeroCuotas` y `cuotasPagadas`. Aparecen `registrarPago()` y `getSaldoPendiente()`. El UML indica `List`; el código declara `List` e instancia `ArrayList`.

### 9.2. Diferencias encontradas

1. **`Main` no está en el diagrama.** Existe en el código. Es habitual omitir la clase de arranque en un UML de dominio.
2. **`Vehiculo` tiene más métodos** que el UML: constructor, `estaDisponible()`, `marcarVendido()` y getters. `estaDisponible()` es el método que usa `Venta`.
3. **`Venta` tiene atributos no dibujados:** `precioTotal`, `valorCuota` y `fechaUltimoPago`. También `estaCompletamentePagada()`, `getCuotasPendientes()` y getters.
4. **`Concesionario` en el UML solo muestra** `agregarVehiculo()`, `buscarVehiculo()` y `realizarVenta()`. El código añade `registrarCliente`, `registrarVendedor`, `buscarCliente`, `buscarVendedor`, `vehiculosDisponibles`, `vehiculosVendidos`, `buscarVentaPorPlaca` y los cuatro getters.
5. **Getters y constructores** no aparecen. Es una simplificación frecuente, no un error de modelo, pero deja fuera parte de la API real.

No hay clases de más en el código respecto al dominio dibujado, salvo `Main`. No hay clases del UML que falten en el código.

---

## 10. Limitaciones y recomendaciones

### 10.1. Limitaciones principales

No hay persistencia ni pruebas automáticas. La unicidad de placas y cédulas depende de `Main`. Los constructores aceptan nombres vacíos y precios negativos. Un automóvil de exhibición no puede dejar de serlo. `leerEntero` (`Main.java`, líneas 287-296) no reintenta: si el usuario escribe letras, devuelve `-1`. El dinero se representa con `double`. Las listas internas se exponen por getters. El programa es de un solo usuario y de consola. Estas limitaciones son esperables en un laboratorio; dejan de serlo si se evalúa el programa como una aplicación de gestión.

### 10.2. Mejoras por prioridad

**Necesarias si se quisiera un comportamiento más sólido, aún dentro del laboratorio:**

1. Rechazar placa o cédula duplicada dentro de `Concesionario`.
2. Validar precio positivo y textos no vacíos en el alta.
3. Hacer que `leerEntero` insista, como `leerDecimal` y como `BibliotecaPOO`.
4. Devolver copias o listas no modificables en los getters de colecciones.

**Opcionales, solo si el alcance del curso lo pide:** persistencia en archivo; un método en `Vehiculo` que evite `instanceof`; permitir sacar un automóvil de exhibición; pruebas de `Venta`; interfaz gráfica que reutilice `Concesionario`.

Añadir *Singleton*, DAO, MVC o factorías sin una necesidad concreta aumentaría el número de clases y oscurecería lo que el laboratorio pide ver: herencia, encapsulamiento y polimorfismo.

### 10.3. Qué conviene conservar

Sin cambios, por ser apropiado para un laboratorio de POO: la clase abstracta con `puedeVenderse()`; la separación entre `Venta` y `Concesionario`; el menú de consola; las excepciones para estados ilegales de la venta; el tamaño reducido del modelo.

---

## 11. Pruebas propuestas

No hay *tests* en el proyecto. Las siguientes se pueden hacer a mano con el menú o, más adelante, con JUnit sobre el dominio.

### 11.1. No permitir vender dos veces el mismo vehículo

Registrar cliente, vendedor y una motocicleta. Realizar la venta. Volver a vender la misma placa. Debe aparecer el mensaje de `IllegalStateException` y el historial debe tener una sola venta.

### 11.2. Búsqueda de un vehículo inexistente

En venta o pago, escribir una placa que no existe. El programa debe indicar que no encontró el vehículo o que no hay venta financiada, y no debe lanzar una excepción no capturada.

### 11.3. Cálculo y registro de una venta

Automóvil que no sea de exhibición, precio 10000 y 4 cuotas. Tras la venta, cada cuota debe ser 2500. Tras un pago: 1/4 y saldo 7500. Tras cuatro pagos, estado PAGADA y un quinto pago rechazado.

### 11.4. Listas vacías

Arrancar y elegir opciones 5, 6 y 9 sin datos. Deben mostrarse los mensajes de vacío, sin recorrer colecciones nulas (las listas se crean vacías, no `null`).

### 11.5. Datos incorrectos o incompletos

Precio con letras: `leerDecimal` insiste. Fecha `32-13-2020`: `leerFecha` insiste. Cuotas 0 o negativas: `Venta` lanza `IllegalArgumentException`. Cliente no registrado al vender: “Cliente no encontrado”. Automóvil de exhibición: la venta no se realiza y `vendido` permanece en `false`.

### 11.6. Pago con fecha anterior a la venta

Venta con fecha `2026-05-10` y pago en `2026-05-01`. Debe rechazarse. `cuotasPagadas` no debe aumentar.

---

## 12. Conclusiones

El laboratorio pedía diseñar clases, implementarlas en Java y acompañarlas de un UML y de una memoria. El concesionario cubre ese encargo con un modelo pequeño: jerarquía de vehículos, dos roles de persona, una transacción financiada y una clase que coordina listas.

La POO aquí no es un adorno. `puedeVenderse()` evita que `Concesionario` pregunte el tipo de cada vehículo. El constructor de `Venta` evita que el menú olvide marcar el inventario. Los atributos privados evitan reescribir a medias el precio o las cuotas. Eso es lo que el enunciado pretende entrenar.

Si el sistema evolucionara, el orden razonable sería endurecer validaciones, no devolver las listas internas, persistir en un archivo y, solo después, pensar en otra interfaz. `Concesionario` y `Venta` podrían mantenerse. No haría falta partir el programa en capas de más para que el diseño “parezca profesional”. Un laboratorio de este tamaño se entiende mejor cuando las clases siguen cabiendo en la cabeza.

El UML y el código coinciden en la idea y discrepan en el detalle. Actualizar el diagrama con `estaDisponible()`, los campos de financiación y los métodos de `Concesionario` dejaría la entrega más honesta.

---

## 13. Referencias

1. Universidad Internacional de La Rioja (UNIR). *Laboratorio #1: Diseño e implementación de clases*. Archivo `colgii24T2lab1.docx` (asignatura Programación Avanzada, Tema 2. Actividades).
2. Oracle. Tutorial de clases y objetos en Java: https://docs.oracle.com/javase/tutorial/java/javaOO/index.html
3. Oracle. API de `java.util.ArrayList`: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
4. Oracle. API de `java.time.LocalDate`: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html
5. Object Management Group. *OMG Unified Modeling Language*: https://www.omg.org/spec/UML/
6. Código fuente de `Concesionario/src/concesionario/`.
7. Código fuente de referencia `BibliotecaPOO/src/biblioteca/` (consulta estructural).
8. Diagrama de clases `UML/UML.png`.

No se han citado fuentes no utilizadas.
