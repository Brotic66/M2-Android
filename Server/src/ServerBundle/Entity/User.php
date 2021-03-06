<?php

namespace ServerBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;

/**
 * User
 *
 * @ORM\Table(name="user")
 * @ORM\Entity(repositoryClass="ServerBundle\Repository\UserRepository")
 */
class User
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
     * @var string
     *
     * @ORM\Column(name="username", type="string", length=255, unique=true)
     */
    private $username;

    /**
     * @var string
     *
     * @ORM\Column(name="password", type="string", length=255)
     */
    private $password;

    /**
     * @var string
     *
     * @ORM\Column(name="phoneNumber", type="string", length=255)
     */
    private $phoneNumber;

    /**
     * @var array
     *
     * @ORM\Column(name="tokens", type="array")
     */
    private $tokens;

    /**
     * @var string
     *
     * @ORM\Column(name="profilPicture_url", type="string", length=1024, nullable=true)
     */
    private $profilPicture;

    /**
     * @ORM\ManyToMany(targetEntity="User", mappedBy="friends")
     */
    private $friendsWithMe;

    /**
     * @var ArrayCollection
     *
     * @ORM\ManyToMany(targetEntity="User", inversedBy="friendsWithMe")
     * @ORM\JoinTable(name="users_friends",
     *      joinColumns={@ORM\JoinColumn(name="friend1_id", referencedColumnName="id")},
     *      inverseJoinColumns={@ORM\JoinColumn(name="friend2_id", referencedColumnName="id")}
     *      )
     */
    private $friends;

    /**
     * @var string
     *
     * @ORM\Column(name="latitude", type="string", length=255, nullable=true)
     */
    private $latitude;

    /**
     * @var string
     *
     * @ORM\Column(name="longitude", type="string", length=255, nullable=true)
     */
    private $longitude;

    /**
     * @var string
     *
     * @ORM\Column(name="gcm_token", type="string", length=1024, nullable=true)
     */
    private $gcmToken;


    public function __construct()
    {
        $this->friends = new ArrayCollection();
        $this->friendsWithMe = new ArrayCollection();
    }

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
     * Set username
     *
     * @param string $username
     *
     * @return User
     */
    public function setUsername($username)
    {
        $this->username = $username;

        return $this;
    }

    /**
     * Get username
     *
     * @return string
     */
    public function getUsername()
    {
        return $this->username;
    }

    /**
     * Set password
     *
     * @param string $password
     *
     * @return User
     */
    public function setPassword($password)
    {
        $this->password = $password;

        return $this;
    }

    /**
     * Get password
     *
     * @return string
     */
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * Set phoneNumber
     *
     * @param string $phoneNumber
     *
     * @return User
     */
    public function setPhoneNumber($phoneNumber)
    {
        $this->phoneNumber = $phoneNumber;

        return $this;
    }

    /**
     * Get phoneNumber
     *
     * @return string
     */
    public function getPhoneNumber()
    {
        return $this->phoneNumber;
    }

    /**
     * @return array
     */
    public function getTokens()
    {
        return $this->tokens;
    }

    /**
     * @param array $token
     */
    public function setTokens($token)
    {
        $this->tokens = $token;
    }

    public function addToken($token)
    {
        $this->tokens[] = $token;
    }

    public function removeToken($token)
    {
        unset($this->tokens[array_search($token, $this->tokens)]);
    }

    /**
     * @return string
     */
    public function getProfilPicture()
    {
        return $this->profilPicture;
    }

    /**
     * @param string $profilPicture
     */
    public function setProfilPicture($profilPicture)
    {
        $this->profilPicture = $profilPicture;
    }

    /**
     * @return ArrayCollection
     */
    public function getFriends()
    {
        return $this->friends;
    }

    /**
     * @param ArrayCollection $friends
     */
    public function setFriends($friends)
    {
        $this->friends = $friends;
    }

    public function addFriend(User $friend)
    {
        if (!$this->friends->contains($friend))
            $this->friends->add($friend);
    }

    public function removeFriend(User $friend)
    {
        if ($this->friends->contains($friend))
            $this->friends->removeElement($friend);
    }

    /**
     * @return string
     */
    public function getLatitude()
    {
        return $this->latitude;
    }

    /**
     * @param string $latitude
     */
    public function setLatitude($latitude)
    {
        $this->latitude = $latitude;
    }

    /**
     * @return string
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * @param string $longitude
     */
    public function setLongitude($longitude)
    {
        $this->longitude = $longitude;
    }

    /**
     * @return mixed
     */
    public function getFriendsWithMe()
    {
        return $this->friendsWithMe;
    }

    /**
     * @param mixed $friendsWithMe
     */
    public function setFriendsWithMe($friendsWithMe)
    {
        $this->friendsWithMe = $friendsWithMe;
    }

    public function addFriendWithMe(User $friend)
    {
        $this->friendsWithMe[] = $friend;
    }

    /**
     * @return string
     */
    public function getGcmToken()
    {
        return $this->gcmToken;
    }

    /**
     * @param string $gcmToken
     */
    public function setGcmToken($gcmToken)
    {
        $this->gcmToken = $gcmToken;
    }
}

