from collections import deque

file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day20/input_story.txt'
PICOSECONDS_SAVED = 50

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day20/input.txt'
# PICOSECONDS_SAVED = 100

LOG_LEVEL = 3
def log(level: int, message: str):
    if level <= LOG_LEVEL:
        print(message)

with open(file_path) as f:
    data = f.read()

grid_list = [[position for position in element] for element in data.split("\n")]

grid = {(x, y): grid_list[y][x] for y in range(len(grid_list)) for x in range(len(grid_list[0]))}

log(5, grid_list)
# [
#   ['#', '#', '#', '#', '#'],
#   ['#', 'S', '.', '.', '#'],
#   ['#', '#', '#', '.', '#'],
#   ['#', 'E', '.', '.', '#'],
#   ['#', '#', '#', '#', '#']
# ]

log(5, grid)
# {
#   (0, 0): '#', (1, 0): '#', (2, 0): '#', (3, 0): '#', (4, 0): '#', 
#   (0, 1): '#', (1, 1): 'S', (2, 1): '.', (3, 1): '.', (4, 1): '#', 
#   (0, 2): '#', (1, 2): '#', (2, 2): '#', (3, 2): '.', (4, 2): '#', 
#   (0, 3): '#', (1, 3): 'E', (2, 3): '.', (3, 3): '.', (4, 3): '#', 
#   (0, 4): '#', (1, 4): '#', (2, 4): '#', (3, 4): '#', (4, 4): '#'
# }

def visit_grid(grid):
    # m, n = len(grid), len(grid[0])
    rows, cols = len(grid), len(grid[0])
    for row in range(rows):
        for col in range(cols):
            if grid[row][col] == 'S':
                start = (row, col)
            elif grid[row][col] == 'E':
                end = (row, col)

    queue = deque([(*start, 0, dict())])
    while queue:
        row, col, nr_of_picoseconds, visited = queue.popleft()
        visited[(row, col)] = nr_of_picoseconds

        if (row, col) == end:
            break

        for next_row, next_col in [(row+1, col), (row-1, col), (row, col-1), (row, col+1)]:
            if (next_row in range(rows) and
                next_col in range(cols) and
                (next_row, next_col) not in visited and
                grid[next_row][next_col] != '#'
            ):
                queue.append((next_row, next_col, nr_of_picoseconds + 1, visited.copy()))
    
    return visited

def get_number_of_cheats_with_2_seconds_window(visited, picoseconds_saved):
    cheats = 0
    for (row, col), nr_of_picoseconds in visited.items():
        for next_row, next_col in [(row+2, col), (row-2, col), (row, col-2), (row, col+2)]:
            if visited.get((next_row, next_col), 0) - nr_of_picoseconds >= (picoseconds_saved + 2):
                log(4, f"({next_row},{next_col}): {visited.get((next_row, next_col), 0)} - {nr_of_picoseconds} >= {picoseconds_saved + 2}")
                cheats += 1
                
    return cheats

def get_number_of_cheats_with_20_seconds_window(visited, picoseconds_saved):
    cheats = 0
    path = sorted(visited, key=visited.get)
    for t2 in range(picoseconds_saved, len(path)):
        for t1 in range(t2 - picoseconds_saved):
            x1, y1 = path[t1]
            x2, y2 = path[t2]
            distance = abs(x1-x2) + abs(y1-y2)
            if distance <= 20 and t2 - t1 - distance >= picoseconds_saved:
                log(4, f"{distance} <= 20 and {t2} - {t1} - {distance} >= {picoseconds_saved}")
                cheats += 1

    return cheats

visited = visit_grid(grid_list)

print(f"Part 1: {get_number_of_cheats_with_2_seconds_window(visited, PICOSECONDS_SAVED)}")

print(f"Part 2: {get_number_of_cheats_with_20_seconds_window(visited, PICOSECONDS_SAVED)}")
