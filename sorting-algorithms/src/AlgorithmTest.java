import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.ShellSort;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTests {

    List<Integer> list = new ArrayList<>(Arrays.asList(12, 34, 54, 2, 3, 3, 45, 2, 678, 1, 0, 24, 67, 8987, 34, 74, 24, 98, 246, 964, 8));

    @Test
    void testMergeSort() {
        List<Integer> expected = list.stream().sorted().toList();
        MergeSort.mergeSort(list, 0, list.size() - 1);
        assertEquals(expected, list);
    }

    @Test
    void testShellSort() {
        List<Integer> expected = list.stream().sorted().toList();
        ShellSort.shellSort(list);
        assertEquals(expected, list);
    }

    @Test
    void testQuickSort() {
        List<Integer> expected = list.stream().sorted().toList();
        QuickSort.quickSort(list, 0, list.size() - 1);
        assertEquals(expected, list);
    }
}
