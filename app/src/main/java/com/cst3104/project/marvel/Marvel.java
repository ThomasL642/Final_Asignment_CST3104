package com.cst3104.project.marvel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/*
    Marvel character class
    This class is used to represent a Marvel character.
    It contains the character's name, image, and Wikipedia page.
    The class also contains methods to read a list of characters from a JSON file.
 */

// Marvel character class
public class Marvel {
    private static Context context = null;  // context to access assets (drawable)

    private String name = null;    // character name
    private String  drawable = null;    // character image
    private boolean shown = false;   // if character's name will be shown
    private String url      = null;    // character's Wikipedia page

    // Default Constructor
    public Marvel() {
    }

    // Parameterized Constructor
    public Marvel(String name, String drawable, boolean shown) {
        this.name = name;
        this.drawable = drawable;
        this.shown = shown;
    }

    // Name is returned only if shown is true
    public String getName() {
        if (this.shown)
            return name;
        else
            return null;
    }

    // Name mutator
    public void setName(String name) {
        this.name = name;
    }

    // shown accessor
    public boolean isShown() {
        return shown;
    }

    // shown mutator
    public void setShown(boolean shown) {
        this.shown = shown;
    }

    // URL accessor
    public String getUrl() {
        return url;
    }

    // URL mutator
    public void setUrl(String url) {
        this.url = url;
    }

    // Returns a String with the character name
    @Override
    public String toString() {
        return this.name;
    }

    // Returns the drawable resource ID of the character
    public int getDrawableId() {
        int id = context.getResources().getIdentifier(this.drawable,"drawable", context.getPackageName());
        return id;
    }

    // To insert an image in a provided ImageView
    // The image must be found in res/drawable and should contain
    // a name equal to the name of the character
    public void flagInImageView(ImageView iv) {

        int imageResource = context.getResources().getIdentifier(this.drawable,"drawable", context.getPackageName());

        Drawable res = context.getDrawable(imageResource);
        iv.setImageDrawable(res);
    }

    //Deserialize a list of characters from a JSON file
    public static ArrayList<Marvel> readData(Context context) {
        // Keep a reference to context to access resources (R.drawable)
        Marvel.context = context;

        final ArrayList<Marvel> mylist = new ArrayList<>();

        try {
            // Load data in ArrayList
            String     jsonString  = readJson("marvel.json", Marvel.context);
            JSONObject json        = new JSONObject(jsonString);
            JSONArray  items  = json.getJSONArray("marvel");

            // Read each character from the file
            for(int i = 0; i < items.length(); i++){
                Marvel p = new Marvel();

                p.name = items.getJSONObject(i).getString("name");
                p.drawable = items.getJSONObject(i).getString("drawable");
                p.url      = items.getJSONObject(i).getString("url");

                mylist.add(p);
            }
        } catch (JSONException e) {
            // Error
            e.printStackTrace();
        }

        return mylist;
    }

    // Reads a tag from a JSON file
    private static String readJson(String fileName, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (java.io.IOException ex) {
            // Log the error
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
