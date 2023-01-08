from api import get_news_headlines, get_stock_data
import py_utils as pu

import sys
import re
from enum import Enum



class _paths(Enum):
    NEWS = '../storage/data/news.csv'
    STOCK = '../storage/data/stock.txt'


def retrieve(data_category: str, **kwargs) -> dict:
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

    match (data_category.lower()):
        case "stock":
            return get_stock_data(**kwargs)
        case "news":
            news_d = get_news_headlines(**kwargs)

            if news_d.get("status") != "ok":
                raise ValueError("NewsAPI status not ok")

            articles: list[dict] = news_d.get("articles")

            # example test json
            # articles = [{'source': {'id': None, 'name': 'Motley Fool'}, 'author': 'Eric Volkman', 'title': "Why Walgreens Boots Alliance's Stock Surged Higher Today - The Motley Fool", 'description': 'The stock experiences an "it\'s not so bad after all" rally.', 'url': 'https://www.fool.com/investing/2023/01/06/why-walgreens-boots-alliances-stock-surged-higher/', 'urlToImage': 'https://g.foolcdn.com/editorial/images/715637/medical-professional-holding-dollar-sign-paperweight.jpg', 'publishedAt': '2023-01-06T22:28:00Z', 'content': "What happened\r\nYou can't keep a good stock down, and on Friday it was abundantly clear that scores of investors consider Walgreens Boots Alliance(WBA 4.04%) to be a good stock. A day after getting di▒ [+1606 chars]"}, {'source': {'id': 'ars-technica', 'name': 'Ars Technica'}, 'author': 'Dan Goodin', 'title': 'ChatGPT is enabling script kiddies to write functional malware - Ars Technica', 'description': "For a beta, ChatGPT isn't all that bad at writing fairly decent malware.", 'url': 'https://arstechnica.com/information-technology/2023/01/chatgpt-is-enabling-script-kiddies-to-write-functional-malware/', 'urlToImage': 'https://cdn.arstechnica.net/wp-content/uploads/2023/01/chatgpt-760x380.jpg', 'publishedAt': '2023-01-06T22:05:06Z', 'content': '28 with 0 posters participating\r\nSince its beta launch in November, AI chatbot ChatGPT has been used for a wide range of tasks, including writing poetry, technical papers, novels, and essays, plannin▒ [+5526 chars]'}, {'source': {'id': 'reuters', 'name': 'Reuters'}, 'author': None, 'title': 'Tesla slashes prices in China, other Asian markets as sales stumble - Reuters', 'description': 'Tesla <a href="https://www.reuters.com/companies/TSLA.O" target="_blank">(TSLA.O)</a> cut prices in China for the second time in less than three months on Friday, fuelling forecasts of a wider price war amid weaker demand in the world\'s largest autos market.', 'url': 'https://www.reuters.com/technology/tesla-cuts-prices-model-3-model-y-china-2023-01-06/', 'urlToImage': 'https://www.reuters.com/resizer/7Iocf_ATea8qihvMC0mkDtpWm0s=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/Y4ATO5S6IZNILD4AZ3NXX3GZIY.jpg', 'publishedAt': '2023-01-06T21:49:00Z', 'content': "SHANGHAI, Jan 6 (Reuters) - Tesla (TSLA.O) cut prices in China for the second time in less than three months on Friday, fuelling forecasts of a wider price war amid weaker demand in the world's large▒ [+4881 chars]"}, {'source': {'id': None, 'name': 'CNBC'}, 'author': 'Melissa Repko', 'title': "Macy's warns holiday-quarter sales will come in light, citing squeeze on shoppers' wallets - CNBC", 'description': "Macy's on Friday said consumers' budgets are under pressure and that it expects the squeeze to continue into this year.", 'url': 'https://www.cnbc.com/2023/01/06/macys-cuts-holiday-quarter-forecast-citing-squeeze-on-shoppers-wallets.html', 'urlToImage': 'https://image.cnbcfm.com/api/v1/image/106994202-1644609684532-abc.jpg?v=1673040825&w=1920&h=1080', 'publishedAt': '2023-01-06T21:33:45Z', 'content': "Macy's on Friday warned its holiday-quarter sales will come in on the lighter side, saying consumers' budgets are under pressure and that it anticipates that squeeze to continue into this year.\r\nThe ▒ [+2341 chars]"}, {'source': {'id': 'the-wall-street-journal', 'name': 'The Wall Street Journal'}, 'author': 'Alexander Osipovich, Caitlin McCabe', 'title': 'Dow Closes 700 Points Higher On Signs of Slowing Wage Growth - The Wall Street Journal', 'description': 'Major indexes wrap up first week of 2023 with gains', 'url': 'https://www.wsj.com/articles/global-stocks-markets-dow-update-01-06-2023-11673006793', 'urlToImage': 'https://images.wsj.net/im-698353/social', 'publishedAt': '2023-01-06T21:33:00Z', 'content': 'The Dow Jones Industrial Average rallied about 700 points on Friday after fresh data showed a \r\nslowdown in wage growth, an upbeat sign for the Federal Reserves battle against inflation that could ea▒ [+237 chars]'}]
            
            # debug
            print(f"\nARTICLES: \n\n", articles)

            articles_list = '"sourceName", "author", "title", "description", "url", "urlToImage", "publishedAt", "content"\n'
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
            pu.write_to_path(_paths.NEWS.value, articles_list)
            return articles_list
        case _:
            raise ValueError("invalid data category")



if __name__ == '__main__':
    data = retrieve(sys.argv[1], **dict(arg.split('=') for arg in sys.argv[2:]))
    print(f"\DATA: \n\n", data)
    # print("Hello World: retrieve.py")