# Hybrid Social Login AuthCode Library
This is a **Hybrid Social Login AuthCode Library**, which is
very useful when we want to **implement social login feature** 
using the **AuthCode grant type**.

### Current, supported Social Login Providers
* Google
* Facebook
* Linkedin
* Twitter
 
**Note: If you want us to add support for other social providers, 
please let us know here, [Request New Feature](https://github.com/udpnarola/social-login-authcode-library/issues).**

### The library is available on Maven Central
You can easily use this library in your project because we 
have deployed it on **Maven Central**, try now [Get Library from Maven Central](https://mvnrepository.com/artifact/com.github.udpnarola/social-login-authcode-library/1.1.0).

### How to Use this Library
As this a **hybrid auth-code grant type library**, so here you 
should get the **auth-code** from the **frontend** and then use 
that **code** to get the **user** from the **social provider via 
using this library**.

In the case of Twitter, you will get **oauthToken** and 
**oauthVerifier** from the frontend. Because Twitter uses 
the **oauth1** protocol.

**Note: In case you are looking for the 100% backend 
integration library, [Go Here](https://github.com/udpnarola/social-login-backend-integration-library)**.        

#### Prerequisites
You will need a **clientId**, **clientSecret**, and pre-configured 
**redirectUri**.

```java
// authCode which we got from the frontend
String authCode = "dummyAuthCode";

SocialLoginProvider socialProvider;
SocialUser socialUser; 

// get facebook user
socialProvider = new FacebookProvider(facebookclientId, facebookClientSecret, facebookRedirectUri);
socialUser = socialProvider.getUser(new SocialLoginDetail(authCode));

// get Linkedin user
socialProvider = new LinkedinProvider(linkedinClientId, linkedinClientSecret, linkedinRedirectUri);
socialUser = socialProvider.getUser(new SocialLoginDetail(authCode));

// get Google user
socialProvider = new GoogleProvider(googleClientId, googleClientSecret, googleRedirectUri);
socialUser = socialProvider.getUser(new SocialLoginDetail(authCode));


// oauthToken & oauthVerifier which we got from the frontend
String oauthToken = "dummyOauthToken";
String oauthVerifier = "dummyOauthVerifier";

// get Twitter user
socialProvider = new TwitterProvider(twitterClientId, twitterClientSecret);
socialUser = socialProvider.getUser(new SocialLoginDetail(oauthToken, oauthVerifier));
```

And we are done, that's all we have to do **:)**

### License 
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/udpnarola/social-login-authcode-library/blob/master/LICENSE) 

**The MIT License**