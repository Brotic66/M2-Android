<?php
/**
 * @author Brice VICO
 * @date 10/12/2015
 * @version 1.0.0
 *
 * Base Controller for NTAngularBundle
 */

namespace Brotic66\NTAngularBundle\Controller;


use Brotic66\NTAngularBundle\Component\Form\NTFormBuilder;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class NTAngularController extends Controller
{
    public function NTRender(array $params)
    {
        return $this->get('brotic66_nt_angular.return')
            ->send($params);
    }

    public function NTCreateFormBuilder($obj)
    {
        return $this->get('brotic66_nt_angular.form_factory')
            ->create($obj);
    }
}