import sys



class paths:
    enum_dict: dict[str, any] = {
        "NEWS": "C:/Users/suraj/Code/StockAnalysis/src/main/storage/data/news.csv",
        "STOCK": "C:/Users/suraj/Code/StockAnalysis/src/main/storage/data/lateststock.txt",
        "METADATA": "C:/Users/suraj/Code/StockAnalysis/src/main/storage/data/metadata.txt",
        "STOCKGRAPH": "C:/Users/suraj/Code/StockAnalysis/src/main/storage/stockgraph.png",
        "DESCRIPTION": "C:/Users/suraj/Code/StockAnalysis/src/main/storage/data/description.txt",
        "POINTS": "C:/Users/suraj/Code/StockAnalysis/src/main/storage/data/points.txt",
        "DEBUGLOG": "C:/Users/suraj/Code/StockAnalysis/src/main/storage/debuglog.txt",
        "KEYS": "C:/Users/suraj/Code/StockAnalysis/src/main/assets/keys.txt"
    }

    def set_enum(enum_key_value_dict: dict[str, any]):
        paths.enum_dict = enum_key_value_dict

    def value(keyword: str):
        return paths.enum_dict[keyword]

    def set(keyword: str, val: str):
        paths.enum_dict[keyword] = val

    def body():
        return paths.enum_dict

    def keys():
        return list(paths.enum_dict.keys())

    def values():
        return list(paths.enum_dict.values())

    def for_each(func):
        path_keys = paths.keys()
        for pkey in path_keys:
            paths.set(pkey, func(paths.value(pkey)))

# if __name__ == '__main__':
#     abs_path = "C:/Users/suraj/Code/StockAnalysis"
#     # command_line_arguments = dict(sys.argv[1].split('='))
#     # abs_path = dict["abs_path"]