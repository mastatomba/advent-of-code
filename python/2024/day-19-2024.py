# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day19/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day19/input.txt'

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

towel_patterns = lines[0].split(', ')
print(towel_patterns)

visited = {}

def design_check(design: str, tag: str):
    # print(f"{tag} Checking design '{design}'..")
    if (design in visited):
        return visited[design]

    count = 0
    for towel_pattern in towel_patterns:
        # print(f"{tag}   Checking pattern '{towel_pattern}'.. ({count})")
        if (design == towel_pattern):
            # print(f"{tag}     Design '{design}' equals '{towel_pattern}'")
            return count+1
        if (design.startswith(towel_pattern)):
            # print(f"{tag}     Design '{design}' starts with '{towel_pattern}'")
            count += design_check(design[len(towel_pattern):], tag+towel_pattern+'==')

    if (count > 0):
        if (design not in visited):
            visited[design] = count

    return count

count1 = count2 = 0
for i in range(2, len(lines)):
    design = lines[i]
    res = design_check(design, '=='+design+"==")
    if (res > 0):
        count1 += 1
    count2 += res
    print(f"Design '{design}': {res}")

print(f"Part 1: {count1}")

# 527329926448226 is too low?
print(f"Part 2: {count2}")
