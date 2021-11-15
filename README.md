# About project

Storage-ms is a REST based service to get info about user's files from Azure's One Drive using Graph API.

# REST API
### List of available endpoints
```
GET
/api/azure/user/file
/api/azure/user/file/path?searchPath
```
Example response for both endpoints:
```
{
   "userFilesList":[
      {
         "id":"1111",
         "name":"file_name.txt",
         "modifiedDateTime":"2021-11-10T20:24:09",
         "directoryFiles":[],
         "directory":false
      },
      {
         "id":"2222",
         "name":"dir",
         "modifiedDateTime":"2021-11-11T19:43:37",
         "directoryFiles":[
            {
               "id":"3333",
               "name":"test.txt",
               "modifiedDateTime":"2021-11-11T20:05:17",
               "directoryFiles":[],
               "directory":false
            }
         ],
         "directory":true
      }
   ],
   "userId":"9999",
   "searchPath":"/"
}
```
# Configuration
Configuration contains azure project properties. 

To use Graph API you have to create Azure project and configure oauth2 authorization. This project uses
oauth2 with grant_type: client_credentials.

To learn about oauth2 client_credentials flow check out docs:

https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-client-creds-grant-flow

For more info about Graph API check out docs:

https://docs.microsoft.com/en-us/graph/

```
azure:  
  app:  
    clientId:  
    clientSecret:   
    tenantId:  
    userId: 
    searchPath: 
```

# USAGE

### BUILD
Firstly build project with gradle to create .jar file:
```
gradle bootJar
```
To run application go to /build/libs and run java command:
```
java -jar storage-ms-{version}.jar
```
Replace {version} with current project version.
### Docker
Project contains docker files to create image and container.

Build docker image from Dockerfile with command:
```
docker build -t storage-ms .
```
Then create and run container from docker-compose with commands:
```
docker-compose up
```
