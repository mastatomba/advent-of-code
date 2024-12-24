# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day24/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day24/input_story2.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day24/input.txt'

def binary_to_decimal(binary_str: str) -> int:
    """
    Converts a binary string to its decimal equivalent.
    
    Args:
        binary_str (str): The binary number as a string.
    
    Returns:
        int: The decimal equivalent of the binary number.
    """
    return int(binary_str, 2)

def calculate_output(input1: int, input2: int, operator: str):
    if operator == 'AND':
        if input1 == 1 and input2 == 1:
            return 1
    elif operator == 'OR':
        if input1 == 1 or input2 == 1:
            return 1
    elif operator == 'XOR':
        if input1 != input2:
            return 1
    return 0

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

count1 = 0
wires = {}
gate_lines = []
is_gate_output = False
for line in lines:
    if (line == ''):
        is_gate_output = True
    else:
        if is_gate_output:
            # print(f"Gate: {line}")
            gate_lines.append(line)
        else:
            # print(f"Wire: {line}")
            splitted = line.split(': ')
            wires[splitted[0]] = int(splitted[1])

z_wires = {}
remaining = len(gate_lines)
while remaining > 0:
    for line in gate_lines:
        splitted1 = line.split(' -> ')
        if (splitted1[1] not in wires):
            splitted2 = splitted1[0].split(' ')
            if (splitted2[0] in wires and splitted2[2] in wires):
                wires[splitted1[1]] = calculate_output(wires[splitted2[0]], wires[splitted2[2]], splitted2[1])
                if (splitted1[1].startswith('z')):
                    z_wires[splitted1[1]] = str(wires[splitted1[1]])
                remaining -= 1

output = ''
z_keys = list(z_wires.keys())
z_keys.sort()
for key in reversed(z_keys):
    # print(f"{key}: {z_wires[key]}")
    output += z_wires[key]

print(output)

print(f"Part 1: {binary_to_decimal(output)}")

