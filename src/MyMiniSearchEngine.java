import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MyMiniSearchEngine {
    private Map<String, List<List<Integer>>> indexes;
    

    @SuppressWarnings("unused")
	private MyMiniSearchEngine() {
    }

    public MyMiniSearchEngine(List<String> documents) {
        index(documents);
    }

    
    //MAIN HOMEWORK METHODS
    //--------------------------------------------------------------------------------------------
    
    private void index(List<String> texts) {
    	indexes = new HashMap<String, List<List<Integer>>>();
    	
    	/* PLAN
    	 * ----
    	 * READ WITH A SCANNER .next() function
         * 
         * Given a list of strings (each representing a document) fill out the "index" map (above) with 
         * each word acting as a key within the map then within each list (representing each document) 
         * append an integer value to the inner list representing the location/index within the document
         * 
         */
    	
    	for(int i = 0; i < texts.size(); i++) {
    		//counter for the index of the current word in each document
			int indexCt = 0;
    		for(String word : texts.get(i).toLowerCase().split(" ")) {
    			//For when the word hasn't been identified before
    			if(indexes.get(word) == null ) {
    				indexes.put(word, new ArrayList<List<Integer>>());
    				for(int k = 0; k < texts.size(); k++) {
    					indexes.get(word).add(new ArrayList<Integer>());
    				}
    			}
    			indexes.get(word).get(i).add(indexCt);
    			indexCt++;
    		}
    	}
    }

    public List<Integer> search(String keyPhrase) {
    	//Creating the return List
    	List<Integer> docs = new ArrayList<Integer>();
    	//Creating List of Keys
    	List<String> keys = new ArrayList<String>();
    	//Creating List of lists that contains the documents in which each word exists
    	List<List<Integer>> instances = new  ArrayList<List<Integer>>();
    	
    	//populates key list
    	for(String token : keyPhrase.toLowerCase().split(" ")) {
    		if(indexes.get(token) == null) {
    			return docs;
    		}
    		keys.add(token);
        }
		
		//Finds and stores where each word exists
    	for(int i = 0; i < keys.size(); i++) {
    		instances.add(existsIn(indexes.get(keys.get(i))));
    	}
    	//Find Intersections and store
    	List<Integer> commons = new ArrayList<Integer>();
    	commons.addAll(instances.get(0));
    	for (ListIterator<List<Integer>> iter = instances.listIterator(1); iter.hasNext(); ) {
    	    commons.retainAll(iter.next());
    	}
    	
    	//check using indexes
    	for(int l = 0; l < commons.size(); l++) {
    		if(checkOrder(keys, commons.get(l)) == true) {
    			docs.add(commons.get(l));
    		}
    	}
    	return docs; 
    }
    
    //HELPER METHODS
    //--------------------------------------------------------------------------------------------
    
    private List<Integer> existsIn(List<List<Integer>> word){
    	List<Integer> in = new ArrayList<Integer>();
    	for(int i = 0; i < word.size(); i++) {
    		for(int j = 0; j < word.get(i).size(); j++) {
    			if(word.get(i).get(j) != null) {
    				in.add(i);
    			}
    		}
    	}
    	return in;
    }
    
    //Passed each of the item indexes in a list in order
    private boolean checkOrder(List<String> keys, int docNum) {
    	List<Integer> tempIndexes = new ArrayList<Integer>();
    	for(int i = 0; i < keys.size(); i++) {
    		for(int k = 0; k < indexes.get(keys.get(i)).get(docNum).size(); k++) {
    			int add = indexes.get(keys.get(i)).get(docNum).get(k) - keys.indexOf(keys.get(i));
    			tempIndexes.add(add);
    		}
    	}
    	//At this point all of the index values should appear in the tempIndexes List
    	//Just look for key.size() number of the same integer in a row
    	int identifier = tempIndexes.get(0); int count = 0;
    	for(int m = 0; m < tempIndexes.size(); m++) {
    		if(identifier == tempIndexes.get(m)) {
    			count++;
    			if(count == keys.size()) {return true;}
    		}
    		if(identifier != tempIndexes.get(m)) {
    			count = 0;
    			identifier = tempIndexes.get(m);
    		}
    	}
    	return false; 
    }
}
