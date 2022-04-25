package com.learning.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagePackTest {
    
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("msgpack");
        list.add("kumofs");
        list.add("viver");
        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(list);
        List<String> result = messagePack.read(raw, Templates.tList(Templates.TString));
        System.out.println(Arrays.toString(result.toArray()));
    }
}
