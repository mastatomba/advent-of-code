# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day15/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day15/input_story2.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day15/input.txt'

ROBOT = '@'
EMPTY = '.'
BOX = 'O'
BIG_BOX_START = '['
BIG_BOX_END = ']'
WALL = '#'

LOG_LEVEL = 3

def log(level: int, message: str):
    if level <= LOG_LEVEL:
        print(message)

class Grid:
    def __init__(self, grid_lines):
        self.grid_list = [[position for position in element] for element in grid_lines]
        self.grid = {(x, y): self.grid_list[y][x] for y in range(len(self.grid_list)) for x in range(len(self.grid_list[0]))}

    def get_cell(self, position):
        return self.grid[position]

    def update_cell(self, position, value: chr):
        self.grid_list[position[1]][position[0]] = value
        self.grid[position] = value

    def print(self):
        if LOG_LEVEL >= 4:
            for y, row in enumerate(self.grid_list):
                s = ''
                for x, cell in enumerate(row):
                    s += self.grid[(x,y)]
                log(4, s)

class Robot:
    def __init__(self, grid: Grid):
        self.x = -1
        self.y = -1
        self.find_in_grid(grid)

    def __str__(self):
        return f"Robot(x={self.x}, y={self.y})"

    def find_in_grid(self, grid: Grid):
        for y, row in enumerate(grid.grid_list):
            for x, cell in enumerate(row):
                if (cell == ROBOT):
                    self.x = x
                    self.y = y

    def update_robot_position(self, new_position, grid: Grid):
        grid.update_cell((self.x, self.y), EMPTY)
        grid.update_cell(new_position, ROBOT)
        (self.x, self.y) = new_position

    def move_robot(self, direction, grid: Grid):
        (dx, dy) = direction
        new_position = (self.x + dx, self.y + dy)
        if grid.get_cell(new_position) == EMPTY:
            self.update_robot_position(new_position, grid)
        elif grid.get_cell(new_position) == BOX:
            if move_box(new_position, direction, grid):
                self.update_robot_position(new_position, grid)
        elif grid.get_cell(new_position) in [BIG_BOX_START, BIG_BOX_END]:
            if move_big_box(new_position, self.x, self.y, dx, dy, grid):
                self.update_robot_position(new_position, grid)

def move_box(box_position, direction, grid: Grid):
    done = False
    (current_x, current_y) = box_position
    (dx, dy) = direction
    while not done:
        new_position = (current_x + dx, current_y + dy)
        if grid.get_cell(new_position) == EMPTY:
            grid.update_cell(new_position, BOX)
            return True
        elif grid.get_cell(new_position) == WALL:
            return False
        (current_x, current_y) = new_position
    return False

def update_big_box_position(new_position, grid: Grid):
    (x, y) = new_position
    grid.update_cell(new_position, BIG_BOX_START)
    grid.update_cell((x + 1, y), BIG_BOX_END)
    return True

def move_big_box_left(box_position, grid: Grid):
    (current_x, current_y) = box_position
    new_position = (current_x - 1, current_y)
    if grid.get_cell(new_position) == EMPTY:
        return update_big_box_position(new_position, grid)
    elif grid.get_cell(new_position) == WALL:
        return False
    else:
        if move_big_box_left((current_x - 2, current_y), grid):
            return update_big_box_position(new_position, grid)
    return False

def move_big_box_right(box_position, grid: Grid):
    (current_x, current_y) = box_position
    new_position = (current_x + 2, current_y)
    if grid.get_cell(new_position) == EMPTY:
        return update_big_box_position((current_x + 1, current_y), grid)
    elif grid.get_cell(new_position) == WALL:
        return False
    else:
        if move_big_box_right(new_position, grid):
            return update_big_box_position((current_x + 1, current_y), grid)
    return False

