package se.berkar.model;

import java.util.Map;

public class ResultatX implements Loadable {

    private Integer itsPlace;
    private Integer itsBib;
    private String itsTime;

    @Override
    public void load(Map<String, String> theProperties) throws Exception {
        itsPlace = Double.valueOf(theProperties.get("Place")).intValue();
        itsBib = Double.valueOf(theProperties.get("Bib")).intValue();
        itsTime = theProperties.get("Time");
    }

    public Integer getPlace() {
        return itsPlace;
    }

    public void setPlace(Integer itsPlace) {
        this.itsPlace = itsPlace;
    }

    public Integer getBib() {
        return itsBib;
    }

    public void setBib(Integer itsBib) {
        this.itsBib = itsBib;
    }

    public String getTime() {
        return itsTime;
    }

    public void setTime(String itsTime) {
        this.itsTime = itsTime;
    }

    @Override
    public String toString() {
        return "Resultat{" +
                "place='" + itsPlace + '\'' +
                ", bib='" + itsBib + '\'' +
                ", time='" + itsTime + '\'' +
                '}';
    }
}
