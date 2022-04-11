/**
 * This  file contains two class, one is to implement power of two max heap
 * data structure and other is use to test that implementation.
 * TestTwoPowerMaxHeap class is used to check, Where power of two Max Heap
 * data structure which is implemented in Heap class is working properly or not.
 */
package practice;

import java.util.ArrayList;

public class TestTwoPowerMaxHeap {
    public static void main(String[] args) throws Exception {
        Heap heap = new Heap(10);
        heap.insert(5);
        heap.insert(6);
        heap.insert(65);
        heap.insert(56);
        heap.insert(10);
        heap.insert(66);
        heap.insert(77);
        heap.insert(70);
        heap.insert(63);
        heap.insert(64);
        heap.insert(93);
        System.out.println(heap.getHeap());
        System.out.println(heap.popMax());
        System.out.println(heap.getHeap());

        /**
         * Output of above operations :-
         * [93, 5, 6, 56, 10, 65, 66, 70, 63, 64, 77]
         * 93
         * [77, 5, 6, 56, 10, 65, 66, 70, 63, 64]
         */

    }
}


class Heap {
    /** Here i have chosen ArrayList to implement max heap with provided constraints,
     *  i.e each node in heap has 2^x child.
     *  Since ArrayList implements RandomAccess interface, so accessing elements in the list becomes faster
     *  which is used frequently in several functions/methods.
     *  I have implemented this data structure based on integers but can be easily transformed Generic, if needed.
     *  I have chosen ArrayList for one more reason, as it dynamic in nature and it has method which reduces
     *  its allocated space to its actual used space, Which means wastage will be less.
     */

    /**
     * Below field num_child is the number of child each parent node will have in the heap.
     * It is calculated as power of 2, power value is passed as constructor argument while
     * creating the instance of this heap.
     * And this field is made final so that at no point of time its gets changed,
     * otherwise our whole implementation will collapse.
     */
    private final int num_child;
    private ArrayList<Integer> heap;

    /**
     * Creates a heap with 2^X child. Here powerOfTwo is taken as power of 2,
     * which means that each node heap will have 2^powerOfTwo child nodes.
     *
     * @param powerOfTwo Provide positive value, which will taken as power of 2.
     */


    public Heap(int powerOfTwo) {
        this.num_child = (int) Math.pow(2, powerOfTwo);
        this.heap = new ArrayList<>();
    }

    /**
     * This function is used get location of parent node of any node in the ArrayList.
     * Its private because it is totally for use of implementation of other methods,
     * if its exposed unexpected value can create problem for other methods.
     *
     * @param index of child node.
     * @return index of parent node.
     */
    private int parentIndex(int index) {
        return ((index - 1) / num_child);
    }

    /**
     * This function is used get location of all child nodes of any node in the ArrayList.
     * Its private because it is totally for use of implementation of other methods,
     * if its exposed unexpected value can create problem for other methods.
     *
     * @param index of parent node.
     * @return array of child nodes indexes.
     */
    private int[] childIndexes(int index) {
        int[] childsIndexes = new int[num_child];
        for (int i = 1; i <= num_child; i++) {
            childsIndexes[i - 1] = (num_child * index + i);
        }
        return childsIndexes;
    }

    /**
     * This functions swaps the elements at index1 and index2.
     * Its is used by function like Heapify() and popMax().
     *
     * @param index1 index of element1.
     * @param index2 index of element2.
     */
    private void swap(int index1, int index2) {
        this.heap.set(index1, this.heap.set(index2, this.heap.get(index1)));
    }

    /**
     * This function is used to determine the size of heap,
     * i.e total number elements in the Heap.
     *
     * @return size of the Heap.
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * This function is used to insert a element into the Heap.
     * After insertion of every element Heap it makes changes in the heap to its max heap property and
     * also resize itself to its actual size.
     *
     * @param key provide integer element to insert into Heap.
     */
    public void insert(int key) {
        heap.add(key);
        heap.trimToSize();
        for (int i = size() - 1; i != 0 && heap.get(parentIndex(i)) < heap.get(i); ) {
            int currentKey = heap.get(i);
            int parentKey = heap.get(parentIndex(i));
            heap.set(i, parentKey);
            heap.set(parentIndex(i), currentKey);
            i = parentIndex(i);
        }
    }

    /**
     * This function is used to get Max Heap data structure in the for array representation.
     * Max element is at the first position.
     *
     * @return ArrayList of Integer, Array representation of a Max Heap.
     * @throws Exception throw exception if there is no element in Heap.
     */
    public ArrayList<Integer> getHeap() throws Exception {
        if (this.size() == 0)
            throw new Exception("No element in Heap");
        return this.heap;
    }

    /**
     * Heapify the Heap to maintain its Max Heap property.
     *
     * @param index of element which needs to be Heapified.
     */
    private void Heapify(int index) {
        int largestIndex = index;
        for (int i : childIndexes(index)) {
            if (i < size() && this.heap.get(i) > this.heap.get(largestIndex))
                largestIndex = i;
        }
        if (largestIndex != index) {
            swap(index, largestIndex);
            Heapify(largestIndex);
        }
    }

    /**
     * Remove and returns the Maximum element of the Heap, which is generally at the top of the Heap.
     *
     * @return Maximum element of the Heap.
     * @throws Exception throw exception if there is no element in Heap.
     */
    public int popMax() throws Exception {
        if (this.size() == 0)
            throw new Exception("No element in Heap");
        if (this.size() == 1) {
            return this.heap.remove(0);
        }
        swap(0, size() - 1);
        int max = this.heap.remove(size() - 1);
        Heapify(0);
        this.heap.trimToSize();
        return max;
    }
}
