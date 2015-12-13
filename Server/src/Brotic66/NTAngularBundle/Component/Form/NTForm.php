<?php
/**
 * Created by PhpStorm.
 * User: brice
 * Date: 10/12/15
 * Time: 15:33
 */

namespace Brotic66\NTAngularBundle\Component\Form;


class NTForm
{
    private $elements;

    public function __construct($elements)
    {
        $this->elements = $elements;
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

    public function createView()
    {
        return new NTFormView($this->elements);
    }


}