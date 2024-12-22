file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day15/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day15/input_story2.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day15/input.txt'

LOG_LEVEL = 4
def log(level: int, message: str):
    if level <= LOG_LEVEL:
        print(message)

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

grid_lines = []
move_lines = []
is_move_line = False
for line in lines:
    if (line == ''):
        is_move_line = True
    elif not is_move_line:
        grid_lines.append(line)
    else:
        move_lines.append(line)

grid_list = [[position for position in element] for element in grid_lines]
grid = {(x, y): grid_list[y][x] for y in range(len(grid_list)) for x in range(len(grid_list[0]))}

GRID_WIDTH, GRID_HEIGHT = (len(grid_list[0]), len(grid_list))

ROBOT = '@'
EMPTY = '.'
BOX = 'O'
WALL = '#'

def find_robot():
    for y, row in enumerate(grid_list):
        for x, cell in enumerate(row):
            if (cell == ROBOT):
                return (x,y)
    raise Exception("Robot not found!")

ROBOT_POS = find_robot()
log(4, f"ROBOT_POS={ROBOT_POS}")

def update_robot_position(new_position):
    global ROBOT_POS
    # log(4, f"update_robot_position({new_position}), ROBOT_POS={ROBOT_POS}")
    grid[ROBOT_POS] = EMPTY
    grid[new_position] = ROBOT
    ROBOT_POS = new_position

def move_box(box_position, direction):
    done = False
    (current_x, current_y) = box_position
    (dx, dy) = direction
    while not done:
        new_position = (current_x + dx, current_y + dy)
        if grid[new_position] == EMPTY:
            grid[new_position] = BOX
            return True
        elif grid[new_position] == WALL:
            return False
        (current_x, current_y) = new_position
    return False

def move_big_box(box_position, direction):
    done = False
    (current_x, current_y) = box_position
    (dx, dy) = direction
    while not done:
        new_position = (current_x + dx, current_y + dy)
        if grid[new_position] == EMPTY:
            grid[new_position] = BOX
            return True
        elif grid[new_position] == WALL:
            return False
        (current_x, current_y) = new_position
    return False

def move_robot(direction):
    (current_x, current_y) = ROBOT_POS
    (dx, dy) = direction
    new_position = (current_x + dx, current_y + dy)
    if grid[new_position] == EMPTY:
        update_robot_position(new_position)
    elif grid[new_position] == BOX:
        if move_box(new_position, direction):
            update_robot_position(new_position)
    elif grid[new_position] == '[' or grid[new_position] == ']':
        if move_big_box(new_position, direction):
            update_robot_position(new_position)

def print_grid():
    for y, row in enumerate(grid_list):
        s = ''
        for x, cell in enumerate(row):
            s += grid[(x,y)]
        print(s)

moves = ''.join(move_lines)
for move in moves:
    # log(4, f"Moving robot {move}")
    (current_x, current_y) = ROBOT_POS
    if (move == '^'):
        move_robot((0,-1))
    elif (move == 'v'):
        move_robot((0,1))
    elif (move == '<'):
        move_robot((-1,0))
    elif (move == '>'):
        move_robot((1,0))
    
    # print_grid()

print_grid()

count1 = 0
for y, row in enumerate(grid_list):
    for x, cell in enumerate(row):
        if grid[(x,y)] == BOX:
            gps = (100 * y) + x
            # log(4, f"GPS at box ({x},{y}): {gps}")
            count1 += gps

print(f"Part 1: {count1}")

grid_lines2 = []
for line in grid_lines:
    line2 = line.replace('#', '##')
    line2 = line2.replace('O', '[]')
    line2 = line2.replace('.', '..')
    line2 = line2.replace('@', '@.')
    grid_lines2.append(line2)

grid_list = [[position for position in element] for element in grid_lines2]
grid = {(x, y): grid_list[y][x] for y in range(len(grid_list)) for x in range(len(grid_list[0]))}

print_grid()

ROBOT_POS = find_robot()
log(4, f"ROBOT_POS={ROBOT_POS}")

for move in moves:
    # log(4, f"Moving robot {move}")
    (current_x, current_y) = ROBOT_POS
    if (move == '^'):
        move_robot((0,-1))
    elif (move == 'v'):
        move_robot((0,1))
    elif (move == '<'):
        move_robot((-1,0))
    elif (move == '>'):
        move_robot((1,0))

print_grid()

count2 = 0
for y, row in enumerate(grid_list):
    for x, cell in enumerate(row):
        if grid[(x,y)] == '[':
            gps = (100 * y) + x
            # log(4, f"GPS at box ({x},{y}): {gps}")
            count2 += gps

print(f"Part 2: {count2}")