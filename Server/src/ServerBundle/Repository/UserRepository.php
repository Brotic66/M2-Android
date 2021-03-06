<?php

namespace ServerBundle\Repository;

/**
 * UserRepository
 *
 * This class was generated by the Doctrine ORM. Add your own custom
 * repository methods below.
 */
class UserRepository extends \Doctrine\ORM\EntityRepository
{
    public function getUserNotNull()
    {
        $qb = $this->createQueryBuilder('a');

        $qb
            ->where(
            $qb->expr()->isNotNull('a.latitude')
        )
            ->andWhere(
                $qb->expr()->isNotNull('a.longitude')
            );

        return $qb->getQuery()->getResult();
    }
}
