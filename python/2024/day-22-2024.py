# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day22/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day22/input.txt'

def calculate_bitwise_xor(number1: int, number2: int):
    result = number1 ^ number2
    return result

def mix_and_prune(number1: int, number2: int):
    xor = calculate_bitwise_xor(number1, number2)
    mod16777216 = xor % 16777216
    return mod16777216

def get_next_secret_number(number: int, iterations: int):
    for i in range(iterations):
        mult64 = number * 64
        number = mix_and_prune(number, mult64)
        div32 = int(number / 32)
        number = mix_and_prune(number, div32)
        mult2048 = number * 2048
        number = mix_and_prune(number, mult2048)
    return number

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

count1 = 0
for line in lines:
    count1 += get_next_secret_number(int(line), 2000)

print(f"Part 1: {count1}")

