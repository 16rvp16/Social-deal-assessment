# Social Deal Assessment asignment
Rick van Pelt

Total amount of time spend: 20 hours

### 3th Party Libraries used:

- ##### Retrofit + Kotlinx Serialization
  I included Retrofit because it allows for a easy to use and clean method of specifiying API request without the need for writing a large amount of boilerplate code.
  I also added Kotlinx Serialisation for its JSON deserializer that works well with Retrofit. This Serialization library allowed me to easily specify and deserialize the JSON data retrieved from the API.
- ##### Coil + OKHTTP
  I included the combination of OKHTTP and Coil to download and display the images for the deals.
  
### Next Steps / Known Issues:

- ##### Add more Unit tests
  I have currently only added a single unit test to demonstrate how to test with fake data while still using repositories. This way the viewmodel and the views can be tested without requiring IO or being forced to make changes to these classes for testing.  
- ##### Create Repo for favorites
  Currently the favorites are only stored in the ViewModel. This ofcourse means that they will get reset everytime the app is reopened. With more time I would have liked to create a repo to persist the favorites (with Room). And I would have like to add a domain layer as well to the app so that the code that combines the deals-data with the favorites-data no longer has to reside in the Viewmodel.
- ##### Add large screen support
  At the moment the app is only build for phone screens. With more time I would have liked to adjust the screen based on the available width, so that the list and detail screen can be placed side by side when using tablet landscape mode. With the way the app is currently set up this should not be to big of a adjustment.
- ##### Keep track of the scroll position
  When you return from the detail screen, the list view scroll position is not restored to the where you were before entering the detail view. I would have liked to move this scroll position in to the ViewModel so that it is preserved.
