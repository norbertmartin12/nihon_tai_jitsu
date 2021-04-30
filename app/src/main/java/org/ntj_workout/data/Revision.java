package org.ntj_workout.data;

import java.io.Serializable;
import java.util.List;

public class Revision implements Serializable {

    private List<Question> iterator;
    private int index;
    private Question currentQuestion;

    public Revision(List<Question> iterator) {
        this.iterator = iterator;
        this.index = 0;
    }

    public Question next() {
        if (this.index >= this.iterator.size()) {
            return this.currentQuestion = null;
        }
        return this.currentQuestion = this.iterator.get(this.index++);
    }

    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }
}
