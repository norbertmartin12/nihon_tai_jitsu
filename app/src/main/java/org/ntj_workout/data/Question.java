package org.ntj_workout.data;


import java.io.Serializable;

import kotlin.text.StringsKt;

public class Question implements Serializable {

    private final int id;
    private final String label;
    private final Level level;
    private final Type type;
    private final String textAnswer;
    private final String imageAnswer;
    private final String videoAnswer;

    public Question(int id, Level level, Type type,String label, String textAnswer, String imageAnswer, String videoAnswer) {
        this.id = id;
        this.label = label;
        this.level = level;
        this.type = type;
        this.textAnswer = StringsKt.isBlank(textAnswer) ? null : textAnswer;
        this.imageAnswer = StringsKt.isBlank(imageAnswer) ? null : imageAnswer;
        this.videoAnswer = StringsKt.isBlank(videoAnswer) ? null : videoAnswer;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getImageAnswer() {
        return imageAnswer;
    }

    public String getVideoAnswer() {
        return videoAnswer;
    }

    public Level getLevel() {
        return level;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public Type getType() {
        return type;
    }

    public boolean hasAnswer() {
        return textAnswer != null || imageAnswer != null || videoAnswer != null;
    }

    public boolean hasMediaAnswer() {
        return imageAnswer != null || videoAnswer != null;
    }
}
