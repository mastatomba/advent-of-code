# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day5/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day5/input.txt'

def to_int_list(char_list: list):
    numbers = []
    for char in char_list:
        numbers.append(int(char))
    return numbers

with open(file_path, 'r') as file:
    lines = file.readlines()

debug = False
before_rules = {}
after_rules = {}
updates = []
append_updates = False
for line in lines:
    line = line.rstrip('\n')
    if line == '':
        append_updates = True
    elif (append_updates):
        updates.append(to_int_list(line.split(',')))
    else:
        splitted = line.split('|')
        page1 = int(splitted[0])
        page2 = int(splitted[1])

        if (page1 not in before_rules):
            before_rules[page1] = []
        before_rules[page1].append(page2)
        if (page2 not in after_rules):
            after_rules[page2] = []
        after_rules[page2].append(page1)

def is_valid(from_page, to_page):
    if (from_page in after_rules) and (after_rules[from_page].count(to_page) > 0):
        return False
    if (to_page in before_rules) and (before_rules[to_page].count(from_page) > 0):
        return False
    return True

def is_update_valid(update: list):
    for i in range(len(update)):
        for j in range(len(update)):
            if (j > i):
                if (not is_valid(update[i], update[j])):
                    return False
    return True

def get_correct_order(update: list):
    update_in_correct_order = []
    for page in update:
        if (len(update_in_correct_order) == 0):
            update_in_correct_order.append(page)
        else:
            before_idx = after_idx = -1
            if (page in before_rules):
                for before_page in before_rules[page]:
                    if (before_page in update_in_correct_order):
                        i = update_in_correct_order.index(before_page)
                        if (before_idx == -1 or i < before_idx):
                            before_idx = i
            if (page in after_rules):
                for after_page in after_rules[page]:
                    if (after_page in update_in_correct_order):
                        i = update_in_correct_order.index(after_page)
                        if (after_idx == -1 or i > after_idx):
                            after_idx = i

            if (before_idx != -1):
                update_in_correct_order.insert(before_idx, page)
            elif (after_idx != -1):
                update_in_correct_order.insert(after_idx + 1, page)
            else:
                update_in_correct_order.append(page)

    return update_in_correct_order

count1 = count2 = 0
for update in updates:
    if (is_update_valid(update)):
        mid_index = len(update) // 2
        count1 += update[mid_index]
    else:
        update_in_correct_order = get_correct_order(update)
        mid_index = len(update_in_correct_order) // 2
        count2 += update_in_correct_order[mid_index]

print(f"Part 1: {count1}")

print(f"Part 2: {count2}")