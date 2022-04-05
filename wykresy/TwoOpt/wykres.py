import matplotlib.pyplot as plt


def draw():
    x_line = [0 for n in range(15)]
    k_random = [0 for n in range(15)]

    file1 = open('twoOptTimeComparison.csv')
    index = 0
    file1.readline()
    for n in range(15):
        line = file1.readline().split(';')
        x_line[index] = int(line[0])
        k_random[index] = int(line[1])
        index += 1

    plt.axis([0, 8000, 50000, 120000])
    plt.title('Wyniki dla algorytmu 2-OPT w zaleznosci od czasu')
    plt.plot(x_line, k_random, color='red')
    plt.xlabel('Czas wykonania algorytmu w milisekundach')
    plt.ylabel('Najlepsze rozwiązanie')
    plt.savefig('twoOptTimeComparison.png')
    plt.show()
    plt.clf()


draw()
