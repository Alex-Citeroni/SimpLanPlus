cal
push $al
cfp
li $a0 1
push $a0
b _783e635_
__f0_:
cal
push $al
cfp
push $ra
cal
addi $al 1
lw $a0 $al
push $a0
li $a0 0
lw $t0 $sp
eq $t0 $a0 $a0
pop
li $t0 0
beq_60addb5_ $a0 $t0
cal
push $al
cfp
cal
lw $al $al
lw $al $al
addi $al -1
lw $a0 $al
print $a0
_3f2a3a_:
lw $fp $fp
pop
b _77b52d1_
_60addb5_:
cal
push $al
cfp
cal
lw $al $al
lw $al $al
addi $al -1
lw $a0 $al
push $a0
cal
lw $al $al
addi $al 1
lw $a0 $al
lw $t0 $sp
mult $t0 $a0 $a0
pop
push $a0
cal
lw $al $al
lw $al $al
addi $al -1
lal
lw $t0 $sp
pop
sw $t0 $a0
cal
lw $al $al
addi $al 1
lw $a0 $al
push $a0
li $a0 1
lw $t0 $sp
sub $t0 $a0 $a0
pop
push $a0
jal __f0_
_34b7bfc_:
lw $fp $fp
pop
_77b52d1_:
_42d3bd8_:
lw $a0 $sp
sra
pop
lw $a0 $sp
sfp
pop
pop
lrv
jr
_783e635_:
li $a0 10
push $a0
jal __f0_
_3fb6a44_:
pop
lw $fp $fp
pop
halt
