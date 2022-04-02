import matplotlib.pyplot as plt


def draw():
    max = 20
    x_line = [0 for n in range(max)]
    nnMulti = [0 for n in range(max)]

    file1 = open('mulithreadNNComp_d657.csv')
    index = 0
    file1.readline()
    for n in range(max):
        line = file1.readline().split(';')
        x_line[index] = int(line[1])
        nnMulti[index] = float(line[0])
        index += 1

    plt.axis([0, 21, 300, 2200])
    plt.title('Wyniki dla algorytmu Nearest Neighbour w zaleznosci od ilosci watkow')
    plt.plot(x_line, nnMulti, color='red')
    plt.savefig('nnMultiThread_d657.png')
    plt.show()
    plt.clf()

    max = 30
    x_line = [0 for n in range(max)]
    nnMulti = [0 for n in range(max)]

    file1 = open('mulithreadNNComp_d1291.csv')
    index = 0
    file1.readline()
    for n in range(max):
        line = file1.readline().split(';')
        x_line[index] = int(line[1])
        nnMulti[index] = float(line[0])
        index += 1

    plt.axis([0, 31, 0, 16000])
    plt.title('Wyniki dla algorytmu Nearest Neighbour w zaleznosci od ilosci watkow')
    plt.plot(x_line, nnMulti, color='red')
    plt.savefig('nnMultiThread_d1291.png')
    plt.show()
    plt.clf()


draw()
