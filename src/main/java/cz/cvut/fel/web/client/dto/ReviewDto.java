package cz.cvut.fel.web.client.dto;

public class ReviewDto {
    private Integer rating;
    private String description;
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
