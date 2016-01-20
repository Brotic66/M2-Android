<?php
/**
 * @author Brice VICO
 * @date 20/01/2016
 * @version 1.0.0
 */

namespace ServerBundle\Services;


class GCMService
{
    const URL = 'https://gcm-http.googleapis.com/gcm/send';

    /**
     * @var string
     */
    private $gcmApiKey;

    public function __construct($gcmApiKey)
    {
        $this->gcmApiKey = $gcmApiKey;
    }

    public function send($data, $to)
    {
        $post = array(
            'data' => $data,
            'to' => $to
        );

        $headers = array(
            'Authorization: key=' . $this->gcmApiKey,
            'Content-Type: application/json'
        );

        // Initialize curl handle
        $ch = curl_init();

        // Set URL to GCM endpoint
        curl_setopt( $ch, CURLOPT_URL, GCMService::URL);

        // Set request method to POST
        curl_setopt( $ch, CURLOPT_POST, true );

        // Set our custom headers
        curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers );

        // Get the response back as string instead of printing it
        curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );

        // Set JSON post data
        curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $post ) );

        // Actually send the push
        $result = curl_exec( $ch );

        // Error handling
        if ( curl_errno( $ch ) )
        {
            echo 'GCM error: ' . curl_error( $ch );
        }

        // Close curl handle
        curl_close( $ch );

        // Debug GCM response
       // echo $result;
    }
}