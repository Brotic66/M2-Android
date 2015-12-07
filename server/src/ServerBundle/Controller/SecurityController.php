<?php

namespace ServerBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class SecurityController extends Controller
{
    /**
     * @param $login
     * @param $password
     * @return \Symfony\Component\HttpFoundation\Response
     *
     * @Route("/login/{login}/{password}"}
     */
    public function registerAction($login, $password, $phone)
    {
        $formatService = $this->get('server.format_service');
        $phone = $formatService->formatPhone($phone);


        return $this->render('');
    }
}
