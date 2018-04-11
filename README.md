# Authoritative Name Server for HisCinema.com
Class `ResourceRecord` stores psuedo resource records.  
By default, contains the following Resource Records:
- (video.hiscinema.com, herCDN.com, R)
- (herCDN.com, www.herCDN.com, CN)
- (www.herCDN.com, _IP of www.herCDN.com_, A)
```
javac HisCinemaNS.java
java HisCinemaNS
```
