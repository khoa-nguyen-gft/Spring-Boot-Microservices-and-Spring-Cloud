


#The docker-compose will create 1 zookeeper, 3 kafka-brokers and 1 kafka manager. 
docker-compose -f kafka-compose.yml up
docker-compose -f redis-compose.yml up


#Once you have played with kafka cluster, you can bring entire cluster down by issuing below command
docker-compose down


#Kafka:
    #zookeeper
        localhost:2181
    #broker
        localhost:9091
        localhost:9092
        localhost:9093
    #kafka-manager
        localhost:9000
#Redis
    #Server:
        localhost:6379
    #redis-browser
        localhost:4567