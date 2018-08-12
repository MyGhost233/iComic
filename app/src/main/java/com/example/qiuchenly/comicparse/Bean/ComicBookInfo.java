package com.example.qiuchenly.comicparse.Bean;

public class ComicBookInfo {
    public String link;
    public String title;


    public static class ComicBookInfo_Recently {
        public String BookName;
        public String BookName_Link;
        public String BookName_Pic_Link;
        public String BookName_read_point;
        public String author;

        @Override
        public String toString() {
            return BookName + "|" + BookName_Pic_Link + "|" + "" + "|" + "" + "|" + BookName_Link;
        }
    }
}


