import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.25


def draw():
    file = open('compareBnnAndKr.csv')
    file.readline()
    names = ['' for n in range(10)]
    kr = [0 for n in range(10)]
    bnn = [0 for n in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        kr[i] = int(line[1])
        bnn[i] = int(line[2])

    r1 = np.arange(10)
    plt.title('Porownanie algorytmu KRandom i Better Nearest Neighbour')
    plt.bar(r1, kr, color='green', width=l, label='K-Random')
    plt.bar(r1+l, bnn, color='red', width=l, label='Better Nearest Neighbour')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.savefig('BnnAndKr.png')
    print(stats.wilcoxon(kr, bnn))


draw()
