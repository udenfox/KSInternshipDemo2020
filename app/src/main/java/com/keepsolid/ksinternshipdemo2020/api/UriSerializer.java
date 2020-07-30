package com.keepsolid.ksinternshipdemo2020.api;

import android.net.Uri;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class UriSerializer implements JsonSerializer<Uri> {
    @Override
    public JsonElement serialize(Uri src, Type typeOfSrc, JsonSerializationContext context) {

        return new JsonPrimitive(src.toString());
    }
}
