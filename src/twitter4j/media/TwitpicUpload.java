/*
 * Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013-2014 vanita5 <mail@vanita5.de>
 *
 * This program incorporates a modified version of Twidere.
 * Copyright (C) 2012-2014 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package twitter4j.media;

import twitter4j.TwitterException;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.http.HttpParameter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Rémy Rakic - remy.rakic at gmail.com
 * @author Takao Nakaguchi - takao.nakaguchi at gmail.com
 * @author withgod - noname at withgod.jp
 * @since Twitter4J 2.1.8
 */
class TwitpicUpload extends AbstractImageUploadImpl {

    public TwitpicUpload(Configuration conf, String apiKey, OAuthAuthorization oauth) {
        super(conf, apiKey, oauth);
    }


    @Override
    protected String postUpload() throws TwitterException {
        int statusCode = httpResponse.getStatusCode();
        if (statusCode != 200)
            throw new TwitterException("Twitpic image upload returned invalid status code", httpResponse);

        String response = httpResponse.asString();

        try {
            JSONObject json = new JSONObject(response);
            if (!json.isNull("url"))
                return json.getString("url");
        } catch (JSONException e) {
            throw new TwitterException("Invalid Twitpic response: " + response, e);
        }

        throw new TwitterException("Unknown Twitpic response", httpResponse);
    }

    @Override
    protected void preUpload() throws TwitterException {
        uploadUrl = "https://twitpic.com/api/2/upload.json";
        String verifyCredentialsAuthorizationHeader = generateVerifyCredentialsAuthorizationHeader(TWITTER_VERIFY_CREDENTIALS_JSON_V1_1);

        headers.put("X-Auth-Service-Provider", TWITTER_VERIFY_CREDENTIALS_JSON_V1_1);
        headers.put("X-Verify-Credentials-Authorization", verifyCredentialsAuthorizationHeader);

        if (null == apiKey) {
            throw new IllegalStateException("No API Key for Twitpic specified. put media.providerAPIKey in twitter4j.properties.");
        }
        HttpParameter[] params = {
                new HttpParameter("key", apiKey),
                this.image};
        if (message != null) {
            params = appendHttpParameters(new HttpParameter[]{
                    this.message
            }, params);
        }
        this.postParameter = params;
    }
}
