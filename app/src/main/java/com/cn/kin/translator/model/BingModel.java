package com.cn.kin.translator.model;

import java.util.ArrayList;

/**
 * Created by Kin on 17.6.7.
 */

//调用必应翻译api 翻译示例 Hello
//{
//        "word":"hello",

//        "pronunciation":{
//        "AmE":"heˈləʊ",
//        "AmEmp3":"https://dictionary.blob.core.chinacloudapi.cn/media/audio/tom/bf/b1/BFB1169AD46D18FDC9145E494EF4D22B.mp3",
//        "BrE":"hə'ləʊ",
//        "BrEmp3":"https://dictionary.blob.core.chinacloudapi.cn/media/audio/george/bf/b1/BFB1169AD46D18FDC9145E494EF4D22B.mp3"
//        },

//        "defs":
//        [
//        {"pos":"int.","def":"你好；喂；您好；哈喽"},
//        {"pos":"Web","def":"哈罗；哈啰；大家好"}
//        ],

//        "sams":
//        [
//        {"eng":"The thing that annoys me about him is the way he never says \"Hello! \" .",
//        "chn":"他使我讨厌的是，从来不跟别人打呼。",
//        "mp3Url":"https://dictionary.blob.core.chinacloudapi.cn/media/audio/tom/ab/32/AB32B8A03CD33F690A293E8EFA617A14.mp3",
//        "mp4Url":null
//        },
//
//        {
//        "eng":"He said to say hello and he hopes we'll see you for dinner soon,
//        but he's got too much work to go out pub crawling with the boys.",
//        "chn":"他说让我问好，还说希望我们三个最近能在一起吃顿午饭，但是他要干的活儿实在太多了，所以没时间和咱们在酒吧里鬼混了。",
//        "mp3Url":"https://dictionary.blob.core.chinacloudapi.cn/media/audio/tom/cd/af/CDAFCC5F6169ADA400A8AEF32CD91D4E.mp3",
//        "mp4Url":null
//        },
//
//        {
//        "eng":"When she greeted me with her hello, I replied \"Guess what I need from you today? \"",
//        "chn":"她向我打招呼，我说：你说我想弄点什么呢？",
//        "mp3Url":"https://dictionary.blob.core.chinacloudapi.cn/media/audio/tom/8c/35/8C3523F1D5D7F17FED7A3666CAB437E4.mp3",
//        "mp4Url":null
//        },

//        ]
//        }

public class BingModel {

    private String word;
    private Pronunciation pronunciation;
    private ArrayList<Definition> defs;
    private ArrayList<Sample> sams;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Pronunciation getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(Pronunciation pronunciation) {
        this.pronunciation = pronunciation;
    }

    public ArrayList<Definition> getDefs() {
        return defs;
    }

    public void setDefs(ArrayList<Definition> defs) {
        this.defs = defs;
    }

    public ArrayList<Sample> getSams() {
        return sams;
    }

    public void setSams(ArrayList<Sample> sams) {
        this.sams = sams;
    }

    public class Pronunciation {

        private String AmE;
        private String AmEmp3;
        private String BrE;
        private String BrEmp3;

        public String getAmE() {
            return AmE;
        }

        public void setAmE(String amE) {
            AmE = amE;
        }

        public String getAmEmp3() {
            return AmEmp3;
        }

        public void setAmEmp3(String amEmp3) {
            AmEmp3 = amEmp3;
        }

        public String getBrE() {
            return BrE;
        }

        public void setBrE(String brE) {
            BrE = brE;
        }

        public String getBrEmp3() {
            return BrEmp3;
        }

        public void setBrEmp3(String brEmp3) {
            BrEmp3 = brEmp3;
        }
    }

    public class Definition {

        private String pos;
        private String def;

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getDef() {
            return def;
        }

        public void setDef(String def) {
            this.def = def;
        }
    }

    public class Sample {

        private String eng;
        private String chn;
        private String mp3Url;
        private String mp4Url;

        public String getEng() {
            return eng;
        }

        public void setEng(String eng) {
            this.eng = eng;
        }

        public String getChn() {
            return chn;
        }

        public void setChn(String chn) {
            this.chn = chn;
        }

        public String getMp3Url() {
            return mp3Url;
        }

        public void setMp3Url(String mp3Url) {
            this.mp3Url = mp3Url;
        }

        public String getMp4Url() {
            return mp4Url;
        }

        public void setMp4Url(String mp4Url) {
            this.mp4Url = mp4Url;
        }
    }

}
