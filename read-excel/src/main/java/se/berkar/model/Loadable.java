package se.berkar.model;

import java.util.Map;

public interface Loadable {

    void load(Map<String, String> theProperties) throws Exception;

}
