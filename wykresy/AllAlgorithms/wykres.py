import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.12


def draw():
    file = open('compareAll.csv')
    file.readline()
    names = ['' for n in range(10)]
    kr = [0 for n in range(10)]
    kr2 = [0 for n in range(10)]
    nn = [0 for n in range(10)]
    nn2 = [0 for n in range(10)]
    to = [0 for n in range(10)]
    best = [0 for n in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        kr[i] = int(line[1])
        kr2[i] = int(line[2])
        nn[i] = int(line[3])
        nn2[i] = int(line[4])
        to[i] = int(line[5])
        best[i] = int(line[6])

    r1 = np.arange(10)
    plt.title('Porownanie wszystkich algorytmow dla dowolnego czasu')
    plt.bar(r1 - 2 * l, kr, color='lime', width=l, label='K-Random dla k = 100')
    plt.bar(r1 - 1 * l, kr2, color='darkgreen', width=l, label='K-Random dla k = 2*n')
    plt.bar(r1, nn, color='red', width=l, label='Nearest Neighbour')
    plt.bar(r1 + 1 * l, nn2, color='darkorange', width=l, label='Nearest Neighbour 2')
    plt.bar(r1 + 2 * l, to, color='deepskyblue', width=l, label='2-OPT')
    plt.bar(r1 + 3 * l, best, color='yellow', width=l, label='Best solution')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.xlabel('Rodzaj i wielkość problemu')
    plt.ylabel('Najlepsze rozwiązanie')
    plt.yscale('log')
    plt.savefig('all.png')
    plt.clf()

    r1 = np.arange(10)
    plt.title('Porownanie wszystkich algorytmow oprocz K-Random dla dowolnego czasu')
    plt.bar(r1 - 1 * l, nn, color='red', width=l, label='Nearest Neighbour')
    plt.bar(r1, nn2, color='darkorange', width=l, label='Nearest Neighbour 2')
    plt.bar(r1 + 1 * l, to, color='deepskyblue', width=l, label='2-OPT')
    plt.bar(r1 + 2 * l, best, color='green', width=l, label='Best solution')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.xlabel('Rodzaj i wielkość problemu')
    plt.ylabel('Najlepsze rozwiązanie')
    plt.savefig('allWithoutKRandom.png')

    print('2 algorytmy k-random: ' + str(stats.wilcoxon(kr, kr2)))


draw()
