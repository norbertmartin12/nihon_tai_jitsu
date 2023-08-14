package org.ntj_workout.data;

import java.io.Serializable;
import java.util.List;

public class Revision implements Serializable {

    private List<Question> list;
    private int index;
    private Question currentQuestion;

    public Revision(List<Question> list) {
        this.list = list;
        this.index = -1;
    }

    public Question next() {
        if (this.index + 1 == this.list.size()) {
            return this.currentQuestion = null;
        }
        return this.currentQuestion = this.list.get(++this.index);
    }

    public Question previous() {
        if (this.index - 1 < 0) {
            return this.currentQuestion = null;
        }
        return this.currentQuestion = this.list.get(--this.index);
    }

    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }

    public int size() {
        return list.size();
    }
    public int currentIndex() {
        return index;
    }
}
