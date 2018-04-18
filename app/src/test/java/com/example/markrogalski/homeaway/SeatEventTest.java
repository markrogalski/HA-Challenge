package com.example.markrogalski.homeaway;

import android.os.Bundle;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SeatEventTest {
    @Test
    public void testJsonConstructor() throws Exception {

        JSONObject jsonObject = new JSONObject("{\"links\": []," +
                "\"id\": 2930972," +
                "\"stats\": {" +
                "\"listing_count\": 192," +
                "\"average_price\": 26.5," +
                "\"lowest_price_good_deals\": 8.0," +
                "\"lowest_price\": 8.0," +
                "\"highest_price\": 47.0" +
                "}," +
                "\"title\": \"Texas Rangers at Oakland Athletics\"," +
                "\"announce_date\": \"2015-11-17T00:00:00\"," +
                "\"score\": 0.689314," +
                "\"date_tbd\": false," +
                "\"type\": \"mlb\"," +
                "\"datetime_local\": \"2016-06-13T19:05:00\"," +
                "\"visible_until_utc\": \"2016-06-14T06:05:00\"," +
                "\"time_tbd\": false," +
                "\"performers\": [{" +
                "\"image\": \"https:\\/\\/chairnerd.global.ssl.fastly.net\\/images\\/performers-landscape\\/oakland-athletics-c4a159\\/14\\/huge.jpg\"," +
                "\"primary\": true," +
                "\"images\": {" +
                "\"huge\": \"https:\\/\\/chairnerd.global.ssl.fastly.net\\/images\\/performers-landscape\\/oakland-athletics-c4a159\\/14\\/huge.jpg\"" +
                "}," +
                "\"has_upcoming_events\": true," +
                "\"id\": 14," +
                "\"stats\": {" +
                "\"event_count\": 101" +
                "}," +
                "\"score\": 0.65348," +
                "\"type\": \"mlb\"," +
                "\"short_name\": \"Athletics\"," +
                "\"home_venue_id\": 14," +
                "\"slug\": \"oakland-athletics\"," +
                "\"home_team\": true," +
                "\"name\": \"Oakland Athletics\"," +
                "\"url\": \"https:\\/\\/seatgeek.com\\/oakland-athletics-tickets\"" +
                "}, {" +
                "\"away_team\": true," +
                "\"stats\": {" +
                "\"event_count\": 100" +
                "}," +
                "\"name\": \"Texas Rangers\"," +
                "\"short_name\": \"Rangers\"," +
                "\"url\": \"https:\\/\\/seatgeek.com\\/texas-rangers-tickets\"," +
                "\"type\": \"mlb\"," +
                "\"image\": \"https:\\/\\/chairnerd.global.ssl.fastly.net\\/images\\/performers-landscape\\/texas-rangers-a16a31\\/16\\/huge.jpg\"," +
                "\"home_venue_id\": 16," +
                "\"slug\": \"texas-rangers\"," +
                "\"score\": 0.743116," +
                "\"images\": {" +
                "\"huge\": \"https:\\/\\/chairnerd.global.ssl.fastly.net\\/images\\/performers-landscape\\/texas-rangers-a16a31\\/16\\/huge.jpg\"" +
                "}," +
                "\"url\": \"https:\\/\\/seatgeek.com\\/rangers-at-athletics-tickets\\/6-13-2016-oakland-california-oakland-coliseum\\/mlb\\/2930972\"," +
                "\"created_at\": \"2015-11-17T00:00:00\"," +
                "\"venue\": {" +
                "\"city\": \"Oakland\"," +
                "\"name\": \"Oakland Coliseum\"," +
                "\"extended_address\": \"Oakland, CA 94621\"," +
                "\"url\": \"https:\\/\\/seatgeek.com\\/venues\\/oakland-coliseum\\/tickets\"," +
                "\"country\": \"US\"," +
                "\"display_location\": \"Oakland, CA\"," +
                "\"links\": []," +
                "\"slug\": \"oakland-coliseum\"," +
                "\"state\": \"CA\"," +
                "}," +
                "\"short_title\": \"Rangers at Athletics\"," +
                "}}");

        SeatEvent event = new SeatEvent(jsonObject);

        // Android JSON parser does not work in unit tests so all we can do is test for object
        assertNotNull(event);
    }

    // This will fail because Android classes all need to be mocked now. :( Suggest using RoboElectric.
    @Test
    public void testBundleConstructor() throws Exception {

        Bundle bundle = new Bundle();
        bundle.putInt(SeatEvent.JSON_ID, 123);
        bundle.putString(SeatEvent.JSON_TITLE, "title");
        bundle.putString(SeatEvent.JSON_SHORT_TITLE, "short title");
        bundle.putString(SeatEvent.JSON_DATE, "May 1");
        bundle.putString(SeatEvent.JSON_VENUE_LOCATION, "Miami");
        bundle.putString(SeatEvent.JSON_PERFORMER_IMAGE, "https://image.com");
        bundle.putDouble(SeatEvent.JSON_LOW_PRICE, 12.5);
        bundle.putDouble(SeatEvent.JSON_AVERAGE_PRICE, 13.22);
        bundle.putDouble(SeatEvent.JSON_HIGH_PRICE, 18.5);
        bundle.putBoolean(SeatEvent.KEY_IS_FAVORITE, true);

        SeatEvent event = new SeatEvent(bundle);

        assertEquals(123, event.mId);
        assertEquals("title", event.mTitle);
        assertEquals("short title", event.mShortTitle);
        assertEquals("May 1", event.mDate);
        assertEquals("Miami", event.mLocation);
        assertEquals("https://image.com", event.mImageUrl);
        assertEquals(12.5, event.mLowPrice, 0);
        assertEquals(13.22, event.mAveragePrice, 0);
        assertEquals(18.5, event.mHighPrice, 0);
    }
}