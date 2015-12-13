<?php
/**
 * Created by PhpStorm.
 * User: brice
 * Date: 10/12/15
 * Time: 15:47
 */

namespace Brotic66\NTAngularBundle\Services;


use Brotic66\NTAngularBundle\Component\Form\NTFormBuilder;

class NTFormFactory
{
    public function create($obj)
    {
        return new NTFormBuilder($obj);
    }
}