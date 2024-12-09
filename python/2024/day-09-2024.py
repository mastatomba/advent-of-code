# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day9/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day9/input_story_2.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day9/input.txt'

def map_to_blocks(data: str):
    # 2333133121414131402
    # 00...111...2...333.44.5555.6666.777.888899
    blocks = []
    is_file = True
    file_index = 0
    for c in list(data):
        number = int(c)
        if (is_file):
            for i in range(number):
                blocks.append(file_index)
            file_index += 1
        else:
            for i in range(number):
                blocks.append('.')
        is_file = not is_file
    return blocks

def compact(blocks: list):
    # 00...111...2...333.44.5555.6666.777.888899
    # 0099811188827773336446555566..............
    start = 0
    end = len(blocks) - 1
    for i in range(len(blocks) - 1, -1, -1):
        if blocks[i] != '.':
            for j in range(start, end):
                if blocks[j] == '.':
                    blocks[j] = blocks[i]
                    blocks[i] = '.'
                    start = j
                    end = i
                    break

def calculate_checksum(blocks: list):
    sum = 0
    for i in range(len(blocks)):
        if (blocks[i] != '.'):
            sum += i * blocks[i]
    return sum

with open(file_path) as f:
    data = f.read()

# print(data)

blocks = map_to_blocks(data)
# print(blocks)

compact(blocks)
compact(blocks)
# print(blocks)
# print(''.join(str(x) for x in blocks))

checksum = calculate_checksum(blocks)

# answer 1: 6291146829743 (too high) -> it needs to be -5257 = 6291146824486, because there is a gap still '52595258.5257.....'
# answer 2: 85132078996 (too low)
print(f"Part 1: {checksum}")
