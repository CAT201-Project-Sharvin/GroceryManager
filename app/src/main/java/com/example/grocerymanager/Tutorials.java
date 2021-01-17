package com.example.grocerymanager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tutorials {
    private String title;
    private String shortDesc;
    private String imageUrl;
    private String videoID;
    private String thumbnailURL;
    private ArrayList<String> steps = new ArrayList<>();

    public Tutorials(String title, String shortDesc, String imageUrl, ArrayList<String> steps) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.imageUrl = imageUrl;
        this.videoID = extractId(this.imageUrl);
        this.thumbnailURL = getThumbnailUrl(this.imageUrl);
        this.steps = steps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public static String getThumbnailUrl(String videoUrl) {
        return "https://img.youtube.com/vi/"+extractId(videoUrl) + "/1.jpg";
    }

    public static String extractId(String YoutubeUrl) {
        YoutubeUrl = YoutubeUrl.replace("&feature=youtu.be", "");
        if (YoutubeUrl.toLowerCase().contains("youtu.be")) {
            return YoutubeUrl.substring(YoutubeUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(YoutubeUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
