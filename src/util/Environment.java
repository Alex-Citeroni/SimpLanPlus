package util;

import java.util.ArrayList;
import java.util.HashMap;

import ast.STentry;

// Implementazione della tabella dei simboli come elenco di hash maps.
public class Environment {
    public ArrayList<HashMap<String, STentry>> symTable = new ArrayList<>();
    public int nestingLevel = -1, offset = 0;

    public Environment(int nestingLevel, int offset) {
        this.nestingLevel = nestingLevel;
        this.offset = offset;
    }

    public Environment() {

    }
}