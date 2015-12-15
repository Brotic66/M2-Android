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
    public function registerAction($id)
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

        if ($path = null || $path == '')
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
}