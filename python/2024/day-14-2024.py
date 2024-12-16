import re

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day14/input_story.txt'
# WIDTH = 11
# HEIGHT = 7
# IGNORE_X = 5
# IGNORE_Y = 3

file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day14/input.txt'
WIDTH = 101
HEIGHT = 103
IGNORE_X = 50
IGNORE_Y = 51

class Position:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __str__(self):
        return f"Position(x={self.x}, y={self.y})"

    def get_key(self):
        return f"{self.x}_{self.y}"

class Velocity:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __str__(self):
        return f"Velocity(x={self.x}, y={self.y})"

    def get_key(self):
        return f"{self.x}_{self.y}"

class Robot:
    def __init__(self, position: Position, velocity: Velocity):
        self.position = position
        self.velocity = velocity
        self.initial_position = Position(position.x,position.y)

    def __str__(self):
        return f"Robot({self.get_key()} np={self.position.x},{self.position.y})"

    def get_key(self):
        return f"p={self.initial_position.x},{self.initial_position.y} v={self.velocity.x},{self.velocity.y}"

    def move(self, seconds: int):
        if (self.velocity.x > WIDTH or self.velocity.y > HEIGHT):
            print(f"Velocity higher than grid bounds! {self.velocity}")
        for s in range(seconds):
            x = self.position.x + self.velocity.x
            y = self.position.y + self.velocity.y
            if (x < 0):
                x += WIDTH
            elif (x >= WIDTH):
                x -= WIDTH
            if (y < 0):
                y += HEIGHT
            elif (y >= HEIGHT):
                y -= HEIGHT
            self.position = Position(x,y)
    
    def get_quadrant(self):
        if self.position.x < IGNORE_X:
            if self.position.y < IGNORE_Y:
                return 1
            elif self.position.y > IGNORE_Y:
                return 3
        elif self.position.x > IGNORE_X:
            if self.position.y < IGNORE_Y:
                return 2
            elif self.position.y > IGNORE_Y:
                return 4
        return 0

def extract_numbers(line: str):
    # Extract all numbers
    numbers = re.findall(r'-?\d+', line)
    # Convert to integers (optional, if you need numeric values)
    numbers = list(map(int, numbers))
    return numbers

def create_robot(line: str):
    # p=0,4 v=3,-3
    splitted = line.split(' ')
    p = extract_numbers(splitted[0])
    v = extract_numbers(splitted[1])
    return Robot(Position(p[0],p[1]), Velocity(v[0],v[1]))

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

robots = []
quadrants = {0:0,1:0,2:0,3:0,4:0}
for line in lines:
    robot = create_robot(line)
    robot.move(100)
    quadrants[robot.get_quadrant()] += 1
    print(robot)
    robots.append(robot)

print(quadrants)
sum = quadrants[1] * quadrants[2] * quadrants[3] * quadrants[4]

print(f"Part 1: {sum}")
