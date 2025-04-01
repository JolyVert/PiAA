import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.ShellSort;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static List<Integer> generateList(int size, double sortedRatio) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        if (sortedRatio == -1.0) {  // -1.0 represents reverse-sorted order
            Collections.reverse(list);
        } else {
            int shuffleStart = (int) (size * sortedRatio);
            Collections.shuffle(list.subList(shuffleStart, size));
        }

        return list;
    }


    private static long measureSortingTime(List<Integer> list) {
        List<Integer> temp = new ArrayList<>(list);
        long start = System.nanoTime();
        //MergeSort.mergeSort(temp, 0, temp.size() - 1);
        //QuickSort.quickSort(temp, 0, temp.size() - 1);
        ShellSort.shellSort(temp);
        return System.nanoTime() - start;
    }

    private static long getMedianTime(List<Long> times) {
        Collections.sort(times);
        int mid = times.size() / 2;
        return times.get(mid);
    }

    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 50000, 100000, 500000, 1000000};
        double[] sortedRatios = {0.0, 0.25, 0.5, 0.75, 0.95, 0.99, 0.997, 1.0, -1.0};
        int runs = 100;
        String outputFile = "ShellSort_Benchmark.txt";

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("Size,Sorted Ratio,Median Time (mks)\n");
            for (int size : sizes) {
                for (double ratio : sortedRatios) {
                    List<Long> times = new ArrayList<>();
                    for (int i = 0; i < runs; i++) {
                        List<Integer> list = generateList(size, ratio);
                        times.add(measureSortingTime(list));
                    }
                    long medianTime = getMedianTime(times);
                    writer.write(size + "," + ratio + "," + medianTime/1000 + "\n");
                }
            }
            System.out.println("Benchmark completed. Results saved to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
