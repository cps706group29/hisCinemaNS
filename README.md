# Authoritative Name Server for HisCinema.com
Class `ResourceRecord` stores psuedo resource records.  
By default, contains the following Resource Records:
- (video.hiscinema.com, herCDN.com, R)
- (herCDN.com, www.herCDN.com, CN)
- (www.herCDN.com, *IP of www.herCDN.com*, A)  

The IP of *www.herCDN.com* should be adjusted appropriately.

```
javac HisCinemaNS.java
java HisCinemaNS
```
