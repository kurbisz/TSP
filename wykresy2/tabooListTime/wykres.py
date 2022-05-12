import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.13


def draw():
    file = open('tabooListTime198.csv')
    file.readline()
    operations = [[0 for n in range(10)] for j in range(2)]
    for i in range(2):
        line = file.readline().split(';')
        for j in range(10):
            operations[i][j] = int(line[j+1])/1000000
    plt.title('Czas Taboo Search dla roznych list tabu dla wielkosci problemu = 198')
    x_axis = [r for r in range(100, 1100, 100)]
    plt.plot(x_axis, operations[0], color='red', marker='.', label='Standardowa lista tabu (zlozonosc O(n))')
    plt.plot(x_axis, operations[1], color='blue', marker='.', label='Ulepszona lista tabu (zlozonosc O(1))')
    plt.legend()
    plt.xticks(x_axis)
    plt.xlabel('Liczba iteracji algorytmu')
    plt.ylabel('Czas w ms')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(linestyle='--', linewidth=0.5)
    plt.savefig('tabooListTime198.png', bbox_inches='tight')
    plt.clf()


    file = open('tabooListTime91.csv')
    file.readline()
    operations = [[0 for n in range(10)] for j in range(2)]
    for i in range(2):
        line = file.readline().split(';')
        for j in range(10):
            operations[i][j] = int(line[j+1])/1000000

    plt.title('Czas Taboo Search dla roznych list tabu dla wielkosci problemu = 91')
    x_axis = [r for r in range(100, 1100, 100)]
    plt.plot(x_axis, operations[0], color='red', marker='.', label='Standardowa lista tabu (zlozonosc O(n))')
    plt.plot(x_axis, operations[1], color='blue', marker='.', label='Ulepszona lista tabu (zlozonosc O(1))')
    plt.legend()
    plt.xticks(x_axis)
    plt.xlabel('Liczba iteracji algorytmu')
    plt.ylabel('Czas w ms')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(linestyle='--', linewidth=0.5)
    plt.savefig('tabooListTime91.png', bbox_inches='tight')
    plt.clf()


    file = open('tabooListTime48.csv')
    file.readline()
    operations = [[0 for n in range(10)] for j in range(2)]
    for i in range(2):
        line = file.readline().split(';')
        for j in range(10):
            operations[i][j] = int(line[j+1])/1000000

    plt.title('Czas Taboo Search dla roznych list tabu dla wielkosci problemu = 48')
    x_axis = [r for r in range(100, 1100, 100)]
    plt.plot(x_axis, operations[0], color='red', marker='.', label='Standardowa lista tabu (zlozonosc O(n))')
    plt.plot(x_axis, operations[1], color='blue', marker='.', label='Ulepszona lista tabu (zlozonosc O(1))')
    plt.legend()
    plt.xticks(x_axis)
    plt.xlabel('Liczba iteracji algorytmu')
    plt.ylabel('Czas w ms')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(linestyle='--', linewidth=0.5)
    plt.savefig('tabooListTime48.png', bbox_inches='tight')
    plt.clf()



draw()
