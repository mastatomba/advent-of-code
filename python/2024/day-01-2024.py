# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day1/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day1/input.txt'

with open(file_path, 'r') as file:
    lines = file.readlines()

list1 = []
list2 = []
for line in lines:
    line = line.rstrip('\n')

    splitted = line.split("   ")
    list1.append(int(splitted[0]))
    list2.append(int(splitted[1]))

list1.sort()
list2.sort()

total1 = total2 = 0
for i in range(len(list1)):
    loc1 = list1[i]
    loc2 = list2[i]
    total1 += abs(loc1 - loc2)
    total2 += loc1 * list2.count(loc1)

print(f"Part 1: {total1}")

print(f"Part 2: {total2}")
