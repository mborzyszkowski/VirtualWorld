package model;

import java.io.Serializable;
import java.util.ArrayList;


public class ActionLogger implements Serializable{

    private ArrayList<String> actionDescriptions;

    public ActionLogger() {
        this.actionDescriptions = new ArrayList<String>();
    }

    public void addDescriptions(String description) {
        this.actionDescriptions.add(description);
    }

    @Override
    public String toString() {
        String result = "";
        result = actionDescriptions.stream().map((act) -> act + "\n").reduce(result, String::concat);
        return result;
    }

    public void clear() {
        this.actionDescriptions.clear();
    }
    
    public boolean isEmpty(){
        return actionDescriptions.isEmpty();
    }
}
