# Overview
Application to search for people with swapi.dev api.

# Architecture
Uses DIY Model-View-Intent (MVI)-like architecture (leveraging Coroutines + StateFlow). Repository to manage data.
Package by feature with high-level split for data, UI.

# TODO Future Enhancements
An in-exhaustive todo-list of enhancements to implementation, if more time is spent:

* Refine MVI architecture implementation
* Add side effect handling as part of MVI
* Implement data store/caching
* Use either manual or framework dependency injection
* Unit testing
* More animations in detail screen
* Handling infinite scrolling 
* Setup multiple modules to further separate data and ui logic 

