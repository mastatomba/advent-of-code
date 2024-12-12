# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day12/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day12/input_story_2.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day12/input_story_3.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day12/input.txt'

with open(file_path) as f:
    data = f.read()

grid_list = [[position for position in element] for element in data.split("\n")]

grid = {(x, y): grid_list[y][x] for y in range(len(grid_list)) for x in range(len(grid_list[0]))}

# print(grid_list)
# [
# 	['A', 'A', 'A', 'A'],
# 	['B', 'B', 'C', 'D'],
# 	['B', 'B', 'C', 'C'],
# 	['E', 'E', 'E', 'C']
# ]

# print(grid)
# {
# 	(0, 0): 'A',
# 	(1, 0): 'A',
# 	(2, 0): 'A',
# 	(3, 0): 'A',
# 	(0, 1): 'B',
# 	(1, 1): 'B',
# 	(2, 1): 'C',
# 	(3, 1): 'D',
# 	(0, 2): 'B',
# 	(1, 2): 'B',
# 	(2, 2): 'C',
# 	(3, 2): 'C',
# 	(0, 3): 'E',
# 	(1, 3): 'E',
# 	(2, 3): 'E',
# 	(3, 3): 'C'
# }

class GridPos:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __str__(self):
        return f"GridPos(x={self.x}, y={self.y})"

    def get_key(self):
        return f"{self.x}_{self.y}"

class Region:
    def __init__(self, plant_type: str, plot: GridPos):
        self.plant_type = plant_type
        self.plots = {}
        self.plots[plot.get_key()] = plot
        self.min_x = plot.x
        self.max_x = plot.x
        self.min_y = plot.y
        self.max_y = plot.y

    def __str__(self):
        return f"Region(plant_type={self.plant_type}, plots={len(self.plots)}, min_x={self.min_x}, max_x={self.max_x}, min_y={self.min_y}, max_y={self.max_y})"

    def append(self, plot: GridPos):
        self.plots[plot.get_key()] = plot
        if (plot.x < self.min_x):
            self.min_x = plot.x
        elif (plot.x > self.max_x):
            self.max_x = plot.x
        if (plot.y < self.min_y):
            self.min_y = plot.y
        elif (plot.y > self.max_y):
            self.max_y = plot.y
    
    def area(self):
        return len(self.plots)
  
    def perimeter(self):
        perimeter = 0
        for plot_key in self.plots:
            plot = self.plots[plot_key]
            count = 4
            positions = [GridPos(plot.x-1,plot.y), GridPos(plot.x+1,plot.y), GridPos(plot.x,plot.y-1), GridPos(plot.x,plot.y+1)]
            for position in positions:
                if (position.get_key() in self.plots):
                    count -= 1
            # print(f"    {plot}: {count}")
            if (count > 0):
                perimeter += count
        return perimeter

    def find_top_left_plot(self):
        res = None
        for plot_key in self.plots:
            plot = self.plots[plot_key]
            if (not res):
                if (plot.y < res.y) or (plot.y == res.y and plot.x < res.x):
                    res = plot    
            else:
                res = plot
        return res

    def sides(self):
        if len(self.plots) == 1:
            return 4
        
        plot = self.find_top_left_plot()
        sides = 1
        done = False
        cur_pos = GridPos(plot.x,plot.y)
        move_x = 1
        move_y = 0
        while not done:
            new_pos = GridPos(cur_pos.x + move_x, cur_pos.y + move_y)
            if (new_pos.get_key() in self.plots):
                cur_pos = new_pos
            else:
                if (move_x == 1):
                    move_x = 0
                    move_y = 1
                elif (move_y == 1):
                    move_x = 0
                    move_y = 1
        return sides



visited = {}

def find_plot_bounds(region: Region, x: int, y: int):
    positions = [GridPos(x-1,y), GridPos(x+1,y), GridPos(x,y-1), GridPos(x,y+1)]
    for position in positions:
        key = (position.x, position.y)
        if key not in visited:
            if key in grid and grid[key] == region.plant_type:
                # print(f"  Found other plot for plant type '{plant}' at ({position})")
                region.append(position)
                visited[key] = 1
                find_plot_bounds(region, position.x, position.y)

region_nr = 0
regions = {}
for y, row in enumerate(grid_list):
    for x, plant_type in enumerate(row):
        if (x,y) not in visited:
            # print(f"Found new plant type '{plant}' at ({x},{y})")
            region_nr += 1
            region_key = f"{region_nr}_{plant_type}"
            region = Region(plant_type, GridPos(x,y))
            regions[region_key] = region
            visited[(x,y)] = 1
            find_plot_bounds(region, x, y)

sum = 0
for region_key in regions:
    region = regions[region_key]
    print(f"{region_key}: {region}")
    area = region.area()
    perimeter = region.perimeter()
    price = area * perimeter
    print(f"  {area} * {perimeter} = {price}")
    sum += price

print(f"Part 1: {sum}")
