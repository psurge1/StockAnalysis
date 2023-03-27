from api import get_news_headlines, get_stock_data, get_stock_info
from paths import paths
import py_utils as pu
import data as dt

import sys
import re
import time

from matplotlib import pyplot as plt
import numpy as np



def plot_graph(xs: list[list[float]], ys: list[list[float]], labels: list[list[str]], colors: list[list[str]], path_to_graph: str) -> str:
    len_xs = len(xs)
    if len_xs != len(ys) or len_xs != len(labels) or len_xs != len(colors):
        raise ValueError("Invalid Arrays")
    
    for i in reversed(range(len_xs)):
        plt.plot(xs[i], ys[i], c=colors[i], label=labels[i])
    plt.legend()
    plt.savefig(path_to_graph, transparent=True, dpi=400)

    return path_to_graph


def retrieve(data_category: str, **kwargs: str) -> dict[str, str] | str:
    """ Wrapper function to get data from various apis

    Retrieves data from various apis given specified key word arguments (kwargs)
    Writes output to specified paths

    Parameters
    ---
    data_category : str, required
        type of data to be returned
        either 'news' or 'stock'
    
    **kwargs
        arguments for the http request (excluding api keys)
    """

    print(paths.body())

    match (data_category.lower()):
        case "stock":
            ## RETRIEVE STOCK JSON
            stock_data: dict[str, str] = get_stock_data(**kwargs)
            data_keys: list[str] = list(stock_data.keys())
            s = ""
            s += kwargs["symbol"] + "\n\n"
            s += str(stock_data)
            pu.write_to_path(paths.value("DEBUGLOG"), s)

            ## CHECK FOR INVALID STOCK SYMBOLS
            if data_keys[0] == "Error Message":
                pu.write_to_path(paths.value("STOCK"), "NONE")
                pu.write_to_path(paths.value("METADATA"), "NONE")
                raise ValueError("Invalid Stock Symbol")
            elif data_keys[0] == "Note":
                pu.write_to_path(paths.value("STOCK"), "EXCEEDED")
                pu.write_to_path(paths.value("METADATA"), "EXCEEDED")
                raise LookupError("Exceeded API call frequency. AlphaVantage API: Our standard API call frequency is 5 calls per minute and 500 calls per day.")
            
            ## METADATA
            md: str = ""
            metadata_dict: dict[str, str] = stock_data["Meta Data"]
            meta_keys: list[str] = list(metadata_dict.keys())

            for key in meta_keys:
                md += key + ": " + metadata_dict[key] + "\n"
            pu.write_to_path(paths.value("METADATA"), md)

            ## STOCK VALUES
            open_key: str = '1. open'
            close_key: str = '4. close'

            stock_value_data: dict[str, str] = stock_data[data_keys[1]] # stock values
            sv_keys: list[str] = list(stock_value_data.keys()) # stock dates
            
            ## LATEST AVAILABLE STOCK VALUE
            latest: dict[str, str] = stock_value_data[sv_keys[0]] # latest date for value data
            latest_keys: list[str] = list(latest.keys()) # open, high, low, close etc.
            stock_l_s: str = sv_keys[0] + "\n"
            for lkey in latest_keys:
                stock_l_s += lkey + ": " + latest[lkey] + "\n"
            stock_l_s += "\n"
            pu.write_to_path(paths.value("STOCK"), stock_l_s)

            
            ## STOCK GRAPH
            ### DAILY RETURN RATE CALCULATIONS
            stock_open_ys: list[float] = [] # list of stock open values
            stock_close_ys: list[float] = [] # list of stock close values
            for sv_key in sv_keys:
                stock_value_dict_at_sv_key: dict[str, str] = stock_value_data[sv_key]
                stock_open_ys.append(float(stock_value_dict_at_sv_key[open_key]))
                stock_close_ys.append(float(stock_value_dict_at_sv_key[close_key]))
            num_y: int = len(stock_close_ys)
            stock_close_xs: np = np.linspace(0, num_y, num_y)
            
            stock_sma_y, stock_sma_x = dt.sma(stock_close_ys, 10)
            plot_graph([stock_close_xs, stock_sma_x], [stock_close_ys, stock_sma_y], [kwargs["symbol"], 'Simple Moving Average'], ['black', 'red'], paths.value("STOCKGRAPH"))
            pu.write_to_path(paths.value("POINTS"), kwargs["symbol"] + "\n" + pu.format_list_for_file(list(stock_close_xs)) + "\n" + pu.format_list_for_file(stock_close_ys))

            # plot_graph([stock_close_xs], [stock_close_ys], ['black'], paths.value("STOCKGRAPH"))
            
            # pu.write_to_path(paths.value("POINTS"), str(open_ys) + "\n" + str(ys))
            
            ## STOCK DESCRIPTION
            symbl: str = kwargs['symbol']
            stock_info: dict[str, str] = get_stock_info(symbol=symbl)
            s_matches: list[dict[str, str]] = stock_info['bestMatches']
            desc_s: str = ""
            # if len(s_matches) != 0:
            s_match: dict[str, str] = s_matches[0]
            s_match_keys: list[str] = list(s_match.keys())
            for i in range(len(s_match_keys) - 1):
                desc_s += s_match_keys[i].casefold() + ": " + s_match[s_match_keys[i]] + "\n"
                # desc_s += s_match_keys[i].split()[-1].casefold() + ": " + s_match[s_match_keys[i]] + "\n"
            
            ## VOLATILITY MEASUREMENT
            # S&P 500 market data for "market movement"
            spy_kwargs: dict[str, str] = kwargs
            spy_kwargs["symbol"] = 'SPY'
            spy_data: dict[str, str] = get_stock_data(**spy_kwargs)
            spy_data_keys: list[str] = list(spy_data.keys())

            for i in range(14):
                if spy_data_keys[0] != "Meta Data":
                    time.sleep(5)
                    spy_data: dict[str, str] = get_stock_data(**spy_kwargs)
                    spy_data_keys: list[str] = list(spy_data.keys())
                else:
                    i = 14
            
            spy_value_data: dict[str, str] = spy_data[spy_data_keys[1]]
            spy_keys: list[str] = list(spy_value_data.keys())
            spy_open_ys: list[float] = [] # list of stock open values
            spy_close_ys: list[float] = [] # list of stock close values
            for spy_key in spy_keys:
                spy_value_dict_at_sv_key: dict[str, str] = spy_value_data[spy_key]
                spy_open_ys.append(float(spy_value_dict_at_sv_key[open_key]))
                spy_close_ys.append(float(spy_value_dict_at_sv_key[close_key]))
            
            ## VOLATILITY
            desc_s += "\n--\nVolatility\n"

            stock_5day_beta = dt.beta4(stock_open_ys, stock_close_ys, spy_open_ys, spy_close_ys)
            desc_s += "beta (5day): " + str(stock_5day_beta) + "\n"

            stock_5day_variance = dt.variance(dt.daily_return(stock_open_ys, stock_close_ys))
            desc_s += "variance: " + str(stock_5day_variance) + "\n"

            stock_5day_standard_deviation = dt.stdev(dt.daily_return(stock_open_ys, stock_close_ys))
            desc_s += "standard deviation: " + str(stock_5day_standard_deviation) + "\n\n"

            desc_s += "Start Date: " + sv_keys[-1] + "\n"
            desc_s += "End Date:  " + sv_keys[0]

            pu.write_to_path(paths.value("DESCRIPTION"), desc_s)

            return stock_data
        

        case "sma":
            # py Python/retrieve.py sma symbol=#### iteration=## s_close_x="3 4 5" s_close_y="1 2 3"
            stock_symbol = kwargs["symbol"]
            s_close_x = pu.str_list_to_float_list(kwargs["s_close_x"].strip().split())
            s_close_y = pu.str_list_to_float_list(kwargs["s_close_y"].strip().split())
            stock_sma_y, stock_sma_x = dt.sma(s_close_y, int(kwargs["iteration"]))
            plot_graph([s_close_x, stock_sma_x], [s_close_y, stock_sma_y], [stock_symbol, 'Simple Moving Average'], ['black', 'red'], paths.value("STOCKGRAPH"))
        

        case "news":
            ## RETRIEVE NEWS JSON
            news_d: dict[str, str] = get_news_headlines(**kwargs)

            ## VERIFY HEADLINES EXIST
            if news_d.get("status") != "ok":
                raise ValueError("NewsAPI status not ok")

            ## GET AND FORMAT ARTICLES
            articles: list[dict[str, str]] = news_d.get("articles")
            articles_list: str = '"sourceName", "author", "title", "description", "url", "urlToImage", "publishedAt", "content"\n'
            for i in range(len(articles)):
                articles_list +=  '"'  +  re.sub('"', "'", re.sub('\r', '', re.sub('\n', '', str(  articles[i].get('source').get('name')  ))))  +  '"'  +  ','
                articles_list +=  '"'  +  re.sub('"', "'", re.sub('\r', '', re.sub('\n', '', str(  articles[i].get('author')              ))))  +  '"'  +  ','
                articles_list +=  '"'  +  re.sub('"', "'", re.sub('\r', '', re.sub('\n', '', str(  articles[i].get('title')               ))))  +  '"'  +  ','
                articles_list +=  '"'  +  re.sub('"', "'", re.sub('\r', '', re.sub('\n', '', str(  articles[i].get('description')         ))))  +  '"'  +  ','
                articles_list +=  '"'  +  re.sub('"', "'", re.sub('\r', '', re.sub('\n', '', str(  articles[i].get('url')                 ))))  +  '"'  +  ','
                articles_list +=  '"'  +  re.sub('"', "'", re.sub('\r', '', re.sub('\n', '', str(  articles[i].get('urlToImage')          ))))  +  '"'  +  ','
                articles_list +=  '"'  +  re.sub('"', "'", re.sub('\r', '', re.sub('\n', '', str(  articles[i].get('publishedAt')         ))))  +  '"'  +  ','
                articles_list +=  '"'  +  re.sub('"', "'", re.sub('\r', '', re.sub('\n', '', str(  articles[i].get('content')             ))))  +  '"'
                articles_list += "\n"
            pu.write_to_path(paths.value("NEWS"), articles_list)
            return articles_list
        

        case _:
            raise ValueError("invalid data category")



if __name__ == '__main__':
    # py retrieve.py stock symbol=[str, required] interval=[str, optional] function=[str, optional] apikey=[str, optional]
    # py retrieve.py news category=[str, optional] country=[str, optional] pagesize=[int, optional] apikey=[str, optional]
    data: dict[str, str] | str = retrieve(sys.argv[1], **dict(arg.split('=') for arg in sys.argv[2:]))
    # print(f"DATA: \n\n", data)
    # print("Hello World: retrieve.py")