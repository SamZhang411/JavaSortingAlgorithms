import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Qingyuan Zhang
 * @version 1.0
 * @userid qzhang417
 * @GTID 903497782
 * <p>
 * Collaborators: N/A
 * <p>
 * Resources: N/A
 */
public class Sorting {

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                  null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array. Cannot use null comparator!");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int indexM = i;
            for (int n = i + 1; n < arr.length; n++) {
                if (comparator.compare(arr[n], arr[indexM]) < 0) {
                    indexM = n;
                }
            }
            T data = arr[indexM];
            arr[indexM] = arr[i];
            arr[i] = data;
        }
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                  null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array. Cannot use null comparator!");
        } else if (arr.length <= 1) {
            return;
        }
        int startInd = 0;
        int endInd = arr.length - 1;
        int lastSwap = 0;
        boolean swapsMade = true;
        while (swapsMade) {
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    lastSwap = i;
                }
            }
            endInd = lastSwap;
            if (swapsMade) {
                swapsMade = false;
                for (int n = endInd - 1; n >= startInd; n--) {
                    if (comparator.compare(arr[n], arr[n + 1]) > 0) {
                        T data = arr[n];
                        arr[n] = arr[n + 1];
                        arr[n + 1] = data;
                        swapsMade = true;
                        lastSwap = n + 1;
                    }
                }
                startInd = lastSwap;
            }
        }
    }


    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                  null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array. Cannot use null comparator!");
        }
        if (arr.length == 1) {
            return;
        } else {
            int length = arr.length;
            int midInd = length / 2;
            T[] left = (T[]) new Object[midInd];
            T[] right = (T[]) new Object[length - left.length];
            for (int m = 0; m <= midInd - 1; m++) {
                left[m] = arr[m];
            }
            for (int n = midInd; n <= length - 1; n++) {
                right[n - midInd] = arr[n];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            int i = 0;
            int j = 0;
            while (i < left.length && j < right.length) {
                if (comparator.compare(left[i], right[j]) <= 0) {
                    arr[i + j] = left[i];
                    i++;
                } else {
                    arr[i + j] = right[j];
                    j++;
                }
            }
            while (i < left.length) {
                arr[i + j] = left[i];
                i++;
            }
            while (j < right.length) {
                arr[i + j] = right[j];
                j++;
            }
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException(
                "Cannot sort null array! Cannot use a null comparator! Cannot input a null random variable.");
        } else if (arr.length <= 1) {
            return;
        } else {
            qSHelper(arr, comparator, rand, 0, arr.length - 1);
        }
    }

    /**
     * Helper method for quicksort.
     *
     * @param arr        the array to be sorted
     * @param comparator the comparator used to compare data in array
     * @param rand       random objects to select pivot
     * @param min        index for start of the array
     * @param max        index for end of array
     */
    public static <T> void qSHelper(T[] arr, Comparator<T> comparator, Random rand, int min, int max) {
        if (max - min <= 0) {
            return;
        }
        int intP = rand.nextInt((max - min) + 1) + min;
        T valP = arr[intP];
        T data = arr[min];
        arr[min] = valP;
        arr[intP] = data;
        int i = min + 1;
        int j = max;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], valP) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], valP) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp2 = arr[i];
                arr[i] = arr[j];
                arr[j] = temp2;
                i++;
                j--;
            }
        }
        T temp3 = arr[min];
        arr[min] = arr[j];
        arr[j] = temp3;
        qSHelper(arr, comparator, rand, min, j - 1);
        qSHelper(arr, comparator, rand, j + 1, max);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array!");
        }
        ArrayList[] list = new ArrayList[19];
        int exp = 1;
        int n = 0;
        int t = 1;
        for (int i = 0; i < arr.length; i++) {
            int curr = arr[i];
            while (curr != 0) {
                curr /= 10;
                n++;
            }
            if (t < n) {
                t = n++;
            }
            n = 0;
        }

        for (int j = 0; j < 19; j++) {
            list[j] = new ArrayList<Integer>();
        }

        for (int k = 0; k <= t; k++) {
            for (int l = 0; l < arr.length; l++) {
                list[arr[l] / exp % 10 + 9].add(arr[l]);
            }
            exp *= 10;
            int index = 0;

            for (ArrayList<Integer> item : list) {
                while (!item.isEmpty()) {
                    arr[index] = item.remove(0);
                    index++;
                }
            }
        }
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(data);
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot sort through null data!");
        }
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = queue.poll();
        }
        return arr;
    }
}
