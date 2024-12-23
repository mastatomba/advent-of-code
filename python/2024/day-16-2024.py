import sys

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day16/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day16/input_story2.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day16/input.txt'

sys.setrecursionlimit(10000)

def find_paths(grid, start, end):
    rows, cols = len(grid), len(grid[0])
    paths = []
    path = []

    def is_valid(x, y):
        # Check if the cell is within bounds and not blocked
        return 0 <= x < rows and 0 <= y < cols and (grid[x][y] == '.' or grid[x][y] == 'E')

    def dfs(x, y):
        # If we reach the end, record the path
        if (x, y) == end:
            paths.append(path.copy())
            return

        # Mark the cell as visited
        grid[x][y] = '#'  # Temporarily block the cell
        path.append((x, y))

        # Explore all four possible directions
        for dx, dy in [(0, 1), (1, 0), (0, -1), (-1, 0)]:
            nx, ny = x + dx, y + dy
            if is_valid(nx, ny):
                dfs(nx, ny)

        # Backtrack
        path.pop()
        grid[x][y] = '.'  # Unmark the cell

    # Start the DFS
    dfs(start[0], start[1])
    return paths

def find_all_paths_iterative(grid, start, end):
    rows, cols = len(grid), len(grid[0])
    paths = []
    stack = [(start, [start])]  # Stack holds (current position, path to position)

    def is_valid(x, y):
        return 0 <= x < rows and 0 <= y < cols and (grid[x][y] == '.' or grid[x][y] == 'E')

    while stack:
        (x, y), path = stack.pop()

        # If the end is reached, save the path
        if (x, y) == end:
            paths.append(path)
            continue

        # Temporarily block the cell to prevent revisiting
        grid[x][y] = '#'

        # Explore neighbors
        for dx, dy in [(0, 1), (1, 0), (0, -1), (-1, 0)]:
            nx, ny = x + dx, y + dy
            if is_valid(nx, ny):
                stack.append(((nx, ny), path + [(nx, ny)]))

        # Unblock the cell for other paths
        grid[x][y] = '.'

    return paths


with open(file_path) as f:
    data = f.read()

grid_list = [[position for position in element] for element in data.split("\n")]
grid = {(x, y): grid_list[y][x] for y in range(len(grid_list)) for x in range(len(grid_list[0]))}

def find_start():
    for y, row in enumerate(grid_list):
        for x, cell in enumerate(row):
            if (cell == 'S'):
                return (y,x)
    raise Exception("Start not found!")

def find_end():
    for y, row in enumerate(grid_list):
        for x, cell in enumerate(row):
            if (cell == 'E'):
                return (y,x)
    raise Exception("End not found!")

start = find_start()
# print(f"start={start}")
end = find_end()
# print(f"end={end}")

all_paths = find_all_paths_iterative(grid_list, start, end)

def calculate_path_score(path, end):
    score = 0
    path.append(end)
    cur = path[0]
    dir = '>'
    for i in range (1, len(path)):
        next = path[i]
        if (cur[1] > next[1]):
            if (dir == '>'):
                score += 1
            else:
                score += 1001
                dir = '>'
        elif (cur[1] < next[1]):
            if (dir == '<'):
                score += 1
            else:
                score += 1001
                dir = '<'
        elif (cur[0] > next[0]):
            if (dir == '^'):
                score += 1
            else:
                score += 1001
                dir = '^'
        elif (cur[0] < next[0]):
            if (dir == 'v'):
                score += 1
            else:
                score += 1001
                dir = 'v'
        cur = next
    return score;

min_score = -1
for path in all_paths:
    score = calculate_path_score(path, end)
    if (min_score == -1 or score < min_score):
        print(f"Path found with lower score '{score}': {path}")
        min_score = score

print(f"Part 1: {min_score}")
