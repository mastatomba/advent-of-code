import copy

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day2/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day2/input.txt'

with open(file_path, 'r') as file:
    lines = file.readlines()

def is_report_safe(levels: list):
    is_increasing = False
    is_decreasing = False
    prev_level = int(levels[0])
    for i in range(1,len(levels)):
        cur_level = int(levels[i])
        diff = 0
        if cur_level > prev_level:
            if (is_decreasing):
                # print(f"  Previously decreasing ({prev_level},{cur_level})")
                return False
            is_increasing = True
            diff = cur_level - prev_level
        elif cur_level < prev_level:
            if (is_increasing):
                # print(f"  Previously increasing ({prev_level},{cur_level})")
                return False
            is_decreasing = True
            diff = prev_level - cur_level
        if (diff<1 or diff > 3):
            # print(f"  Diff too small/large ({prev_level},{cur_level},{diff})")
            return False;
        prev_level = cur_level
    return True

def is_report_safe_by_using_dampener(levels: list):
    if (is_report_safe(levels)):
        return True
    for i in range(len(levels)):
        levels2 = copy.deepcopy(levels)
        levels2.pop(i)
        if (is_report_safe(levels2)):
            return True
    return False

count1 = count2 = 0
for line in lines:
    line = line.rstrip('\n')
    levels = line.split(" ")
    if (is_report_safe(levels)):
        count1 += 1
    if (is_report_safe_by_using_dampener(levels)):
        count2 += 1
    #     print(f"Line '{line}' is safe")
    # else:
    #     print(f"Line '{line}' is unsafe..")

print(f"Part 1: {count1}")

print(f"Part 2: {count2}")
