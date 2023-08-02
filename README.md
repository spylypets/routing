# routing
Routing service provides a possibility to find a route between two countries as a list of contry borders (as 3-letter abbreviations) we need to cross to get to the destination.

HOW TO RUN THE SOURCE CODE:

    Clone the repository (git clone https://github.com/spylypets/routing.git) or download the repository zip and unpack it to a directory of your choice;

    Provided having Maven installed in your computer, run "mvn spring-boot:run" in the routing directory;
    
    Then you can access the application at http://localhost:8080 like:

    curl  http://127.0.0.1:8080/routing/CZE/ITA
    
    ["CZE","AUT","ITA"]

    To build the project, run "mvn clean package" in the routing directory. Then you can run the application from the target directory like:

    java -jar country-routing-0.0.1-SNAPSHOT.jar

    To build the Docker image, run mvn clean spring-boot:build-image in the routing directory
    
