package algorithms;

import java.util.List;

public class ShellSort {

    public static void shellSort(List<Integer> list) {
        int n = list.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = list.get(i);
                int j;
                for (j = i; j >= gap && list.get(j - gap) > temp; j -= gap) {
                    list.set(j, list.get(j - gap));
                }
                list.set(j, temp);
            }
        }
    }


}

