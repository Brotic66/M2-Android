<?php

namespace ServerBundle\Controller;

use Brotic66\NTAngularBundle\Controller\NTAngularController;
use Doctrine\DBAL\Exception\UniqueConstraintViolationException;
use ServerBundle\Entity\User;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Response;

class UserController extends NTAngularController
{
    /**
     * @param $id
     * @return \Symfony\Component\HttpFoundation\Response
     * @internal param $login
     * @internal param $password
     * @Route("/getProfilPicture/{id}/")
     */
    public function getPictureAction($id)
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

        $path = $user->getProfilPicture();

        if ($path == null || $path == '')
            $path = "Files/Profil/BasePhotoProfil.png";

        if (($file = fopen("". $path, "r")) != null)
        {
            $content =  fread($file, filesize("" . $path));
            $size = filesize($path);
        }
        else
            return new Response(0);

        return new Response($content,
            200, array('Content-Type' => 'image/jpeg', 'Content-Length' => $size, 'Content-Disposition' => 'attachment; filename="image.jpg"'));
    }

    /**
     * @param $id
     * @param $token
     * @return mixed
     *
     * @Route("/changePicture/{id}/{token}/")
     */
    public function changerPhotoAction($id, $token)
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
            return $this->NTRender(array(
                'response' => 0,
                'message' => 'Erreur d\'authentification'
            ));

        $date = new \DateTime();
        $data = base64_decode(file_get_contents('php://input'));
        $cheminElement = 'Files/Profil/'. $id .'/'. $date->format('dmYHis') .'.jpg';

        $img = imagecreatefromstring($data);
        imagejpeg($img, $cheminElement);

        $user->setProfilPicture($cheminElement);

        $em->flush();

        return $this->NTRender(array(
            'response' => 1
        ));
    }

    /**
     * @param $id
     * @param $token
     * @return mixed
     *
     * @Route("/changeMdp/{id}/{token}/{old}/{new}/")
     */
    public function changerMdpAction($id, $token, $old, $new)
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
            return $this->NTRender(array(
                'response' => 0,
                'message' => 'Erreur d\'authentification'
            ));

        if (!password_verify($old, $user->getPassword()))
            return $this->NTRender(array(
                'response' => 0,
                'message' => 'Mauvais mot de passe'
            ));

        $user->setPassword(password_hash($new, PASSWORD_BCRYPT));

        $em->flush();

        return $this->NTRender(array(
            'response' => 1
        ));
    }


    /**
     * @param $id
     * @Route("/position/{id}/{token}/{latitude}/{longitude}/")
     * @return Response
     */
    public function positionAction($id, $token, $latitude, $longitude)
    {
        $userService = $this->get('server.user_service');
        $em = $this->getDoctrine()->getManager();
        $repository = $this->getDoctrine()->getRepository('ServerBundle:User');

        /**
         * @var User
         */
        $user = $repository->findOneBy(array(
            'id' => $id
        ));

        if (!$user)
            return $this->NTRender(array(
                'response' => 404,
                'message' => 'Utilisateur inconnu'
            ));

        if (!$userService->tokenExist($user, $token))
            return $this->NTRender(array(
                'response' => 0,
                'message' => 'Erreur d\'authentification'
            ));

        $user->setLatitude($latitude);
        $user->setLongitude($longitude);
        $em->flush();

        return $this->NTRender(array(
            'response' => 1
        ));
    }

    /**
     * @param $id
     * @Route("/getPosition/{id}/{token}/{friendId}/")
     * @return Response
     */
    public function getPositionAction($id, $token, $friendId)
    {
        $userService = $this->get('server.user_service');
        $em = $this->getDoctrine()->getManager();
        $repository = $this->getDoctrine()->getRepository('ServerBundle:User');
        $user = $repository->findOneBy(array(
            'id' => $id
        ));
        $friend = $repository->findOneBy(array(
           'id' => $friendId
        ));

        if (!$user || !$friend)
            return $this->NTRender(array(
                'response' => 404,
                'message' => 'Utilisateur inconnu'
            ));

        if (!$userService->tokenExist($user, $token))
            return $this->NTRender(array(
                'response' => 0,
                'message' => 'Erreur d\'authentification'
            ));

        if (!$user->getFriends()->contains($friend) && !$user->getFriendsWithMe()->contains($friend) && !$user != $friend)
            return $this->NTRender(array(
                'response' => 0,
                'message' => 'Cette personne n\'esrt pas votre ami !'
            ));

        return $this->NTRender(array(
            'response' => 1,
            'latitude' => $friend->getLatitude(),
            'longitude' => $friend->getLongitude(),
            'pseudo' => $friend->getUsername()
        ));
    }
}