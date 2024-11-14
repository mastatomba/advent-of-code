# file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day4/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2022/day4/input.txt'

def does_comp_overlap(assignment1, assignment2):
    # example: 2-4,6-8
    # print(f"Assignments: {assignment1}, {assignment2}")

    lb1 = left_bound(assignment1)
    rb1 = right_bound(assignment1)
    lb2 = left_bound(assignment2)
    rb2 = right_bound(assignment2)

    return lb1<=lb2 and rb1>=rb2

def does_part_overlap(assignment1, assignment2):
    # example: 2-4,6-8
    # print(f"Assignments: {assignment1}, {assignment2}")

    lb1 = left_bound(assignment1)
    rb1 = right_bound(assignment1)
    lb2 = left_bound(assignment2)
    rb2 = right_bound(assignment2)

    return not(lb1>rb2 or rb1<lb2)

def bounds(assignment):
    # example: 2-4
    return assignment.split('-')

def left_bound(assignment):
    return int(bounds(assignment)[0])

def right_bound(assignment):
    return int(bounds(assignment)[1])

with open(file_path, 'r') as file:
    lines = file.readlines()

pairs = 0
for line in lines:
    assignments = line.rstrip('\n').split(',')
    if does_comp_overlap(assignments[0], assignments[1]) or does_comp_overlap(assignments[1], assignments[0]):
        pairs+=1

print(f"Part 1: {pairs}")

pairs = 0
for line in lines:
    assignments = line.rstrip('\n').split(',')
    if does_part_overlap(assignments[0], assignments[1]):
        pairs+=1

print(f"Part 2: {pairs}")
