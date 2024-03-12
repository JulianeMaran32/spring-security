# Method Level Security

As of now we have applied authorization rules on the API paths/URLs using spring security but method level security
allows to apply the authorization rules at any layer of an application like in service layer or repository layer etc.
Method level security can be enabled using the annotation `@EnableMethodSecurity` on the configuration class.

