import numpy as np

# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2022/day8/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2022/day8/input.txt'

def number_line_to_int_list(line):
    numbers = []
    for char in line:
        numbers.append(int(char))
    return numbers

def is_visible_row(row,column,start,stop,step):
    val = grid[row,column]
    # print(f"  is_visible_row({(row)},{column}): {val}")
    for c in range(start,stop,step):
        val2 = grid[row,c]
        # print(f"    ({(row)},{c}): {val2}")
        if (val2 >= val):
            return False
    return True

def is_visible_column(row,column,start,stop,step):
    val = grid[row,column]
    # print(f"  is_visible_column({(row)},{column}): {val}")
    for r in range(start,stop,step):
        val2 = grid[r,column]
        # print(f"    ({(r)},{column}): {val2}")
        if (val2 >= val):
            return False
    return True

def is_visible_left(row,column):
    return is_visible_row(row,column,column-1,-1,-1)

def is_visible_right(row,column):
    return is_visible_row(row,column,column+1,width,1)

def is_visible_up(row,column):
    return is_visible_column(row,column,row-1,-1,-1)

def is_visible_down(row,column):
    return is_visible_column(row,column,row+1,height,1)

def view_distance_row(row,column,start,stop,step):
    val = grid[row,column]
    # print(f"  view_distance_row({(row)},{column}): {val}")
    dis = 0
    for c in range(start,stop,step):
        val2 = grid[row,c]
        # print(f"    ({(row)},{c}): {val2}")
        dis += 1
        if (val2 >= val):
            break
    # print(f"    dis = {dis}")
    return dis

def view_distance_column(row,column,start,stop,step):
    val = grid[row,column]
    # print(f"  view_distance_column({(row)},{column}): {val}")
    dis = 0
    for r in range(start,stop,step):
        val2 = grid[r,column]
        # print(f"    ({(r)},{column}): {val2}")
        dis += 1
        if (val2 >= val):
            break
    # print(f"    dis = {dis}")
    return dis

def view_distance_left(row,column):
    return view_distance_row(row,column,column-1,-1,-1)

def view_distance_right(row,column):
    return view_distance_row(row,column,column+1,width,1)

def view_distance_up(row,column):
    return view_distance_column(row,column,row-1,-1,-1)

def view_distance_down(row,column):
    return view_distance_column(row,column,row+1,height,1)

with open(file_path, 'r') as file:
    lines = file.readlines()

temp_grid = []
for line in lines:
    line = line.rstrip('\n')
    temp_grid.append(number_line_to_int_list(line))

grid = np.array([np.array(xi) for xi in temp_grid])
shape = grid.shape
width = shape[0]
height = shape[1]

# print(grid)
# print(f"{shape}: {width} x {height}")

visible = 0;
max_view_distance = 0
for x in range(width):
    for y in range(height):
        # print(f"  {(x)},{y}): {grid[x,y]}")
        if (x == 0 or y == 0 or (x + 1 == width) or (y + 1 == height)):
            visible += 1
        else:
            if (is_visible_left(x,y) or is_visible_right(x,y) or is_visible_up(x,y) or is_visible_down(x,y)):
                visible += 1
            view_distance = view_distance_left(x,y) * view_distance_right(x,y) * view_distance_up(x,y) * view_distance_down(x,y)
            if (view_distance > max_view_distance):
                max_view_distance = view_distance

print(f"Part 1: {visible}")

print(f"Part 2: {max_view_distance}")
