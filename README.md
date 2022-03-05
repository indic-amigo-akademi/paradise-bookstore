# Paradise Bibliothecca

## Installation

- Clone the repository from [GitHub](https://github.com/indic-amigo-akademi/paradise.git)

- For backend

  - Move into paradise/paradise_server
  
   `cd paradise_server`

  - Install dependencies

    `mvn clean install`

  - Run the server

    `mvn exec:java -Dexec.mainClass=com.iaa.paradise_server.ParadiseApplication`

  or

  - Install and run the server
    `mvn spring-boot:run`

- For frontend
  - Move into paradise/paradise-client

    `cd paradise-client`
  
  - Install dependencies

    `npm install`

  - Run the server

    `npm start`
