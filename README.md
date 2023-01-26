# testing2reverse
Welcome to my security testing project.

Now we have some bugs that we are trying to fix.
Native classes cannot be openned on Users that has a name between spaces.

NOTES:
Glide is used on ContactAdapter.java
Retrofit is used on ContactRepository.java
Native function is called on ContactRepository.java to "encrypt" (only to + a number and * 31) and came back to hide the proccess.
RecyclerView is on the main Fragment
Permissions are added to manifest
Fragment - ViewModel architecture
