import numpy as np
import copy

# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day6/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day6/input.txt'

class Guard:
    def __init__(self, c: chr, row: int, col: int):
        self.c = c
        self.row = row
        self.col = col

    def __str__(self):
        return f"Pos(c={self.c}, row={self.row}, col={self.col})"

    def move(self, grid):
        grid[self.row, self.col] = '.'

        new_row = self.row
        new_col = self.col
        if (self.c == '^'):
            new_row -= 1
        elif (self.c == 'v'):
            new_row += 1
        elif (self.c == '<'):
            new_col -= 1
        elif (self.c == '>'):
            new_col += 1

        if (grid[new_row, new_col] == '#'):
            # blockage!
            new_row = self.row
            new_col = self.col
            if (self.c == '^'):
                new_col += 1
                self.c = '>'
            elif (self.c == 'v'):
                new_col -= 1
                self.c = '<'
            elif (self.c == '<'):
                new_row -= 1
                self.c = '^'
            elif (self.c == '>'):
                new_row += 1
                self.c = 'v'

        # take new position
        grid[new_row, new_col] = self.c
        self.row = new_row
        self.col = new_col

    def is_exit(self):
        if (self.c == '^' and self.row == 0):
            return True
        if (self.c == 'v' and self.row == height - 1):
            return True
        if (self.c == '<' and self.col == 0):
            return True
        if (self.c == '>' and self.col == width - 1):
            return True
        return False

    def get_key(self):
        return f"{self.row}_{self.col}"

def create_grid(lines: list):
    grid = []
    for line in lines:
        line = line.rstrip('\n')
        grid.append(list(line))
    return np.array(grid)

def find_guard():
    for row in range(height):
        for col in range(width):
            c = _grid[row,col]
            if (c != '#' and c != '.'):
                return Guard(c, row, col)
    raise Exception("Guard not found!")

def move_guard(guard: Guard, grid):
    visited = {}
    visited[guard.get_key()] = 1
    max_moves = 12000
    move_count = 0
    while move_count < max_moves and not guard.is_exit():
        guard.move(grid)
        move_count += 1
        # print(grid)
        if (guard.get_key() in visited):
            visited[guard.get_key()] += 1
            if (visited[guard.get_key()] == 100):
                return {}
        else:
            visited[guard.get_key()] = 1

    if (move_count == max_moves):
        return {}
    return visited

def find_obstructive_positions(visited: dict):
    position_count = 0
    for pos in visited:
        splitted = pos.split('_')
        row = int(splitted[0])
        col = int(splitted[1])
        c = _grid[row,col]
        if (c == '.'):
            grid = copy.deepcopy(_grid)
            grid[row,col] = '#'
            visited2 = move_guard(Guard(_guard.c, _guard.row, _guard.col), grid)
            # print(f"  check move guard on position ({row},{col}): {len(visited2)}")
            if (len(visited2) == 0):
                position_count += 1
    return position_count;

with open(file_path, 'r') as file:
    lines = file.readlines()

for line in lines:
    line = line.rstrip('\n')

_grid = create_grid(lines)
shape = _grid.shape
width = shape[0]
height = shape[1]

# print(grid)
print(f"{shape}: {width} x {height}")

_guard = find_guard()
# print(_guard)

visited = move_guard(Guard(_guard.c, _guard.row, _guard.col), copy.deepcopy(_grid))

print(f"Part 1: {len(visited)}")

count2 = find_obstructive_positions(visited)

print(f"Part 2: {count2}")
