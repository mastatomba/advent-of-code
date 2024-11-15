# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2022/day6/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2022/day6/input.txt'

def is_marker(packet):
    return len(set(packet)) == len(packet)

def find_marker(data_stream, packet_length):
    for i in range(len(data_stream)):
        packet = data_stream[i:(packet_length+i)]
        if (is_marker(packet)):
            return i+packet_length
    return -1

with open(file_path, 'r') as file:
    lines = file.readlines()
    data_stream = ''.join(lines)

print(f"Part 1: {find_marker(data_stream, 4)}")

# print(f"Part 1-2: {find_marker('bvwbjplbgvbhsrlpgdmjqwftvncz', 4)}")
# print(f"Part 1-3: {find_marker('nppdvjthqldpwncqszvftbrmjlhg', 4)}")
# print(f"Part 1-4: {find_marker('nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg', 4)}")
# print(f"Part 1-5: {find_marker('zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw', 4)}")

print(f"Part 2: {find_marker(data_stream, 14)}")

# print(f"Part 2-2: {find_marker('bvwbjplbgvbhsrlpgdmjqwftvncz', 14)}")
# print(f"Part 2-3: {find_marker('nppdvjthqldpwncqszvftbrmjlhg', 14)}")
# print(f"Part 2-4: {find_marker('nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg', 14)}")
# print(f"Part 2-5: {find_marker('zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw', 14)}")
