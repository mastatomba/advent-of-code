import re

# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day13/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day13/input.txt'

BUTTON_A_TOKENS = 3
BUTTON_B_TOKENS = 1

class Button:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __str__(self):
        return f"Button(x={self.x}, y={self.y})"

    def get_key(self):
        return f"{self.x}_{self.y}"

class Prize:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

    def __str__(self):
        return f"Prize(x={self.x}, y={self.y})"

    def get_key(self):
        return f"{self.x}_{self.y}"

class Machine:
    def __init__(self, button_a: Button, button_b: Button, prize: Prize):
        self.button_a = button_a
        self.button_b = button_b
        self.prize = prize

    def __str__(self):
        return f"Machine(button_a={self.button_a}, button_b={self.button_b}, prize={self.prize})"
    
    def get_min_token_count_to_win_cramer_rule(self, increase_prize: int):
        #https://en.wikipedia.org/wiki/Cramer%27s_rule
        prize_x = self.prize.x + increase_prize
        prize_y = self.prize.y + increase_prize
        #A = (p_x*b_y - prize_y*b_x) / (a_x*b_y - a_y*b_x)
        #B = (a_x*p_y - a_y*p_x)     / (a_x*b_y - a_y*b_x)
        A = (prize_x*self.button_b.y - prize_y*self.button_b.x) / (self.button_a.x*self.button_b.y - self.button_a.y*self.button_b.x)
        B = (self.button_a.x*prize_y - self.button_a.y*prize_x) / (self.button_a.x*self.button_b.y - self.button_a.y*self.button_b.x)
        if (A.is_integer() and B.is_integer()):
            token_count = (int(A) * BUTTON_A_TOKENS) + (int(B) * BUTTON_B_TOKENS)
            # print(f"  Win combination found! Button a pressed {A} times and button b pressed {B} times. Token count: {token_count}")
            return token_count
        return -1

    def get_min_token_count_to_win(self, max_button_presses: int, increase_prize: int):
        min_token_count = -1
        prize_x = self.prize.x + increase_prize
        prize_y = self.prize.y + increase_prize
        for i in range(max_button_presses):
            a_x = i * self.button_a.x
            a_y = i * self.button_a.y
            if a_x > prize_x or a_y > prize_y:
                # print(f"    Button a pushes are already over prize possibility: {i}/{a_x}/{a_y}/{prize_x}/{prize_y}")
                break
            remainder_x = prize_x - a_x;
            if (remainder_x % self.button_b.x == 0):
                j = int(remainder_x / self.button_b.x)
                if j <= max_button_presses:
                    b_y = j * self.button_b.y
                    if (a_y + b_y) == prize_y:
                        token_count = (i * BUTTON_A_TOKENS) + (j * BUTTON_B_TOKENS)
                        # print(f"  Win combination found! Button a pressed {i} times and button b pressed {j} times. Token count: {token_count}")
                        if min_token_count == -1 or token_count < min_token_count:
                            min_token_count = token_count

        return min_token_count

def extract_numbers(line: str):
    # Extract all numbers
    numbers = re.findall(r'\d+', line)
    # Convert to integers (optional, if you need numeric values)
    numbers = list(map(int, numbers))
    return numbers

def create_button(line: str):
    # Button A: X+69, Y+23
    numbers = extract_numbers(line)
    return Button(numbers[0], numbers[1])

def create_prize(line: str):
    # Prize: X=7870, Y=6450
    numbers = extract_numbers(line)
    return Prize(numbers[0], numbers[1])

with open(file_path) as f:
    data = f.read()

lines = data.split("\n")

machines = []
for i in range(0, len(lines), 4):
    machines.append(Machine(create_button(lines[i]), create_button(lines[i+1]), create_prize(lines[i+2])))

sum1 = sum2 = 0
for machine in machines:
    # print(machine)
    count = machine.get_min_token_count_to_win(100, 0)
    if (count != -1):
        sum1 += count
    count = machine.get_min_token_count_to_win_cramer_rule(10000000000000)
    if (count != -1):
        sum2 += count

print(f"Part 1: {sum1}")

print(f"Part 2: {sum2}")
