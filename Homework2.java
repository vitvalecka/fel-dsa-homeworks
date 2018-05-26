import java.util.Arrays;

class Homework2 {
    public int search(int first, int last, int what, int[] data) {
        if (first < 0 || last >= data.length || first >= data.length || first > last || first == last || data[first] == data[last]) {
            return -1;
        } else if (data[first] == what) {
            return first;
        }
        
        int index = (int)Math.round((first + (last - first) * ((double)what - data[first]) / (data[last] - data[first])));

        if (index >= data.length || index < first || index > last) {
            return -1;
        } else if (data[index] > what) {
            return search(first, index - 1, what, data);
        } else if (data[index] < what) {
            return search(index + 1, last, what, data);
        } else {
            return index;
        }
    }
}