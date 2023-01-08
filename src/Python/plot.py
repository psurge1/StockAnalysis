import sys
import matplotlib as mpl
import matplotlib.pyplot as plt


mpl.rcParams['figure.dpi'] = 150



def gen_py_chart(path_to_img: str, **kwargs):
    labels = kwargs.keys()
    values = list(map(float, kwargs.values()))
    explode = [0.01 for key in labels]

    plt.pie(values, explode=explode, labels=labels, autopct="%1.1f%%", startangle=90)
    plt.axis('equal')
    plt.savefig(path_to_img)


if __name__ == '__main__':
    data = gen_py_chart(sys.argv[1], **dict(arg.split('=') for arg in sys.argv[2:]))
    # Example cmd:
    #   py Python/plot.py [path] [industry]=[number] [industry2]=[number2] ... (etc)
    #   py Python/plot.py "../assets/img.png" Technology=1 "Oil and gas"=2 Pharmacy=1.5 ... (etc)