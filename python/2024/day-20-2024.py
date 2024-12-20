import copy
import sys

file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day20/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day20/input.txt'

LOG_LEVEL = 4
def log(level: int, message: str):
    if level <= LOG_LEVEL:
        print(message)

class GridPos:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __str__(self):
        return f"GridPos(x={self.x}, y={self.y})"

    def get_key(self):
        return f"{self.x}_{self.y}"
    
    def equals(self, pos: 'GridPos') -> bool:
        return self.x == pos.x and self.y == pos.y

class Step:
    def __init__(self, dir: str, pos: GridPos):
        self.dir = dir
        self.pos = pos

    def __str__(self):
        return f"Step(dir={self.dir}, pos={self.pos})"

class Path:
    def __init__(self):
        self.steps = []
        self.visited = {}

    def __str__(self):
        return f"Path(steps={self.steps}, visited={self.visited})"

    def get_key(self):
        if (len(self.steps) == 0):
            return 'S'
        directions = ''
        for step in self.steps:
            if directions != '':
                directions += '-'
            directions += step.dir
        return directions
    
    def create_new_path(self, new_step: Step) -> 'Path':
        new_path = Path()
        for step in self.steps:
            new_path.steps.append(Step(step.dir, GridPos(step.pos.x, step.pos.y)))
        new_path.steps.append(Step(new_step.dir, GridPos(new_step.pos.x, new_step.pos.y)))
        for key in self.visited:
            new_path.visited[key] = 1
        new_path.visited[new_step.pos.get_key()] = 1
        return new_path

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

GRID_WIDTH, GRID_HEIGHT = (len(grid_list[0]), len(grid_list))
log(5, f"Grid dimensions: {GRID_WIDTH} x {GRID_HEIGHT}")
# Grid dimensions: 15 x 15
# Grid dimensions: 141 x 141

def is_within_bounds(pos: GridPos) -> bool:
    if (pos.x <= 0 or pos.x >= GRID_WIDTH-1):
        return False
    if (pos.y <= 0 or pos.y >= GRID_HEIGHT-1):
        return False
    return True

START_POS = None
END_POS = None
BLOCKAGE = '#'
BLOCKED_POSITIONS = {}
for y, row in enumerate(grid_list):
    for x, cell in enumerate(row):
        pos = GridPos(x,y)
        if (is_within_bounds(pos)):
            if (cell == 'S'):
                START_POS = GridPos(x,y)
            elif (cell == 'E'):
                END_POS = GridPos(x,y)
            elif (cell == BLOCKAGE):
                BLOCKED_POSITIONS[pos.get_key()] = pos

log(5, f"START_POS={START_POS}")
# start_pos=GridPos(x=1, y=3)
# start_pos=GridPos(x=89, y=83)

log(5, f"END_POS={END_POS}")
# end_pos=GridPos(x=5, y=7)
# end_pos=GridPos(x=73, y=71)

log(5, f"Number of blocked positions: {len(BLOCKED_POSITIONS)}")
# Number of blocked positions: 84
# Number of blocked positions: 9952

def find_single_path() -> Path:
    pos = START_POS
    path = Path()
    while not pos.equals(END_POS):
        # log(4, f"Checking pos {pos}")
        new_steps = [Step('u', GridPos(pos.x,pos.y-1)), Step('d', GridPos(pos.x,pos.y+1)), Step('l', GridPos(pos.x-1,pos.y)), Step('r', GridPos(pos.x+1,pos.y))]
        for new_step in new_steps:
            if (is_within_bounds(new_step.pos)):
                if (new_step.pos.get_key() not in BLOCKED_POSITIONS):
                    if (new_step.pos.get_key() not in path.visited):
                        path.steps.append(new_step)
                        path.visited[new_step.pos.get_key()] = 1
                        pos = GridPos(new_step.pos.x, new_step.pos.y)
                        break
    return path

def find_all_paths(pos: GridPos, path: Path, paths: dict, open_pos: GridPos, max_steps: int):
    # path_key = path.get_key()
    # log(5, f"{path_key} find_all_paths({pos}) called")
    if (max_steps == -1 or len(path.steps) <= max_steps):
        new_steps = [Step('u', GridPos(pos.x,pos.y-1)), Step('d', GridPos(pos.x,pos.y+1)), Step('l', GridPos(pos.x-1,pos.y)), Step('r', GridPos(pos.x+1,pos.y))]
        for new_step in new_steps:
            if (is_within_bounds(new_step.pos)):
                if (new_step.pos.get_key() not in BLOCKED_POSITIONS or open_pos.equals(new_step.pos)):
                    if (new_step.pos.get_key() not in path.visited):
                        new_path = path.create_new_path(new_step)

                        if (END_POS.equals(new_step.pos)):
                            # log(4, f"{path_key}    found end position! ({new_step.pos})")
                            paths[new_path.get_key()] = new_path
                        else:
                            find_all_paths(new_step.pos, new_path, paths, open_pos, max_steps)

# sys.setrecursionlimit(2000)

path = find_single_path()
number_of_picoseconds = len(path.steps)
log(4, f"Number of picoseconds: {number_of_picoseconds}")

PICOSECONDS_SAVED = 12
count1 = 0
for key in BLOCKED_POSITIONS:
    open_pos = BLOCKED_POSITIONS[key]
    paths = {}
    find_all_paths(START_POS, Path(), paths, open_pos, (number_of_picoseconds - PICOSECONDS_SAVED))
    for key in paths:
        path = paths[key]
        step_count = len(path.steps)
        if (step_count > 0):
            saved = number_of_picoseconds - step_count
            if (saved >= PICOSECONDS_SAVED):
                log(4, f"  Number of picoseconds: {step_count}, saved {saved}")
                count1 += 1

print(f"Part 1: {count1}")
