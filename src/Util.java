import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Util {
    public static List<Integer> generateRandomList(int size) {
        Random random = new Random();
        List<Integer> list = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(size) + 1); // Generates numbers from 1 to size
        }

        return list;
    }

    public static void partialSort(List<Integer> list, double percentage) {
        int sortSize = (int) (list.size() * percentage);
        List<Integer> subList = list.subList(0, sortSize);
        Collections.sort(subList);
    }

    public static void reverseSort(List<Integer> list) {
        Collections.sort(list);
        Collections.reverse(list);
    }
}
