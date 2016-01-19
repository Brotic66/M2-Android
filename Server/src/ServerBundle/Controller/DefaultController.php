<?php

namespace ServerBundle\Controller;

use Brotic66\NTAngularBundle\Controller\NTAngularController;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class DefaultController extends NTAngularController
{
    /**
     * @Route("/friendsList/{id}/{token}/")
     */
    public function indexAction($id, $token)
    {
        $userService = $this->get('server.user_service');
        $em = $this->getDoctrine()->getManager();
        $repository = $this->getDoctrine()->getRepository('ServerBundle:User');
        $user = $repository->findOneBy(array(
            'id' => $id
        ));

        if (!$user)
            return $this->NTRender(array(
                'response' => 404,
                'message' => 'Utilisateur inconnu'
            ));

        if (!$userService->tokenExist($user, $token))
            if (!$user)
                return $this->NTRender(array(
                    'response' => 0,
                    'message' => 'Erreur d\'authentification'
                ));

        return $this->NTRender(array(
            'response' => 1,
            'friendsList' => $userService->formatFriends($user)
        ));
    }

    /**
     * @param $id
     * @param $token
     * @return \Symfony\Component\HttpFoundation\Response
     *
     * @Route("/getUserInZone/{id}/{token}/")
     */
    public function getUserInZoneAction($id, $token)
    {
        $userService = $this->get('server.user_service');
        $geoService = $this->get('server.geo_service');
        $em = $this->getDoctrine()->getManager();
        $repository = $this->getDoctrine()->getRepository('ServerBundle:User');
        $user = $repository->findOneBy(array(
            'id' => $id
        ));

        if (!$user)
            return $this->NTRender(array(
                'response' => 404,
                'message' => 'Utilisateur inconnu'
            ));

        if (!$userService->tokenExist($user, $token))
            if (!$user)
                return $this->NTRender(array(
                    'response' => 0,
                    'message' => 'Erreur d\'authentification'
                ));

        $users = $repository->getUserNotNull();
        $list = array();

        if ($geoService->createGeolocalisation($user->getLatitude(), $user->getLongitude()))
            $list = $geoService->getUserInZoneFromArray($users);
        else
            return $this->NTRender(array(
                'response' => 0,
            ));

        return $this->NTRender(array(
            'response' => 0,
            'users' => $list
        ));
    }
}
