package xyz.felipemeloni.android.gingachat;

import java.util.List;

/**
 * Created by felipemeloni on 17/11/15.
 */
public class DataStorage {
    static List<String> msgList;
    static void addMsg(String msg){
        msgList.add(msg);
    }
    static String getMsg(int pos){
        return msgList.get(pos);
    }
    static List<String> getMsgList(){
        return msgList;
    }
}
