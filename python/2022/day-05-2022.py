import re
import copy

# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2022/day5/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2022/day5/input.txt'

with open(file_path, 'r') as file:
    lines = file.readlines()

stack_count = -1
stacks = []
is_rearrangement_input = False
rearrangements = []
for line in lines:
    line = line.rstrip('\n')
    if (is_rearrangement_input):
        rearrangements.append(line)
    elif (line.find('[') != -1):
        # Example:
        #     [D]    
        # [N] [C]    
        # [Z] [M] [P]
        #  1   2   3 
        if (stack_count == -1):
            stack_count = (len(line) + 1) // 4
            # print(f"Number of stacks: {stack_count}")
            for i in range(stack_count):
                stacks.append([])

        # print(f"Crates: {line}")
        for i in range(stack_count):
            start = i*4
            crate = line[start:(start+4)]
            crate = crate.replace('[', '').replace(']', '').strip()
            if (crate != ''):
                stack = stacks[i]
                stack.append(crate)

    elif (line == ''):
        is_rearrangement_input = True

def rearrange_method_1(stacks, rearrangements):
    # Example:
    # move 1 from 2 to 1
    # move 3 from 1 to 3
    # move 2 from 2 to 1
    # move 1 from 1 to 2
    for rearrangement in rearrangements:
        # print(f"Rearrangement: {rearrangement}")
        numbers = re.findall(r'\d+', rearrangement)
        count = int(numbers[0])
        from_i = int(numbers[1])
        to_i = int(numbers[2])
        # print(f"Rearrangement: move {count} from {from_i} to {to_i}")
        for i in range(count):
            from_stack = stacks[from_i-1]
            to_stack = stacks[to_i-1]
            to_stack.insert(0, from_stack.pop(0))

    return stacks

def rearrange_method_2(stacks, rearrangements):
    for rearrangement in rearrangements:
        # print(f"Rearrangement: {rearrangement}")
        numbers = re.findall(r'\d+', rearrangement)
        count = int(numbers[0])
        from_i = int(numbers[1])
        to_i = int(numbers[2])
        # print(f"Rearrangement: move {count} from {from_i} to {to_i}")
        for i in range(count):
            from_stack = stacks[from_i-1]
            to_stack = stacks[to_i-1]
            to_stack.insert(i, from_stack.pop(0))

    return stacks

rearranged_stacks = rearrange_method_1(copy.deepcopy(stacks), rearrangements)

message = ''
for stack in rearranged_stacks:
    message += stack[0]

print(f"Part 1: {message}")

rearranged_stacks = rearrange_method_2(copy.deepcopy(stacks), rearrangements)

message = ''
for stack in rearranged_stacks:
    message += stack[0]

print(f"Part 2: {message}")