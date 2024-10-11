cal
push $al
cfp
b _7a5d012_
__f0_:
cal
push $al
cfp
push $ra
cal
addi $al 1
lw $a0 $al
push $a0
cal
addi $al 2
lw $a0 $al
lw $t0 $sp
less $a0 $t0 $a0
pop
li $t0 0
beq_3b81a1b_ $a0 $t0
cal
push $al
cfp
cal
lw $al $al
addi $al 1
lw $a0 $al
push $a0
cal
lw $al $al
addi $al 2
lw $a0 $al
lw $t0 $sp
add $t0 $a0 $a0
pop
print $a0
_64616ca_:
lw $fp $fp
pop
b _1755048_
_3b81a1b_:
cal
push $al
cfp
li $a0 1
push $a0
cal
lw $al $al
addi $al 2
lw $a0 $al
push $a0
li $a0 1
lw $t0 $sp
add $t0 $a0 $a0
pop
push $a0
cal
lw $al $al
addi $al 1
lw $a0 $al
push $a0
li $a0 1
lw $t0 $sp
add $t0 $a0 $a0
pop
push $a0
jal __f0_
_6be46e8_:
pop
lw $fp $fp
pop
_1755048_:
_1a407d5_:
lw $a0 $sp
sra
pop
lw $a0 $sp
sfp
pop
pop
pop
lrv
jr
_7a5d012_:
li $a0 4
push $a0
li $a0 5
push $a0
jal __f0_
li $a0 5
push $a0
li $a0 4
push $a0
jal __f0_
_22f7133_:
lw $fp $fp
pop
halt
