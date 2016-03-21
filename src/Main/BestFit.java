package Main;

import java.util.*;

/**
 * This memory model allocates memory cells based on the best-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class BestFit extends Memory {
    private Status[] status;
    private Map<Pointer, Integer> hash;
    private LinkedList<Pointer> pointerList;
    private Pointer p;
    private int readPos;

    /**
     * Initializes an instance of a best fit-based memory.
     *
     * @param size The number of cells.
     */
    public BestFit(int size) {
        super(size);
        pointerList = new LinkedList<Pointer>();
        hash = new HashMap<>();
        status = new Status[size];
        for (int i = 0; i < status.length; i++) {
            status[i] = Status.Empty;
        }

    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    /**
     * Allocates a number of memory cells.
     *
     * @param size the number of cells to allocate.
     * @return The address of the first cell.
     */
    @Override
    public Pointer alloc(int size) {
        int i = 0;
        p = new Pointer(this);
        int bestAddress = checkFreeMemory(size);

        while (i < size && status[bestAddress] == Status.Empty) {
            p.pointAt(bestAddress);
            status[bestAddress] = Status.USED;
            bestAddress++;
            i++;
        }
        pointerList.add(p);
        hash.put(p, size);
        return p;
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p1) {
        int remover = 0;
        readPos = (p1.pointsAt());

        try {
            while (status[readPos] == Status.USED && hash.get(p1) - remover > 0) {
                status[readPos] = Status.Empty;
                readPos--;
                remover++;
            }
            pointerList.remove(p);
            hash.remove(p);

        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    /**
     * Prints a simple model of the memory. Example:
     * <p>
     * |    0 -  110 | Allocated
     * |  111 -  150 | Free
     * |  151 -  999 | Allocated
     * | 1000 - 1024 | Free
     */
    @Override
    public void printLayout() {
        for (int i = 0; i < status.length; i++) {

            if (status[i] == Status.USED) {
                System.out.println("----- Used Memory:" + i);
            }
            if (status[i] == Status.Empty) {
                System.out.println("+++++ Empty Memory:" + i);
            }

        }
    }

    public int checkFreeMemory(int size) {
        HashMap<Integer, Integer> list = new HashMap<>();
        int start = 0;
        int end = 0;
        int count = 0;

        for (int i = 0; i < status.length; i++) {
            if (status[i] == Status.Empty) {
                if (count == 0) {
                    start = i;
                    end = i;
                    count++;
                }
                end++;

                if (end - start == size) {
                    count = 0;
                    list.put(start, end - start);
                }
            }
            if (status[i] == Status.USED) {
                if (i < status.length) {
                    if (status[i + 1] == Status.Empty) {
                        count = 0;

                    }
                }

            }

        }
        Map<Integer, Integer> map = new TreeMap<>(sortByValues(list));
        Set set2 = map.entrySet();
        Iterator iterator2 = set2.iterator();
        if (iterator2.hasNext()) {
            Map.Entry me2 = (Map.Entry) iterator2.next();
            start = (int) me2.getKey();
        }

        return start;

    }
}