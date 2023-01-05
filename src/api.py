import requests
from enum import Enum



def _get_API_keys(path: str = './assets/keys.txt') -> list[str]:
    """ Retrieve localy stored api keys

    Get api keys from txt file

    Each line in the file cpntains a different api key

    Parameters
    ---
    path : str, optional
        path to the txt file
    """

    with open(path, 'r', encoding = 'utf8') as f:
        k = f.readlines()
    return k


class _keys(Enum):
    """ Private enum containing api keys

    """

    COLLECTION = _get_API_keys()
    STOCK = COLLECTION[0]
    NEWS = COLLECTION[1]



def send_HTTP_request(url: str) -> any:
    """ Request json object given a url

    Parameters
    ---
    url : str, required
        url to be requested
    
    Return
    ---
    json object
    """

    response = requests.get(url)
    data = response.json()
    return data


def get_stock_data(symbol: str, interval: str = '5min', function: str = 'TIME_SERIES_INTRADAY', apikey: str = "default") -> any:
    """ Retrieve stock data from alphavantage api

    Parameters
    ---
    symbol : str, required

    interval : str, optional

    function : str, optional

    apikey : str, optional
    """

    apikey = _keys.STOCK.value if apikey == "default" else apikey
    data = send_HTTP_request(f'https://www.alphavantage.co/query?function={function}&symbol={symbol}&interval={interval}&apikey={apikey}')
    return data


def get_news_headlines(category: str = 'business', country: str = 'us', pagesize: int = 5, apikey: str = "default") -> any:
    """ Retrieve the latest headlines from newsapi

    Parameters
    ---
    category : str, optional
        possible categories:
            - business
            - entertainment
            - general
            - health
            - science
            - sports
            - technology
    
    country : str, optional

    pagesize : int, optional

    apikey : str, optional
    """

    apikey = _keys.NEWS.value if apikey == "default" else apikey
    data = send_HTTP_request(f'https://newsapi.org/v2/top-headlines?country={country}&category={category}&pageSize={pagesize}&apiKey={apikey}')
    return data


if __name__ == '__main__':
    # ibm5m = get_stock_data(keys[0], 'AAPL', '5min')
    # print(ibm5m)
    # news_headlines = get_news_headlines()
    # print(news_headlines)
    print("api.py")