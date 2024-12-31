from collections import deque
import re

file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day21/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day21/input.txt'

ACTIVATE = 'A'
BLOCKAGE = '#'

NUMPAD_GRID = [
    ['7', '8', '9'],
    ['4', '5', '6'],
    ['1', '2', '3'],
    [BLOCKAGE, '0', ACTIVATE]
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
    ACTIVATE: (3,2),
}

DIRPAD_GRID = [
    [BLOCKAGE, '^', ACTIVATE],
    ['<', 'v', '>']
]
DIRPAD_KEYS = {
    '^': (0,1),
    ACTIVATE: (0,2),
    '<': (1,0),
    'v': (1,1),
    '>': (1,2),
}

def find_all_shortest_paths(grid, start, end):
    """
    Find all shortest paths in a grid from start to end using BFS.

    Parameters:
        grid (list[list[int]]): 2D grid where 0 represents passable cells and 1 represents obstacles.
        start (tuple): Starting coordinates (x, y).
        end (tuple): Target coordinates (x, y).

    Returns:
        list: A list of all shortest paths, each path is a list of coordinates [(x1, y1), (x2, y2), ...].
    """
    rows, cols = len(grid), len(grid[0])
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]  # Up, Down, Left, Right
    
    # BFS setup
    queue = deque([(start, [start])])  # (current position, path so far)
    visited = {start: 0}  # Tracks the shortest distance to each cell
    shortest_distance = float('inf')  # Shortest distance to the end
    all_paths = []  # To store all shortest paths

    while queue:
        (current_x, current_y), path = queue.popleft()

        # Stop exploring if this path is longer than the shortest known distance
        if len(path) > shortest_distance:
            continue

        # If the destination is reached
        if (current_x, current_y) == end:
            if len(path) < shortest_distance:
                # Found a shorter path; reset the result list
                shortest_distance = len(path)
                all_paths = [path]
            elif len(path) == shortest_distance:
                # Found another path of the same shortest length
                all_paths.append(path)
            continue

        # Explore neighbors
        for dx, dy in directions:
            neighbor_x, neighbor_y = current_x + dx, current_y + dy

            # Check if the neighbor is within bounds and passable
            if (0 <= neighbor_x < rows and
                0 <= neighbor_y < cols and
                grid[neighbor_x][neighbor_y] != BLOCKAGE):
                
                # Determine the new distance to this neighbor
                new_distance = len(path) + 1

                # Only add to the queue if:
                # 1. It's unvisited, or
                # 2. It's visited but we found an equally short path
                if (neighbor_x, neighbor_y) not in visited or new_distance <= visited[(neighbor_x, neighbor_y)]:
                    visited[(neighbor_x, neighbor_y)] = new_distance
                    queue.append(((neighbor_x, neighbor_y), path + [(neighbor_x, neighbor_y)]))

    return all_paths

def path_to_directions(path: list):
    res = ''
    y1, x1 = path[0]
    for i in range(1, len(path)):
        y2, x2 = path[i]
        if (y2 > y1):
            res += 'v'
        elif (y2 < y1):
            res += '^'
        elif (x2 > x1):
            res += '>'
        else:
            res += '<'
        x1 = x2
        y1 = y2
    return res

def transform_numpad_sequence(sequence, index=0, from_char=ACTIVATE, current_sequence=""):
    """
    Recursively generates all possible sequences from the given sequence.

    Parameters:
        sequence (str): The input sequence to transform.
        index (int): The current index being processed.
        current_sequence (str): The sequence built so far.

    Returns:
        list: A list of all possible transformed sequences.
    """
    # Base case: If we've processed the entire sequence, return the current result
    if index == len(sequence):
        return [current_sequence]

    # Get the current character to process
    char = sequence[index]
    possibilities = get_numpad_transformations(from_char, char)
    
    # Store all results in a list
    results = []
    
    # Recursively transform for each possibility of the current character
    for possibility in possibilities:
        new_sequence = current_sequence + possibility
        results.extend(transform_numpad_sequence(sequence, index + 1, char, new_sequence))
    
    return results

def transform_dirpad_sequence(sequence, index=0, from_char=ACTIVATE, current_sequence=""):
    # Base case: If we've processed the entire sequence, return the current result
    if index == len(sequence):
        return [current_sequence]

    # Get the current character to process
    char = sequence[index]
    possibilities = get_dirpad_transformations(from_char, char)
    
    # Store all results in a list
    results = []
    
    # Recursively transform for each possibility of the current character
    for possibility in possibilities:
        new_sequence = current_sequence + possibility
        results.extend(transform_dirpad_sequence(sequence, index + 1, char, new_sequence))
    
    return results

def get_numpad_transformations(from_char, to_char):
    transformations = []
    shortest_paths = find_all_shortest_paths(NUMPAD_GRID, NUMPAD_KEYS[from_char], NUMPAD_KEYS[to_char])
    for path in shortest_paths:
        transformations.append(path_to_directions(path) + ACTIVATE)
    return transformations

DIRPAD_TRANSFORMATIONS = {}

def get_dirpad_transformations(from_char, to_char):
    key = from_char + '_' + to_char
    if key in DIRPAD_TRANSFORMATIONS:
        return DIRPAD_TRANSFORMATIONS[key]
    
    transformations = []
    shortest_paths = find_all_shortest_paths(DIRPAD_GRID, DIRPAD_KEYS[from_char], DIRPAD_KEYS[to_char])
    for path in shortest_paths:
        transformations.append(path_to_directions(path) + ACTIVATE)
    DIRPAD_TRANSFORMATIONS[key] = transformations
    return transformations

def keep_shortest_sequences(sequences):
    if not sequences:
        return []  # Return empty list if the input list is empty

    # Find the length of the shortest sequence
    min_length = min(len(seq) for seq in sequences)

    # Filter sequences with the minimum length
    shortest_sequences = [seq for seq in sequences if len(seq) == min_length]

    return shortest_sequences

def find_shortest_sequence(input_sequence: str, number_of_dirpads: int):
    all_sequences1 = transform_numpad_sequence(input_sequence)
    # print(f"{input_sequence}: Number of numpad sequences found: {len(all_sequences1)}")

    for i in range(number_of_dirpads):
        # print(f"  {i}: Number of sequences: {len(all_sequences1)}")
        all_sequences2 = []
        for sequence in all_sequences1:
            sequences = transform_dirpad_sequence(sequence)
            # print(f"  {i}: {sequence}: Number of dirpad sequences found: {len(sequences)}")
            all_sequences2 += sequences
        all_sequences1 = keep_shortest_sequences(all_sequences2)

    return all_sequences1[0]

def get_a_segments(sequence: str):
    # Split the string based on 'A' while keeping 'A' in the results
    parts = re.split(r'(A)', sequence)

    # Join the 'A' back to its preceding part, skipping empty splits
    segments = [parts[i] + parts[i + 1] for i in range(0, len(parts) - 1, 2)]

    return segments

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

count1 = 0
count2 = 0
for line in lines:
    match = re.search(r'\d+', line)
    shortest_sequence1 = find_shortest_sequence(line, 2)
    count1 += int(match.group()) * len(shortest_sequence1)

print(f"Part 1: {count1}")
