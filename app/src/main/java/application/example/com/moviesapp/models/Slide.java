package application.example.com.moviesapp.models;


public class Slide {
    private  String Image;
    private String Title;

    public Slide(String image, String title) {
        Image = image;
        Title = title;
    }


    public String getImage() {
        return Image;
    }

    public Slide setImage(String image) {
        Image = image;
        return this;
    }

    public String getTitle() {
        return Title;
    }

    public Slide setTitle(String title) {
        Title = title;
        return this;
    }
}
