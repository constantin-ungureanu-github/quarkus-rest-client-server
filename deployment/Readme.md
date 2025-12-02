
```shell
podman compose up
```

- [Swagger Client API](http://localhost:8080/api/)
- [Swagger Server API](http://localhost:8083/api/)


## Test Client

```shell
curl -v --request POST http://localhost:8080/test \
-H "Content-Type:application/octet-stream" \
--data-binary "@zip_10MB.zip" --output local.zip
```


## Test Server

```shell
curl -v --request POST http://localhost:8083/test \
-H "Content-Type:application/octet-stream" \
--data-binary "@zip_10MB.zip" --output local.zip
```
