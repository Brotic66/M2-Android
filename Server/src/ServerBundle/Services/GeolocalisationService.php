<?php
/**
 * @author Brice VICO
 * @date 19/01/2016
 * @version 1.0.0
 */

namespace ServerBundle\Services;
use ServerBundle\Services\GeoLocation;


class GeolocalisationService
{
    /**
     * @var GeoLocation
     */
    private $geo;

    public function createGeolocalisation($latitude, $longitude)
    {
        if ($latitude == null || $longitude == null)
            return false;

        $this->geo = GeoLocation::fromDegrees($latitude, $longitude);

        return true;

    }

    public function isInZone(GeoLocation $geoToCompare, $distance)
    {
        if ($this->geo->distanceTo($geoToCompare, 'km') > $distance)
            return false;
        else
            return true;
    }

    public function getUserInZoneFromArray($users)
    {
        $toRtn = array();

        foreach ($users as $user) {
            $userGeo = GeoLocation::fromDegrees($user->getLatitude(), $user->getLongitude());

            /** ===== distance : 1km ===== */

            $i = 0;
            if ($this->isInZone($userGeo, 1)) {
                $toRtn[$i]['id'] = $user->getId();
                $toRtn[$i]['username'] = $user->getUsername();

                $i++;
            }
        }
        return $toRtn;
    }
}