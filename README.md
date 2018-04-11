# Authoritative Name Server ns.HisCinema.com
Class `ResourceRecord` stores psuedo resource record objects.  
By default, `ns.hiscinema.com` contains the following Resource Records:
- (video.hiscinema.com, herCDN.com, R) *Redirect for herCDN content network*

## Set up
The following variable should be set:
- Port for name-server/machine running *ns.hiscinema.com*
Set this line accordingly:
```
public static final int HIS_CINEMA_NS_LISTENING_PORT = xxxxx;
```
## Run
To run
```
javac HisCinemaNS.java
java HisCinemaNS
```
