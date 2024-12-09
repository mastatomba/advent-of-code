# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day9/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day9/input_story_2.txt'
# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day9/input.txt'

disk_single: list[int] = []
disk_full: list[tuple[int, int]] = []
total_file_size = 0

FREE = -1

with open(file_path) as f:
    file_id_counter = 0

    for (index, length) in enumerate(f.read()):
        if index % 2 == 0:
            file_id = file_id_counter
            file_id_counter += 1
            total_file_size += int(length)
        else:
            file_id = FREE

        disk_single += [file_id] * int(length)
        disk_full += [(file_id, int(length))]


def checksum(disk: list[int]) -> int:
    return sum([i * f for (i, f) in enumerate(disk) if f != FREE])


def print_disk(disk: list[int]):
    print("".join([str(i)[0] if i != FREE else "." for i in disk]))


def part1():
    result = []
    reverse_disk = [f for f in disk_single[::-1] if f != FREE]

    for i in range(total_file_size):
        if disk_single[i] == FREE:
            result.append(reverse_disk.pop(0))
        else:
            result.append(disk_single[i])

    print(checksum(result))


def blank_second(result, file):
    seen = False

    for index, (other_id, size) in enumerate(result):
        if other_id == file[0]:
            if not seen:
                seen = True
            else:
                result[index] = (FREE, size)



def part2():
    result = disk_full[::] # copy
    reverse_disk = [(f, l) for (f, l) in disk_full[::-1] if f != FREE]
    offset = 0

    for (file_id, size) in reverse_disk:
        for (other_index, (other_file_id, other_size)) in enumerate(result):
            if other_file_id == FREE and other_size >= size:
                other_file_index = other_index

                # add to new
                result.insert(other_file_index, (file_id, size))

                if other_size == size:
                    del result[other_file_index + 1]
                else:
                    result[other_file_index + 1] = (FREE, other_size - size)
                    offset += 1

                blank_second(result, (file_id, size))

                break

    result_blocks = []

    for (file_id, size) in result:
        result_blocks += [file_id] * size

    # print_disk(result_blocks)

    print(checksum(result_blocks))

part1()
part2()