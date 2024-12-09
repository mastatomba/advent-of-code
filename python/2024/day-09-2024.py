file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day9/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day9/input_story_2.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day9/input.txt'

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
    # 00...111...2...333.44.5555.6666.777.888899
    # 0099811188827773336446555566..............
    start = 0
    end = len(blocks) - 1
    for end_idx in range(len(blocks) - 1, -1, -1):
        if blocks[end_idx] != '.':
            print(f"  found file block: {blocks[end_idx]} at index {end_idx}")
            if (move_full_file):
                count = file_ids[blocks[end_idx]]
            else:
                count = 1

            start_idx = find_empty_space_index(blocks, end_idx, count)
            if (start_idx != -1):
                print(f"  found start_id: {start_idx}")
                if ((start_idx + count) < end_idx):
                    for i in range(start_idx, start_idx + count):
                        blocks[i] = blocks[end_idx]
                    for i in range(end_idx, end_idx - count, -1):
                        blocks[i] = '.'

            print(''.join(str(x) for x in blocks))

def find_empty_space_index(blocks: list, end_idx: int, blocks_needed: int):
    index = -1
    count = 0
    for i in range((end_idx - blocks_needed)):
        if blocks[i] == '.':
            if (index == -1):
                index = i
            count += 1
            if (count >= blocks_needed):
                return index;
        else:
            index = -1
            count = 0
    return index

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
compact(blocks, file_ids, False)
# print(blocks)
# print(''.join(str(x) for x in blocks))

checksum = calculate_checksum(blocks)

# answer 1: 6291146829743 (too high) -> it needs to be -5257 = 6291146824486, because there is a gap still '52595258.5257.....'
# answer 2: 85132078996 (too low)
print(f"Part 1: {checksum}")

file_ids = {}
blocks = map_to_blocks(data, file_ids)
# print(blocks)
# print(file_ids)

compact(blocks, file_ids, True)
# print(blocks)

checksum = calculate_checksum(blocks)

print(f"Part 2: {checksum}")
