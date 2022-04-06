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


    max = 15
    x_line2 = [0 for n in range(max+1)]
    twoOpt = [0 for n in range(max+1)]

    file2 = open('twoOptTimeComparison.csv')
    index = 0
    file2.readline()
    for n in range(max):
        line = file2.readline().split(';')
        x_line2[index] = int(line[0])/1000
        twoOpt[index] = int(line[1])
        index += 1
    twoOpt[max] = twoOpt[max-1]
    x_line2[max] = 5150

    plt.axis([0, 5150, 50000, 151000])
    plt.title('Wyniki dla algorytmu 2-OPT i 3-OPT w zaleznosci od czasu w s')
    plt.plot(x_line, threeOpt, color='red', label='algorytm 3-OPT')
    plt.plot(x_line2, twoOpt, color='green', label='algorytm 2-OPT')
    plt.legend()
    plt.subplots_adjust(left=0.15)
    plt.xlabel('Czas wykonania algorytmów w sekundach')
    plt.xscale('log')
    plt.xlim(1, 6000)
    plt.ylim(55000, 160000)
    plt.ylabel('Najlepsze rozwiązanie')
    plt.savefig('twoAndThreeOptTimeComparison.png')
    plt.show()
    plt.clf()

draw()
