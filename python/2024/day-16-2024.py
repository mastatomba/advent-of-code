import heapq

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day16/input_story.txt'
# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day16/input_story2.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day16/input.txt'

def part1(grid_lines):
    grid = grid_lines
    m, n = len(grid), len(grid[0])
    for i in range(m):
        for j in range(n):
            if grid[i][j] == 'S':
                start = (i, j)
            elif grid[i][j] == 'E':
                end = (i, j)

    grid[end[0]] = grid[end[0]].replace('E', '.')

    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    heap = [(0, 0, *start)]
    visited = set()
    while heap:
        score, d, i, j = heapq.heappop(heap)
        if (i, j) == end:
            break

        if (d, i, j) in visited:
            continue

        visited.add((d, i, j))
        
        x = i + directions[d][0]
        y = j + directions[d][1]
        if grid[x][y] == '.' and (d, x, y) not in visited:
            heapq.heappush(heap, (score + 1, d, x, y))
        
        left = (d - 1) % 4
        if (left, i, j) not in visited:
            heapq.heappush(heap, (score + 1000, left, i, j))

        right = (d + 1) % 4
        if (right, i, j) not in visited:
            heapq.heappush(heap, (score + 1000, right, i, j))

    return score

def part2(grid_lines):
    grid = grid_lines
    m, n = len(grid), len(grid[0])
    for i in range(m):
        for j in range(n):
            if grid[i][j] == 'S':
                start = (i, j)
            elif grid[i][j] == 'E':
                end = (i, j)

    grid[end[0]] = grid[end[0]].replace('E', '.')

    def can_visit(d, i, j, score):
        prev_score = visited.get((d, i, j))
        if prev_score and prev_score < score:
            return False
        visited[(d, i, j)] = score
        return True

    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    heap = [(0, 0, *start, {start})]
    visited = {}
    lowest_score = None
    winning_paths = set()
    while heap:
        score, d, i, j, path = heapq.heappop(heap)
        if lowest_score and lowest_score < score:
            break

        if (i, j) == end:
            lowest_score = score
            winning_paths |= path
            continue

        if not can_visit(d, i, j, score):
            continue

        x = i + directions[d][0]
        y = j + directions[d][1]
        if grid[x][y] == '.' and can_visit(d, x, y, score+1):
            heapq.heappush(heap, (score + 1, d, x, y, path | {(x, y)}))
        
        left = (d - 1) % 4
        if can_visit(left, i, j, score + 1000):
            heapq.heappush(heap, (score + 1000, left, i, j, path))

        right = (d + 1) % 4
        if can_visit(right, i, j, score + 1000):
            heapq.heappush(heap, (score + 1000, right, i, j, path))

    return len(winning_paths)

with open(file_path) as f:
    data = f.read()

score = part1(data.split("\n"))
print(f"Part 1: {score}")

count = part2(data.split("\n"))
print(f"Part 2: {count}")
