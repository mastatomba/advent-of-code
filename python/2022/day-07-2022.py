# file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day7/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day7/input.txt'

ROOT = 'root'

with open(file_path, 'r') as file:
    lines = file.readlines()

def folder_identifier(path):
    fid = ROOT
    for i in range(1,len(path),1):
        fid += '_' + path[i]
    return fid

def calculate_total_size(fid):
    # print(f"calculate_total_size({fid}) called")
    size = dir_sizes[fid];
    if (fid in dir_tree):
        for dir in dir_tree[fid]:
            size += calculate_total_size(fid + '_' + dir)
    return size

current_path = ['/']
fid = ''
is_listing = False
dir_sizes = {}
dir_tree = {}
for line in lines:
    line = line.rstrip('\n')

    # check folder traversal
    if (line.startswith('$ cd')):
        is_listing = False
        if (line == '$ cd /'): # go to root folder
            current_path = ['/']
        elif (line == '$ cd ..'): # go up one folder
            current_path.pop(-1)
        else: # go into folder
            current_path.append(line[5:])

        fid = folder_identifier(current_path)
        # print(f"Current folder identifier: {fid}")
        if (fid not in dir_sizes):
            dir_sizes[fid] = 0
            dir_tree[fid] = []

    elif (line == '$ ls'):
        is_listing = True

    elif (is_listing):
        if (line.startswith('dir')):
            dir_tree[fid].append(line[4:])
        else:
            splitted = line.split(' ')
            dir_sizes[fid] += int(splitted[0])

    else:
        print(f"Error handling command {line}")

sum = 0
for dir in dir_sizes:
    # print(f"{dir}: {dir_sizes[dir]}")
    size = calculate_total_size(dir)
    if (size < 100000):
        # print(f"matched {dir}: {size}")
        sum += size

print(f"Part 1: {sum}")

size_in_use = calculate_total_size(ROOT)  # real: 44804833
size_free = 70000000 - size_in_use        # real: 25195167
size_needed = 30000000 - size_free        # real: 4804833

lowest_size = 30000000
for dir in dir_sizes:
    # print(f"{dir}: {dir_sizes[dir]}")
    size = calculate_total_size(dir)
    if (size > size_needed and size < lowest_size):
        # print(f"matched {dir}: {size}")
        lowest_size = size

print(f"Part 2: {lowest_size}")
