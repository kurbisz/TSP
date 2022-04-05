import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.2


def draw():
    file = open('twoOptStarting.csv')
    file.readline()
    names = ['' for n in range(10)]
    to = [0 for n in range(10)]
    bto = [0 for n in range(10)]
    bto2 = [0 for n in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        to[i] = int(line[1])
        bto[i] = int(line[2])
        bto2[i] = int(line[3])

    r1 = np.arange(10)
    plt.title('Porownanie algorytmu 2-OPT w zaleznosci od rozwiazania poczatkowego')
    plt.bar(r1 - l, to, color='green', width=l, label='Zwykly 2-OPT')
    plt.bar(r1, bto, color='red', width=l, label='2-OPT dla najblizszego sasiada od 1')
    plt.bar(r1 + l, to, color='blue', width=l, label='2-OPT dla ulepszonego najblizszego sasiada')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.xlabel('Rodzaj i wielkość problemu')
    plt.ylabel('Najlepsze rozwiązanie')
    plt.savefig('twoOptStarting.png')
    plt.clf()


draw()
