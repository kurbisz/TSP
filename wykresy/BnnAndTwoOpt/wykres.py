import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.25


def draw():
    file = open('compareBnnAndTwoOpt.csv')
    file.readline()
    names = ['' for n in range(10)]
    bnn = [0 for n in range(10)]
    to = [0 for n in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        to[i] = int(line[1])
        bnn[i] = int(line[2])

    r1 = np.arange(10)
    plt.title('Porownanie algorytmu 2-OPT i Nearest Neighbour')
    plt.bar(r1, bnn, color='green', width=l, label='Better Nearest Neighbour')
    plt.bar(r1+l, to, color='red', width=l, label='2-OPT')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.savefig('BnnAndTwoOpt.png')
    print(stats.wilcoxon(to, bnn))


draw()
