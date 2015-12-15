<?php
/**
 * @author Brice VICO
 * @date 15/12/2015
 * @version 1.0.0
 */

namespace ServerBundle\Services;


use ServerBundle\Entity\User;

class UserService
{
    public function generateToken()
    {
        return uniqid("", true);
    }

    public function tokenExist(User $user, $token)
    {
        if (in_array($token, $user->getTokens()))
            return true;
        else
            return false;
    }

    public function formatFriends(User $user)
    {
        $toRtn = array();
        $friendsList = $user->getFriends();

        $i = 0;

        foreach ($friendsList as $friend) {
            $toRtn[$i]["username"] = $friend->getUsername();
            $toRtn[$i]["id"] = $friend->getId();

            $i++;
        }

        return $toRtn;
    }
}