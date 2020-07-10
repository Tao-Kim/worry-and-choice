package com.tao.wnc.model.domain;

import java.util.ArrayList;

public class PostItem {
    private String title;
    private String description;
    private String writer;
    private String timeStamp;
    private SelectItem selectA;
    private SelectItem selectB;

    private short selected;
    private ArrayList<CommentItem> comments;
    private int checkCounts;
    private int commentCounts;


    public PostItem(String title, String description, String writer, String timeStamp, SelectItem selectA, SelectItem selectB, Short selected){
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.timeStamp = timeStamp;
        this.selectA = selectA;
        this.selectB = selectB;
        this.selected = selected;
        comments = null;
        checkCounts = 0;
        commentCounts = 0;
    }

    public PostItem(String title, String description, String writer, String timeStamp, SelectItem selectA, SelectItem selectB, short selected, ArrayList<CommentItem> comments, int checkCounts, int commentCounts) {
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.timeStamp = timeStamp;
        this.selectA = selectA;
        this.selectB = selectB;
        this.selected = selected;
        this.comments = comments;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
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

    public short getSelected() {
        return selected;
    }

    public void setSelected(short selected) {
        this.selected = selected;
    }

    public ArrayList<CommentItem> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentItem> comments) {
        this.comments = comments;
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
