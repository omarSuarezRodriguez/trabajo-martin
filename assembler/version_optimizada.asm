# Laboratorio: Estructura de Computadores

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