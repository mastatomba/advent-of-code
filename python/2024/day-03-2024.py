import re

# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day3/input_story.txt'
# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day3/input_story_2.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day3/input.txt'

def calculate_mul(mul):
    # Example: mul(2,4)
    pattern = r'\d{1,5}'
    result = re.findall(pattern, mul)
    return int(result[0]) * int(result[1])

def calculate_input(line):
    # Example: xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
    pattern = r'mul\(\d{1,5},\d{1,5}\)'
    result = re.findall(pattern, line)
    total = 0
    for mul in result:
        total += calculate_mul(mul)
    return total

def calculate_input_extra(line: str):
    # Example: xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
    # print(f"\n{line}")
    enabled = True
    start = 0
    total = 0
    while start != -1 and start < len(line):
        if enabled:
            end = line.find("don't()", start)
            if end == -1:
                sub_line = line[start:]
            else:
                sub_line = line[start:end]
            total += calculate_input(sub_line)
            # print(f"  DO: {sub_line}: {total}")
            start = end
            enabled = False
        else:
            end = line.find("do()", start)
            if end == -1:
                sub_line = line[start:]
            else:
                sub_line = line[start:end]
            # print(f"  DON'T: {sub_line}")
            start = end
            enabled = True

    return total

with open(file_path, 'r') as file:
    lines = file.readlines()

input = ''
total1 = total2 = 0
for line in lines:
    line = line.rstrip('\n')
    input += line

total1 += calculate_input(input)
total2 += calculate_input_extra(input)

print(f"Part 1: {total1}")

print(f"Part 2: {total2}")
# 102 631 226: too high, needed to combine the lines, a line could actually still be disabled due to don't on previous line
