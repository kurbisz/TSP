import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.25


def draw():
    file = open('twoOptRandomStartingTime.csv')
    file.readline()
    names = ['' for n in range(10)]
    to = [0 for n in range(10)]
    bto = [0 for n in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        to[i] = int(line[1])
        bto[i] = int(line[2])

    r1 = np.arange(10)
    plt.title('Porownanie czasu w ms algorytmu 2-OPT w zaleznosci od rozwiazania poczatkowego')
    plt.bar(r1, to, color='green', width=l, label='Zwykly 2-OPT')
    plt.bar(r1 + l, to, color='red', width=l, label='2-OPT dla losowego poczatku')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.savefig('twoOptRandomStartingTime.png')
    plt.clf()


draw()
