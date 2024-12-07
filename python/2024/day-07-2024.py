import itertools

# file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day7/input_story.txt'
file_path = '/home/tom/Projects/personal/advent-of-code/resources/input/2024/day7/input.txt'

def calculate(number1: int, number2: int, operator: chr):
    if (operator == '+'):
        return number1 + number2
    if (operator == '*'):
        return number1 * number2
    if (operator == '||'):
        return int(''+str(number1)+str(number2))
    raise Exception(f"Operator '{operator}' is not supported!")

def create_equation(line: str):
    # 292: 11 6 16 20
    splitted = line.split(': ')
    return Equation(int(splitted[0]), splitted[1].split(' '))

class Equation:
    def __init__(self, sum: chr, numbers: list):
        self.sum = sum
        self.numbers = numbers

    def __str__(self):
        return f"Pos(sum={self.sum}, numbers={self.numbers})"

    def get_operator_combinations(self, operators: list):
        # Number of times the operators should be used
        n = len(self.numbers) - 1
        # Generate all possible combinations
        combinations = list(itertools.product(operators, repeat=n))
        return combinations

    def is_equation_true(self, operators: list):
        combinations = self.get_operator_combinations(operators)
        for combination in combinations:
            sum = int(self.numbers[0])
            for i in range(1, len(self.numbers)):
                sum = calculate(sum, int(self.numbers[i]), combination[i-1])
            if (sum == self.sum):
                return True
        return False

with open(file_path, 'r') as file:
    lines = file.readlines()

equations = []
for line in lines:
    line = line.rstrip('\n')
    equations.append(create_equation(line))

sum1 = sum2 = 0
for equation in equations:
    # print(equation)
    if (equation.is_equation_true(['+', '*'])):
        sum1 += equation.sum
    if (equation.is_equation_true(['+', '*', '||'])):
        sum2 += equation.sum

print(f"Part 1: {sum1}")

print(f"Part 2: {sum2}")
