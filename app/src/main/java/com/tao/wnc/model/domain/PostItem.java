package com.tao.wnc.model.domain;

public class PostItem {
    private String title;
    private String description;
    private String others;
    private boolean selected;
    private int checkCounts;
    private int commentCounts;

    public PostItem(String title, String description, String others, boolean selected, int checkCounts, int commentCounts) {
        this.title = title;
        this.description = description;
        this.others = others;
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

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getCheckCounts() {
        return String.valueOf(checkCounts);
    }

    public void setCheckCounts(int checkCounts) {
        this.checkCounts = checkCounts;
    }

    public String getCommentCounts() {
        return String.valueOf(commentCounts);
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }

//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }
//
//    public String getCheckCounts() {
//        return String.valueOf(checkCounts);
//    }
//
//    public void setCheckCounts(int checkCounts) {
//        this.checkCounts = checkCounts;
//    }
//
//    public String getCommentCounts() {
//        return String.valueOf(commentCounts);
//    }
//
//    public void setCommentCounts(int commentCounts) {
//        this.commentCounts = commentCounts;
//    }
}
