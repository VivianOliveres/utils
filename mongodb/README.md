# MongoDB
Useful informations about MongoDB

# RoboMongo / Robo3T
https://robomongo.org/download

# Use cases
## Start inside docker instance
```bash
docker run -d --net=host --name mongo-instance mongo
```
```bash
docker run -d --name mongo-instance -p 27017:27017 mongo
```
## Queries
### Update
```
db.getCollection('COLLECTION_NAME').update(
    { day: "2019-08-02"},
    { $set: {day: "2019-08-09"}},
    {upsert: true, multi: true}
)
```
### Delete
Delete document
```
db.getCollection('COLLECTION_NAME').remove(
    {day: "2019-08-02"}
)
```
Delete field inside document
```
db.getCollection('COLLECTION_NAME').update(
    {day: "2019-08-02"}, 
    {$unset: {"day": ""}}, 
    { multi: true })
```

### Select
Basic
```
db.getCollection('COLLECTION_NAME').find(
    {day: "2019-08-02"}
)
```
Basic with projection (no **_id** but only **label**)
```
db.getCollection('COLLECTION_NAME').find(
    {day: "2019-08-02"},
    {_id: 0, label: 1}
)
```
https://docs.mongodb.com/manual/reference/operator/query/#query-selectors

**exists** matcher
```
db.getCollection('COLLECTION_NAME').find(
    {day:  { $exists: true} }}
)
```
**GreaterThan** matcher
```
db.getCollection('COLLECTION_NAME').find(
    {day:  { $gt: new Date('1950-01-01') }}
)
```
**In** matcher
```
db.getCollection('COLLECTION_NAME').find(
    {label:  { $in: [ "red", "blue" ] }}
)
```

## MongoDump / MongoRestore
### From target to localhost
Database and collection
```bash
mongodump -h TARGET_IP --db DB_NAME -c COLLECTION_NAME --archive | mongorestore --archive
```
Database and all collections
```bash
mongodump -h TARGET_IP --db DB_NAME --archive | mongorestore --archive
```
### To local file
Dump
```bash
mongodump --db DB_NAME
```
Restore
```bash
mongorestore --db DB_NAME -c COLLECTION_NAME --drop dump/DB_NAME/COLLECTION_NAME.bson
```
