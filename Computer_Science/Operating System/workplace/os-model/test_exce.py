import random

def main():
    for _ in range(5):
        x = 0
        b = yield
        x = x * 2 + b

    print(f'x = {x:010b}b')


if __name__ == '__main__':
    