from api import get_news_headlines, get_stock_data
import sys



def retrieve(data_category: str, **kwargs) -> any:
    """
    Parameters
    ---
    data_category : str, required
        type of data to be returned
        either 'news' or 'stock'
    
    **kwargs
        arguments for the http request (excluding apikey)
    
    Return
    ---
    json object (dictionary)
    """
    match (data_category.lower()):
        case "stock":
            return get_stock_data(**kwargs)
        case "news":
            return get_news_headlines(**kwargs)
        case _:
            raise ValueError("invalid data category")



if __name__ == '__main__':
    # data = retrieve(sys.argv[1], **dict(arg.split('=') for arg in sys.argv[2:]))
    # print(data)
    print("retrieve.py")