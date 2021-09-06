# gktechtest
A small technical proficiency assignment

I used a different take on MVP architecture for this test assignment. 
Previously in some projects I became overburdened with the use of Live Data and was looking for a different way to cleanly implement messaging between presenter and the view.

What resulted was a lifecycle aware presenter that persisted itself through configuration changes.
No LiveData anywhere. 

Standard use of repository pattern and Retrofit and coroutines to aid in implicit api response models.
I used libraries where applicable because reinventing the wheel is often a fools errand. 

Swipe to refresh is on the main screen to refresh data

Maps api key can be added to local.properties
MAPS_API_KEY={key}

This is generally good practice. 
I used googles mapsplatform secrets plugin for obfuscate the key at compile time so it is cryprographically secure.
Additionaly, I would have liked to have kept the other keys and base urls in remote config but I dont have remote config available to me under the current firebase plan I have personally. 
Maps and Geocoder are technically free given the amount of use this app will see and I will be killing those keys shortly after submitting this project.

I use transformers when transitioning boundaries of concern. Namely api / presenter -> view to keep the ui layer and its data clean. I could have persisted this data in a ViewModel proper but it was not needed for this project. 
The repository pattern lends itself well to caching and further data transform too if it was so required. 

I also did something interesting with requesting the location, utilizing some experimental kotlin coroutines, mainly callbackFlow which allowed for a very convenient way to grab location data while in a suspend function.

I used Koin for DI as I am most familiar with it, but I could have used Dagger as well, or HILT as thats the new kid on the block. 

View Binding was also used.
