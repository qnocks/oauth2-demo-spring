# OAuth demo

## Servers

There are multiple servers when using the OAuth2 protocol:

1. Authorization server *(authorization-server)*

The only one, which stores the credentials. It's the one dedicated to validating the username and password of the end user

2. Resources server *(resource-server)*

This is the one, which contains the protected data. But this protected data won't be requested directly by the end user

3. Client server *(client-server)*

This is the server, which will request the protected data. This is also the server used by the end user

## OAuth 2.0 Workflow

Let's say I'm regular user of **a site "I brag about my vacations"** *(client-server)*. 
And I want to upload some of my pictures to the site. The pictures stored in my personal email

So client, which is the site "I brag about my vacations" will try to access the resources, which are the pictures located on a separate server
To access those pictures, the client will need my **authorization**.

To do so the client will delegate the authentication to the authorization server. Now the client talking directly to the authorization server,
it will ask me for my credentials and ask if I **consent** the client "I brag about my vacations" to access the protected pictures. This way the site "I brag about vacations" doesn't need to store my credentials. It delegates all of this to the authorization server. 

Nevertheless, the communication between the client and the authorization server in only possible if the client is already known by the authorization server.
The client must previously obtain a **clientId** and **clientSecret** from the authorization server.
Otherwise, the authorization server won't trust it

Now, the client "I brag about my vacations" has the consent to access the protected pictures.
Nevertheless, consent is only given for a specific **scope**.
So the client can access the resources within this given scope.

**Let's reformulate the workflow**:

I consent to the client to access the protected resources on my behalf. And the client doesn't need my credentials.
For that, only authorization server handles my credentials.

## OAuth 2.0 vs OpenID

OpenID Connect, or OIDC, is just another layer on top of the OAuth2 protocol.
Let's see what changes. The components are exactly the same. We still have the client, the resources and the authorization server.

When the client asks for the authorization, it won't ask for a specific scope but for OpenID connect scope.
With the OAuth2 protocol, the client asks for the scopes like "read pictures", "edit pictures", "read profile".
With the OIDC, the client asks for the **"openid" scope**.

This won't return a simple code as before for a single usage.
Instead, the authorization server will respond with a rich full **JWT** with a lot of claims. 
Those claims are endpoints and encryption information to facilitate new requests from the client. 

If the client needs another code to access the pictures again, it already hast the necessary endpoints to get a new generated code to be used against the resource server.
No need to ask for the credentials.

## Creating backends

1. authorization-server

    Dependencies:
   * security
   * web
   * jpa
   * postgres
   * lombok 

       added manually:

   * oauth2-authorization-server
   * map-struct

2. resource-server

    Dependencies:
   * security
   * web
   * oauth2-resource-server

3. client-server

    Dependencies:
   * security
   * web
   * oauth2-client
   * reactive web
 






