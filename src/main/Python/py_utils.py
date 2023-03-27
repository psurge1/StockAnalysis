import json
import sys



def json_write(path: str, json_object: dict) -> None:
    jo = json.dumps(json_object)
    write_to_path(path, jo)


def write_to_path(path: str, item: any) -> None:
    with open(path, 'w', encoding='utf-8') as f:
        f.write(item)


def command_line_action(action_category: str, path: str, item: str) -> None:
    match(action_category):
        case "save":
            item_string: str = ""
            item_list: list[str] = item.split("-")
            for im in item_list:
                item_string += im + "\n"
            
            write_to_path(path, item_string)
        case "jsonsave":
            json_write(path, item)


def str_list_to_float_list(str_list: list[str]) -> list[float]:
    float_list: list[float] = []
    for i in range(len(str_list)):
        float_list.append(float(str_list[i]))
    return float_list

def float_list_to_str_list(float_list: list[float]) -> list[str]:
    str_list: list[str] = []
    for i in range(len(float_list)):
        str_list.append(str(float_list[i]))
    return str_list

def format_list_for_file(l: list[any]) -> str:
	list_string = ""
	for item_at_i in l:
		list_string += str(item_at_i) + " "
	return list_string



if __name__ == '__main__':
    command_line_action(sys.argv[1], **dict(arg.split('=') for arg in sys.argv[2:]))