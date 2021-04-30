package org.ntj_workout.data;


import java.io.Serializable;

public class Question implements Serializable {

    private String label;
    private Level level;
    private Type type;
    private String answer;

    public Question( Level level, Type type,String label, String answer) {
        this.label = label;
        this.level = level;
        this.type = type;
        this.answer = answer;
    }

    public String getLabel() {
        return label;
    }

    public Question setLabel(String label) {
        this.label = label;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    public Question setLevel(Level level) {
        this.level = level;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public Question setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Question setType(Type type) {
        this.type = type;
        return this;
    }
}
