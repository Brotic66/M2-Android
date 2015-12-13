<?php
/**
 * Created by PhpStorm.
 * User: brice
 * Date: 09/12/15
 * Time: 16:26
 */

namespace Brotic66\NTAngularBundle\Services\Exceptions;


class MethodNotExistException extends \RuntimeException
{
    public function __toString()
    {
       return 'couocu';
    }

}