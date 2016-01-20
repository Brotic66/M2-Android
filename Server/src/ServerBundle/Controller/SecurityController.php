<?php

namespace ServerBundle\Controller;

use Brotic66\NTAngularBundle\Controller\NTAngularController;
use Doctrine\DBAL\Exception\UniqueConstraintViolationException;
use ServerBundle\Entity\User;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\Filesystem\Filesystem;
use Symfony\Component\HttpFoundation\Response;

class SecurityController extends NTAngularController
{
    /**
     * @param $login
     * @param $password
     * @return \Symfony\Component\HttpFoundation\Response
     *
     * @Route("/register/{login}/{password}/{phone}/")
     */
    public function registerAction($login, $password, $phone)
    {
        $em = $this->getDoctrine()->getManager();
        $formatService = $this->get('server.format_service');
        $fs = new Filesystem();
        $user = new User();
        $user->setUsername($login);
        $user->setPassword(password_hash($password, PASSWORD_BCRYPT));
        $user->setPhoneNumber($formatService->formatPhone($phone));

        try {
            $em->persist($user);
            $em->flush();
        } catch (UniqueConstraintViolationException $e){
            return $this->NTRender(array(
                'response' => 0,
                'message' => 'Utilisateur existant'
            ));
    }

        $fs->mkdir('Files/Profil/'.$user->getId(), 0777);

        return $this->NTRender(array(
            'response' => 1,
            'message' => 'Inscription effectuée'
        ));
    }

    /**
     * @param $login
     * @param $password
     *
     * @Route("/login/{login}/{password}/")
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function loginAction($login, $password)
    {
        $userService = $this->get('server.user_service');
        $em = $this->getDoctrine()->getManager();
        $repository = $this->getDoctrine()->getRepository('ServerBundle:User');
        $user = $repository->findOneBy(array(
            'username' => $login,
        ));

        if (!$user)
            return $this->NTRender(array(
                'response' => 404,
                'message' => 'Utilisateur inconnu'
            ));

        if (!password_verify($password, $user->getPassword()))
            return $this->NTRender(array(
                'response' => 0,
                'message' => 'Mauvais mot de passe'
            ));

        $token = $userService->generateToken();
        $user->addToken($token);
        $em->flush();

        return $this->NTRender(array(
            'response' => 1,
            'userId' => $user->getId(),
            'username' => $user->getUsername(),
            'userPhone' => $user->getPhoneNumber(),
            'token' => $token
        ));
    }

    /**
     * @Route("/gcmToken/{id}/{token}/{gcmToken}/")
     */
    public function gcmAction($id, $token, $gcmToken)
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

        $user->setGcmToken($gcmToken);
        $em->flush();

        return $this->NTRender(
            array(
                'response' => 1
            ));
    }

    /**
     * @Route("/testgcm/{type}")
     *
     * type :
     *  - addFriend
     *  - acceptFriend
     *  - un ami vient de partager sa position (username en paramètre)
     *  - askPosition (username en paramètre)
     */
    public function testGCM($type)
    {
        $gcmService = $this->get('server.gcm_service');
        $gcmService->send(array(
            "type" => $type
        ),
            'fQglG8ZfZt4:APA91bHsixdMa1LNX-o4oScaHYVXh8TQ72XTzmnNtAS20aXvFKcOG1h_EVULlqtadRc8bMSh4xBrMzbDj8oA757z-0Lox3GTE2K1Vk7YvZsMLF24LvDZ98jHQg2AZ5NGjAm2PGxwbHlE');

        return new Response(1);
    }
}
