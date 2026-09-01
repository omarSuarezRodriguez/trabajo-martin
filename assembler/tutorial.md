# Guía paso a paso para MARS 4.5

Los pasos van seguidos del 1 al 47. El resultado final son **6 capturas de pantalla**.

---

## Ajustes iniciales

1. Abrir MARS con doble clic en `Mars4_5.jar`.
2. Crear una carpeta llamada `capturas` en el Escritorio.
3. Menú `Settings`. Comprobar que **"Assemble all files in directory"** esté SIN marcar. Si tiene visto, hacer clic para quitarlo.
4. Menú `Settings`. Comprobar que **"Delayed branching"** esté SIN marcar.
5. Menú `Settings`. Comprobar que **"Assemble file upon opening"** esté SIN marcar (da igual si está marcado, pero así se controla mejor).

---

## Captura 1 de 6: X-Ray del programa base

6. `File > Open`. Navegar a `C:\Users\Usuario\Desktop\Trabajo Martin\assembler\` y abrir `programa_base.asm`.
7. Pulsar **F3**. En el panel inferior `Mars Messages` debe aparecer "Assemble: operation completed successfully". Si aparece un error en rojo, avisar y no continuar.
8. En el panel `Text Segment` (centro de la pantalla), buscar en la columna `Source` la línea `lw $t6, 0($t5)`.
9. En esa misma fila, marcar la casilla de la columna **`Bkpt`** (primera columna de la izquierda). Eso pone un punto de parada.
10. Menú `Tools > MIPS X-Ray`.
11. Maximizar la ventana de X-Ray que se acaba de abrir.
12. Dentro de la ventana de X-Ray, hacer clic en el botón **"Connect to MIPS"**.
13. Volver a la ventana principal de MARS (barra de tareas) y pulsar **F5**. La ejecución se detendrá en el punto de parada.
14. Volver a la ventana de X-Ray y pulsar **F7** una sola vez. Se animará el datapath de la instrucción `lw`.
15. Con la ventana de X-Ray activa, pulsar **`Alt + Impr Pant`** (copia solo esa ventana).
16. Abrir Paint, pulsar `Ctrl + V`, luego `Ctrl + S`. Guardar en la carpeta `capturas` con el nombre `1_base_xray.png`.
17. Cerrar la ventana de X-Ray (botón "Close" o la X).
18. En la ventana principal de MARS, desmarcar la casilla `Bkpt` que se puso en el paso 9.
19. Pulsar **F12** (Reset).

---

## Capturas 2 y 3 de 6: contadores del programa base

20. Menú `Tools > Instruction Counter`. En esa ventana, clic en **"Connect to MIPS"**.
21. Menú `Tools > Instruction Statistics`. En esa ventana, clic en **"Connect to MIPS"**.
22. Mover las dos ventanas con el ratón para que no se tapen entre sí.
23. Ir a la ventana principal de MARS y pulsar **F5**.
24. Esperar a que en `Mars Messages` aparezca "program is finished running".
25. Clic en la ventana `Instruction Counter`, pulsar `Alt + Impr Pant`, pegar en Paint y guardar como `2_base_counter.png`.
26. Clic en la ventana `Instruction Statistics`, pulsar `Alt + Impr Pant`, pegar en Paint y guardar como `3_base_statistics.png`.
27. Anotar el número total que muestra `Instruction Counter` y los 5 valores de `Instruction Statistics` (ALU, Jump, Branch, Memory, Other).
28. Comprobación de que el programa funcionó: en el panel `Data Segment` (abajo), en el desplegable de la izquierda elegir `.data`. Deben verse los valores 8, 11, 14, 17, 20, 23, 26 y 29 después de los ocho primeros. Si no aparecen, avisar.
29. Cerrar las ventanas `Instruction Counter` e `Instruction Statistics`.

---

## Crear el programa optimizado

30. Menú `File > Close` para cerrar la pestaña de `programa_base.asm`.
31. Menú `File > New`.
32. Copiar y pegar en el editor exactamente este contenido:

```asm
# Laboratorio: Estructura de Computadores
# Version OPTIMIZADA: elimina el riesgo Load-Use por reordenamiento

