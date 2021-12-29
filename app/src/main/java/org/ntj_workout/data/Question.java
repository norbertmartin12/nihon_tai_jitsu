package org.ntj_workout.data;


import java.io.Serializable;

import kotlin.text.StringsKt;

public class Question implements Serializable {

    private String label;
    private Level level;
    private Type type;
    private String textAnswer;
    private String imageAnswer;
    private String videoAnswer;

    public Question( Level level, Type type,String label, String textAnswer, String imageAnswer, String videoAnswer) {
        this.label = label;
        this.level = level;
        this.type = type;
        this.textAnswer = StringsKt.isBlank(textAnswer) ? null : textAnswer;
        this.imageAnswer = StringsKt.isBlank(imageAnswer) ? null : imageAnswer;
        this.videoAnswer = StringsKt.isBlank(videoAnswer) ? null : videoAnswer;
    }

    public String getLabel() {
        return label;
    }

    public String getImageAnswer() {
        return imageAnswer;
    }

    public Question setImageAnswer(String imageAnswer) {
        this.imageAnswer = imageAnswer;
        return this;
    }

    public String getVideoAnswer() {
        return videoAnswer;
    }

    public Question setVideoAnswer(String videoAnswer) {
        this.videoAnswer = videoAnswer;
        return this;
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

    public String getTextAnswer() {
        return textAnswer;
    }

    public Question setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Question setType(Type type) {
        this.type = type;
        return this;
    }

    public boolean hasAnswer() {
        return textAnswer != null || imageAnswer != null || videoAnswer != null;
    }

    public boolean hasMediaAnswer() {
        return imageAnswer != null || videoAnswer != null;
    }
}
