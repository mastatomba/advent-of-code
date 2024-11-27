# file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day9/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day9/input.txt'

with open(file_path, 'r') as file:
    lines = file.readlines()

class Pos:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __str__(self):
        return f"Pos(x={self.x}, y={self.y})"
    
    def move_left(self, count: int):
        self.x -= count

    def move_right(self, count: int):
        self.x += count

    def move_up(self, count: int):
        self.y -= count

    def move_down(self, count: int):
        self.y += count

    def is_same(self, other: "Pos"):
        return self.x == other.x and self.y == other.y

    def is_same_row(self, other: "Pos"):
        return self.y == other.y

    def is_same_column(self, other: "Pos"):
        return self.x == other.x

    def get_key(self):
        return f"{self.x}_{self.y}"

class State:
    def __init__(self, start_pos: Pos, width: int, height: int):
        self.start_pos = start_pos
        self.width = width
        self.height = height

    def __str__(self):
        return f"State(start_pos={self.start_pos}, width={self.width}, height={self.height})"

def get_initial_state(lines):  
    cur_pos = Pos(0,0)
    min_pos = Pos(0,0)
    max_pos = Pos(0,0)
    for line in lines:
        line = line.rstrip('\n')
        # print(line)
        count = int(line[2:])
        # print(f'  count={count}')
        if (line.startswith('L ')):
            # print(f'  move {count} left')
            cur_pos.move_left(count)
            if (cur_pos.x < min_pos.x):
                min_pos.x = cur_pos.x
        elif (line.startswith('R ')):
            # print(f'  move {count} right')
            cur_pos.move_right(count)
            if (cur_pos.x > max_pos.x):
                max_pos.x = cur_pos.x
        elif (line.startswith('U ')):
            # print(f'  move {count} up')
            cur_pos.move_up(count)
            if (cur_pos.y < min_pos.y):
                min_pos.y = cur_pos.y
        else:
            # print(f'  move {count} down')
            cur_pos.move_down(count)
            if (cur_pos.y > max_pos.y):
                max_pos.y = cur_pos.y
        # print(f'  cur_pos={cur_pos}')

    # print(f'  min_pos={min_pos}')
    # print(f'  max_pos={max_pos}')

    start_pos = Pos(0,0)
    if (min_pos.x < 0):
        start_pos.x -= min_pos.x
        max_pos.x -= min_pos.x
    if (min_pos.y < 0):
        start_pos.y -= min_pos.y
        max_pos.y -= min_pos.y

    # print('Initial state:')
    # print(f'  start_pos={start_pos}')
    # print(f'  grid_size=({max_pos.x+1},{max_pos.y+1})')

    return State(start_pos, max_pos.x+1, max_pos.y+1)

state = get_initial_state(lines)
print(f'Initial state: {state}')

head_pos = Pos(state.start_pos.x, state.start_pos.y)
tail_pos = Pos(state.start_pos.x, state.start_pos.y)
visited = {}
visited[tail_pos.get_key()] = 1

for line in lines:
    line = line.rstrip('\n')
    # print(line)

    count = int(line[2:])
    # print(f'  count={count}')
    if (line.startswith('L ')):
        # print(f'  move {count} left')
        for c in range(count):
            head_pos.move_left(1)
            if (not head_pos.is_same(tail_pos)):
                if (tail_pos.x - head_pos.x > 1):
                    if (not head_pos.is_same_row(tail_pos)):
                        tail_pos.y = head_pos.y
                    tail_pos.x = head_pos.x + 1
                    visited[tail_pos.get_key()] = 1

    elif (line.startswith('R ')):
        # print(f'  move {count} right')
        for c in range(count):
            head_pos.move_right(1)
            if (not head_pos.is_same(tail_pos)):
                if (head_pos.x - tail_pos.x > 1):
                    if (not head_pos.is_same_row(tail_pos)):
                        tail_pos.y = head_pos.y
                    tail_pos.x = head_pos.x - 1
                    visited[tail_pos.get_key()] = 1

    elif (line.startswith('U ')):
        # print(f'  move {count} up')
        for c in range(count):
            head_pos.move_up(1)
            if (not head_pos.is_same(tail_pos)):
                if (tail_pos.y - head_pos.y > 1):
                    if (not head_pos.is_same_column(tail_pos)):
                        tail_pos.x = head_pos.x
                    tail_pos.y = head_pos.y + 1
                    visited[tail_pos.get_key()] = 1

    else:
        # print(f'  move {count} down')
        for c in range(count):
            head_pos.move_down(1)
            if (not head_pos.is_same(tail_pos)):
                if (head_pos.y - tail_pos.y > 1):
                    if (not head_pos.is_same_row(tail_pos)):
                        tail_pos.x = head_pos.x
                    tail_pos.y = head_pos.y - 1
                    visited[tail_pos.get_key()] = 1

    # print(f'  head_pos={head_pos}')

print(f"Part 1: {len(visited)}")
