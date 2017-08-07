package me.itsmas.forgemodblocker.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Web utility methods
 */
public final class UtilHttp
{
    private UtilHttp(){}

    /**
     * The user agent
     */
    private static final String USER_AGENT = "ForgeModBlocker";

    /**
     * The {@link JsonParser}
     */
    private static final JsonParser PARSER = new JsonParser();

    /**
     * Parses a {@link JsonElement} from a web page
     *
     * @param address The web address
     * @return The json element
     */
    public static JsonElement getJsonFromUrl(String address)
    {
        try
        {
            URL url = new URL(address);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", USER_AGENT);// Set User-Agent

            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);

            return PARSER.parse(reader);
        }
        catch (IOException ex)
        {
            return null;
        }
    }
}
