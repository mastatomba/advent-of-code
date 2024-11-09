# file_path = '/home/tomschoutens/Projects/personal/advent-of-code/resources/input/2022/day1/input_story.txt'
file_path = '/home/tomschoutens/Projects/personal/advent-of-code/resources/input/2022/day1/input.txt'

with open(file_path, 'r') as file:
    lines = file.readlines()
    file_content = ''.join(lines)

# print(file_content)

cur = max = 0
for line in lines:
    if line == '\n':
        if cur > max:
            max = cur
            # print(max)
        cur = 0
    else:
        cur += int(line)

if cur > max:
    max = cur

print(f"Part 1: ({max})")

cur = 0
max = []
for line in lines:
    if line == '\n':
        max.append(cur)
        cur = 0
    else:
        cur += int(line)

max.append(cur)
max.sort(reverse = True)

max_3 = 0
max_3 += max.pop(0)
max_3 += max.pop(0)
max_3 += max.pop(0)

print(f"Part 2: ({max_3})")
