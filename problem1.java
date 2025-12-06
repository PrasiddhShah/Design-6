// Time Complexity : O(1)
// Space Complexity :O(1)
// Did this code successfully run on Leetcode : yes 
// Any problem you faced while coding this :no

/*
Approach
we making a Hashset to if have the number with us or not in O(1)
We using a Queue to the get can give you numbers in O(1) and also add them back in O(1)

when PhoneDirectory is initialised we add all the number to queue and set as we have all the number to give

get opertation will get the first number in queue and return that and remove it from set so we know that number is given out
check checks if we have the number with us in set
release adds back the number to the back of the queue and set
*/

class PhoneDirectory {
    HashSet<Integer> set;
    Queue<Integer> q;

    public PhoneDirectory(int maxNumbers) {
        this.set = new HashSet<>();
        this.q = new LinkedList<>();
        for (int i = 0; i < maxNumbers; i++) {
            q.add(i);
            set.add(i);
        }
    }

    public int get() {
        if (q.isEmpty())
            return -1;
        int re = q.poll();
        set.remove(re);
        return re;
    }

    public boolean check(int number) {
        return set.contains(number);
    }

    public void release(int number) {
        if (!set.contains(number)) {
            set.add(number);
            q.add(number);
        }
    }
}