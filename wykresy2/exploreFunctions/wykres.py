import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.13


def draw():
    file = open('exploreFunctions2.csv')
    file.readline()
    names = ['' for n in range(10)]
    longTermList = [[0 for n in range(10)] for j in range(8)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        for j in range(6):
            longTermList[j][i] = int(line[j+1])

    r1 = np.arange(10)*2
    plt.title('Porównanie tabu search dla metody eksploracji co n kroków')
    color = ['black', 'red', 'darkorange', 'greenyellow', 'green', 'blue']
    plt.bar(r1 - 1.5 * l, longTermList[0], color=color[0], width=l, label='Bez metody eksploracji')
    plt.bar(r1 - 0.5 * l, longTermList[1], color=color[1], width=l, label='n = 50')
    plt.bar(r1 + 0.5 * l, longTermList[2], color=color[2], width=l, label='n = 100')
    plt.bar(r1 + 1.5 * l, longTermList[3], color=color[3], width=l, label='n = 200')
    plt.bar(r1 + 2.5 * l, longTermList[4], color=color[4], width=l, label='n = 300')
    plt.bar(r1 + 3.5 * l, longTermList[5], color=color[5], width=l, label='n = wielkość_problemu/4')
    plt.xticks([2*r + l for r in range(10)], names)
    plt.legend()
    plt.xlabel('Rodzaj i wielkość problemu')
    plt.ylabel('Otrzymane rozwiązanie')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(axis='y', linestyle='--', linewidth=0.5)
    plt.yscale('log')
    plt.yticks([1000, 10000, 100000])
    plt.savefig('exploreFunctions.png', bbox_inches='tight')
    plt.clf()


    file = open('exploreFunctions15.csv')
    file.readline()
    names = ['' for n in range(10)]
    longTermList = [[0 for n in range(10)] for j in range(8)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        for j in range(6):
            longTermList[j][i] = int(line[j+1])

    r1 = np.arange(10)*2
    plt.title('Porównanie tabu search dla metody eksploracji co n kroków')
    color = ['black', 'red', 'darkorange', 'greenyellow', 'green', 'blue']
    plt.bar(r1 - 1.5 * l, longTermList[0], color=color[0], width=l, label='Bez metody eksploracji')
    plt.bar(r1 - 0.5 * l, longTermList[1], color=color[1], width=l, label='n = 50')
    plt.bar(r1 + 0.5 * l, longTermList[2], color=color[2], width=l, label='n = 100')
    plt.bar(r1 + 1.5 * l, longTermList[3], color=color[3], width=l, label='n = 200')
    plt.bar(r1 + 2.5 * l, longTermList[4], color=color[4], width=l, label='n = 300')
    plt.bar(r1 + 3.5 * l, longTermList[5], color=color[5], width=l, label='n = wielkość_problemu/4')
    plt.xticks([2*r + l for r in range(10)], names)
    plt.legend()
    plt.xlabel('Rodzaj i wielkość problemu')
    plt.ylabel('Otrzymane rozwiązanie')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(axis='y', linestyle='--', linewidth=0.5)
    plt.yscale('log')
    plt.yticks([1000, 10000, 100000])
    plt.savefig('exploreFunctions15.png', bbox_inches='tight')
    plt.clf()


draw()
