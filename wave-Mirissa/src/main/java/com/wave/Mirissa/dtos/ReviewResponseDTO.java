package com.wave.Mirissa.dtos;

public class ReviewResponseDTO {
    private Long reviewId;
    private int rating;
    private String comment;
    private String userName;

    public ReviewResponseDTO(Long reviewId, int rating, String comment, String userName) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
}