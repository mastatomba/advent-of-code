from collections import deque

file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day21/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day21/input.txt'

BLOCKAGE = '#'

def shortest_path(grid, start, end):
    """
    Find the shortest path from start to end in a grid.
    
    Parameters:
        grid (list[list[int]]): 2D grid where 0 represents passable cells and 1 represents obstacles.
        start (tuple): Starting coordinates (x, y).
        end (tuple): Target coordinates (x, y).
    
    Returns:
        list: The shortest path as a list of coordinates [(x1, y1), (x2, y2), ...].
        If no path exists, returns an empty list.
    """
    rows, cols = len(grid), len(grid[0])
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]  # Up, Down, Left, Right
    visited = set()
    queue = deque([(start, [start])])  # Queue stores (current position, path to reach it)

    while queue:
        (current_x, current_y), path = queue.popleft()

        # print(f"  current position ({current_x},{current_y})")
        # If we've reached the end, return the path
        if (current_x, current_y) == end:
            return path

        # Mark current cell as visited
        visited.add((current_x, current_y))

        # Explore neighbors
        for dx, dy in directions:
            neighbor_x, neighbor_y = current_x + dx, current_y + dy
            # Check if the neighbor is within bounds and not visited or blocked
            # print(f"  checking position ({neighbor_x},{neighbor_y}) ({cols}x{rows})")
            if (0 <= neighbor_x < rows and
                0 <= neighbor_y < cols and
                (neighbor_x, neighbor_y) not in visited and
                grid[neighbor_x][neighbor_y] != BLOCKAGE):

                queue.append(((neighbor_x, neighbor_y), path + [(neighbor_x, neighbor_y)]))

    # If we exhaust the queue without finding the end, return an empty list
    return []

NUMPAD_GRID = [
    ['7', '8', '9'],
    ['4', '5', '6'],
    ['1', '2', '3'],
    [BLOCKAGE, '0', 'A']
]
NUMPAD_KEYS = {
    '7': (0,0),
    '8': (0,1),
    '9': (0,2),
    '4': (1,0),
    '5': (1,1),
    '6': (1,2),
    '1': (2,0),
    '2': (2,1),
    '3': (2,2),
    '0': (3,1),
    'A': (3,2),    
}
DIRPAD_GRID = [
    [BLOCKAGE, '^', 'A'],
    ['<', 'v', '>']
]
DIRPAD_KEYS = {
    '^': (0,1),
    'A': (0,2),
    '<': (1,0),
    'v': (1,1),
    '>': (1,2),
}

def path_to_directions(path: list):
    res = ''
    pos1 = path[0]
    for i in range(1, len(path)):
        pos2 = path[i]
        if (pos2[0] > pos1[0]):
            res += 'v'
        elif (pos2[0] < pos1[0]):
            res += '^'
        elif (pos2[1] > pos1[1]):
            res += '>'
        else:
            res += '<'
        pos1 = pos2
    return res

def handle_numpad_input(input: str):
    res = ''
    pos = NUMPAD_KEYS['A']
    for c in input:
        new_pos = NUMPAD_KEYS[c]
        path = shortest_path(NUMPAD_GRID, pos, new_pos)

        # print(f"Shortest path from {pos} to {new_pos}: {path} {path_to_directions(path)}")

        res += path_to_directions(path) + 'A'
        pos = new_pos
    return res

def handle_dirpad_input1(input: str):
    res = ''
    pos = DIRPAD_KEYS['A']
    for c in input:
        new_pos = DIRPAD_KEYS[c]
        path = shortest_path(DIRPAD_GRID, pos, new_pos)

        # print(f"Shortest path from {pos} to {new_pos}: {path} {path_to_directions(path)}")

        res += path_to_directions(path) + 'A'
        pos = new_pos
    return res

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

for line in lines:
    print(f"{line}")
    input1 = handle_numpad_input(line)
    print(f"  {input1}")
    input2 = handle_dirpad_input1(input1)
    print(f"    {input2}")
    input3 = handle_dirpad_input1(input2)
    print(f"    {input3}")

