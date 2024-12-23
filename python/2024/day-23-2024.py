import networkx as nx

# file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day23/input_story.txt'
file_path = '/home/tom/projects/advent-of-code/resources/input/2024/day23/input.txt'

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

def add_pc_mapping(pcs: dict, pc1: str, pc2: str):
    if (pc1 not in pcs):
        pcs[pc1] = []
    pcs[pc1].append(pc2)

def find_3_pc_sets(pcs: dict):
    result = []
    ignored = {}
    for pc in pcs:
        ignored[pc] = 1
        connected_pcs = pcs[pc]
        for i in range(len(connected_pcs)):
            if (connected_pcs[i] not in ignored):
                for j in range(i+1, len(connected_pcs)):
                    if (connected_pcs[j] not in ignored):
                        if (connected_pcs[j] in pcs[connected_pcs[i]]):
                            result.append(pc+','+connected_pcs[i]+','+connected_pcs[j])

    return result

def find_largest_pc_set(lines):
    G = nx.Graph()
    for line in lines:
        l, r = line.strip().split("-")
        G.add_edge(l, r)

    max_interconnected = [clique for clique in nx.enumerate_all_cliques(G)][-1]
    return sorted(max_interconnected)

# {
#     kh: ['tc', 'qp', 'ub', 'ta']
#     tc: ['kh', 'wh', 'td', 'co']
# }
pcs = {}
for line in lines:
    # kh-tc
    splitted = line.split('-')
    add_pc_mapping(pcs, splitted[0], splitted[1])
    add_pc_mapping(pcs, splitted[1], splitted[0])

print(f"Number of pcs: {len(pcs)}")
# for pc in pcs:
    # print(f"{pc}: {pcs[pc]}")

count1 = 0
pc_sets = find_3_pc_sets(pcs)
print(f"Number of three pc sets: {len(pc_sets)}")
for pc_set in pc_sets:
    # print(pc_set)
    if pc_set.startswith('t') or ',t' in pc_set:
        # print(pc_set)
        count1 += 1

# 2526 too high, i overlooked pcs starting with t
print(f"Part 1: {count1}")

pc_set = find_largest_pc_set(lines)
print(pc_set)
print(f"Part 2: {','.join(pc_set)}")