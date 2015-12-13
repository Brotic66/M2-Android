<?php
/**
 * Created by PhpStorm.
 * User: brice
 * Date: 10/12/15
 * Time: 15:43
 */

namespace Brotic66\NTAngularBundle\Component\Form;


class NTFormView
{
    private $elements;

    public function __construct(array $elements)
    {
        $this->elements = $elements;

        /*$this->elements = array();

        $i = 0;
        foreach ($elements as $name => $type) {
            $this->elements[$name]['name'] = $name;
            $this->elements[$name]['type'] = $type;
            $i++;
        }*/
    }

    /**
     * @return mixed
     */
    public function getElements()
    {
        return $this->elements;
    }

    /**
     * @param mixed $elements
     */
    public function setElements($elements)
    {
        $this->elements = $elements;
    }
}