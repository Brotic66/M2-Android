<?php

namespace ServerBundle\Services;

/**
 * @author Brice VICO
 * @date 07/12/2015
 * @version 1.0.0
 */
class Format
{
    public function formatPhone($number)
    {
        $phone = preg_replace("/[^0-9]/", "", $number);

        if(strlen($phone) == 11)
            return preg_replace("/[0-9]{2}([0-9]+)/", "0$1", $phone);
        else
            return $phone;
    }
}