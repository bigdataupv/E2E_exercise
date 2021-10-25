# Exercise 1: Data Ingestion

In this exercise we will learn to ingest data in real-time into the architecture so, at the end, we will have data prices flowing in real-time through NiFi to Kafka.

## Data source

The data source we will be using for this exercise is [**IEX Cloud**](https://iexcloud.io/), where they have a full API, including the delivery of real-time data.

For a reference of the API follow the below link:

* https://iexcloud.io/docs/api/

## Development

### Setup

Start the NiFi service:

```
docker-compose start nifi
```

Once it is running, go to http://localhost:8090/nifi

### Part 1: Getting company prices from IEX Cloud

In this part we will be getting data in real-time from IEX Cloud, and storing locally for testing purposes.

So that, we have first to create an account in [**IEX Cloud**](https://iexcloud.io/). Then you will have your own tokens. It is recommended to use the sandbox tokens, where we have unlimited free credits, at least until we have the whole arquitecture working properly. In order to switch to sandbox view, we have to click on the "Sandbox View" button on the left panel at IEX Cloud home page. Then, we have to go to API Tokens, where we could find our **publisable* token.

First **load the template** with the Coinbase workflow:

* Stop (or even remove) previous processors
* Load template
  * Upload Template `iexcloud-basic.xml`
    * Right click on canvas and "Upload template"
  * Add template to the canvas
    * Top menu --> Drag & Drop "Template" --> Select the uploaded template

Now we need to **configure the API Call** as explained below:

* Risht click on InvokeHTTP component
  * Click on "Configure" --> "Properties"
  * Now configure the Remote URl, remember to use your personal token value: 
    * example: https://sandbox.iexapis.com/stable/stock/twtr/quote?token={your_publisable_token}&filter=companyName,latestPrice
  * Save and enable the services

Now the workflow is configured and should not appear any "warning sign":

If everything is ok, **run it**:

* Right-click on the canvas and "Start"

Check the processors to confirm nothing is failing and, if you want to **check the results** (files saved), they will be available in the NiFi Docker container (if using Docker). Do the following to check it:

```
docker ps
docker exec -it <nifi_container_id> /bin/bash
ls -l <folder_configured_in_PutFile_Processor>
```

### Part 3: Send to Kafka

Now, your turn. You will have to update the workflow so it sends the data to Kafka, instead of file system.

First of all, **start the Kafka services**:

```
docker-compose start zookeeper broker control-center
```

Once it is running, go to Control Center (http://localhost:9021/) and navigate to the topics section (click on the cluster and then on "Topics"). There should be no topics and/or messages.

Now change the NiFi workflow to send messages to Kafka:

* **TIP**: Replace the **PutFile** processor for **PublishKafka_2_6**.

Once done and, if everything is working, go back to Control Center and check that the topic is created and the messages flowing.

# Reference

* [Apache NiFi documentation](https://nifi.apache.org/docs.html)
* [IEX Cloud API](https://iexcloud.io/docs/api/)