package dev.edmt.firebasedemo;

/**
 * Created by bircan on 9.7.2017.
 */

public class Note {

    private String noteid,title,content;

    public Note() {
    }
    public Note(String noteid, String title, String content){
        this.noteid = noteid;
        this.title = title;
        this.content = content;
    }

    public String getNoteid() {
        return noteid;
    }

    public void setNoteid(String noteid) {
        this.noteid = noteid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
