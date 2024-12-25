# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day25/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day25/input.txt'

class Key:
    def __init__(self, line: str):
        self.lines = []
        self.lines.append(line)

    def append(self, line: str):
        self.lines.append(line)

    def get_pin_heights(self):
        heights = [0, 0, 0, 0, 0]
        for i in range(0, len(self.lines)-1):
            line = self.lines[i]
            for j in range(len(line)):
                if (line[j] == '#'):
                    heights[j] += 1
        return heights

class Lock:
    def __init__(self, line: str):
        self.lines = []
        self.lines.append(line)

    def append(self, line: str):
        self.lines.append(line)
    
    def get_pin_heights(self):
        heights = [0, 0, 0, 0, 0]
        for i in range(1, len(self.lines)):
            line = self.lines[i]
            for j in range(len(line)):
                if (line[j] == '#'):
                    heights[j] += 1
        return heights
    
    def does_key_fit(self, key: Key):
        lock_heights = self.get_pin_heights()
        key_heights = key.get_pin_heights()
        for i in range(5):
            if (lock_heights[i] + key_heights[i] > 5):
                return False
        return True

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

locks = []
lock = None
keys = []
key = None
is_lock = False
is_key = False
for line in lines:
    if (line != ''):
        if is_lock:
            lock.append(line)
        elif is_key:
            key.append(line)
        else:
            if (line == '#####'):
                lock = Lock(line)
                locks.append(lock)
                is_lock = True
            elif (line == '.....'):
                key = Key(line)
                keys.append(key)
                is_key = True
    else:
        lock = None
        key = None
        is_lock = False
        is_key = False

count1 = 0
for lock in locks:
    for key in keys:
        if lock.does_key_fit(key):
            count1 += 1

print(f"Part 1: {count1}")
