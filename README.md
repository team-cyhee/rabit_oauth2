# rabit_oauth2
simple oauth2

#### To get new token with password grant type
`curl $client_id:$client_secret@localhost:8081/oauth/token -d "grant_type=password&username=$user&password=$password"`

#### To get new token with credential client grant type
`curl $client_id:$client_secret@localhost:8081/oauth/token -d "grant_type=client_credentials"`
