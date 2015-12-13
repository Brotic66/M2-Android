<?php
/**
 * @author Brice VICO
 * @date 10/12/2015
 * @version 1.0.0
 */

namespace Brotic66\NTAngularBundle\Component\Form;

//use Symfony\Component\Form\FormBuilder;

use Brotic66\NTAngularBundle\Component\Form\NTForm;

class NTFormBuilder // extends FormBuilder
{
    private $elements;
    private $obj;
    private $formControl;

    public function __construct($obj)
    {
        $this->obj = $obj;
        $this->formControl = new FormControl($this->obj);
    }

    public function add($elem, $type = 'text', $params = array())
    {
        $this->elements[$elem]['name'] = $elem;
        $this->elements[$elem]['type'] = $type;
        $this->elements[$elem]['label'] = $elem;
        $this->elements[$elem]['value'] = $this->formControl->getValue($elem);

        foreach ($params as $key => $param) {
                $this->elements[$elem][$key] = $param;
        }

        return $this;
    }

    public function getForm()
    {
        return new NTForm($this->elements);
    }
}