import numpy as np
import matplotlib.pyplot as plt

l = 0.25

def draw():
    file = open('compareKrAndNn.csv')
    file.readline()
    names = ['' for n in range(10)]
    kr = [0 for n in range(10)]
    nn = [0 for n in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        nn[i] = int(line[2])
        kr[i] = int(line[1])

    r1 = np.arange(10)
    plt.title('Porownanie algorytmu KRandom i Nearest Neighbour')
    plt.bar(r1, kr, color='green', width=l, label='K-Random')
    plt.bar(r1+l, nn, color='red', width=l, label='Nearest Neighbour')
    plt.xticks([r + l for r in range(10)], names)
    plt.legend()
    plt.savefig('krAndNN.png')


draw()
