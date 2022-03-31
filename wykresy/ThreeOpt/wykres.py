import matplotlib.pyplot as plt


def draw():
    max = 11153
    x_line = [0 for n in range(max)]
    threeOpt = [0 for n in range(max)]

    file1 = open('threeOptTimeComparison.csv')
    index = 0
    file1.readline()
    for n in range(max):
        line = file1.readline().split(';')
        x_line[index] = int(line[0])/1000
        threeOpt[index] = int(line[1])
        index += 1

    plt.axis([0, 5150, 50000, 151000])
    plt.title('Wyniki dla algorytmu 3-OPT w zaleznosci od czasu w s')
    plt.plot(x_line, threeOpt, color='red')
    plt.savefig('threeOptTimeComparison.png')
    plt.show()
    plt.clf()


draw()
