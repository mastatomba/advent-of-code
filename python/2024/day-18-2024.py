from collections import deque

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day18/input_story.txt'
# MAX = 7
# NR_OF_BYTES = 12

file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day18/input.txt'
MAX = 71
NR_OF_BYTES = 1024

SAFE = '.'
CORRUPTED = '#'

START = (0,0)
END = (MAX-1,MAX-1)

LOG_LEVEL = 3
def log(level: int, message: str):
    if level <= LOG_LEVEL:
        print(message)

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

def print_grid(grid):
    print('GRID STATE:')
    for row in range(MAX):
        s = ''
        for col in range(MAX):
            s += grid[row][col]
        print(s)

def shortest_path(grid, start, end):
    rows, cols = len(grid), len(grid[0])
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]
    visited = set()
    visited.add(start)
    queue = deque([(start, [start])])

    while queue:
        (current_x, current_y), path = queue.popleft()

        if (current_x, current_y) == end:
            return path

        for dx, dy in directions:
            neighbor_x, neighbor_y = current_x + dx, current_y + dy

            if (0 <= neighbor_x < rows and
                0 <= neighbor_y < cols and
                grid[neighbor_x][neighbor_y] == SAFE and 
                (neighbor_x, neighbor_y) not in visited):

                visited.add((neighbor_x, neighbor_y))
                queue.append(((neighbor_x, neighbor_y), path + [(neighbor_x, neighbor_y)]))

    return []

def visualize_path(grid, path):
    visual_grid = [row[:] for row in grid]  # Create a copy of the grid

    for x, y in path:
        visual_grid[x][y] = 'O'

    # Replace integers with more readable symbols
    return [['#' if cell == '#' else 'O' if cell == 'O' else '.' for cell in row] for row in visual_grid]

def find_shortest_path_after_x_bytes(nr_of_bytes: int):
    grid = [[SAFE for _ in range(MAX)] for _ in range(MAX)]

    # print_grid(grid)

    for i in range(nr_of_bytes):
        line = lines[i]
        splitted = line.split(',')
        grid[int(splitted[1])][int(splitted[0])] = CORRUPTED

    # print_grid(grid)

    path = shortest_path(grid, START, END)
    return grid, path

grid, path = find_shortest_path_after_x_bytes(NR_OF_BYTES)
log(4, f"Shortest path: {path}")

print(f"Part 1: {len(path)-1}")

low, high = NR_OF_BYTES, len(lines)
while low < high:
    nr_of_bytes = (low + high) // 2
    grid, path = find_shortest_path_after_x_bytes(nr_of_bytes)
    if (len(path) > 0): # Operation succeeds
        low = nr_of_bytes + 1
    else:  # Operation fails
        high = nr_of_bytes

print(f"Part 2: {lines[low-1]}")

# print last successful path
# grid, path = find_shortest_path_after_x_bytes(low-1)
# visualized_grid = visualize_path(grid, path)
# print("Visualized grid:")
# for row in visualized_grid:
#     print(" ".join(row))
