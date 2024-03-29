from paths import paths

import requests
from enum import Enum



def _get_API_keys(path: str = paths.value("KEYS")) -> list[str]:
    """ Retrieve localy stored api keys

    Get api keys from txt file

    Each line in the file cpntains a different api key

    Parameters
    ---
    path : str, optional
        path to the txt file
    """

    with open(path, 'r', encoding = 'utf8') as f:
        k: list[str] = f.readlines()
    return k


class _keys(Enum):
    """ Private enum containing api keys

    """

    COLLECTION = _get_API_keys()
    STOCK = COLLECTION[0]
    NEWS = COLLECTION[1]



def send_HTTP_request(url: str) -> dict[str, str]:
    """ Request json object given a url

    Parameters
    ---
    url : str, required
        url to be requested
    
    Return
    ---
    json object : dict
    """

    response = requests.get(url)
    data: dict[str, str] = response.json()
    return data


def get_stock_data(symbol: str, interval: str = '60min', function: str = 'TIME_SERIES_INTRADAY', apikey: str = "default") -> dict[str, str]:
    """ Retrieve stock data from alphavantage api

    Parameters
    ---
    symbol : str, required

    interval : str, optional

    function : str, optional

    apikey : str, optional
    
    Return
    ---
    json object : dict
    """

    apikey: str = _keys.STOCK.value if apikey == "default" else apikey
    data: dict[str, str] = send_HTTP_request(f'https://www.alphavantage.co/query?function={function}&symbol={symbol}&interval={interval}&apikey={apikey}')
    return data


def get_stock_info(symbol: str, apikey: str = "default") -> dict[str, str]:
    """ Retrieve stock company name and description from alphavantage api

    Parameters
    ---
    symbol : str, required

    apikey : str, optional
    
    Return
    ---
    json object : dict
    """

    apikey: str = _keys.STOCK.value if apikey == "default" else apikey
    data: dict[str, str] = send_HTTP_request(f'https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords={symbol}&apikey={apikey}')
    return data


def get_news_headlines(category: str = 'business', country: str = 'us', pagesize: int = 5, apikey: str = "default") -> dict[str, str]:
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
    
    Return
    ---
    json object : dict
    """

    apikey: str = _keys.NEWS.value if apikey == "default" else apikey
    data: dict[str, str] = send_HTTP_request(f'https://newsapi.org/v2/top-headlines?country={country}&category={category}&pageSize={pagesize}&apiKey={apikey}')
    return data



if __name__ == '__main__':
    # ibm5m: dict[str, str] = get_stock_data(keys[0], 'AAPL', '5min')
    # print(ibm5m)
    # news_headlines = get_news_headlines()
    # print(news_headlines)
    print("Hello World: api.py")