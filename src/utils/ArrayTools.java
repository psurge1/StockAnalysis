package utils;

import java.util.Arrays;

public class ArrayTools {
    public static int exists(String s, String[] a)
    {
        Arrays.parallelSort(a);
        return Arrays.binarySearch(a, s);
    }
}