import sys
import matplotlib as mpl
import matplotlib.pyplot as plt


mpl.rcParams['figure.dpi'] = 150
mpl.rcParams['figure.figsize'] = (3, 3)



def gen_py_chart(path_to_img: str, **kwargs):
    labels = kwargs.keys()
    values = list(map(float, kwargs.values()))
    explode = [0.01 for _ in labels]

    # autopct is changeable to determine the format of the pie chart labels
    plt.pie(values, explode=explode, labels=labels, autopct="%1.1f%%", startangle=90)
    plt.axis('equal')
    # plt.show()
    plt.savefig(path_to_img, transparent=True)


if __name__ == '__main__':
    data = gen_py_chart(sys.argv[1], **dict(arg.split('=') for arg in sys.argv[2:]))
    # Example cmd:
    #   py Python/plot.py [path] [industry]=[number] [industry2]=[number2] ... (etc)
    #   py Python/plot.py "../storage/diversitypie.png" Technology=1 "Oil and gas"=2 Pharmacy=1.5 ... (etc)