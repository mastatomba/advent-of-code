import numpy as np

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day4/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day4/input.txt'

def create_grid(lines: list):
    grid = []
    for line in lines:
        line = line.rstrip('\n')
        grid.append(list(line))
    return np.array(grid)

def number_of_occurences(text: str, substring: str):
    count = text.count(substring)
    # if (count > 0):
    #     print(f"The substring '{substring}' occurs {count} times in '{text}'.")
    return count

def check_cell(grid, value, row, col):
    if (row < 0 or row > grid.shape[1] - 1):
        return False
    if (col < 0 or col > grid.shape[0] - 1):
        return False
    return grid[row,col] == value

with open(file_path, 'r') as file:
    lines = file.readlines()

count1 = count2 = 0

grid = create_grid(lines)
shape = grid.shape
width = shape[0]
height = shape[1]

# print(grid)
print(f"{shape}: {width} x {height}")

# Check horizontal
for row in range(height):
    text = ''.join(grid[row, :])
    count1 += number_of_occurences(text, 'XMAS')
    count1 += number_of_occurences(text, 'SAMX')

# Check vertical
for col in range(width):
    text = ''.join(grid[:, col])
    count1 += number_of_occurences(text, 'XMAS')
    count1 += number_of_occurences(text, 'SAMX')

# Check diagonal
for row in range(height):
    for col in range(width):
        if (grid[row,col] == 'X'):
            if (check_cell(grid, 'M', row - 1, col - 1)):
                if (check_cell(grid, 'A', row - 2, col - 2)):
                    if (check_cell(grid, 'S', row - 3, col - 3)):
                        # print(f"Found diagonal match starting from ({row},{col}) towards ({row-3},{col-3})")
                        count1 += 1
            if (check_cell(grid, 'M', row - 1, col + 1)):
                if (check_cell(grid, 'A', row - 2, col + 2)):
                    if (check_cell(grid, 'S', row - 3, col + 3)):
                        # print(f"Found diagonal match starting from ({row},{col}) towards ({row-3},{col+3})")
                        count1 += 1
            if (check_cell(grid, 'M', row + 1, col - 1)):
                if (check_cell(grid, 'A', row + 2, col - 2)):
                    if (check_cell(grid, 'S', row + 3, col - 3)):
                        # print(f"Found diagonal match starting from ({row},{col}) towards ({row+3},{col-3})")
                        count1 += 1
            if (check_cell(grid, 'M', row + 1, col + 1)):
                if (check_cell(grid, 'A', row + 2, col + 2)):
                    if (check_cell(grid, 'S', row + 3, col + 3)):
                        # print(f"Found diagonal match starting from ({row},{col}) towards ({row+3},{col+3})")
                        count1 += 1

# Check X-MAS
# M.M   M.S   S.M   S.S
# .A.   .A.   .A.   .A.
# S.S   M.S   S.M   M.M
for row in range(height):
    for col in range(width):
        if (grid[row,col] == 'A'):
            if (check_cell(grid, 'M', row - 1, col - 1)):
                if (check_cell(grid, 'M', row - 1, col + 1)):
                    if (check_cell(grid, 'S', row + 1, col - 1) and check_cell(grid, 'S', row + 1, col + 1)):
                            count2 += 1
                elif (check_cell(grid, 'S', row - 1, col + 1)):
                    if (check_cell(grid, 'M', row + 1, col - 1) and check_cell(grid, 'S', row + 1, col + 1)):
                            count2 += 1
            elif (check_cell(grid, 'S', row - 1, col - 1)):
                if (check_cell(grid, 'M', row - 1, col + 1)):
                    if (check_cell(grid, 'S', row + 1, col - 1) and check_cell(grid, 'M', row + 1, col + 1)):
                            count2 += 1
                elif (check_cell(grid, 'S', row - 1, col + 1)):
                    if (check_cell(grid, 'M', row + 1, col - 1) and check_cell(grid, 'M', row + 1, col + 1)):
                            count2 += 1

print(f"Part 1: {count1}")

print(f"Part 2: {count2}")
