# ShareIt
Back-end of service for belongings share

## Synopsis
Someone has something that he is willing to let others use. He publishes a description of this subject. Other users can send a request for booking item.

## Features
- Item search by keyword
- Booking request may be approved by item owner or rejected by him
- After end of the item booking, the user can leave a comment about this item
- Main servis runs behind gateway

## Start with Docker
### Requirements
- JDK 11
- Apache Maven
- Docker
- Docker Compose

In project directory

```mvn package```

```docker-compose up```
