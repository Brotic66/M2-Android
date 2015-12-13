<?php
/**
 * Created by PhpStorm.
 * User: brice
 * Date: 10/12/15
 * Time: 22:28
 */

namespace Brotic66\NTAngularBundle\Component\Form;


use Brotic66\NTAngularBundle\Component\Exceptions\NTNoElemExistException;
use Brotic66\NTAngularBundle\Component\Exceptions\NTNoGetterException;

class FormControl
{
    private $obj;
    private $reflect;

    public function __construct($obj)
    {
        $this->obj = $obj;
        $this->reflect = new \ReflectionClass(get_class($this->obj));
    }

    public function getValue($elem)
    {
        if ($this->reflect->hasProperty($elem)) {
            $property = $this->reflect->getProperty($elem);

            if ($property->isPrivate() || $property->isProtected()) {
                $getter = 'get' . ucfirst($property->getName());
                if ($this->reflect->hasMethod($getter)) {
                    return $this->obj->$getter();
                }
                else
                    throw new NTNoGetterException();
            } else {
                return $property->getValue($this->obj);
            }
        } else {
            throw new NTNoElemExistException();
        }
    }
}