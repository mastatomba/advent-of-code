import numpy as np

# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day8/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day8/input.txt'

class GridPos:
    def __init__(self, row: int, col: int):
        self.row = row
        self.col = col

    def __str__(self):
        return f"GridPos(row={self.row}, col={self.col})"

    def get_key(self):
        return f"{self.row}_{self.col}"

def create_grid(lines: list):
    grid = []
    for line in lines:
        line = line.rstrip('\n')
        grid.append(list(line))
    return np.array(grid)

def is_in_grid(row: int, col: int):
    if (row < 0 or row >= height):
        return False
    if (col < 0 or col >= width):
        return False
    return True

def create_antinodes(antennas: dict, is_unlimited: bool):
    antinodes = {}
    for antenna in antennas:
        positions = antennas[antenna]
        if (len(positions) > 1):
            for i in range(0, len(positions) -1):
                for j in range(i + 1, len(positions)):
                    antenna1 = positions[i]
                    antenna2 = positions[j]
                    # print(f"{antenna}: Generate antinodes for ({antenna1}) and ({antenna2})")
                    for pos in create_upward_antinode_positions(antenna1, antenna2, is_unlimited):
                        # print(f"  upward {pos}")
                        key = pos.get_key()
                        if (key not in antinodes):
                            antinodes[key] = 1
                        else:
                            antinodes[key] += 1
                    for pos in create_downward_antinode_positions(antenna1, antenna2, is_unlimited):
                        # print(f"  downward {pos}")
                        key = pos.get_key()
                        if (key not in antinodes):
                            antinodes[key] = 1
                        else:
                            antinodes[key] += 1
        else:
            print(f"{antenna}: Just 1 antenna position found, skipping..")
    return antinodes

def create_upward_antinode_positions(antenna1: GridPos, antenna2: GridPos, is_unlimited: bool):
    positions = []
    row = antenna1.row - (antenna2.row - antenna1.row)
    col = antenna1.col - (antenna2.col - antenna1.col)
    while (is_in_grid(row,col)):
        positions.append(GridPos(row,col))
        if (not is_unlimited):
            return positions
        row -= (antenna2.row - antenna1.row)
        col -= (antenna2.col - antenna1.col)
    return positions

def create_downward_antinode_positions(antenna1: GridPos, antenna2: GridPos, is_unlimited: bool):
    positions = []
    row = antenna2.row + (antenna2.row - antenna1.row)
    col = antenna2.col + (antenna2.col - antenna1.col)
    while (is_in_grid(row,col)):
        positions.append(GridPos(row,col))
        if (not is_unlimited):
            return positions
        row += (antenna2.row - antenna1.row)
        col += (antenna2.col - antenna1.col)
    return positions

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

grid = create_grid(lines)
shape = grid.shape
width = shape[0]
height = shape[1]

# print(grid)
print(f"{shape}: {width} x {height}")

antennas = {}
for row in range(height):
    for col in range(width):
        c = grid[row,col]
        if (c != '.'):
            if (c not in antennas):
                antennas[c] = []
            antennas[c].append(GridPos(row,col))

antinodes = create_antinodes(antennas, False)

print(f"Part 1: {len(antinodes)}")

antinodes = create_antinodes(antennas, True)
count = len(antinodes)
for antenna in antennas:
    for pos in antennas[antenna]:
        if (pos.get_key() not in antinodes):
            count += 1
print(f"Part 2: {count}")
