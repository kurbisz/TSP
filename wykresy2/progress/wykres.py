import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats


l = 0.13


def draw():
    file = open('progress100.csv')
    file.readline()
    names = ['' for n in range(11)]
    operations = [[0 for n in range(11)] for j in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        for j in range(11):
            operations[i][j] = int(line[j+1])

    plt.title('Proces zmiany wyniku w zalezności od czasu')
    color = ['black', 'dimgray', 'silver', 'red', 'darkorange', 'greenyellow', 'green', 'aqua', 'blue', 'blueviolet', 'hotpink']
    x_axis = [r for r in range(0, 110, 10)]
    for i in range(10):
        plt.plot(x_axis, operations[i], color=color[i], marker='.', label=names[i])
    plt.legend()
    plt.xticks(x_axis)
    plt.xlabel('% czasu potrzebnego do wykonania 100 iteracji')
    plt.ylabel('Otrzymane rozwiązanie')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(linestyle='--', linewidth=0.5)
    plt.savefig('progress100.png', bbox_inches='tight')
    plt.clf()


    file = open('progress500.csv')
    file.readline()
    names = ['' for n in range(11)]
    operations = [[0 for n in range(11)] for j in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        for j in range(11):
            operations[i][j] = int(line[j+1])

    plt.title('Proces zmiany wyniku w zalezności od czasu')
    color = ['black', 'dimgray', 'silver', 'red', 'darkorange', 'greenyellow', 'green', 'aqua', 'blue', 'blueviolet', 'hotpink']
    x_axis = [r for r in range(0, 110, 10)]
    for i in range(10):
        plt.plot(x_axis, operations[i], color=color[i], marker='.', label=names[i])
    plt.legend()
    plt.xticks(x_axis)
    plt.xlabel('% czasu potrzebnego do wykonania 500 iteracji')
    plt.ylabel('Otrzymane rozwiązanie')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(linestyle='--', linewidth=0.5)
    plt.savefig('progress500.png', bbox_inches='tight')
    plt.clf()


    file = open('progress1000.csv')
    file.readline()
    names = ['' for n in range(11)]
    operations = [[0 for n in range(11)] for j in range(10)]
    for i in range(10):
        line = file.readline().split(';')
        names[i] = str(line[0])
        for j in range(11):
            operations[i][j] = int(line[j+1])

    plt.title('Proces zmiany wyniku w zalezności od czasu')
    color = ['black', 'dimgray', 'silver', 'red', 'darkorange', 'greenyellow', 'green', 'aqua', 'blue', 'blueviolet', 'hotpink']
    x_axis = [r for r in range(0, 110, 10)]
    for i in range(10):
        plt.plot(x_axis, operations[i], color=color[i], marker='.', label=names[i])
    plt.legend()
    plt.xticks(x_axis)
    plt.xlabel('% czasu potrzebnego do wykonania 1000 iteracji')
    plt.ylabel('Otrzymane rozwiązanie')
    plt.subplots_adjust(left=0.15, bottom=0.1, right=0.9, top=0.9)
    plt.grid(linestyle='--', linewidth=0.5)
    plt.savefig('progress1000.png', bbox_inches='tight')
    plt.clf()



draw()
