file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day10/input_story.txt'
# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day10/input.txt'

with open(file_path) as f:
    data = f.read()

grid_list = [[position for position in element] for element in data.split("\n")]

grid = {(x, y): grid_list[y][x] for y in range(len(grid_list)) for x in range(len(grid_list[0]))}

def calculate_score(start_x: int, start_y: int, grid=grid):
    end_positions = {}
    check_next_step(x, y - 1, 1, end_positions, grid)
    check_next_step(x, y + 1, 1, end_positions, grid)
    check_next_step(x - 1, y, 1, end_positions, grid)
    check_next_step(x + 1, y, 1, end_positions, grid)
    return len(end_positions)

def calculate_rating(start_x: int, start_y: int, grid=grid):
    rating = 0
    end_positions = {}
    rating += check_next_step(x, y - 1, 1, end_positions, grid)
    rating += check_next_step(x, y + 1, 1, end_positions, grid)
    rating += check_next_step(x - 1, y, 1, end_positions, grid)
    rating += check_next_step(x + 1, y, 1, end_positions, grid)
    return rating

def check_next_step(x: int, y: int, next: int, end_positions: dict, grid=grid):
    if (x,y) not in grid:
        return 0
    
    if int(grid[(x, y)]) != next:
        return 0

    if (next == 9):
        if (x,y) not in end_positions:
            end_positions[(x,y)] = 1
        else:
            end_positions[(x,y)] += 1

        return 1
    else:
        rating = 0
        rating += check_next_step(x, y - 1, next + 1, end_positions, grid)
        rating += check_next_step(x, y + 1, next + 1, end_positions, grid)
        rating += check_next_step(x - 1, y, next + 1, end_positions, grid)
        rating += check_next_step(x + 1, y, next + 1, end_positions, grid)
        return rating

# find trailheads
score_sum = rating_sum = 0
for y, row in enumerate(grid_list):
    for x, position in enumerate(row):
        if position == '0':
            # print(f"Found trailhead ({x},{y})")
            score = calculate_score(x, y)
            score_sum += score
            # print(f"  Score: {score}")
            rating = calculate_rating(x, y)
            rating_sum += rating
            # print(f"  Rating: {rating}")

print(f"Part 1: {score_sum}")

print(f"Part 2: {rating_sum}")