# file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day2/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day2/input.txt'

with open(file_path, 'r') as file:
    lines = file.readlines()

ROCK = 1
PAPER = 2
SCISSORS = 3

def map_shape(shape):
    if shape == 'A' or shape == 'X':
        return ROCK
    if shape == 'B' or shape == 'Y':
        return PAPER
    return SCISSORS

def score(shape1, shape2):
    # selected shape score
    if shape2 == ROCK:
        score1 = 1
    elif shape2 == PAPER:
        score1 = 2
    else:
        score1 = 3
    # outcome score
    score2 = 0
    if shape1 == shape2:
        score2 = 3
    elif shape2 > shape1:
        if (shape2 - shape1) == 1:
            score2 = 6
    else:
        if shape1 == SCISSORS and shape2 == ROCK:
            score2 = 6

    # print(f"Game score: [{shape1}] vs [{shape2}] = {score1} + {score2}")
    return score1 + score2

total = 0
for line in lines:
    # example: "A Y"
    game = line.rstrip('\n').split(" ")

    total += score(map_shape(game[0]), map_shape(game[1]))

print(f"Part 1: {total}")

def shape(shape1, outcome):
    shape = 0
    
    if outcome == 'X': # lose
        if shape1 == ROCK:
            shape = SCISSORS
        elif shape1 == PAPER:
            shape = ROCK
        else: # Scissors
            shape = PAPER

    elif outcome == 'Y': # draw
        shape = shape1

    else: # Z win
        if shape1 == ROCK:
            shape = PAPER
        elif shape1 == PAPER:
            shape = SCISSORS
        else: # Scissors
            shape = ROCK

    # print(f"Game shape: [{shape1}], [{outcome}] = {shape}")
    return shape

total = 0
for line in lines:
    # example: "A Y"
    game = line.rstrip('\n').split(" ")

    total += score(map_shape(game[0]), shape(map_shape(game[0]), game[1]))

print(f"Part 2: {total}")