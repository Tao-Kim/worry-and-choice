package com.tao.wnc.model.domain;

public class PostItem {
    private String title;
    private String description;
    private String writer;
    private long timeStamp;
    private SelectItem selectA;
    private SelectItem selectB;

    private int selected;
    private int checkCounts;
    private int commentCounts;

    public PostItem() {
    }

    public PostItem(String title, String description, String writer, long timeStamp, SelectItem selectA, SelectItem selectB, Short selected){
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.timeStamp = timeStamp;
        this.selectA = selectA;
        this.selectB = selectB;
        this.selected = selected;
        checkCounts = 0;
        commentCounts = 0;
    }

    public PostItem(String title, String description, String writer, long timeStamp, SelectItem selectA, SelectItem selectB, short selected, int checkCounts, int commentCounts) {
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.timeStamp = timeStamp;
        this.selectA = selectA;
        this.selectB = selectB;
        this.selected = selected;
        this.checkCounts = checkCounts;
        this.commentCounts = commentCounts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public SelectItem getSelectA() {
        return selectA;
    }

    public void setSelectA(SelectItem selectA) {
        this.selectA = selectA;
    }

    public SelectItem getSelectB() {
        return selectB;
    }

    public void setSelectB(SelectItem selectB) {
        this.selectB = selectB;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getCheckCounts() {
        return checkCounts;
    }

    public void setCheckCounts(int checkCounts) {
        this.checkCounts = checkCounts;
    }

    public int getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }
}
