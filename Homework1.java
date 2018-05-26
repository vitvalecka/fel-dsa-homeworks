class Homework1 {

    public int[] mergesort(int[] array) {
        boolean alreadySorted = array.length <= 1;
        if (alreadySorted) {
            return array;
        } else {
            int[] firstHalf = getFirstHalfOf(array);
            int[] secondHalf = getSecondHalfOf(array);
            int[] firstHalfSorted = mergesort(firstHalf);
            int[] secondHalfSorted = mergesort(secondHalf);

            return merge(firstHalfSorted, secondHalfSorted);
        }
    }

    public int[] getFirstHalfOf(int[] array) {
        int length = (array.length + 1) / 2;
        int[] result = new int[length];
        
        for (int i = 0; i < result.length; i++) {
            result[i] = array[i];
        }
        
        return result;
    }

    public int[] getSecondHalfOf(int[] array) {
        int length = array.length / 2;
        int[] result = new int[length];
        int startingPossition = (array.length + 1) / 2;
        int endingPossition = startingPossition + result.length;
        
        for (int i = startingPossition; i < endingPossition; i++) {
            result[i - startingPossition] = array[i];
        }
        
        return result;
    }

    public int[] merge(int[] firstHalf, int[] secondHalf) {
        int first = 0;
        int second = 0;
        
        int totalLength = firstHalf.length + secondHalf.length;
        int[] result = new int[totalLength];
        int resultIndex = 0;
        
        while (resultIndex < result.length) {
            if (first == firstHalf.length) {
                result[resultIndex] = secondHalf[second];
                second++;
            }
            else
            {
                if (second == secondHalf.length) {
                    result[resultIndex] = firstHalf[first];
                    first++;
                }
                else
                {
                    if (firstHalf[first] < secondHalf[second]) {
                        result[resultIndex] = firstHalf[first];
                        first++;
                    }
                    else
                    {
                        result[resultIndex] = secondHalf[second];
                        second++;
                    }
                }
            }
            
            resultIndex++;
        }
        
        return result;
    }
    
}