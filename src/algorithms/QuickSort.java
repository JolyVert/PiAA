package algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuickSort {

    public static void quickSort(List<Integer> list, int left, int right) {
        while (left < right) {
            int pi = partition(list, left, right);

            if (pi - left < right - pi) {
                quickSort(list, left, pi - 1);
                left = pi + 1;
            } else {
                quickSort(list, pi + 1, right);
                right = pi - 1;
            }
        }
    }

    private static int partition(List<Integer> list, int low, int high) {
        int pivotIndex = medianOfThree(list, low, high);
        int pivot = list.get(pivotIndex);

        Collections.swap(list, pivotIndex, high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j) <= pivot) {
                i++;
                Collections.swap(list, i, j);
            }
        }

        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    private static int medianOfThree(List<Integer> list, int low, int high) {
        int mid = low + (high - low) / 2;

        int a = list.get(low), b = list.get(mid), c = list.get(high);
        int pivotIndex;

        if ((a > b) != (a > c)) pivotIndex = low;
        else if ((b > a) != (b > c)) pivotIndex = mid;
        else pivotIndex = high;

        return pivotIndex;
    }
}

