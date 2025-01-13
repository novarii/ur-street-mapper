/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */

public class URPriorityQueue<T extends Comparable<T>> implements UR_Heap<T> {
    private T[] heapArr;
    private int currIndex;

    public URPriorityQueue() {
        heapArr = (T[]) new Comparable[2];
        currIndex = 0;
    }

    public URPriorityQueue(int size) {
        heapArr = (T[]) new Comparable[size];
        currIndex = 0;
    }

    public URPriorityQueue(T[] arr) {
        heapArr = (T[]) new Comparable[arr.length * 2];
        currIndex = arr.length;

        for (int i = 0; i < currIndex; i++) {
            if (arr[i] == null) {
                throw new IllegalArgumentException("Null elements not allowed.");
            }
            heapArr[i] = arr[i];
        }

        heapify();
    }

    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't insert null elements.");
        }

        if (currIndex >= heapArr.length) {
            resize();
        }

        heapArr[currIndex] = item;
        bubbleUp(currIndex);
        currIndex++;
    }

    // algorithm inspired from zybooks csc 172: data structures and algorithms chapter 8.2 heaps using arrays
    private void bubbleUp(int nodeIndex) {
        while (nodeIndex > 0) {
            int parentIndex = (nodeIndex - 1) / 2;

            if (heapArr[nodeIndex].compareTo( heapArr[parentIndex]) >= 1) {
                return;
            } else {
                T temp = heapArr[nodeIndex];
                heapArr[nodeIndex] = heapArr[parentIndex];
                heapArr[parentIndex] = temp;
                nodeIndex = parentIndex;
            }
        }
    }


    public boolean isEmpty() {
        return currIndex == 0;
    }

    public int size() {
        return currIndex;
    }

    private void resize() {
        int newSize = heapArr.length * 2;
        T[] newHeapArr = (T[]) new Comparable[newSize];

        for (int i = 0; i < currIndex; i++) {
            newHeapArr[i] = heapArr[i];
        }
        heapArr = newHeapArr;
    }

    public T deleteMin() {
        if (isEmpty()) {
            return null;
        }

        // Save the root element and move the last element to the root's place.
        T root = heapArr[0];
        heapArr[0] = heapArr[currIndex - 1];

        heapArr[currIndex - 1] = null;
        currIndex--;

        // If heap isn't empty after removal, fix heap property
        if (!isEmpty()) {
            bubbleDown(0);
        }

        return root;
    }

    public boolean delete(T item) {
        if (item == null || isEmpty()) {
            return false;
        }

        int removeIndex = -1;
        for (int i = 0; i < currIndex; i++) {
            if (heapArr[i].equals(item)) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) {
            return false;
        }

        heapArr[removeIndex] = heapArr[currIndex - 1];
        heapArr[currIndex - 1] = null;
        currIndex--;

        if (!isEmpty() && removeIndex < currIndex) {
            int parentIndex = (removeIndex - 1) / 2;

            if (removeIndex > 0 && heapArr[removeIndex].compareTo(heapArr[parentIndex]) < 0) {
                bubbleUp(removeIndex);
            } else {
                bubbleDown(removeIndex);
            }
        }

        return true;
    }

    public boolean contains(T item) {
        if (item == null) {
            return false;
        }

        for (int i = 0; i < currIndex; i++) {
            if (heapArr[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    private void bubbleDown(int nodeIndex) {
        int childIndex = 2 * nodeIndex + 1;
        T item = heapArr[nodeIndex];

        while (childIndex < currIndex) {
            T minItem = item;
            int minIndex = -1;
            for (int i = 0; i < 2 && i + childIndex < currIndex; i++) {
                if (heapArr[i + childIndex].compareTo(minItem) <= 0) {
                    minItem = heapArr[i + childIndex];
                    minIndex = i + childIndex;
                }
            }

            if (minItem.equals(item)) {
                return;
            } else {
                T temp = heapArr[nodeIndex];
                heapArr[nodeIndex] = heapArr[minIndex];
                heapArr[minIndex] = temp;
                nodeIndex = minIndex;
                childIndex = 2 * nodeIndex + 1;
            }
        }
    }

    private void heapify() {
        for (int i = currIndex / 2 - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    public void clear() {
        for (int i = 0; i < currIndex; i++) {
            heapArr[i] = null;
        }
        currIndex = 0;
    }

    public void printHeap() {
        for (int i = 0; i < currIndex; i++) {
            System.out.print(heapArr[i] + " ");
        }
        System.out.println();
    }


}
