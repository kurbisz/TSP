import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.25


def draw():
    file = open('compareKrAndTwoOpt.csv')
    file.readline()
    names = ['' for n in range(10)]
    kr = [0 for n in range(10)]
    to = [0 for n in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        to[i] = int(line[1])
        kr[i] = int(line[2])

    r1 = np.arange(10)
    plt.title('Porownanie algorytmu KRandom i 2-OPT')
    plt.bar(r1, kr, color='green', width=l, label='K-Random')
    plt.bar(r1+l, to, color='red', width=l, label='2-OPT')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.xlabel('Rodzaj i wielkość problemu')
    plt.ylabel('Najlepsze rozwiązanie')
    plt.yscale('log')
    plt.savefig('krAndTwoOpt.png')
    print(stats.wilcoxon(kr, to))


draw()
