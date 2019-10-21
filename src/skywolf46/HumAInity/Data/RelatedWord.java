package skywolf46.HumAInity.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RelatedWord {
    // next related word have x0.5 related value and 100 first value
    private List<String> nextRelated = new ArrayList<>();
    // upper related word have x0.25 related value and 25 first value
    private List<String> beforeRelated = new ArrayList<>();
    // current text; Immutable
    private String word;

    public RelatedWord(String word) {
        this.word = word;
    }

    public void addDirectRelatedWord(String word) {
        if (!nextRelated.contains(word))
            nextRelated.add(word);
    }

    public void addIndirectRelatedWord(String word) {
        beforeRelated.add(word);
    }


    List<String> getDirectRelatedInstance() {
        return nextRelated;
    }

    List<String> getIndirectRelatedInstance() {
        return beforeRelated;
    }

    public List<String> getDirectRelated() {
        return new ArrayList<>(nextRelated);
    }

    public List<String> getIndirectRelated() {
        return new ArrayList<>(beforeRelated);
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RelatedWord && ((RelatedWord) obj).getWord().equals(getWord());
    }
}
