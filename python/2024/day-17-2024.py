# REGISTER_A=729
# REGISTER_B=0
# REGISTER_C=0
# PROGRAM=[0,1,5,4,3,0]

REGISTER_A=2024
REGISTER_B=0
REGISTER_C=0
PROGRAM=[0,3,5,4,3,0]

# REGISTER_A=46187030
# REGISTER_B=0
# REGISTER_C=0
# PROGRAM=[2,4,1,5,7,5,0,3,4,0,1,6,5,5,3,0]

def calculate_bitwise_xor(number1: int, number2: int):
    result = number1 ^ number2
    return result

class Execution:
    def __init__(self, a: int, b: int, c: int):
        self.a = a
        self.b = b
        self.c = c
        self.output = []
        self.pointer = -1

    def __str__(self):
        return f"Execution(a={self.a}, b={self.b}, c={self.c}, output={self.output})"

    def execute(self, opcode: int, operand: int):
        # reset pointer
        self.pointer = -1

        if (opcode == 0):
            self.oc0_adv(operand)
        elif (opcode == 1):
            self.oc1_bxl(operand)
        elif (opcode == 2):
            self.oc2_bst(operand)
        elif (opcode == 3):
            self.oc3_jnz(operand)
        elif (opcode == 4):
            self.oc4_bxc()
        elif (opcode == 5):
            self.oc5_out(operand)
        elif (opcode == 6):
            self.oc6_bdv(operand)
        elif (opcode == 7):
            self.oc7_cdv(operand)

    def output_matches_program(self):
        if (len(self.output) > len(PROGRAM)):
            return False
        for i in range(len(self.output)):
            if (self.output[i] != PROGRAM[i]):
                return False
        return True
    
    def get_combo_operand_value(self, operand: int):
        # The value of a combo operand can be found as follows:
        # - Combo operands `0` through `3` represent literal values `0` through `3`.
        # - Combo operand `4` represents the value of register `A`.
        # - Combo operand `5` represents the value of register `B`.
        # - Combo operand `6` represents the value of register `C`.
        # - Combo operand `7` is reserved and will not appear in valid programs.
        if (operand<=3):
            return operand
        if (operand==4):
            return self.a
        if (operand==5):
            return self.b
        if (operand==6):
            return self.c
        raise Exception(f"Operand '{operand}' is not supported!")
        
    def oc0_adv(self, operand: int):
        # The `adv` instruction (opcode `0`) performs division. The numerator is the value in the `A` register. 
        # The denominator is found by raising `2` to the power of the instruction's combo operand. (So, an operand 
        # of `2` would divide `A` by `4` (`2^2`); an operand of `5` would divide `A` by `2^B`.) The result of the 
        # division operation is truncated to an integer and then written to the `A` register.
        res = self.a / pow(2, self.get_combo_operand_value(operand))
        self.a = int(res)

    def oc1_bxl(self, operand: int):
        # The `bxl` instruction (opcode `1`) calculates the bitwise XOR of register `B` and the instruction's literal 
        # operand, then stores the result in register `B`.
        res = calculate_bitwise_xor(self.b, operand)
        self.b = res

    def oc2_bst(self, operand: int):
        # The `bst` instruction (opcode `2`) calculates the value of its combo operand modulo 8 (thereby keeping only 
        # its lowest 3 bits), then writes that value to the `B` register.
        res = self.get_combo_operand_value(operand) % 8
        self.b = res

    def oc3_jnz(self, operand: int):
        # The `jnz` instruction (opcode `3`) does nothing if the `A` register is `0`. However, if the `A` register is 
        # not zero, it jumps by setting the instruction pointer to the value of its literal operand; if this 
        # instruction jumps, the instruction pointer is not increased by `2` after this instruction.
        if (self.a != 0):
            self.pointer = operand

    def oc4_bxc(self):
        # The `bxc` instruction (opcode `4`) calculates the bitwise XOR of register `B` and register `C`, then stores 
        # the result in register `B`. (For legacy reasons, this instruction reads an operand but ignores it.)
        res = calculate_bitwise_xor(self.b, self.c)
        self.b = res

    def oc5_out(self, operand: int):
        # The `out` instruction (opcode `5`) calculates the value of its combo operand modulo 8, then outputs that 
        # value. (If a program outputs multiple values, they are separated by commas.)
        res = self.get_combo_operand_value(operand) % 8
        self.output.append(res)

    def oc6_bdv(self, operand: int):
        # The `bdv` instruction (opcode `6`) works exactly like the `adv` instruction except that the result is 
        # stored in the `B` register. (The numerator is still read from the `A` register.)
        res = self.a / pow(2, self.get_combo_operand_value(operand))
        self.b = int(res)

    def oc7_cdv(self, operand: int):
        # The `cdv` instruction (opcode `7`) works exactly like the `adv` instruction except that the result is 
        # stored in the `C` register. (The numerator is still read from the `A` register.)
        res = self.a / pow(2, self.get_combo_operand_value(operand))
        self.c = int(res)

execution = Execution(REGISTER_A, REGISTER_B, REGISTER_C)
i = 0
while i < len(PROGRAM):
    execution.execute(PROGRAM[i],PROGRAM[i+1])
    # print(execution)
    if (execution.pointer != -1):
        i = execution.pointer
    else:
        i += 2

result = ",".join(map(str, execution.output))
print(f"Part 1: {result}")

wanted = ",".join(map(str, PROGRAM))
register_a = -1
for a in range(1000000000):
    execution = Execution(a, REGISTER_B, REGISTER_C)
    i = 0
    skip = False
    while i < len(PROGRAM):
        execution.execute(PROGRAM[i],PROGRAM[i+1])
        # print(execution)
        if (execution.output_matches_program()):
            if (execution.pointer != -1):
                i = execution.pointer
            else:
                i += 2
        else:
            skip = True
            break
    if (not skip):
        result = ",".join(map(str, execution.output))
        # print(f"  results for {a}: {result}")
        if (wanted == result):
            register_a = a
            break
    if (a % 10000):
        print(a)

# 2-4 REG_A mod 8      > REG_B
# 1-5 REG_B XOR 5      > REG_B
# 7-5 REG_A / 2^REG_B  > REG_B
# 0-3 REG_A / 8        > REG_A
# 4-0 REG_B XOR REG_C  > REG_B
# 1-6 REG_B XOR 6      > REG_B
# 5-5 REG_B mod 8      > OUTPUT
# 3-0 RERUN

print(f"Part 2: {register_a}")
