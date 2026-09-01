# Laboratorio: Estructura de Computadores
# Actividad: Optimizacion de Pipeline en Procesadores MIPS
# Objetivo: Calcular Y[i] = A * X[i] + B e identificar riesgos de datos
# Estudiante: Martin Camilo Suarez Rodriguez

.data                     # zona donde declaro los datos del programa
    vector_x: .word 1, 2, 3, 4, 5, 6, 7, 8   # los 8 numeros de entrada
    vector_y: .space 32                      # espacio vacio para los 8 resultados (8 x 4 bytes)
    const_a:  .word 3                        # constante A, la que multiplica
    const_b:  .word 5                        # constante B, la que se suma
    tamano:   .word 8                        # cuantos elementos tiene el vector

.text                     # zona donde empieza el codigo
.globl main               # marco main como el punto de arranque del programa

main:
    la $s0, vector_x      # guardo en $s0 la direccion donde empieza el vector X
    la $s1, vector_y      # guardo en $s1 la direccion donde empieza el vector Y
    lw $t0, const_a       # traigo de memoria el valor de A (3) y lo dejo en $t0
    lw $t1, const_b       # traigo de memoria el valor de B (5) y lo dejo en $t1
    lw $t2, tamano        # traigo de memoria el tamano (8) y lo dejo en $t2
    li $t3, 0             # arranco el contador del bucle en i = 0

loop:
    beq $t3, $t2, fin     # si i ya llego al tamano, me salgo del bucle

    sll $t4, $t3, 2       # multiplico i por 4, porque cada entero ocupa 4 bytes
    addu $t5, $s0, $t4    # sumo ese desplazamiento a la base de X y obtengo la direccion de X[i]

    lw $t6, 0($t5)        # leo de memoria el valor de X[i] y lo dejo en $t6

    mul $t7, $t6, $t0     # multiplico X[i] por A. Aqui esta el riesgo Load-Use: $t6 aun no esta listo
    addu $t8, $t7, $t1    # le sumo B al resultado anterior, ya tengo el valor de Y[i]

    addu $t9, $s1, $t4    # sumo el mismo desplazamiento a la base de Y y obtengo la direccion de Y[i]
    sw $t8, 0($t9)        # guardo el resultado en Y[i]

    addi $t3, $t3, 1      # subo el contador: i = i + 1
    j loop                # vuelvo al inicio del bucle

fin:
    li $v0, 10            # el codigo 10 significa terminar el programa
    syscall               # le pido al sistema que cierre la ejecucion
