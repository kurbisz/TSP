import matplotlib.pyplot as plt


def draw():
    x_line = [0 for n in range(1, 190)]
    k_random = [0 for n in range(1, 190)]

    x_line_avg = [0 for n in range(1, 29)]
    k_random_avg = [0 for n in range(1, 29)]

    file1 = open('kRandom.csv')
    index = 0
    for n in range(1, 190):
        line = file1.readline().split(';')
        x_line[index] = int(line[0])
        k_random[index] = int(line[1])
        index += 1

    file2 = open('kRandomAverage.csv')
    index = 0
    for n in range(1, 29):
        line = file2.readline().split(';')
        x_line_avg[index] = int(line[0])
        k_random_avg[index] = int(line[1])
        index += 1

    plt.subplots_adjust(left=0.15)
    plt.title('Wyniki dla algorytmu KRandom w zaleznosci od k')
    plt.plot(x_line, k_random, color='green', label='wynik dla k przedzialami co 100')
    plt.plot(x_line_avg, k_random_avg, color='red', label='usredniony wynik 100 wywolan dla poszczegolnych k')
    plt.legend()
    plt.xlabel('Wartość parametru k')
    plt.ylabel('Najlepsze rozwiązanie')
    plt.savefig('krandom.png')
    plt.show()
    plt.clf()


draw()
