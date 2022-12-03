import matplotlib.pyplot as plt
import sys
import requests

def main():
    xi = []
    yi = []
    with open(sys.argv[1]) as f:
        xs = f.readline().split()
        ys = f.readline().split()
        e = f.readline()
        for i in range(len(xs)):
            xi.append(float(xs[i]))
            yi.append(float(ys[i]))
        
    # f = plt.figure()
    # f.set_title(e)
    plt.title(e)
    plt.plot(xi, yi)
    # ax.legend()
    plt.show()
    plt.savefig("./assets/stkimg.png")


def get_data(apikey:str, symbol:str, interval:str='5min', function:str='TIME_SERIES_INTRADAY') -> any:
    url = f'https://www.alphavantage.co/query?function={function}&symbol={symbol}&interval={interval}&apikey={apikey}'
    r = requests.get(url)
    data = r.json()
    return data

def get_API_key(path:str='./assets/keys.txt'):
    with open(path, 'r', encoding='utf8') as f:
        k = f.readlines()[0]
    return k


if __name__ == '__main__':
    # main()
    ibm5m = get_data(get_API_key(), 'AAPL', '5min')
    print(ibm5m)