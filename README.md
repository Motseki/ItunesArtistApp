<img src="/images/previewgif.gif" width="200" align="left" hspace = "20">

# ItunesArtistApp

A sample app that consume the Itune Search API. This demo app only fetches MOVIES that matches the search keyword.

This app is using Model–view–viewmodel architecture because we wanted to follow the single responsibility principle, with this approach it makes the app more maintainable and easier to test since we can mock each part.

It also caches the results after consuming the API with that it makes the searching a lot efficient since we do not need to perform a similar request (save some bandwidth) plus the cached movies should be viewable offline except the Video playback.
