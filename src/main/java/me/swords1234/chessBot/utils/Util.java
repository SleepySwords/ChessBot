package me.swords1234.chessBot.utils;

import com.google.auth.oauth2.ClientId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.Uploader;

import java.io.File;

public class Util {


    private static Gson gson = new Gson();

    public static String fileToUrl(File file) {
        String uploaded = Uploader.upload(file);

        JsonObject obj = gson.fromJson(uploaded, JsonObject.class);
        // msgs.delete().complete();
        StringBuilder builder = new StringBuilder("https://i.imgur.com/");
        builder.append( obj.getAsJsonObject("data").getAsJsonPrimitive("id").getAsString());
        builder.append(".png");
        return builder.toString();
    }
}
