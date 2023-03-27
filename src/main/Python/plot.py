import sys
import matplotlib as mpl
import matplotlib.pyplot as plt



def gen_py_chart(path_to_img: str, **kwargs: str) -> str:
    labels: list[str] = kwargs.keys()
    values: list[float] = list(map(float, kwargs.values()))

    s: float = sum(values)
    # dictionary of "percentage (3 decimal places)" : "label" pairs
    reverse_dict: dict[str, str] = dict(zip(map(lambda v: round(v, 3), map(lambda val: round(100 * val/s, 3), values)), labels))

    explode: list[float] = [0.01 for _ in labels]

    # autopct is changeable to determine the format of the pie chart labels
    # autopct_option1 = "%1.1f%%"
    autopct_option2 = lambda pct: reverse_dict[round(pct, 3)]
    plt.pie(values, explode=explode, autopct=autopct_option2, startangle=90, textprops={'fontsize': 6})
    # plt.pie(values, explode=explode, labels=labels, autopct=autopct_option1 pct: reverse_dict[round(pct, 3)], startangle=90)
    plt.axis('equal')
    # plt.show()
    plt.savefig(path_to_img, transparent=True)

    return path_to_img



if __name__ == '__main__':
    mpl.rcParams['figure.dpi'] = 150
    mpl.rcParams['figure.figsize'] = (float(sys.argv[2]), float(sys.argv[3]))
    data: str = gen_py_chart(sys.argv[1], **dict(arg.split('=') for arg in sys.argv[4:]))
    # Example cmd:
    #   py Python/plot.py [path] [industry]=[number] [industry2]=[number2] ... (etc)
    #   py Python/plot.py "../storage/diversitypie.png" Technology=1 "Oil and gas"=2 Pharmacy=1.5 ... (etc)