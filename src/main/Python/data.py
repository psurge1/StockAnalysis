import numpy as np

def apply_f_to_list(function, *sample: list) -> list:
    f_list = list(map(function, *sample))
    return f_list

def summation(function, *sample: list[float]) -> float:
    f_applied_list: list[float] = apply_f_to_list(function, *sample)
    summ: float = sum(f_applied_list)
    return summ

def variance(sample: list[float]) -> float:
    v: float = float(np.var(sample, ddof=1))
    # mean: float = float(np.mean(sample))
    # summ: float = summation(lambda x: (x - mean) ** 2, sample)
    # sample_size: int = len(sample)
    # v = summ / (sample_size - 1)
    return v

def covariance(sample_1: list[float], sample_2: list[float]) -> float:
    n = len(sample_1)

    if len(sample_2) != n:
        return 0
    
    avg_1: float = float(np.average(sample_1))
    avg_2: float = float(np.average(sample_2))
    tv: float = summation(lambda x, y: (x - avg_1) * (y - avg_2), sample_1, sample_2)
    return tv / (n - 1)

def stdev(sample: list[float]) -> float:
    standard_deviation: float = float(np.std(sample, ddof=1))
    # o2 = variance(sample)
    # standard_deviation = np.sqrt(o2)
    return standard_deviation

def daily_return(sample_open: list[float], sample_close: list[float]) -> list[float]:
    def R(open: float, close: float):
        return (close - open) / open
    return list(map(lambda o, c: R(o, c), sample_open, sample_close))

def beta4(open_1: list[float], close_1: list[float], open_2: list[float], close_2: list[float]) -> float:
    return_rate_1: list[float] = daily_return(open_1, close_1)
    return_rate_2: list[float] = daily_return(open_2, close_2)
    return beta2(return_rate_1, return_rate_2)

def beta2(return_rate_primary: list[float], return_rate_benchmark: list[float]) -> float:
    primary_benchmark_covariance: float = covariance(return_rate_primary, return_rate_benchmark)
    primary_variance: float = variance(return_rate_primary)
    return primary_benchmark_covariance / primary_variance

def sma(sample: list[float], iteration: int) -> tuple[list[float], list[float]]:
    sma_arr: list[float] = [sample[0]]
    base_arr: list[float] = [0]
    sample_size: float = len(sample)
    for i in range(0, sample_size, iteration):
        average_i: float = 0
        k: int = i + iteration
        diff: int = sample_size - k
        if diff < 0:
            average_i = np.average(sample[i:k + diff])
            k = i + diff
        else:
            average_i = np.average(sample[i:k])
        sma_arr.append(average_i)
        base_arr.append(k)
    return sma_arr, base_arr
