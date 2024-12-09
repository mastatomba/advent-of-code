# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day9/input_story.txt'
# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day9/input_story_2.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day9/input.txt'

def map_to_blocks(data: str, file_ids: dict):
    # 2333133121414131402
    # 00...111...2...333.44.5555.6666.777.888899
    blocks = []
    is_file = True
    file_id = 0
    for c in list(data):
        number = int(c)
        if (is_file):
            for i in range(number):
                blocks.append(file_id)
            file_ids[file_id] = number
            file_id += 1
        else:
            for i in range(number):
                blocks.append('.')
        is_file = not is_file
    return blocks

def compact(blocks: list, file_ids: dict, move_full_file: bool):
    # print(''.join(str(x) for x in blocks))
    start_idx = 0
    skip = 0
    last_file_id = -1
    for end_idx in range(len(blocks) - 1, -1, -1):
        if (skip > 0):
            skip -= 1
        else:
            if (end_idx >= start_idx) and (start_idx != -1):
                if blocks[end_idx] != '.':
                    file_id = blocks[end_idx]
                    if (last_file_id == -1 or last_file_id >= file_id):
                        last_file_id = file_id
                        # print(f"  found file block: {file_id} at index {end_idx}")
                        if (move_full_file):
                            count = file_ids[file_id]
                            skip = count - 1
                        else:
                            count = 1

                        empty_space_idx = find_empty_space_index(blocks, start_idx, end_idx, count)
                        if (empty_space_idx != -1) and ((empty_space_idx + count) < end_idx):
                            # print(f"  found empty_space_idx: {empty_space_idx}")
                            for i in range(empty_space_idx, empty_space_idx + count):
                                blocks[i] = file_id
                            for i in range(end_idx, end_idx - count, -1):
                                blocks[i] = '.'
                            start_idx = find_empty_space_index(blocks, start_idx, end_idx, 1)
                            # print(f"  new start_idx: {start_idx}")
                            # print(''.join(str(x) for x in blocks))
            else:
                break;

def find_empty_space_index(blocks: list, start_idx: int, end_idx: int, blocks_needed: int):
    # print(f"    find_empty_space_index({start_idx},{end_idx},{blocks_needed})")
    index = -1
    count = 0
    for i in range(start_idx, (end_idx - blocks_needed)):
        if blocks[i] == '.':
            if (index == -1):
                index = i
            count += 1
            if (count >= blocks_needed):
                return index;
        else:
            index = -1
            count = 0
    return -1

def calculate_checksum(blocks: list):
    sum = 0
    for i in range(len(blocks)):
        if (blocks[i] != '.'):
            sum += i * int(blocks[i])
    return sum

with open(file_path) as f:
    data = f.read()

# print(data)

file_ids = {}
blocks = map_to_blocks(data, file_ids)
# print(blocks)
# print(file_ids)

compact(blocks, file_ids, False)
# compact(blocks, file_ids, False)
# print(blocks)
# print(''.join(str(x) for x in blocks))

checksum = calculate_checksum(blocks)

print(f"Part 1: {checksum}")

file_ids = {}
blocks = map_to_blocks(data, file_ids)
# print(blocks)
# print(file_ids)

compact(blocks, file_ids, True)
# print(blocks)
# sblock = ''.join('['+str(x)+']' for x in blocks)
# print(sblock[0:2500])

checksum = calculate_checksum(blocks)

# answer 1: 6241803253723 (too low)
# answer 2: 6267571522277 (too low)
# right answer: 6307279963620
print(f"Part 2: {checksum}")
