import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.12


def draw():
    file = open('asynchronous.csv')
    file.readline()
    names = ['' for n in range(10)]
    asyn = [[0 for n in range(10)] for j in range(8)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        for j in range(8):
            asyn[j][i] = int(line[j+1])

    r1 = np.arange(10)*2
    plt.title('Porównanie tabu search dla różnej liczby wątków')
    color = ['black', 'red', 'darkorange', 'greenyellow', 'green', 'aqua', 'blue', 'magenta']
    plt.bar(r1 - 2.5 * l, asyn[0], color=color[0], width=l, label='1 wątek')
    for i in range(1, 5):
        plt.bar(r1 + (-2.5 + i) * l, asyn[i], color=color[i], width=l, label=str(i+1) + ' wątki')
    for i in range(5, 8):
        plt.bar(r1 + (-2.5 + i) * l, asyn[i], color=color[i], width=l, label=str(i+1) + ' wątków')
    plt.xticks([2*r + l for r in range(10)], names)
    plt.legend(loc='lower left')
    plt.xlabel('Rodzaj i wielkość problemu')
    plt.ylabel('Otrzymane rozwiązanie')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.85, top=0.9)
    plt.grid(axis='y', linestyle='--', linewidth=0.5)
    plt.yscale('log')
    plt.yticks([1000, 10000, 100000])
    plt.savefig('asynchronous.png')
    plt.clf()



draw()
