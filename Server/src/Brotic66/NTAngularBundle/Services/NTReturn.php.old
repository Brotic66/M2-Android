<?php
/**
 * Created by PhpStorm.
 * User: brice
 * Date: 09/12/15
 * Time: 15:53
 */

namespace Brotic66\NTAngularBundle\Services;


use Brotic66\NTAngularBundle\Services\Exceptions\MethodNotExistException;
use Exception;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

final class NTReturn
{
    private $response;

    /**
     * AngularReturn constructor.
     */
    public function __construct()
    {
        $this->response = new Response();
        $this->response->headers->add(array('Access-Control-Allow-Headers' => 'Content-Type', 'Access-Control-Allow-Origin' => '*', 'Content-Type' => 'Application/json'));
    }

    /**
     * @param array $params
     * @return Response
     */
    public function send(array $params)
    {
        $toSend = $this->prepare($params);

        $this->response->setContent(json_encode($toSend));

        return $this->response;
    }

    private function prepare(array $params)
    {
        $toRtn = array();
        foreach ($params as $name => $value) {
            if (is_object($value)) {
                $toRtn[$name] = $this->obj2Array($value);
            } elseif (is_array($value)) {
                $toRtn[$name] = $this->prepare($value);
            } else {
                $toRtn[$name] = $value;
            }
        }
        return $toRtn;
    }

    private function obj2Array($obj)
    {
        $toRtn = array();

        $reflect = new \ReflectionClass(get_class($obj));
        $reflect->getProperties();
        foreach ($reflect->getProperties() as $property) {
            $result = null;
            if ($property->isPrivate() || $property->isProtected()) {
                $getter = 'get' . ucfirst($property->getName());

                if ($reflect->hasMethod($getter))
                    $result = $obj->$getter();
            } else {
                $result = $property->getValue($obj);
            }

                if (is_object($result))
                    $toRtn[$property->getName()] = $this->obj2Array($result);
                elseif (is_array($result))
                    $toRtn[$property->getName()] = $this->prepare($result);
                else
                    $toRtn[$property->getName()] = $result;
        }

        return $toRtn;
    }
}