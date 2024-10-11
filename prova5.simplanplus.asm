cal
push $al
cfp
li $a0 1
push $a0
b _7969853_
__f0_:
cal
push $al
cfp
push $ra
cal
addi $al 2
lw $a0 $al
push $a0
li $a0 0
lw $t0 $sp
eq $t0 $a0 $a0
pop
li $t0 0
beq_783e635_ $a0 $t0
cal
push $al
cfp
cal
lw $al $al
addi $al 1
lw $a0 $al
print $a0
_1755048_:
lw $fp $fp
pop
b _2c6a3f7_
_783e635_:
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
mult $t0 $a0 $a0
pop
push $a0
cal
lw $al $al
addi $al 2
lw $a0 $al
push $a0
li $a0 1
lw $t0 $sp
sub $t0 $a0 $a0
pop
push $a0
cal
addi $al -1
lw $a0 $al
push $a0
jal __f0_
_90f6bf_:
pop
lw $fp $fp
pop
_2c6a3f7_:
_380fb43_:
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
_7969853_:
li $a0 6
push $a0
cal
addi $al -1
lw $a0 $al
push $a0
jal __f0_
_22f7133_:
pop
lw $fp $fp
pop
halt
