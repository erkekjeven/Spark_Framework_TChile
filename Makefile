include .env

build: ## Build image
	docker-compose build

up: ## Containers up!
	docker-compose up -d --remove-orphans

stop: ## Containers down!
	docker-compose stop

ps: ## Containers status
	docker-compose ps

logs: ## Show logs
	docker-compose logs -f

shell: ## Execute a shell into the container
	@docker exec -ti spark-sbt_1 /bin/bash

deploy: ## Deploy all necessary files to run the application on the cluster
	cp ./target/scala-2.11/spark-project_2.11-1.0.jar scripts
	cp -r ./lib scripts
	rsync -v -r ./scripts/ $(USER)@$(HOSTNAME):$(REMOTE_PATH)/[PROJECT_PATH]

### FOR WINDOWS
deploy-windows: ## Deploy all necessary files to run the application on the cluster
	powershell copy-item ./target/scala-2.11/spark-project_2.11-1.0.jar scripts -force
	powershell copy-item ./lib -Destination scripts -Recurse -force
	powershell scp -r ./scripts/* $(USER)"@"$(HOSTNAME)":"$(REMOTE_PATH)/[PROJECT_PATH]
