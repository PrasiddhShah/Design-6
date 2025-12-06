// Time Complexity : O(1)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode : yes 
// Any problem you faced while coding this :no

/*
Aprroach
We are using trie and a List of String at every trie node to get how many String are there at every node
but the list is of top 3 string only so that search will become O(1)

Idea is to insert word as well as to sort the top 3 string for that partculat node at insertion
as we will only we sort 4(max) string at a time sorting will be o(1)
to sort the string we use a custom comparator to take freq from the map and use that to sort

in a general trie we only add a word one time but not here we do the insert operation every time we get the
word, this won't update the trie node but it will update the top 3 strings at each node,
and for doing this we will call the insert function after updating the freq map

*/

class AutocompleteSystem {
    class TrieNode {
        HashMap<Character, TrieNode> children;
        List<String> top3;

        public TrieNode() {
            this.children = new HashMap<>();
            this.top3 = new ArrayList<>();
        }
    }

    private void insert(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!cur.children.containsKey(ch)) {
                cur.children.put(ch, new TrieNode());
            }
            cur = cur.children.get(ch);
            List<String> li = cur.top3;
            if (!li.contains(word)) {
                li.add(word);
            }
            Collections.sort(li, (a, b) -> {
                int fa = map.get(a);
                int fb = map.get(b);
                if (fa == fb) {
                    return a.compareTo(b);
                }
                return fb - fa;
            });
            if (li.size() > 3) {
                li.remove(3);
            }
        }
    }

    private List<String> search(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!cur.children.containsKey(ch)) {
                return new ArrayList<>();
            }
            cur = cur.children.get(ch);
        }
        return cur.top3;
    }

    TrieNode root;
    HashMap<String, Integer> map;
    StringBuilder searchTerm;

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.root = new TrieNode();
        this.map = new HashMap<>();
        this.searchTerm = new StringBuilder();
        for (int i = 0; i < sentences.length; i++) {
            String sentence = sentences[i];
            int time = times[i];
            map.put(sentence, map.getOrDefault(sentence, 0) + time);
            insert(sentence);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            String curString = searchTerm.toString();
            map.put(curString, map.getOrDefault(curString, 0) + 1);
            insert(curString);
            searchTerm = new StringBuilder();
            return new ArrayList<>();
        }
        searchTerm.append(c);
        String curSearch = searchTerm.toString();

        return search(curSearch);
    }
}