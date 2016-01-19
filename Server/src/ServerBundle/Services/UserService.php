<?php
/**
 * @author Brice VICO
 * @date 15/12/2015
 * @version 1.0.0
 */

namespace ServerBundle\Services;


use Doctrine\ORM\EntityManager;
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
        $friendsList = array_merge($user->getFriends()->toArray(), $user->getFriendsWithMe()->toArray());

        $i = 0;

        foreach ($friendsList as $friend) {
            $toRtn[$i]["username"] = $friend->getUsername();
            $toRtn[$i]["id"] = $friend->getId();

            $i++;
        }

        return $toRtn;
    }

    public function notExistDemande(EntityManager $em, User $user, User $friend)
    {
        $demandsOfUser = $em->getRepository('ServerBundle:Demande')->findBy(array(
            'demandeur' => $user,
            'demande' => $friend
        ));
        $demandsOfFriend = $em->getRepository('ServerBundle:Demande')->findBy(array(
            'demandeur' => $friend,
            'demande' => $user
        ));

        if (empty($demandsOfFriend) && empty($demandsOfUser))
            return true;
        else
            return false;
    }

    public function formatDemands($demands)
    {
        $toRtn = array();

        $i = 0;
        foreach ($demands as $demand) {
            $toRtn[$i]['demandeurId'] = $demand->getDemandeur()->getId();
            $toRtn[$i]['demandeurUsername'] = $demand->getDemandeur()->getUsername();
            $toRtn[$i]['demandeId'] = $demand->getDemande()->getUsername();
            $toRtn[$i]['demandeUsername'] = $demand->getDemande()->getUsername();

            $i++;
        }

        return $toRtn;
    }
}