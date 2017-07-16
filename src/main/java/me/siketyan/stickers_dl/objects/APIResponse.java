package me.siketyan.stickers_dl.objects;

import me.siketyan.stickers_dl.enums.APIStatus;

public class APIResponse {
    private APIStatus status;
    private String error;
    private String title;
    private String author;
    private String description;
    private String thumbnail;
    private Sticker[] stickers;
    private int price;
    private boolean has_sound;
    private boolean is_official;

    public APIStatus getStatus() { return status; }
    public String getError() { return error; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public String getThumbnail() { return thumbnail; }
    public Sticker[] getStickers() { return stickers; }
    public int getPrice() { return price; }
    public boolean hasSound() { return has_sound; }
    public boolean isOfficial() { return is_official; }

    public void setStatus(APIStatus status) { this.status = status; }
    public void setError(String error) { this.error = error; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setDescription(String description) { this.description = description; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public void setStickers(Sticker[] stickers) { this.stickers = stickers; }
    public void setPrice(int price) { this.price = price; }
    public void setHasSound(boolean hasSound) { this.has_sound = hasSound; }
    public void setIsOfficial(boolean isOfficial) { this.is_official = isOfficial; }
}