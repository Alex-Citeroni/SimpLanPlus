package util;

// Genera Label casuali per funzioni e Label.
public class Label {
    public String getLabel() {
        return "_" + this.toString().substring(11, this.toString().length() - 1) + "_";
    }
}