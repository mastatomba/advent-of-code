# file_path = '/home/tom/projects/personal/advent-of-code/resources/input/2024/day11/input_story.txt'
# file_path = '/home/tom/projects/personal/advent-of-code/resources/input/2024/day11/input_story_2.txt'
# file_path = '/home/tom/projects/personal/advent-of-code/resources/input/2024/day11/input_story_3.txt'
file_path = '/home/tom/projects/personal/advent-of-code/resources/input/2024/day11/input.txt'

with open(file_path) as f:
    data = f.read()

stones = data.split(' ')

# print (stones)

def blink_stone_once(stone: str):
    new_stones = []
    if (stone == '0'):
        new_stones.append('1')
    elif (len(stone) % 2 == 0):
        mid = len(stone) // 2  # Find the middle index
        new_stones.append(stone[:mid])
        new_stones.append(str(int(stone[mid:])))
    else:
        new_stones.append(str(int(stone) * 2024))
    return new_stones

def blink_stone_recursive(stone: str, cur: int, max: int):
    sum = 0
    if (cur == max):
        sum = 1
    else:
        new_stones = blink_stone_once(stone)
        for new_stone in new_stones:
            key = new_stone+'_'+str(cur)
            if key in visited:
                # print(f"  Already processed {key}: {visited[key]}")
                sum += visited[key]
            else:
                count = blink_stone_recursive(new_stone, cur + 1, max)
                visited[key] = count
                sum += count
    return sum;

visited = {}
sum1 = 0
for stone in stones:
    sum1 += blink_stone_recursive(stone, 0, 25)

print(f"Part 1: {sum1}")

visited = {}
sum2 = 0
for stone in stones:
    count = blink_stone_recursive(stone, 0, 75)
    # print(f"  {stone}: {count}")
    sum2 += count

print(f"Part 2: {sum2}")
