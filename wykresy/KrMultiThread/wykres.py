import matplotlib.pyplot as plt


def draw():
    max = 16
    x_line = [0 for n in range(max)]
    nnMulti = [0 for n in range(max)]

    file1 = open('mulithreadKR_d198.csv')
    index = 0
    file1.readline()
    for n in range(max):
        line = file1.readline().split(';')
        x_line[index] = int(line[1])
        nnMulti[index] = float(line[0])
        index += 1

    plt.title('Wyniki dla algorytmu K-Random (k=100000) w zaleznosci od ilosci watkow')
    plt.plot(x_line, nnMulti, color='red')
    plt.xlabel('Dostępna liczba wątków')
    plt.ylabel('Czas wykonania algorytmu w milisekundach')
    plt.savefig('krMultiThread_d198.png')
    plt.show()
    plt.clf()

    max = 16
    x_line = [0 for n in range(max)]
    nnMulti = [0 for n in range(max)]

    file1 = open('mulithreadKR_d1291.csv')
    index = 0
    file1.readline()
    for n in range(max):
        line = file1.readline().split(';')
        x_line[index] = int(line[1])
        nnMulti[index] = float(line[0])
        index += 1

    plt.title('Wyniki dla algorytmu K-Random (k=100000) w zaleznosci od ilosci watkow')
    plt.plot(x_line, nnMulti, color='red')
    plt.xlabel('Dostępna liczba wątków')
    plt.ylabel('Czas wykonania algorytmu w milisekundach')
    plt.savefig('krMultiThread_d1291.png')
    plt.show()
    plt.clf()


draw()
