package util;

import java.util.ArrayList;

/**
 * Tabella dei simboli: memorizza il livello di annidamento corrente per eseguire la ricerca con il
 * collegamento di accesso e un elenco di Label per le istruzioni di salto.
 */

public class CGenEnv {
    private int nestingLevel = -1;
    private final ArrayList<String> labels = new ArrayList<>();
    private final ArrayList<Boolean> isBlockFuncPre = new ArrayList<>();
    private Boolean isReturn = false;

    public Boolean getReturn() {
        return this.isReturn;
    }

    public void setReturn(Boolean aReturn) {
        this.isReturn = aReturn;
    }

    public CGenEnv() {

    }

    public int getNestingLevel() {
        return this.nestingLevel;
    }

    public String getLabel() {
        return this.labels.get(labels.size() - 1);
    }

    public boolean getIsPreBlockFunc() {
        return this.isBlockFuncPre.get(isBlockFuncPre.size() - 1);
    }

    public void removeLabel() {
        this.labels.remove(labels.size() - 1);
        this.isBlockFuncPre.remove(isBlockFuncPre.size() - 1);
    }

    public void setLabel(String label) {
        this.labels.add(label);
        this.isBlockFuncPre.add(false);
    }

    public void setIsBlockFuncPre() {
        this.isBlockFuncPre.set(this.isBlockFuncPre.size() - 1, true);
    }

    public void incrementNestingLevel() {
        this.nestingLevel++;
    }

    public void decrementNestingLevel() {
        this.nestingLevel--;
    }
}