

**After inserting the products data into the table we have to execute this SQL query**

UPDATE `products` SET `created_on` = NOW( ) ,
`currency` = "USD",
`formatted_list_price` = "$5",
`formatted_sale_price` = "$5",
`last_updated_on` = NOW( ) ,
`list_price` = 5.0,
`sale_price` = 5.0


**Build and push docker images**

You can build an image with the above configurations by running this command.

`mvn clean package docker:build`

To push the image you just built to the registry, specify the pushImage flag.

`mvn clean package docker:build -DpushImage`

To push only specific tags of the image to the registry, specify the pushImageTag flag.

`mvn clean package docker:build -DpushImageTag`

Tags-to-be-pushed can also be specified directly on the command line with

`mvn ... docker:build -DpushImageTags -DdockerImageTag=latest -DdockerImageTag=another-tag`

Reference URL: https://github.com/spotify/docker-maven-plugin

**To resolve the docker host connection refused problem**

Run the command

`docker-machine env`

It will show you necessary docker machine env variable, you have to set those veriable to your path. Run this command for that

`eval $(docker-machine env)`

It will set necessary env variables on your system path variable