.data
    vector_x: .word 1, 2, 3, 4, 5, 6, 7, 8
    vector_y: .space 32
    const_a:  .word 3
    const_b:  .word 5
    tamano:   .word 8

.text
.globl main

main:
    la $s0, vector_x
    la $s1, vector_y
    lw $t0, const_a
    lw $t1, const_b
    lw $t2, tamano
    li $t3, 0

loop:
    beq $t3, $t2, fin

    sll  $t4, $t3, 2
    addu $t5, $s0, $t4

    lw   $t6, 0($t5)

    addu $t9, $s1, $t4

    mul  $t7, $t6, $t0
    addu $t8, $t7, $t1

    sw   $t8, 0($t9)

    addi $t3, $t3, 1
    j loop

fin:
    li $v0, 10
    syscall
```

33. Menú `File > Save As`. Navegar a `C:\Users\Usuario\Desktop\Trabajo Martin\assembler\`. Escribir el nombre completo `programa_optimizado.asm` (con la extensión) y guardar.

---

## Captura 4 de 6: X-Ray del programa optimizado

34. Pulsar **F3**. Confirmar "operation completed successfully".
35. En `Text Segment`, buscar la línea `lw $t6, 0($t5)` y marcar su casilla `Bkpt`.
36. Menú `Tools > MIPS X-Ray`. Maximizar la ventana y clic en **"Connect to MIPS"**.
37. Ir a la ventana principal y pulsar **F5**.
38. Volver a X-Ray y pulsar **F7** una vez.
39. `Alt + Impr Pant`, pegar en Paint, guardar como `4_optimizado_xray.png`.
40. Cerrar X-Ray, desmarcar la casilla `Bkpt` y pulsar **F12**.

---

## Capturas 5 y 6 de 6: contadores del programa optimizado

41. Menú `Tools > Instruction Counter`. Clic en **"Connect to MIPS"**.
42. Menú `Tools > Instruction Statistics`. Clic en **"Connect to MIPS"**.
43. Ir a la ventana principal y pulsar **F5**. Esperar "program is finished running".
44. Capturar `Instruction Counter` y guardar como `5_optimizado_counter.png`.
45. Capturar `Instruction Statistics` y guardar como `6_optimizado_statistics.png`.
46. Anotar de nuevo el total y los 5 valores del desglose.
47. Comprobar en `Data Segment` que salen otra vez 8, 11, 14, 17, 20, 23, 26, 29.

---

## Resultado esperado

Al terminar debe haber 6 archivos en la carpeta `capturas`:

| Archivo | Contenido |
|---|---|
| `1_base_xray.png` | Datapath animado del `lw` en el programa base |
| `2_base_counter.png` | Total de instrucciones del programa base |
| `3_base_statistics.png` | Desglose por tipo del programa base |
| `4_optimizado_xray.png` | Datapath animado del `lw` en el programa optimizado |
| `5_optimizado_counter.png` | Total de instrucciones del programa optimizado |
| `6_optimizado_statistics.png` | Desglose por tipo del programa optimizado |

Y dos series de números anotados: total de instrucciones y desglose, para base y para optimizado.

El total de instrucciones debe ser **el mismo** en ambas versiones. Eso es lo esperado y no es un error: MARS es un simulador funcional y no modela el pipeline, así que reordenar instrucciones no cambia cuántas se ejecutan.

---

## Si algo falla

- **Error de etiqueta duplicada** (`main` declarada dos veces) al pulsar F3: el ajuste del paso 3 quedó marcado. Desmarcarlo y volver a ensamblar.
- **El botón "Connect to MIPS" no responde**: el programa no está ensamblado. Pulsar F3 primero.
- **X-Ray no muestra animación**: hay que avanzar con F7 (paso a paso). Con F5 a velocidad máxima no se ve nada.
