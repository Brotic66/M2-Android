<?php

namespace ServerBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Demande
 *
 * @ORM\Table(name="demande")
 * @ORM\Entity(repositoryClass="ServerBundle\Repository\DemandeRepository")
 */
class Demande
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var User
     *
     * @ORM\OneToOne(targetEntity="User")
     */
    private $demandeur;

    /**
     * @var User
     *
     * @ORM\OneToOne(targetEntity="User")
     */
    private $demande;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @return User
     */
    public function getDemandeur()
    {
        return $this->demandeur;
    }

    /**
     * @param User $demandeur
     */
    public function setDemandeur($demandeur)
    {
        $this->demandeur = $demandeur;
    }

    /**
     * @return User
     */
    public function getDemande()
    {
        return $this->demande;
    }

    /**
     * @param User $demande
     */
    public function setDemande($demande)
    {
        $this->demande = $demande;
    }
}

