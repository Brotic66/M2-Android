package brotic.findmyfriends.Service;

import android.location.Location;

/**
 * Created by brice on 27/12/15.
 */
public class LocationUtils {

    public String convertFromLocation(Location location) {
        return Location.convert(location.getLatitude(), Location.FORMAT_DEGREES)
                + " "
                + Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
    }
}
