import json
import re

def json_write(path: str, json_object: dict):
    jo = json.dumps(json_object)
    write_to_path(path, jo)

def write_to_path(path: str, item: any):
    with open(path, 'w', encoding='utf-8') as f:
        f.write(item)

# def apply_cmdline_params(function, *args, **kwargs):
#     return function(*args, **kwargs)