<?php
/**
 * @author Brice VICO
 * @date 19/01/2016
 * @version 1.0.0
 */

namespace ServerBundle\Services;


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

    public function getUserInZoneFromArray($users, $current)
    {
        $toRtn = array();

        $i = 0;
        foreach ($users as $user) {
            if ($user != $current) {
                $userGeo = GeoLocation::fromDegrees(str_replace(',', '.', $user->getLatitude()), str_replace(',', '.', $user->getLongitude()));

                /** ===== distance : 1km ===== */

                if ($this->isInZone($userGeo, 1)) {
                    $toRtn[$i]['id'] = $user->getId();
                    $toRtn[$i]['username'] = $user->getUsername();

                    $i++;
                }
            }
        }
        return $toRtn;
    }
}