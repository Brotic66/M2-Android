<?php

namespace ServerBundle\Controller;

use Doctrine\DBAL\Exception\UniqueConstraintViolationException;
use ServerBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class SecurityController extends Controller
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
        $user = new User();
        $user->setUsername($login);
        $user->setPassword(password_hash($password, PASSWORD_BCRYPT));
        $user->setPhoneNumber($formatService->formatPhone($phone));

        try {
            $em->persist($user);
            $em->flush();
        } catch (UniqueConstraintViolationException $e){
            return $this->render('ServerBundle:Default:simple.json.twig', array(
                'code' => 0,
                'message' => 'Utilisateur existant'
            ));
    }

        return $this->render('ServerBundle:Default:simple.json.twig', array(
            'code' => 1,
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
        $repository = $this->getDoctrine()->getRepository('ServerBundle:User');
        $user = $repository->findOneBy(array(
            'username' => $login,
        ));

        if (!$user)
            return $this->render('ServerBundle:Default:simple.json.twig', array(
                'code' => 404,
                'message' => 'Utilisateur inconnu'
            ));

        if (!password_verify($password, $user->getPassword()))
            return $this->render('ServerBundle:Default:simple.json.twig', array(
                'code' => 0,
                'message' => 'Mauvais mot de passe'
            ));

        return $this->render('ServerBundle:Default:user.json.twig', array(
            'code' => 1,
            'user' => $user
        ));
    }
}
