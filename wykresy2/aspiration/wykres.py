import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.12


def draw():
    file = open('aspiration.csv')
    file.readline()
    names = ['' for n in range(10)]
    aspFalse = [0 for n in range(10)]
    aspTrue = [0 for n in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        aspFalse[i] = int(line[1])
        aspTrue[i] = int(line[2])

    r1 = np.arange(10)
    plt.title('Porównanie taboo search dla kryterium aspiracji')
    plt.bar(r1 + 0.5 * l, aspFalse, color='red', width=l, label='Nie używając kryterium aspiracji')
    plt.bar(r1 + 1.5 * l, aspTrue, color='darkorange', width=l, label='Używając kryterium aspiracji')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.xlabel('Rodzaj i wielkość problemu')
    plt.ylabel('Otrzymane rozwiązanie')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(axis='y', linestyle='--', linewidth=0.5)
    plt.yscale('log')
    plt.yticks([1000, 10000, 100000])
    plt.savefig('aspiration.png')
    plt.clf()

    print('kryterium aspiracji: ' + str(stats.wilcoxon(aspFalse, aspTrue)))


draw()
