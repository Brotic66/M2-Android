<?php

namespace ServerBundle\Controller;

use Brotic66\NTAngularBundle\Controller\NTAngularController;
use Brotic66\NTAngularBundle\Services\NTReturn;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use ServerBundle\Entity\Demande;
use ServerBundle\Entity\User;
use Symfony\Component\HttpFoundation\JsonResponse;

class DefaultController extends NTAngularController
{

    /**
     * @Route("/test-json")
     */
    public function testAction()
    {
        $user = new User();
        $user->setUsername('test');
        $user->setPhoneNumber('0627674523');
        $object = new Demande();
        $object->setDemandeur($user);

        /*return new JsonResponse(array(
            'object' => $object
        ));*/

        return $this->NTRender(array(
            'object' => $object));
    }

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

        if ($geoService->createGeolocalisation(str_replace(',', '.', $user->getLatitude()), str_replace(',', '.', $user->getLongitude())))
            $list = $geoService->getUserInZoneFromArray($users, $user);
        else
            return $this->NTRender(array(
                'response' => 0,
            ));

        return $this->NTRender(array(
            'response' => 1,
            'friendsList' => $list
        ));
    }
}
