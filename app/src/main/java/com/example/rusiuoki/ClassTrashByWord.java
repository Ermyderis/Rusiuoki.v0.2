package com.example.rusiuoki;

public class ClassTrashByWord {

    public String trashWord;
    public String trashRecyclePlaceByWord;

    public ClassTrashByWord(){
    }

    public ClassTrashByWord(String trashWord, String trashRecyclePlaceByWord){
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
