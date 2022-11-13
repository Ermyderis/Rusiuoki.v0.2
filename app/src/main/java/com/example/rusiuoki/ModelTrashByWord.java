package com.example.rusiuoki;

public class ModelTrashByWord {

    public String trashWord;
    public String trashRecyclePlaceByWord;

    public ModelTrashByWord(){
    }

    public ModelTrashByWord(String trashWord, String trashRecyclePlaceByWord){
        this.trashWord = trashWord;
        this.trashRecyclePlaceByWord = trashRecyclePlaceByWord;

    }

    public String getTrashWord() {
        return trashWord;
    }

    public String getTrashRecyclePlaceByWord() {
        return trashRecyclePlaceByWord;
    }
}