def can_move_big_box_vertical(box_position, dy, grid: Grid, boxes: set):
    if grid.get_cell(box_position) != BIG_BOX_START:
        raise Exception("Unexpected box edge!")
    (current_x, current_y) = box_position
    new_left_edge_pos = (current_x, current_y + dy)
    new_right_edge_pos = (current_x + 1, current_y + dy)
    if grid.get_cell(new_left_edge_pos) == EMPTY and grid.get_cell(new_right_edge_pos) == EMPTY:
        boxes.add(box_position)
        return True
    elif grid.get_cell(new_left_edge_pos) == WALL or grid.get_cell(new_right_edge_pos) == WALL:
        return False
    else:
        can_move = False
        if grid.get_cell(new_left_edge_pos) == BIG_BOX_START:
            if can_move_big_box_vertical(new_left_edge_pos, dy, grid, boxes):
                can_move = True
        elif grid.get_cell(new_left_edge_pos) == BIG_BOX_END and grid.get_cell(new_right_edge_pos) == EMPTY:
            if can_move_big_box_vertical((current_x - 1, current_y + dy), dy, grid, boxes):
                can_move = True
        elif grid.get_cell(new_left_edge_pos) == EMPTY and grid.get_cell(new_right_edge_pos) == BIG_BOX_START:
            if can_move_big_box_vertical((current_x + 1, current_y + dy), dy, grid, boxes):
                can_move = True
        else: # 2 boxes
            if can_move_big_box_vertical((current_x - 1, current_y + dy), dy, grid, boxes):
                if can_move_big_box_vertical((current_x + 1, current_y + dy), dy, grid, boxes):
                    can_move = True

        if can_move:
            boxes.add(box_position)
            return True
    return False

def move_big_box_vertical(box_position, dy, grid: Grid):
    boxes = set()
    if (can_move_big_box_vertical(box_position, dy, grid, boxes)):
        for box in boxes:
            (current_x, current_y) = box
            grid.update_cell((current_x, current_y), EMPTY)
            grid.update_cell((current_x + 1, current_y), EMPTY)
        for box in boxes:
            (current_x, current_y) = box
            new_left_edge_pos = (current_x, current_y + dy)
            update_big_box_position(new_left_edge_pos, grid)
        return True

    return False

def move_big_box(new_position, current_x, current_y, dx, dy, grid: Grid):
    if (dx == -1): # move left
        if move_big_box_left((current_x - 2, current_y), grid):
            return True
    elif (dx == 1): # move right
        if move_big_box_right(new_position, grid):
            return True
    else:
        x = current_x
        if grid.get_cell(new_position) == BIG_BOX_END:
            x -= 1
        y = current_y + dy

        if move_big_box_vertical((x, y), dy, grid):
            grid.update_cell((x, y), EMPTY)
            grid.update_cell((x + 1, y), EMPTY)
            return True
    return False

def handle_moves(moves, grid: Grid):
    robot = Robot(grid)
    log(4, f"{robot}")

    for move in moves:
        log(4, f"Moving robot {move}")
        if (move == '^'):   robot.move_robot((0,-1), grid)
        elif (move == 'v'): robot.move_robot((0,1), grid)
        elif (move == '<'): robot.move_robot((-1,0), grid)
        elif (move == '>'): robot.move_robot((1,0), grid)

        # grid.print()

    grid.print()

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

grid = Grid(grid_lines)

moves = ''.join(move_lines)
handle_moves(moves, grid)

count1 = 0
for y, row in enumerate(grid.grid_list):
    for x, cell in enumerate(row):
        if grid.get_cell((x,y)) == BOX:
            gps = (100 * y) + x
            # log(4, f"GPS at box ({x},{y}): {gps}")
            count1 += gps

print(f"Part 1: {count1}")

grid_lines2 = []
for line in grid_lines:
    line2 = line.replace(WALL, WALL+WALL)
    line2 = line2.replace(BOX, BIG_BOX_START+BIG_BOX_END)
    line2 = line2.replace(EMPTY, EMPTY+EMPTY)
    line2 = line2.replace(ROBOT, ROBOT+EMPTY)
    grid_lines2.append(line2)

grid = Grid(grid_lines2)

grid.print()

handle_moves(moves, grid)

count2 = 0
for y, row in enumerate(grid.grid_list):
    for x, cell in enumerate(row):
        if grid.get_cell((x,y)) == BIG_BOX_START:
            gps = (100 * y) + x
            # log(4, f"GPS at box ({x},{y}): {gps}")
            count2 += gps

print(f"Part 2: {count2}")