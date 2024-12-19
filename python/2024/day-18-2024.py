import copy

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day18/input_story.txt'
# MAX = 6
# NR_OF_BYTES = 12

file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day18/input.txt'
MAX = 10
NR_OF_BYTES = 0

LOG_LEVEL = 2
def log(level: int, message: str):
    if level <= LOG_LEVEL:
        print(message)

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

class GridPos:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __str__(self):
        return f"GridPos(x={self.x}, y={self.y})"

    def get_key(self):
        return f"{self.x}_{self.y}"

class Step:
    def __init__(self, dir: str, pos: GridPos):
        self.dir = dir
        self.pos = pos

    def is_end_step(self):
        return self.pos.x == MAX and self.pos.y == MAX
    
    def is_corrupted(self):
        return self.pos.get_key() in corrupted

    def is_outside_grid(self):
        if (self.pos.x < 0 or self.pos.x > MAX):
            return True
        if (self.pos.y < 0 or self.pos.y > MAX):
            return True
        return False

    def __str__(self):
        return f"Step(dir={self.dir}, pos={self.pos})"

class Path:
    def __init__(self, steps: list):
        self.steps = steps
        self.visited = {}

    def __str__(self):
        return f"Path(steps={self.steps}, visited={self.visited})"

    def get_key(self):
        directions = ''
        for step in self.steps:
            if directions != '':
                directions += '-'
            directions += step.dir
        return directions
 
corrupted = {}
for i in range(NR_OF_BYTES):
    splitted = lines[i].split(',')
    pos = GridPos(int(splitted[0]), int(splitted[1]))
    corrupted[pos.get_key()] = pos

paths = {}
min_steps = -1

def get_new_steps(pos: GridPos, path: Path):
    path_key = path.get_key()
    log(4, f"{path_key} get_new_steps({pos}) called")
    steps = [Step('u', GridPos(pos.x,pos.y-1)), Step('d', GridPos(pos.x,pos.y+1)), Step('l', GridPos(pos.x-1,pos.y)), Step('r', GridPos(pos.x+1,pos.y))]
    new_steps = []
    for step in steps:
        log(4, f"{path_key}   checking direction {step.dir}, position ({step.pos})")
        if (not step.is_corrupted()):
            if (not step.is_outside_grid()):
                if step.pos.get_key() not in path.visited:
                    new_steps.append(step)
                else:
                    log(4, f"{path_key}     skipping: position is outside grid..")
            else:
                log(4, f"{path_key}     skipping: position is outside grid..")
        else:
            log(4, f"{path_key}     skipping: position is corruped..")
    return new_steps

def find_all_paths(pos: GridPos, path: Path):
    path_key = path.get_key()
    log(4, f"{path_key} find_all_paths({pos}) called")
    if min_steps != -1 and (len(path.steps) -1) > min_steps:
        log(2, f"{path_key} longer than minimum steps")
        return
    new_steps = get_new_steps(pos, path)
    for new_step in new_steps:
        new_path = copy.deepcopy(path)
        new_path.steps.append(new_step)
        new_path.visited[new_step.pos.get_key()] = 1

        if (new_step.is_end_step()):
            log(2, f"{path_key}    found end position! ({new_step.pos})")
            paths[new_path.get_key()] = new_path
            nr_of_steps = len(new_path.steps) - 1
            if min_steps == -1 or min_steps > nr_of_steps:
                min_steps = nr_of_steps
        else:
            find_all_paths(new_step.pos, new_path)

start_pos = GridPos(0,0)
find_all_paths(start_pos, Path([Step('S', start_pos)]))

# log(3, len(paths))
# min_steps = -1
# for key in paths:
#     steps = len(paths[key].steps) - 1
#     if min_steps == -1 or min_steps > steps:
#         log(3, f"Found smaller path {key}: {steps}")
#         min_steps = steps

print(f"Part 1: {min_steps}")
