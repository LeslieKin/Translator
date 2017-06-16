package com.cn.kin.translator.database;

import org.litepal.crud.DataSupport;

/**
 * Created by Kin on 17.6.8.
 */

public class NoteBook extends DataSupport {

    private int id;

    private String input;

    private String output;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getInput(){
        return input;
    }

    public void setInput(String input){
        this.input=input;
    }

    public String getOutput(){
        return output;
    }

    public void setOutput(String output){
        this.output=output;
    }
}
