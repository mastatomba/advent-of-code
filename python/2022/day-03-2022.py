# file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day3/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day3/input.txt'

with open(file_path, 'r') as file:
    lines = file.readlines()

def compartments(rucksack):
    # Using string slicing
    # Splitting string into equal halves
    com1, com2 = rucksack[:len(rucksack)//2], rucksack[len(rucksack)//2:]

    return [com1, com2]

def find_item_type(compartment1, compartment2):
    for it in compartment1:
        if (compartment2.find(it) != -1):
            return it

def priority(item_type):
    ascii = ord(item_type)
    if (ascii >= 97):
        return ascii - 97 + 1
    return ascii - 65 + 27

total = 0
for line in lines:
    rucksack = line.rstrip('\n')
    # print("Rucksack: " + rucksack)

    coms = compartments(rucksack)
    # print(f"Compartments: {coms[0]}, {coms[1]}")

    it = find_item_type(coms[0], coms[1])
    # print(f"Item type: {it}")

    prio = priority(it)
    total += prio
    # print(f"Priority: {prio}")

print(f"Part 1: {total}")

groups = []
group = []
for line in lines:
    rucksack = line.rstrip('\n')
    group.append(rucksack)
    if (len(group) == 3):
        groups.append(group)
        group = []

if (len(group) > 0):
    print("There are leftover rucksacks..")

def find_badge(rucksack1, rucksack2, rucksack3):
    # print("Rucksack1: " + rucksack1)
    # print("Rucksack2: " + rucksack2)
    # print("Rucksack3: " + rucksack3)

    for char in rucksack1:
        if (rucksack2.find(char) != -1 and rucksack3.find(char) != -1):
            return char

total = 0
for group in groups:
    ba = find_badge(group[0], group[1], group[2])
    # print(f"Badge: {ba}")

    prio = priority(ba)
    total += prio
    # print(f"Priority: {prio}")

print(f"Part 2: {total}")
