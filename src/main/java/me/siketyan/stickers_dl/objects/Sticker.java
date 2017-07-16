package me.siketyan.stickers_dl.objects;

public class Sticker {
    private long id;
    private String imageUrl;
    private String audioUrl;

    public long getId() { return id; }
    public String getImageUrl() { return imageUrl; }
    public String getAudioUrl() { return audioUrl; }

    public void setId(long id) { this.id = id; }
    public void setImageUrl(String url) { this.imageUrl = url; }
    public void setAudioUrl(String url) { this.audioUrl = url; }
}